package com.supermarket.pojo;

import java.io.Serializable;

/**
 * @Auther: Jake.Chen
 * @Date: 2/2/2019 10:31
 * @Description:
 */
public class CommodityVO implements Serializable {

    private int id;//商品条码
    private String name;//商品名称
    private String specification;//商品规格登记
    private String units;//单位
    private double price;//价格
    private int stock;//库存

    private int count;//数量
    private double totalPrice;

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

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        this.totalPrice = this.count * this.price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }


}
