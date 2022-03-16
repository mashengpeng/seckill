package com.msp.seckill.controller;


import com.msp.seckill.VO.OrderDetailVo;
import com.msp.seckill.VO.RespBean;
import com.msp.seckill.VO.RespBeanEnum;
import com.msp.seckill.pojo.User;
import com.msp.seckill.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author msp
 * @since 2022-03-16
 */
@Controller
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private IOrderService orderService;

    @RequestMapping("/detail")
    @ResponseBody
    public RespBean detail(User user, Long orderId){
        if(user == null){
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }

        OrderDetailVo detail = orderService.detail(orderId);
        return RespBean.success(detail);

    }

}
