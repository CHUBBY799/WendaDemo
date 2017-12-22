package com.example.demo.model;

import org.springframework.stereotype.Component;

@Component
public class HostHolder {
    private static ThreadLocal<User> users=new ThreadLocal<>();
    public static User getUser(){
        return users.get();
    }
    public static  void setUser(User user){
        users.set(user);
    }
    public static void clear(){
        users.remove();
    }
}
