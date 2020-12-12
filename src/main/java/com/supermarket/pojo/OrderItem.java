package com.supermarket.pojo;


import java.io.Serializable;
import java.math.BigDecimal;

public class OrderItem extends OrderItemVO implements Serializable {
//    private int id;
//    private String orderNumber;//流水号
//    private int commodityId;//商品条码
//    private String commodityName;//商品名称
//    private double price;//单价
//    private int count;//数量
//    private BigDecimal total;//总价
//    private int isChecked;//状态
    private int id;
    private String name;
    private String specification;
    private String units;
    private double price;
    private int stock;

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
