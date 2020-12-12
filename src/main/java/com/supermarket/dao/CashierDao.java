package com.supermarket.dao;

import com.supermarket.pojo.Order;
import com.supermarket.pojo.OrderItem;
import com.supermarket.pojo.OrderItemVO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CashierDao {

    //收银-插入数据
    @Insert("insert into tb_order_item (order_number,commodity_id,commodity_name,price,count,total,is_checked,specification,units,stock) values (#{orderNumber},#{commodityId},#{commodityName},#{price},#{count},#{total},#{isChecked},#{specification},#{units},#{stock})")
//    @Results({
//            @Result(column="Specification", property="specification", jdbcType=JdbcType.INTEGER),
//    })
    int addOrderitem(OrderItemVO orderItem);

    //收银-查询数据
    @Select("select * from tb_order_item")
    List<OrderItemVO> findAll();

    //收银-查询未结账数据
    @Select("select * from tb_order_item where is_checked= 0 and order_number = #{shoppingNum}")
    @Results({
            @Result(column="order_number", property="orderNumber", jdbcType= JdbcType.INTEGER),
            @Result(column="commodity_id", property="commodityId", jdbcType=JdbcType.INTEGER),
            @Result(column="commodity_name", property="commodityName", jdbcType=JdbcType.VARCHAR)
    })
    List<OrderItemVO> getOrderItem(String shoppingNum);

    //收银-删除商品
    @Update("update tb_order_item set is_checked = 1 where order_number = #{shoppingNum} and commodity_id = #{commodityID}")
    void deleteCommodity(@Param(value="shoppingNum") String shoppingNum, @Param(value = "commodityID") int commodityID);

    //收银-查询商品
    @Select("select * from tb_order_item where order_number = #{shoppingNum}")
    List<OrderItemVO> getCommoditys(@Param(value = "shoppingNum") String shoppingNum);

    //收银-查询商品
    @Select("select * from tb_order_item where commodity_Id = #{commodityID} and order_number = #{shoppingNum} and is_checked = 0")
    OrderItemVO getCommodity(@Param(value = "commodityID") int commodityID, @Param(value = "shoppingNum")String shoppingNum);

    //收银-更新商品状态
    @Update("update tb_order_item set is_checked = 1 where order_number = #{orderNumber}")
    void updateOrderStock(String orderNumber);

    //收银-现金结账
    @Insert("insert into tb_order (order_number,sum,user_id,member_id,checkout_type,checkout_time) values (#{orderNumber},#{sum},#{userId},#{memberId},#{checkoutType},#{checkoutTime})")
    void interOrder(Order order);

    //收银-查询结账数据
    @Select("select * from tb_order")
    List<Order> getOrder();

    //收银-更新
    @Update("update tb_order_item set stock = #{newstock},total = #{total},count = #{count} where order_number = #{shoppingNum} and commodity_id = #{commodityID}")
    void updatetotal(@Param(value = "total")BigDecimal total,@Param(value = "count")int count,@Param(value = "commodityID")int commodityID,@Param(value = "shoppingNum")String shoppingNum,@Param(value ="newstock" ) int newstock);

    //余额查询-插入结账表
    @Insert("insert into tb_order (order_number,sum,user_id,member_id,checkout_type,checkout_time,total_points) values" +
            " (#{orderNumber},#{sum},#{userId},#{memberId},#{checkoutType},#{checkoutTime},#{total_points})")
    boolean addOrder(Order order);
}
