package com.mashibing.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TBOrderMapper {

    @Insert("insert into tb_order (id) values (#{id})")
    void save(@Param("id") String id);

    @Update("update tb_order set order_state = #{orderState} where id = #{id}")
    void updateOrderStateById(@Param("orderState") int i, @Param("id") String id);


    @Select("select order_state from tb_order where id = #{id} for update")
    int findOrderStateByIdForUpdate(@Param("id") String id);
}