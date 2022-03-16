package com.msp.seckill.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.msp.seckill.VO.GoodsVo;
import com.msp.seckill.VO.RespBean;
import com.msp.seckill.VO.RespBeanEnum;
import com.msp.seckill.pojo.Order;
import com.msp.seckill.pojo.SeckillOrder;
import com.msp.seckill.pojo.User;
import com.msp.seckill.service.IGoodsService;
import com.msp.seckill.service.IOrderService;
import com.msp.seckill.service.ISeckillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/seckill")
public class SeckillController {

    @Autowired
    IGoodsService goodsService;

    @Autowired
    ISeckillOrderService seckillOrderService;

    @Autowired
    IOrderService orderService;

    @Autowired
    private RedisTemplate redisTemplate;


    @RequestMapping(value = "/doSeckill2")
    public String doSecKill2(Model model, User user, Long goodsId){
        if(user == null){
            return "login";
        }

        model.addAttribute("user", user);
        GoodsVo goods = goodsService.findGoodsVoByGoodsId(goodsId);
        if(goods.getStockCount() < 1){
            model.addAttribute("errmsg", RespBeanEnum.EMPTY_STOCK.getMessage());
            return "seckillFail";
        }
        SeckillOrder seckillOrder = seckillOrderService
                .getOne(new QueryWrapper<SeckillOrder>()
                .eq("user_id", user.getId())
                .eq("goods_id", goodsId));
        if(seckillOrder != null){
            model.addAttribute("errmsg", RespBeanEnum.REPEATE_ERROR.getMessage());
            return "seckillFail";
        }

        Order order = orderService.seckill(user, goods);
        model.addAttribute("order",order);
        model.addAttribute("goods",goods);
        return "orderDetail";

    }

    @PostMapping(value = "/doSeckill")
    @ResponseBody
    public RespBean doSecKill(Model model, User user, Long goodsId){
        if(user == null){
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }


        GoodsVo goods = goodsService.findGoodsVoByGoodsId(goodsId);
        if(goods.getStockCount() < 1){
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }

//        SeckillOrder seckillOrder = seckillOrderService
//                .getOne(new QueryWrapper<SeckillOrder>()
//                        .eq("user_id", user.getId())
//                        .eq("goods_id", goodsId));

        SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goods.getId());


        if(seckillOrder != null){
            return RespBean.error(RespBeanEnum.REPEATE_ERROR);
        }

        Order order = orderService.seckill(user, goods);

        return RespBean.success(order);

    }
}
