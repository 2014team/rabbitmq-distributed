package com.mashibing.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserPointsIdempotentMapper {

    @Select("select count(1) from user_points_idempotent where id = #{id}")
    int findById(@Param("id") String id);

    @Insert("insert into user_points_idempotent (id) values (#{id})")
    void save(@Param("id") String id);

}