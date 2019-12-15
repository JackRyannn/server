package com.renchao.server.dao;

import com.renchao.server.model.Metrics;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface MetricsDao {
    String TABLE_NAME = "api_metrics";
    String PARAMETERS = "id,api_name,count";

    @Select("SELECT * FROM "+TABLE_NAME+" WHERE api_name = #{api_name}")
    Metrics select(String api_name);

    @Update({ "UPDATE "+TABLE_NAME+" SET count = #{count} where id = #{id}" })
    int updateCountById(long count,String id);
}

