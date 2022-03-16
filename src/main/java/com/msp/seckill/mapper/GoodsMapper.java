package com.msp.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msp.seckill.VO.GoodsVo;
import com.msp.seckill.pojo.Goods;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author msp
 * @since 2022-03-16
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    List<GoodsVo> findGoodsVo();

    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}
