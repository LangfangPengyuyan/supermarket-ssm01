package com.supermarket.service;

import com.supermarket.pojo.Order;
import com.supermarket.pojo.OrderItemVO;

import java.util.List;

public interface CashierService {

    //收银-添加数据
    public String addOrderitem(OrderItemVO orderItem, int commodityID, String shoppingNum);

    //收银-查询所有数据
    List<OrderItemVO> findAll();

    //收银-查询未结账数据
    //List<OrderItemVO> getOrderChecked(String shoppingNum);

    //收银-查询未结账数据
    //List<OrderItemVO> getOrderChecked(String shoppingNum, int commodityID);
    public List<OrderItemVO> getOrderChecked(String shoppingNum);
    //收银-删除商品
    void deleteCommodity(String shoppingNum, int commodityID);
    //收银-根据条件查询
    List<OrderItemVO> getCommodity(String shoppingNum);

    //收银-更新数据
    void updateOrderStock(String orderNumber);

    //收银-现金结账
    void interOrder(Order order);
    //收银-查询账单表
    List<Order> getOrder();

    //余额查询-插入结账表
    boolean addOrder(Order order, int memberID,String shoppingNum);
}
