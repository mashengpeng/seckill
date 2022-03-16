package com.msp.seckill.VO;

import com.msp.seckill.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailVo {

    private User user;

    private GoodsVo goodsVo;

    private long remainSeconds;

    private int secKillStatus;
}
