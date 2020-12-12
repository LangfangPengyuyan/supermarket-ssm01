package com.supermarket.service;

import com.supermarket.pojo.Member;
import com.supermarket.pojo.Order;

import java.math.BigDecimal;
import java.util.List;


public interface ManagerService {

    //登录-查询所有数据
    public List<Member> findAll();

    //会员管理-添加会员
    public void addMember(Member member) ;

    //余额结账-更新会员表余额
    void updateMember(int memberID, BigDecimal total);
    //根据ID查询会员信息
    Member getMemberNPT(int memberID);
}
