package com.supermarket.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.annotation.ResponseJSONP;
import com.supermarket.pojo.*;
import com.supermarket.service.impl.CashierServiceImpl;
import com.supermarket.service.impl.CommodityServiceImpl;
import com.supermarket.service.impl.ManagerServiceImpl;
import com.supermarket.util.IDUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jws.WebParam;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

/**
 * 收银管理
 */
@Controller
@RequestMapping("/Cashier")
public class CashierController {


    @Autowired
    CommodityServiceImpl commodityService;

    @Autowired
    CashierServiceImpl cashierService;

    @Autowired
    ManagerServiceImpl managerService;

    /**
     * 查询商品
     *
     * @return
     */
    @RequestMapping(value = "/addBoughtCommodity",method = RequestMethod.POST)
    private String addBoughtCommodity(int commodityID, String shoppingNum, int count, Model model){

        //根据id查询商品
        CommodityVO orderItemVO1 = commodityService.getCommodity(commodityID);

        BigDecimal totalCost = new BigDecimal(0);

        int category = 0;

        if (orderItemVO1 == null){
            return "error";
        }

        if (count > orderItemVO1.getStock()){
            return "error2";
        }

        OrderItemVO orderItem = new OrderItemVO();
        orderItem.setOrderNumber(shoppingNum);
        orderItem.setCommodityId(commodityID);
        orderItem.setCommodityName(orderItemVO1.getName());
        orderItem.setPrice(orderItemVO1.getPrice());
        orderItem.setCount(count);
        //总价
        BigDecimal number = BigDecimal.valueOf(count);//value的原生数据赋值给value
        orderItem.setTotal(number.multiply(BigDecimal.valueOf(orderItemVO1.getPrice())));//数量*单价
        orderItem.setIsChecked(0);
        orderItem.setSpecification(orderItemVO1.getSpecification());
        orderItem.setUnits(orderItemVO1.getUnits());
        orderItem.setStock(orderItemVO1.getStock());

        //添加数据
        cashierService.addOrderitem(orderItem,commodityID,shoppingNum);

        //查询未结账数据
        List<OrderItemVO> orderItemVOS = cashierService.getOrderChecked(shoppingNum);
        //金额相加
        for (OrderItemVO orderItemVO : orderItemVOS) {
            totalCost = totalCost.add(orderItemVO.getTotal());
        }
        //未结账的数量
        category = orderItemVOS.size();
        
        model.addAttribute("orderItemList",orderItemVOS);
        model.addAttribute("shoppingNum",shoppingNum);
        //共有几种商品
        model.addAttribute("category",category);
        //共计多少钱
        model.addAttribute("totalCost", totalCost.doubleValue());
        return "cashier";

    }

    /**
     * 删除商品
     */
    @RequestMapping(value = "/removeCommodity",method = RequestMethod.POST)
    private String removeCommodity(String shoppingNum, int commodityID,Model model){

        //查询库存是否有该数据
        List<OrderItemVO> commodityVO= cashierService.getCommodity(shoppingNum);

        if (commodityVO == null){
            return "error";
        }else {
            cashierService.deleteCommodity(shoppingNum,commodityID);
        }

        //查询未结账数据
        List<OrderItemVO> orderItemVOS = cashierService.getOrderChecked(shoppingNum);


        model.addAttribute("orderItemList",orderItemVOS);
        model.addAttribute("shoppingNum",shoppingNum);

        return "cashier";
    }

    //收银-现金结账
    @RequestMapping(value = "/checkoutByCash",method = RequestMethod.POST)
    private String checkoutByCash(String balance,String category,String receive,String shoppingNum, String commodityID, BigDecimal total_cost,Model model){
        
        //获取结账时间
        long currentTime = System.currentTimeMillis();

        //获取orderitem表中未结账的数据
        List<OrderItemVO> orderChecked = cashierService.getOrderChecked(shoppingNum);

        for (OrderItemVO orderItemVO : orderChecked) {
            orderItemVO.getTotal();
            //更新order_item该流水号中未结账的状态
            cashierService.updateOrderStock(orderItemVO.getOrderNumber());
            //更新tb_commodity表中的库存
            commodityService.updatecommodity(orderItemVO.getCommodityId(),orderItemVO.getCount());
        }

        Order order = new Order();

        order.setOrderNumber(shoppingNum);
        order.setSum(total_cost);
        order.setUserId(1);
        order.setMemberId(0);
        order.setCheckoutType(1);
        order.setCheckoutTime(currentTime);
        //插入账单表
        cashierService.interOrder(order);
        //查询账单表
        List<Order> order1 = cashierService.getOrder();

        model.addAttribute("commodityList",order1);

        //剩余
        //int cashbalance = receive-category;
        //小票
        model.addAttribute("category", category);
        model.addAttribute("total_cost", total_cost);
        // 收款----X
        model.addAttribute("cash_receive", receive);
        // 剩余--X
        model.addAttribute("cash_balance", balance);
        // 积分ID
        model.addAttribute("member_id", 0);
        // 本次积分
        model.addAttribute("member_current_points", 0);
        //累计积分
        model.addAttribute("member_points", 0);
        // 流水号----√
        model.addAttribute("shoppingNum", shoppingNum);

        return "receipt1";

    }

