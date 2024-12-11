package com.mashibing.mapper;

import org.apache.ibatis.annotations.Insert;

import java.util.Map;

public interface ResentMapper {

    @Insert("insert into resend(id,message,exchange,routing_key,send_time)" +
            "values(#{id},#{message},#{exchange},#{routingKey},#{sendTime})")
    void save(Map map);
}
