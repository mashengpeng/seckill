package com.msp.seckill.controller;

import com.msp.seckill.VO.DetailVo;
import com.msp.seckill.VO.GoodsVo;
import com.msp.seckill.VO.RespBean;
import com.msp.seckill.VO.RespBeanEnum;
import com.msp.seckill.pojo.User;
import com.msp.seckill.service.IGoodsService;
import com.msp.seckill.service.IUserService;
import com.msp.seckill.service.impl.UserServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    IUserService userService;
    @Autowired
    IGoodsService goodsService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @RequestMapping(value = "/toList", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toList(Model model, User user, HttpServletRequest request, HttpServletResponse response){
        ValueOperations ops = redisTemplate.opsForValue();
        String html = ((String) ops.get("goodsList"));
        if(!StringUtils.isEmpty(html)){
            return html;
        }
        model.addAttribute("user", user);
        model.addAttribute("goodsList", goodsService.findGoodsVo());


        WebContext context = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsList", context);
        if(!StringUtils.isEmpty(html)){
            ops.set("goodsList", html, 3, TimeUnit.SECONDS);
        }
        return html;
        //return "goodsList";
    }

    @RequestMapping(value = "/toDetail2/{goodsId}", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toDetail2(Model model, User user, @PathVariable Long goodsId, HttpServletRequest request, HttpServletResponse response){

        ValueOperations ops = redisTemplate.opsForValue();
        String html = ((String) ops.get("goodsDetail:" + goodsId));
        if(!StringUtils.isEmpty(html)){
            return html;
        }


        model.addAttribute("user", user);
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);

        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
        Date now = new Date();

        int secKillStatus = 0;
        long remainSeconds = -1;
        if(now.before(startDate)){
            secKillStatus = 0;
            remainSeconds = (startDate.getTime()-now.getTime())/1000;
        }else if(now.before(endDate)){
            secKillStatus = 1;
            remainSeconds = 0;
        }else{
            secKillStatus = 2;
            remainSeconds = -1;
        }

        model.addAttribute("goods", goodsVo);
        model.addAttribute("secKillStatus", secKillStatus);
        model.addAttribute("remainSeconds", remainSeconds);

        WebContext context = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsDetail", context);
        if(!StringUtils.isEmpty(html)){
            ops.set("goodsDetail:" + goodsId, html, 3, TimeUnit.SECONDS);
        }
        return html;
        //return "goodsDetail";

    }


    @RequestMapping(value = "/detail/{goodsId}")
    @ResponseBody
    public RespBean toDetail(Model model, User user, @PathVariable Long goodsId, HttpServletRequest request, HttpServletResponse response){

        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);

        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
        Date now = new Date();

        int secKillStatus = 0;
        long remainSeconds = -1;
        if(now.before(startDate)){
            secKillStatus = 0;
            remainSeconds = (startDate.getTime()-now.getTime())/1000;
        }else if(now.before(endDate)){
            secKillStatus = 1;
            remainSeconds = 0;
        }else{
            secKillStatus = 2;
            remainSeconds = -1;
        }

        DetailVo detailVo = new DetailVo();
        detailVo.setUser(user);
        detailVo.setGoodsVo(goodsVo);
        detailVo.setRemainSeconds(remainSeconds);
        detailVo.setSecKillStatus(secKillStatus);
        return RespBean.success(detailVo);



    }
}
