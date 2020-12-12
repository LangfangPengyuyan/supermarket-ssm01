package com.supermarket.service.impl;

import com.supermarket.dao.CashierDao;
import com.supermarket.dao.CommodityDao;
import com.supermarket.dao.ManagerDao;
import com.supermarket.pojo.CommodityVO;
import com.supermarket.pojo.Member;
import com.supermarket.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service("commodityService")
public class CommodityServiceImpl implements CommodityService {

    //库存
    @Autowired
    CommodityDao commodity;

    //购物车
    @Autowired
    CashierDao cashier;

    @Autowired
    ManagerDao managerDao;

    //进货-查询所有数据
    @Override
    public List<CommodityVO> findAll() {
        return commodity.findAll();
    }

    //进货-添加数据
    @Override
    public void addCommodity(CommodityVO commodityVO) {

        //根据id查询数据库是否有重复记录
        Boolean commodity1 = commodity.getCommodity(commodityVO.getId());

        //如果查询到数据进行更新
        if (commodity1 != null){
            commodity.updateCommodity(commodityVO);
        }else{
            //为空则没有重复记录继续插入数据，返回true
            commodity.insertCommodity(commodityVO);
        }
    }

    //收银-根据条件查询
    @Override
    public CommodityVO getCommodity(int commodityID) {
        //根据条件查询库存数据
        CommodityVO commoditys = commodity.getCommoditys(commodityID);
        return commoditys;
    }
    //结账-更新库存
    @Override
    public void updatecommodity(int commodityId, int count) {
        CommodityVO commodityVO = commodity.getCommoditys(commodityId);
        int stock = commodityVO.getStock()-count;
        commodity.updatestock(commodityId,stock);
    }

    //收银-查询更新后会员表
    @Override
    public String getMember(int memberID, BigDecimal total) {
        Member member = commodity.getMember(memberID);
        //余额对比会员总额
        int oldtotal = total.compareTo(member.getTotal());

        BigDecimal newtotal = total;

        if (oldtotal ==1){
            return "error";
        }else {
            managerDao.updatepoints(memberID,newtotal);
        }
        return null;
    }
    //余额查询-根据卡号查询数据
    @Override
    public Member getMembers(int memberID) {
        return commodity.getMember(memberID);
    }


}