    //收银-余额结账
    @RequestMapping(value = "checkoutByMember",method = RequestMethod.POST)
    private String checkoutByMember(String memberID,String shoppingNum,String category,String total_cost,Model model){

        //商品总金额
        BigDecimal total = new BigDecimal(total_cost) ;

        int memberIDD = Integer.parseInt(memberID);

        //int commodityIDD = Integer.parseInt(commodityID);
        //获取orderitem表中未结账的数据
        List<OrderItemVO> orderChecked = cashierService.getOrderChecked(shoppingNum);

        //遍历获取到的数据
        for (OrderItemVO orderItemVO : orderChecked) {
            //更新order_item该流水号中未结账的状态
            cashierService.updateOrderStock(orderItemVO.getOrderNumber());
            //更新tb_commodity表中的库存
            commodityService.updatecommodity(orderItemVO.getCommodityId(),orderItemVO.getCount());
        }

        BigDecimal totalCost = new BigDecimal(0);
        //金额相加
        for (OrderItemVO orderItemVO : orderChecked) {
            totalCost = total.add(orderItemVO.getTotal());
        }
        //未结账的数量
        int category1 = orderChecked.size();

        //查询更新后会员表
        //commodityService.getMember(memberID,total);

        //查询更新后会员表
        Member member = commodityService.getMembers(memberIDD);

        //查询购物车表
        List<OrderItemVO> orderItemVO = cashierService.findAll();

        Order order = new Order();

        for (OrderItemVO itemVO : orderItemVO) {

            //结账时间
            long currentTime = System.currentTimeMillis();
            //总积分
            BigDecimal sumpoints = total.add(member.getTotal());
            order.setOrderNumber(shoppingNum);
            order.setSum(itemVO.getTotal());
            order.setUserId(1);
            order.setMemberId(memberIDD);
            order.setCheckoutType(2);
            order.setCheckoutTime(currentTime);
            order.setTotal_points(sumpoints);
        }

        //插入到结账表order
        boolean orderFlage = cashierService.addOrder(order,memberIDD,shoppingNum);

        if(orderFlage == true) {

            //小票
//            model.addAttribute("category", category1);
//            model.addAttribute("total_cost", totalCost);
            //数量
            model.addAttribute("category", category);
            //合计
            model.addAttribute("total_cost", total_cost);
            // 收款----X
            //model.addAttribute("cash_receive", total);
            // 剩余--X
            model.addAttribute("cash_balance", member.getTotal());
            // 积分ID
            model.addAttribute("member_id", member.getId());
            // 本次积分
            model.addAttribute("member_current_points", total);
            //累计积分
            model.addAttribute("member_points", order.getTotal_points());
            // 流水号----√
            model.addAttribute("shoppingNum", shoppingNum);

            return "receipt1";
        }
        return "error";
    }
    // 余额结账---
    @RequestMapping(value = "getMember", produces = "application/json; charset=utf-8" )
    @ResponseBody
    private String getMember(String memberID){
        int memberid = Integer.parseInt(memberID);
        //根据ID查询会员信息
        Member member = managerService.getMemberNPT(memberid);
        String data = JSON.toJSONString(member);

      return data;
    }

    // 返回按钮-到收银界面
    @RequestMapping(value = "return")
    private String Return(Model model){
        //进入收银
        Integer shoppingNum = IDUtil.getId();
        model.addAttribute("shoppingNum",shoppingNum);
        return "cashier";
    }


}
