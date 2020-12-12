package com.supermarket.dao;


import com.supermarket.pojo.CommodityVO;
import com.supermarket.pojo.Member;
import com.supermarket.pojo.OrderItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommodityDao {

    //进货-根据id查询
    @Select("select * from tb_commodity where id = #{id}")
    Boolean getCommodity(@Param(value = "id") int id);

    //收银-查询商品
    @Select("select * from tb_commodity where id = #{commodityID}")
    CommodityVO getCommoditys(@Param(value = "commodityID")int commodityID);

    //收银-查询商品
    @Select("select * from tb_commodity where id = #{commodityID}")
    List<Member> gettotal(@Param(value = "commodityID")int commodityID);

    //进货-查询所有数据
    @Select("select * from tb_commodity")
    List<CommodityVO> findAll();

    //进货-更新数据
    @Update("update tb_commodity set name=#{name},specification =#{specification},units = #{units},price = #{price},stock = #{stock}+#{stock} where id = #{id}")
    Boolean updateCommodity(CommodityVO commodity);

    //进货-插入数据
    @Insert("insert into tb_commodity (id,name,specification,units,price,stock) values(#{id},#{name},#{specification},#{units},#{price},#{stock})")
    Boolean insertCommodity(CommodityVO commodity);

    //结账-更新库存
    @Update("update tb_commodity set stock = #{stock} where id = #{commodityId}")
    void updatestock(@Param(value="commodityId") int commodityId,@Param(value = "stock") int stock);

    //收银-根据条件查询商品
    @Select("select * from tb_member where id = #{memberID}")
    Member getMember(int memberID);
}
