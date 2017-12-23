package com.example.demo.dao;

import com.example.demo.model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageDAO {
    String TABLE_NAME=" message ";
    String INSERT_FIELDS=" fromId, toId, content, createdDate, hasRead,conversationId ";
    String SELECT_FIELDS=" id,fromId, toId, content, createdDate, hasRead,conversationId ";
    @Insert({"insert into"+TABLE_NAME+"("+INSERT_FIELDS+")"+" values(#{fromId},#{toId},#{content},#{createdDate},#{hasRead},#{conversationId})"})
    int addMessage(Message message);

    @Select({"select"+SELECT_FIELDS+"from"+TABLE_NAME+"where conversationId=#{conversationId} order by createdDate desc"})
    List<Message> selectCommentByConversationId(String conversationId);
    @Select({"select"+SELECT_FIELDS+"from"+TABLE_NAME+"where toId=#{toId} order by createdDate asc"})
    List<Message> selectCommentByToId(String toId);
//    select *,count(id) as id1 where fromId=..or toId=.. from (select * FROM wenda.message order by createdDate desc limit 1000000000000) a group by conversationId;
    @Select({"select"+INSERT_FIELDS+",count(id) as id from"+" (select"+SELECT_FIELDS+"from"+TABLE_NAME+"where fromId=#{id} or toId=#{id} order by createdDate desc limit 1000000000) a group by conversationId"})
    List<Message> selectMessageById(int id);
    @Select({"select count(id) from"+TABLE_NAME+"where toId=#{id} and conversationId=#{conversationId} and hasRead=1"})
    int selectnotreadCount(@Param("id") int id,@Param("conversationId") String conversationId);
}
