package com.supermarket.dao;

import com.supermarket.pojo.Member;
import com.supermarket.pojo.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ManagerDao {

    //登录-查询数据库所有数据
    @Select("select * from tb_member")
    List<Member> getMembers();

    //会员管理-查询会员
    @Select("select * from tb_member where id=#{id}")
    Boolean getMember(@Param(value = "id") int id);

    //会员管理-添加会员
    @Insert("insert into tb_member (id,name,phone,points,total,register_time) values(#{id},#{name},#{phone},#{points},#{total},#{registerTime})")
    Boolean insertMember(Member member);

    //会员管理-数据更新
    @Update("update tb_member set name=#{name},points=#{points},total=#{total},phone=#{phone},update_time=#{registerTime} where id=#{id}")
    Boolean updateMember(Member member);

    //余额结账-更新会员表余额
    @Update("update tb_member set")
    void updatetotal(int memberID, BigDecimal total);

    //收银-余额结账更新余额和积分
    @Update("update tb_member set points = #{total}+#{points} total=#{total}-#{newtotal} where id = #{commodityID}")
    void updatepoints(@Param(value = "commodityID") int commodityID, @Param(value = "newtotal") BigDecimal newtotal);

    //根据ID查询会员信息
    @Select("select name,points,total from tb_member where id = #{memberID}")
    Member getMemberNPT(@Param(value="memberID") int memberID);
}
