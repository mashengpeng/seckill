package com.msp.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msp.seckill.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author msp
 * @since 2022-03-15
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
