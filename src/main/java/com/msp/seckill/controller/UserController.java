package com.msp.seckill.controller;


import com.msp.seckill.VO.RespBean;
import com.msp.seckill.config.rabbitmq.MQSender;
import com.msp.seckill.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author msp
 * @since 2022-03-15
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    MQSender mqSender;



    @RequestMapping("/info")
    @ResponseBody
    public RespBean info(User user){
        return RespBean.success(user);
    }


    @RequestMapping("/mq")
    @ResponseBody
    public void mq(){
        while(true){
            mqSender.send("你好");
        }

    }
}
