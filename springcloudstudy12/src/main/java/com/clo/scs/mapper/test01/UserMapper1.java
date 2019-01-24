package com.clo.scs.mapper.test01;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper1 {
    @Insert("insert into user(name, age) values(#{name}, #{age})")
    void addUser(@Param("name") String name, @Param("age") int age);
}
