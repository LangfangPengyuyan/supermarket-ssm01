package com.supermarket.service.impl;

import com.supermarket.dao.CashierDao;
import com.supermarket.dao.CommodityDao;
import com.supermarket.pojo.CommodityVO;
import com.supermarket.pojo.Order;
import com.supermarket.pojo.OrderItemVO;
import com.supermarket.service.CashierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service("CashierServiceImpl")
public class CashierServiceImpl implements CashierService {

    @Autowired
    CashierDao cashierDao;

    @Autowired
    CommodityDao commodityDao;

    //收银-插入数据
    @Override
    public String addOrderitem(OrderItemVO orderItem, int commodityID, String shoppingNum) {

        int commodityId = orderItem.getCommodityId();
        //tb_order_item根据流水号和商品ID查询是否重复
        OrderItemVO orderCommodityID = cashierDao.getCommodity(commodityID,shoppingNum);

        //查询表中数据
        CommodityVO commdityOV = commodityDao.getCommoditys(commodityID);
        //如果有相同商品被插入，数量进行累加
        if (orderCommodityID != null){
            //原有数量+新插入数量
            int count = orderCommodityID.getCount() + orderItem.getCount();
            //总价=原有价格+新添加的价格
            BigDecimal total = orderCommodityID.getTotal().add(orderItem.getTotal());
            //库存
            int newstock =commdityOV.getStock()-count;

            //如果购买的数量大于库存，返回0
            if (count > commdityOV.getStock()){
                return "error";
            }
            //更新数据库tb_order_item
            cashierDao.updatetotal(total,count,commodityID,shoppingNum,newstock);

        }else{
            //如果购买的数量大于库存，返回0
            int count = commdityOV.getCount();
            if (count >commdityOV.getStock()){
                return "error";
            }
            //如果没有相同数据，库存符合条件则进行插入
            int orderItemVOS = cashierDao.addOrderitem(orderItem);
        }
        return null;
    }

    //收银-查询所有
    @Override
    public List<OrderItemVO> findAll() {
        return cashierDao.findAll();
    }

    //收银-查询未结账数据
//    @Override
//    public List<OrderItemVO> getOrderChecked(String shoppingNum, int commodityID) {
//        return cashierDao.getOrderItem(shoppingNum,commodityID);
//    }

    //收银-查询未结账数据
    @Override
    public List<OrderItemVO> getOrderChecked(String shoppingNum) {
        return cashierDao.getOrderItem(shoppingNum);
    }

    //收银-删除数据
    @Override
    public void deleteCommodity(String shoppingNum, int commodityID) {
        cashierDao.deleteCommodity(shoppingNum, commodityID);
    }
    //收银-根据条件查询数据
    @Override
    public List<OrderItemVO> getCommodity(String shoppingNum) {
        return cashierDao.getCommoditys(shoppingNum);
    }


    //收银-更新商品
    @Override
    public void updateOrderStock(String orderNumber) {

        cashierDao.updateOrderStock(orderNumber);
    }

    //收银-现金结账
    @Override
    public void interOrder(Order order) {
        cashierDao.interOrder(order);
    }
    //收银-查询账单表
    @Override
    public List<Order> getOrder() {
        return cashierDao.getOrder();
    }

    //余额查询-插入结账表
    @Override
    public boolean addOrder(Order order, int memberID,String shoppingNum) {
        return cashierDao.addOrder(order);
    }

}
