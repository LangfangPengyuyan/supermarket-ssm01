package com.supermarket.controller;

import com.supermarket.pojo.Member;
import com.supermarket.util.IDUtil;
import com.supermarket.service.impl.ManagerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;
import java.util.List;

/**
 * 会员管理
 */
@Controller
@RequestMapping("/managerService")
public class ManagerController {

    @Autowired
    ManagerServiceImpl managerService;

    //添加会员
    @RequestMapping(value= "/addMember" ,method = RequestMethod.POST)
    private String addMember(int id,String name, int points, BigDecimal total, String phone, Model model){

        //创建对象并赋值
        Member member = new Member();

        member.setId(id);
        member.setName(name);
        member.setPoints(points);
        member.setTotal(total);
        member.setPhone(phone);
        member.setRegisterTime(System.currentTimeMillis());

        //将数据插入到数据库
        managerService.addMember(member);

        //查询会员表中所有数据
        List<Member> list = managerService.findAll();
        //保存数据
        model.addAttribute("members",list);
        return "manager";

    }
    public String a(){
        return "";
    }
}
