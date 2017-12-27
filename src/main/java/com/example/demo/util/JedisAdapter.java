package com.example.demo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;
@Service
public class JedisAdapter implements InitializingBean{
    public static  void print(int key,Object o){
        System.out.println(String.format("%d : %s",key,o.toString()));
    }

    public static void main(String[] args) {

        Jedis jedis=new Jedis("redis://localhost:6379/9");
        print(1,jedis.scard("like_2_9"));
//        jedis.set("a","123");
//        print(1,jedis.get("a"));
//        jedis.set("b","123");
//        jedis.rename("b","c");
//        jedis.setex("b",15,"abcd");
//
//        jedis.set("pv","100");
//        print(2,jedis.get("pv"));
//        jedis.incr("pv");
//        print(2,jedis.get("pv"));
//        jedis.incrBy("pv",5);
//        print(2,jedis.get("pv"));
//        jedis.decr("pv");
//        print(2,jedis.get("pv"));
//
//
//        //list
//        String listname="list";
//
//        for(int i=0;i<10;i++){
//            jedis.lpush("d",String.valueOf(i));
//            jedis.lpush("e",String.valueOf(i*i));
//        }
//        print(3,jedis.lpop("d"));
//        print(3,jedis.lpop("e"));
//        print(3,jedis.lrange("e",0,10));
//        print(3,jedis.lindex("e",3));
//        print(3,jedis.linsert("e", BinaryClient.LIST_POSITION.AFTER,"9","100"));
//        print(3,jedis.lrange("e",0,11));
//
//        //hash
//
//        String hash="hash";
//        for(int i=1;i<=10;i++){
//            jedis.hset(hash,String.valueOf(i),String.valueOf(i*i));
//        }
//        print(4,jedis.hgetAll(hash));
//        print(4,jedis.hget(hash,"6"));
//        print(4,jedis.hexists(hash,"6"));
//        print(4,jedis.hsetnx(hash,"11","121"));
//        print(4,jedis.hgetAll(hash));
//
//        //set
//        String set="set";
//        String set1="set1";
//
//        for(int i=0;i<10;i++){
//            jedis.sadd(set,String.valueOf(i));
//            jedis.sadd(set1,String.valueOf(i*i));
//        }
//        print(5,jedis.smembers(set));
//        print(5,jedis.smembers(set1));
//
//        print(5,jedis.sunion(set,set1));
//        print(5,jedis.sdiff(set,set1));
//        print(5,jedis.sinter(set,set1));
//        print(5,jedis.sismember(set,"5"));
//        print(5,jedis.scard(set));
//
//        //rankkey
//        String rankKey="rankey";
//        int j=0;
//        for(int i=0;i<100;i=i+5){
//        jedis.zadd(rankKey,i,String.valueOf(j));
//        j++;
//        }
//        print(6,jedis.zrange(rankKey,0,100));
//        print(6,jedis.zcount(rankKey,0,100));//by score
//        print(6,jedis.zcount(rankKey,0,50));
//        print(6,jedis.zrange(rankKey,0,20));// by range
//        for(Tuple tuple:jedis.zrangeByScoreWithScores(rankKey,0,100)){
//            print(6,tuple.getElement()+"/"+String.valueOf(tuple.getScore()));
//        }
//        print(6,jedis.zrank(rankKey,"19"));
//        print(6,jedis.zlexcount(rankKey,"(1","[6"));
//        print(6,jedis.zrange(rankKey,0,20));
//        jedis.zrem(rankKey,"6");
//        print(6,jedis.zrange(rankKey,0,20));
//        jedis.zremrangeByScore(rankKey,50.0,100.0);
//        print(6,jedis.zrange(rankKey,0,20));
//        jedis.zremrangeByLex(rankKey,"(3","+");
//        print(6,jedis.zrange(rankKey,0,20));
//        print(6,jedis.zcount(rankKey,0,100));



//        User user=new User();
//        user.setId(1);
//        jedis.set("user1", JSONObject.toJSONString(user));
////        print(7,jedis.get("uesr1"));
//        User user2= JSON.parseObject(jedis.get("user1"),User.class);
//        print(7,user2.getId());
    }



    private static final Logger logger= LoggerFactory.getLogger(JedisAdapter.class);
    private JedisPool pool;


    @Override
    public void afterPropertiesSet() throws Exception {
        pool=new JedisPool("redis://localhost:6379/9");

    }

    public long addLikebyset(String key,String value){
        Jedis jedis=null;
        try{
             jedis=pool.getResource();
             return jedis.sadd(key,value);

        }catch (Exception e){
            logger.error("从set中加入失败",e.getMessage());
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
            return 0;
    }
    public long deleteLikebyset(String key,String value){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            return jedis.srem(key,value);

        }catch (Exception e){
            logger.error("从set中移除失败",e.getMessage());
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return 0;
    }
    public long countLikebyset(String key){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            long i=jedis.scard(key);
            return i;

        }catch (Exception e){
            logger.error("从set中计算失败",e.getMessage());
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return 0;
    }
    public boolean ismemberbyset(String key,String value){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            return jedis.sismember(key,value);
        }catch (Exception e ){
            logger.error("从set中判断失败",e.getMessage());
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return  false;
    }
}
