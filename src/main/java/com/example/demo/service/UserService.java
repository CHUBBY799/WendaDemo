package com.example.demo.service;

import com.example.demo.dao.LoginTicketDAO;
import com.example.demo.dao.UserDAO;
import com.example.demo.model.LoginTicket;
import com.example.demo.model.User;
import com.example.demo.util.WendaUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.krb5.internal.Ticket;


import java.util.*;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;
    @Autowired
    LoginTicketDAO loginTicketDAO;
    public User getUserByid(int id){return userDAO.selectById(id);}
    public Map<String,Object> regist(String name,String password){
        Map<String ,Object> map=new HashMap<>();
        if(StringUtils.isBlank(name)){
                    map.put("msg","用户名不能为空");
                    return map;
                }
        if(StringUtils.isBlank(password)){
            map.put("msg","密码不能为空");
            return map;
        }
        User user=userDAO.selectByName(name);
        if(user!=null){
            map.put("msg","用户名已被注册");
            return map;
        }
        //MD5
        user=new User();
        user.setName(name);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        String head = String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000));
        user.setHeadUrl(head);
        user.setPassword(WendaUtil.MD5(password+user.getSalt()));
        userDAO.addUser(user);
        //ticket
        map.put("ticket",addLoginTicket(user.getId()));

        return map;



    }
    public Map<String,Object> login(String username,String password){
        Map<String ,Object> map=new HashMap<>();
        if(StringUtils.isBlank(username)){
            map.put("msg","用户名不能为空");
            return map;
        }
        if(StringUtils.isBlank(password)){
            map.put("msg","密码不能为空");
            return map;
        }
        User user=userDAO.selectByName(username);
        if(user==null){
            map.put("msg","用户名不存在");
            return map;
        }
        if(!WendaUtil.MD5(password+user.getSalt()).equals(user.getPassword())){
            map.put("msg","密码错误");
            return map;
        }
        map.put("ticket",addLoginTicket(user.getId()));
        return map;
    }
    public String addLoginTicket(int userId){
        LoginTicket loginTicket=new LoginTicket();
        loginTicket.setUserId(userId);
        Date date=new Date();
        date.setTime(date.getTime()+1000*3600*24);
        loginTicket.setExpired(date);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll(" ","-"));
        loginTicketDAO.addTicket(loginTicket);
        return loginTicket.getTicket();

    }
    public void logoutTicket(String ticket){
        LoginTicket loginTicket=loginTicketDAO.selectByTicket(ticket);
        loginTicket.setStatus(1);
        loginTicketDAO.updateStatus(loginTicket);
    }
}
