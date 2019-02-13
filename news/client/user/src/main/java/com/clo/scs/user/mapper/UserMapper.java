package com.clo.scs.user.mapper;

import com.clo.scs.user.bean.UserBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select id, mobile, password from news_user where mobile=#{mobile} and password=#{password}")
    public List<UserBean> queryUser(@Param("mobile") String mobile, @Param("password") String password);
}
