package com.example.demo.util;

public class Entityname {
    private static final String DISLIKE="dislike";
    private static final String LIKE="like";
    private static final String QUEUE="queue";
    private static final String FOLLOW="follow";
    private static final String FANS="fans";

    public static String LikeentityName(int entityType,int entityId){
        return String.format("%s_%d_%d",LIKE,entityType,entityId);
    }
    public static String DislikeentityName(int entityType,int entityId){
        return String.format("%s_%d_%d",DISLIKE,entityType,entityId);
    }
    public static String getEventQueuekey(){return QUEUE;}
    //entityType为关注实体的类型
    public static String getFollowkey(int userId,int entityType){
        return String.format("%s_%d_%d",FOLLOW, userId,entityType);
    }
    //entityType为自己的类型
    public static String getFanskey(int entityType,int entityId){
        return String.format("%s_%d_%d",FANS,entityType,entityId);
    }
}
