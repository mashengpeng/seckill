package com.msp.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msp.seckill.VO.LoginVo;
import com.msp.seckill.VO.RespBean;
import com.msp.seckill.VO.RespBeanEnum;
import com.msp.seckill.exception.GlobalException;
import com.msp.seckill.mapper.UserMapper;
import com.msp.seckill.pojo.User;
import com.msp.seckill.service.IUserService;
import com.msp.seckill.utils.CookieUtil;
import com.msp.seckill.utils.MD5Util;
import com.msp.seckill.utils.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p> 服务实现类 </p> @author msp @since 2022-03-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
//
        User user = userMapper.selectById(mobile);
        if (null == user) {
            throw new GlobalException(RespBeanEnum.LOGINVO_ERROR);
        }
        if (!MD5Util.formPassToDBPass(password, user.getSlat()).equals(user.getPasword())) {
            throw new GlobalException(RespBeanEnum.LOGINVO_ERROR);
        }


        String ticket = UUIDUtil.uuid();

        //request.getSession().setAttribute(ticket, user);

        redisTemplate.opsForValue().set("user:" + ticket, user);


        CookieUtil.setCookie(request, response, "userTicket", ticket);
        return RespBean.success(ticket);
    }


    @Override
    public User getUserByTicket(String ticket, HttpServletRequest request, HttpServletResponse response) {
        if(StringUtils.isEmpty(ticket)){
            return null;
        }
        User user = (User) redisTemplate.opsForValue().get("user:" + ticket);
        if(user != null){
            CookieUtil.setCookie(request, response, "userTicket", ticket );
        }
        return user;
    }

    @Override
    public RespBean updatePassWord(String ticket, String passWord, HttpServletRequest request, HttpServletResponse response){
        User user = getUserByTicket(ticket, request, response);
        if(user == null){
            throw new GlobalException(RespBeanEnum.MOBILE_NOT_EXIST);
        }
        user.setPasword(MD5Util.inputPassToDBPass(passWord, user.getSlat()));
        int result = userMapper.updateById(user);
        if(result == 1){
            redisTemplate.delete("user:" + ticket);
            return RespBean.success();
        }
        return RespBean.error(RespBeanEnum.PASSWORD_UPDATE_FAIL);

    }
}
