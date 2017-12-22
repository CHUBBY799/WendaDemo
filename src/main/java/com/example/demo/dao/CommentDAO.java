package com.example.demo.dao;

import com.example.demo.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CommentDAO {
    String TABLE_NAME=" comment ";
    String INSERT_FIELDS=" entityId, content, created_date, user_id, entityType,status ";
    String SELECT_FIELDS=" entityId, content, created_date, user_id, entityType,status ";
    @Insert({"insert into"+TABLE_NAME+"("+INSERT_FIELDS+")"+" values(#{entityId},#{content},#{createdDate},#{userId},#{entityType},#{status})"})
    int addComment(Comment comment);
    @Select({"select"+SELECT_FIELDS+"from"+TABLE_NAME+"where entityId=#{id}"})
    Comment selectCommentByEntityId(int id);
}
