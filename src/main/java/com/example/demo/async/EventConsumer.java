package com.example.demo.async;

import com.alibaba.fastjson.JSON;
import com.example.demo.util.Entityname;
import com.example.demo.util.JedisAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventConsumer implements ApplicationContextAware,InitializingBean{
    private static final Logger logger= LoggerFactory.getLogger(EventConsumer .class);
    private static final Map<EventType,List<EventHandler>> config=new HashMap<EventType,List<EventHandler>>();
    private ApplicationContext applicationContext;
    @Autowired
    JedisAdapter jedisAdapter;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String,EventHandler> beans=applicationContext.getBeansOfType(EventHandler.class);
        if(beans!=null){
            for(Map.Entry<String,EventHandler> entry:beans.entrySet()){
                EventHandler eventHandler=entry.getValue();
                List<EventType> list=eventHandler.getHandleType();
                for(EventType type:list){
                    if(!config.containsKey(type)){
                        config.put(type,new ArrayList<EventHandler>());
                    }
                    config.get(type).add(eventHandler);
                }
            }
        }
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    String key= Entityname.getEventQueuekey();
                    List<String> eventModels=jedisAdapter.brpop(0,key);

                    for(String event:eventModels){
                        if(event.equals(key)){
                            continue;
                        }
                        EventModel model= JSON.parseObject(event,EventModel.class);
                        if(!config.containsKey(model.getEventType())){
                            logger.error("config不包含此事件类型");
                            continue;
                        }
                        List<EventHandler> handlers=config.get(model.getEventType());
                        for(EventHandler eventHandler:handlers){
                            eventHandler.handle(model);
                        }
                    }

                }
            }
        });
        thread.start();
    }
}
