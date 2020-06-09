package com.renchao.server.dao;

import com.renchao.server.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;


@Mapper
@Component
public interface UserDao {
    String TABLE_NAME = "users";
    String PARAMETERS = "id,name,password,email,value";

    @Select("SELECT * FROM " + TABLE_NAME + " WHERE id = #{id}")
    User select(String id);

    @Select("SELECT * FROM " + TABLE_NAME + " WHERE name = #{name}")
    User selectByName(String name);

    @Update({ "UPDATE "+TABLE_NAME+" SET value = #{value} where name = #{name}" })
    boolean update(String name,String value);

}
