package com.supermarket.service;

import com.supermarket.pojo.CommodityVO;
import com.supermarket.pojo.Member;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
@Component
public interface CommodityService {

    //进货-查询所有数据
    public List<CommodityVO> findAll();
    //进货-添加数据
    public void addCommodity(CommodityVO commodityVO);

    //收银-根据条件查询商品
    CommodityVO getCommodity(int commodityID);

    //收银-结账更新库存
    void updatecommodity(int commodityId, int count);

    //收银-查询更新后会员表
    String getMember(int memberID, BigDecimal total);

    //收银-根据条件查询商品
    Member getMembers(int memberID);


    //收银-根据条件查询商品
    //Member getMember(int memberID, BigDecimal total);

    //收银-查询会员卡号余额
    //List<Member> gettotal(int commodityID,BigDecimal total_cost);


}
