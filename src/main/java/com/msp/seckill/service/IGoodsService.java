package com.msp.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msp.seckill.VO.GoodsVo;
import com.msp.seckill.pojo.Goods;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author msp
 * @since 2022-03-16
 */
public interface IGoodsService extends IService<Goods> {

    List<GoodsVo> findGoodsVo();

    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}
