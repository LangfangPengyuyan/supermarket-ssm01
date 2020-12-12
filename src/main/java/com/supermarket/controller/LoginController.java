package com.supermarket.controller;

import com.supermarket.pojo.Member;
import com.supermarket.pojo.User;
import com.supermarket.service.ManagerService;
import com.supermarket.service.loginService;
import com.supermarket.util.IDUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 登录
 */
@Controller
@RequestMapping("/supermarket")
@Api(tags = "supermarket",description = "登录")
public class LoginController {

    @Autowired
    private loginService accountService;

    @Autowired
    private ManagerService managerService;


    //登陆
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation(value = "查询功能")
    @ApiImplicitParam(value = "ID")
    private String login(@ApiParam(name = "id",value = "用户名") String username, String password, String role, Model model) {
        //根据用户名密码查询数据
        User user = accountService.getUser(username, password,role);
        //如果查到该数据
        if (user != null) {
            //判断查询数据是否与传入数据相等
            if (1 == user.getRole()) {

                //查询会员表中所有数据
                List<Member> list = managerService.findAll();
                //保存数据
                model.addAttribute("members",list);

                //进入会员管理
                return "manager";

            }
            if (2 == user.getRole()) {
                //进入收银
                Integer shoppingNum = IDUtil.getId();
                model.addAttribute("shoppingNum",shoppingNum);
                return "cashier";
            }
        }
        return "error";
    }


}
