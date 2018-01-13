package com.example.demo.service;

import com.example.demo.util.Entityname;
import com.example.demo.util.JedisAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class FollowService {
    private static final Logger log= LoggerFactory.getLogger(FollowService.class);
    @Autowired
    JedisAdapter jedisAdapter;
    public boolean follow(int userId,int EntityType,int EntityId){
        try {
            String Followkey = Entityname.getFollowkey(userId, EntityType);
            String Fanskey = Entityname.getFanskey(EntityType, EntityId);
            Jedis jedis = jedisAdapter.getJedis();
            Transaction tx = jedisAdapter.multi(jedis);
            tx.zadd(Followkey, (new Date()).getTime(), String.valueOf(EntityId));
            tx.zadd(Fanskey, (new Date()).getTime(), String.valueOf(userId));
            List<Object> list=jedisAdapter.exec(tx, jedis);
            return list.size()==2&&(long)list.get(0)>0&&(long)list.get(1)>0;
        }catch (Exception e){
            log.error("关注服务失败");
            return false;
        }

    }
    public boolean disfollow(int userId,int EntityType,int EntityId){
        try {
            String Followkey = Entityname.getFollowkey(userId, EntityType);
            String Fanskey = Entityname.getFanskey(EntityType, EntityId);
            Jedis jedis = jedisAdapter.getJedis();
            Transaction tx = jedisAdapter.multi(jedis);
            tx.zrem(Followkey,String.valueOf(EntityId));
            tx.zrem(Fanskey,  String.valueOf(userId));
            List<Object> list=jedisAdapter.exec(tx, jedis);
            return list.size()==2&&(long)list.get(0)>0&&(long)list.get(1)>0;
        }catch (Exception e){
            log.error("关注服务失败");
            return false;
        }

    }
    public long getFanscount(int EntityType,int EntityId){
        String Fanskey=Entityname.getFanskey(EntityType,EntityId);
        return jedisAdapter.zcard(Fanskey);
    }
    public long getFollowcount(int userId,int entityType){
        String Followkey=Entityname.getFollowkey(userId,entityType);
        return jedisAdapter.zcard(Followkey);
    }
    public boolean isfollow(int userId,int EntityType,int EntityId){
            String Followkey = Entityname.getFollowkey(userId, EntityType);
            return  jedisAdapter.zscore(Followkey,String.valueOf(EntityId))!=null;
    }
    public List<Integer> getfollow(int userId, int EntityType,int offset){
        String Followkey = Entityname.getFollowkey(userId, EntityType);
        return tointarray(jedisAdapter.zrevrange(Followkey,offset,offset+10));

    }
    public List<Integer> getfans(int EntityType,int EntityId,int offset){
        String Fanskey = Entityname.getFanskey(EntityType,EntityId);
        return tointarray(jedisAdapter.zrevrange(Fanskey,offset,offset+10));

    }
    public List<Integer> tointarray(Set<String> set){
        List<Integer> list=new ArrayList<>();
        for(String s:set){
            list.add(Integer.parseInt(s));
        }
        return list;
    }

}
