package com.example.demo.async.handler;

import com.example.demo.async.EventHandler;
import com.example.demo.async.EventModel;
import com.example.demo.async.EventType;
import com.example.demo.service.UserService;
import com.example.demo.util.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LoginHandler implements EventHandler{
    @Autowired
    MailSender mailSender;
    @Autowired
    UserService userService;
    @Override
    public void handle(EventModel model) {
        Map<String,Object> model1=new HashMap<>();
        model1.put("username",model.getMap("username"));
        mailSender.sendWithHTMLTemplate(model.getMap("to"),"登陆异常","mail/loginexcepetion.html",model1);
    }

    @Override
    public List<EventType> getHandleType() {
        return Arrays.asList(EventType.LOGIN);
    }
}
