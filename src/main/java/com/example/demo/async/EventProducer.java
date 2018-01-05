package com.example.demo.async;

import com.alibaba.fastjson.JSON;
import com.example.demo.util.Entityname;
import com.example.demo.util.JedisAdapter;
import com.example.demo.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {
    @Autowired
    JedisAdapter jedisAdapter;
    public boolean fireEvent(EventModel eventModel){
        try{
            String json= JSON.toJSONString(eventModel);
            String key= Entityname.getEventQueuekey();
            jedisAdapter.lpush(key,json);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
