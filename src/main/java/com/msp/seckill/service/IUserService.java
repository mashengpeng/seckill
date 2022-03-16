package com.msp.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msp.seckill.VO.LoginVo;
import com.msp.seckill.VO.RespBean;
import com.msp.seckill.pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author msp
 * @since 2022-03-15
 */
public interface IUserService extends IService<User> {

    RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);


    User getUserByTicket(String ticket, HttpServletRequest request, HttpServletResponse response);


    RespBean updatePassWord(String ticket, String passWord, HttpServletRequest request, HttpServletResponse response);
}
