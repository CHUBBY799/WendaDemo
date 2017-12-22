package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class MessageService {
    public String print(int id){
        return "id is:"+String.valueOf(id);
    }
}
