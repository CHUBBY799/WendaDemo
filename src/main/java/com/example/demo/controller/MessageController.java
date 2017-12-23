package com.example.demo.controller;

import com.example.demo.dao.UserDAO;
import com.example.demo.model.HostHolder;
import com.example.demo.model.Message;
import com.example.demo.model.User;
import com.example.demo.model.ViewObject;
import com.example.demo.service.MessageService;
import com.example.demo.service.SensitiveService;
import com.example.demo.service.UserService;
import com.example.demo.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MessageController {
    private static final Logger logger= LoggerFactory.getLogger(MessageController.class);
    @Autowired
    UserDAO userDAO;
    @Autowired
    MessageService messageService;
    @Autowired
    SensitiveService sensitiveService;
    @RequestMapping(path={"/msg/addMessage"},method = RequestMethod.POST)
    @ResponseBody
    public String addMessage(@RequestParam("toName") String toName,
                             @RequestParam("content") String content){
        try{
            if(HostHolder.getUser()==null){
                return WendaUtil.getJSONString(999,"未登录");
            }
            if(userDAO.selectByName(toName)==null){
                return WendaUtil.getJSONString(1,"用户不存在");
            }
            Message message=new Message();
            message.setContent(sensitiveService.filter(HtmlUtils.htmlEscape(content)));
            message.setCreatedDate(new Date());
            message.setFromId(HostHolder.getUser().getId());
            message.setHasRead(1);
            message.setToId(userDAO.selectByName(toName).getId());
            messageService.addMessage(message);
            return WendaUtil.getJSONString(0);

        }catch (Exception e){
            logger.error("添加消息失败",e.getMessage());
            return WendaUtil.getJSONString(1,"添加消息失败");
        }
    }
    @RequestMapping(path = {"/msg/list"},method = RequestMethod.GET)
    public String getMsglist(Model model){
         try{
               List<Message> list=messageService.selectMessageById(HostHolder.getUser().getId());
               List<ViewObject> vos=new ArrayList<>();
               for(Message message:list){
                   ViewObject vo=new ViewObject();
                   vo.set("message",message);
                   vo.set("user",userDAO.selectById(message.getFromId()));
                   vo.set("count",messageService.selectnotreadCount(HostHolder.getUser().getId(),message.getConversationId()));
                   vos.add(vo);
               }
               model.addAttribute("vos",vos);
         }catch (Exception e){
             logger.error("服务器错误",e.getMessage());
         }
        return "letter";
    }
    @RequestMapping(path = {"/msg/detail"},method = RequestMethod.GET)
    public String getMsgdetail(Model model,@RequestParam("conversationId") String conversationId){
        try {
            List<Message> list = messageService.selectMessageByConversationId(conversationId);
            List<ViewObject> vos=new ArrayList<>();
            for(Message message:list){
                User user=userDAO.selectById(message.getFromId());
                ViewObject vo=new ViewObject();
                vo.set("message",message);
                vo.set("user",user);
                vos.add(vo);

            }
            model.addAttribute("vos",vos);
        }catch (Exception e){
            logger.error("服务器错误",e.getMessage());

        }


        return "letterDetail";


    }
}
