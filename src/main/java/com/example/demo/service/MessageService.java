package com.example.demo.service;

import com.example.demo.dao.MessageDAO;
import com.example.demo.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    MessageDAO messageDAO;
   public int addMessage(Message message){
       return messageDAO.addMessage(message);
   }
   public List<Message> selectMessageByConversationId(String conversationId){
       return messageDAO.selectCommentByConversationId(conversationId);
   }
   public List<Message> selectCommentByToId(String toId){
       return messageDAO.selectCommentByToId(toId);
   }
   public List<Message> selectMessageById(int id){
       return messageDAO.selectMessageById(id);
   }
   public int selectnotreadCount(int id,String conversation ){
       return messageDAO.selectnotreadCount(id,conversation);
   }
}