package com.example.demo.util;

public class Entityname {
    private static final String DISLIKE="dislike";
    private static final String LIKE="like";
    private static final String QUEUE="queue";
    public static String LikeentityName(int entityType,int entityId){
        return String.format("%s_%d_%d",LIKE,entityType,entityId);
    }
    public static String DislikeentityName(int entityType,int entityId){
        return String.format("%s_%d_%d",DISLIKE,entityType,entityId);
    }
    public static String getEventQueuekey(){return QUEUE;}
}
