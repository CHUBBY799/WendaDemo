package com.example.demo.async.handler;

import com.example.demo.async.EventHandler;
import com.example.demo.async.EventModel;
import com.example.demo.async.EventType;
import com.example.demo.model.Message;
import com.example.demo.service.MessageService;
import com.example.demo.service.UserService;
import com.example.demo.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class LikeHandler implements EventHandler{
    @Autowired
    MessageService messageService;
    @Autowired
    UserService userService;

    @Override
    public void handle(EventModel model) {
        Message message=new Message();
        message.setFromId(WendaUtil.SYSTEM_ID);
        message.setToId(model.getOwnId());
        message.setCreatedDate(new Date());
        message.setHasRead(1);
        message.setContent(String.format("%s用户给你点赞啦",userService.getUserByid(model.getActionId()).getName()));
        messageService.addMessage(message);

    }

    @Override
    public List<EventType> getHandleType() {

        return Arrays.asList(EventType.LIKE);
    }
}
