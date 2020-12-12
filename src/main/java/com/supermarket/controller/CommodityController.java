package com.supermarket.controller;

import com.supermarket.pojo.CommodityVO;
import com.supermarket.service.CommodityService;
import com.supermarket.service.impl.CommodityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 进货-进货管理
 */

@Controller
@RequestMapping("/commodity")
public class CommodityController {

    @Autowired
    CommodityService commodity;


    //查询商品
    @RequestMapping(value = "/getCommodities",method =RequestMethod.POST)
    private String getCommodities(Model model){

        List<CommodityVO> commodityVOS = commodity.findAll();

        model.addAttribute("commodities",commodityVOS);

        return "commodity";
    }

    //新增商品
    @RequestMapping(value = "/inputCommodity",method = RequestMethod.POST)
    private String inputCommodity(CommodityVO commodityVO, Model model){

        //把实体添加到数据库
        commodity.addCommodity(commodityVO);
        //查询库存表中的数据
        List<CommodityVO> list = commodity.findAll();
        //保存数据
        model.addAttribute("commodities",list);

        return "commodity";

    }


}
