package com.supermarket.dao;

import com.supermarket.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 账户接口
 */
@Repository
public interface LoginDao {


    //登录-根据用户名密码查询
    @Select("select * from tb_user where username = #{username} and password = #{password} and role = #{role}")
    User getUser(@Param(value = "username") String username, @Param(value = "password") String password,@Param(value = "role") String role);

}
