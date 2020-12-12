package com.supermarket.service.impl;

import com.supermarket.pojo.Member;
import com.supermarket.dao.ManagerDao;
import com.supermarket.pojo.Order;
import com.supermarket.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service("managerService")
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerDao managerDao;

    //登录-查询数据库所有数据
    @Override
    public List<Member> findAll() {
        return managerDao.getMembers();
    }

    //会员管理-添加会员
    @Override
    public void addMember(Member member) {

        Boolean member1 = managerDao.getMember(member.getId());
        //如果查询到这条数据返回true
        if (member1 != null){
            managerDao.updateMember(member);
        }else {
            //查询不到就插入
            managerDao.insertMember(member);
        }
    }

    //余额结账-更新会员表余额
    @Override
    public void updateMember(int memberID, BigDecimal total) {
        managerDao.updatetotal(memberID,total);
    }

    //根据ID查询会员信息
    @Override
    public Member getMemberNPT(int memberID) {
        return managerDao.getMemberNPT(memberID);
    }


}