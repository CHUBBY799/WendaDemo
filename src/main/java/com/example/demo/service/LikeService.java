package com.example.demo.service;

import com.example.demo.util.Entityname;
import com.example.demo.util.JedisAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class LikeService {

     @Autowired
    JedisAdapter jedisAdapter;
     public long addlikebyset(int entityType,int entityId,int value){
         String likekey= Entityname.LikeentityName(entityType,entityId);
         String dislikekey=Entityname.DislikeentityName(entityType,entityId);
        jedisAdapter.addLikebyset(likekey,String.valueOf(value));
        return jedisAdapter.deleteLikebyset(dislikekey,String.valueOf(value));

     }
     public long dellikebyset(int entityType,int entityId,int value){
         String likekey= Entityname.LikeentityName(entityType,entityId);
         String dislikekey=Entityname.DislikeentityName(entityType,entityId);
         jedisAdapter.deleteLikebyset(likekey,String.valueOf(value));
         return jedisAdapter.addLikebyset(dislikekey,String.valueOf(value));
     }
     public long countLikebyset(int entityType,int entityId){
         String likekey=Entityname.LikeentityName(entityType,entityId);
         return jedisAdapter.countLikebyset(likekey);
     }
    public boolean islikememberbyset(int entityType,int entityId,int value){
        String likekey=Entityname.LikeentityName(entityType,entityId);
        return jedisAdapter.ismemberbyset(likekey,String.valueOf(value));
    }
}
