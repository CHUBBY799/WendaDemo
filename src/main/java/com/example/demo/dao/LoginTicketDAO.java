package com.example.demo.dao;

import com.example.demo.model.LoginTicket;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LoginTicketDAO {
    String TABLE_NAME=" login_ticket ";
    String INSERT_FIELDS=" userId,expired,status,ticket ";
    @Insert({"insert into"+TABLE_NAME+"("+INSERT_FIELDS+")"+" values(#{userId},#{expired},#{status},#{ticket})"})
    int addTicket(LoginTicket loginTicket);
    @Select({"select"+INSERT_FIELDS+"from"+TABLE_NAME+"where ticket=#{ticket}"})
    LoginTicket selectByTicket(String ticket);

    @Update({"update"+TABLE_NAME+"set status=#{status} where ticket=#{ticket}"})
    void updateStatus(LoginTicket loginTicket);

}
