package com.supermarket.pojo;

import java.io.Serializable;
import java.math.BigDecimal;


public class Order implements Serializable {
    private int id;//小票号
    private String orderNumber;//流水号
    private BigDecimal sum;//收到的钱
    private int userId;//收银员ID
    private int memberId;//会员ID
    private int checkoutType;//结账状态
    private long checkoutTime;//结账时间

    public BigDecimal getTotal_points() {
        return total_points;
    }

    public void setTotal_points(BigDecimal total_points) {
        this.total_points = total_points;
    }

    private BigDecimal total_points;//总积分



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getCheckoutType() {
        return checkoutType;
    }

    public void setCheckoutType(int checkoutType) {
        this.checkoutType = checkoutType;
    }

    public long getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(long checkoutTime) {
        this.checkoutTime = checkoutTime;
    }
}
