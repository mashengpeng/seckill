package com.msp.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msp.seckill.VO.GoodsVo;
import com.msp.seckill.VO.OrderDetailVo;
import com.msp.seckill.pojo.Order;
import com.msp.seckill.pojo.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author msp
 * @since 2022-03-16
 */
public interface IOrderService extends IService<Order> {

    Order seckill(User user, GoodsVo goods);

    OrderDetailVo detail(Long orderId);
}
