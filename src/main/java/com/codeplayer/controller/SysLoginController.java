package com.codeplayer.controller;

import com.alibaba.fastjson.JSONObject;
import com.codeplayer.dto.ResultDTO;
import com.codeplayer.entity.User;
import com.codeplayer.service.UserService;
import com.codeplayer.utils.KaptchaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class SysLoginController {
    @Autowired
    private ResultDTO resultDTO;
    @Autowired
    private UserService userService;

    private static final Logger log = LoggerFactory.getLogger(SysLoginController.class);

    //登录页面
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 管理员登录
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String username, String password,
                        HttpServletRequest request, HttpServletResponse response) {
        if (KaptchaUtil.checkVerifyCode(request)) {
            resultDTO.setCode(2010);
            resultDTO.setMessage("验证码错误，请重试！！");
            return JSONObject.toJSONString(resultDTO);
        } else {
            // 获取管理员对象
            User user = userService.queryById(1L);
            // 进行判断
            if (user.getName().equals(username) && user.getPassword().equals(password)) {
                String token = UUID.randomUUID().toString();
                user.setToken(token);
                user.setAvatarUrl("https://avatars.githubusercontent.com/u/59161468?v=4");
                user.setGithubAccountId("xxx");
                user.setEmail("xxx");
                user.setAddress("xxx");
                user.setBirthday("1999.12.24");
                user.setPhone("xxx");
                user.setSex("男");
                user.setUserId(1L);
                response.addCookie(new Cookie("token", token));
                userService.updateAdministrator(user);
                request.getSession().setAttribute("user", user);
                resultDTO.setCode(200);
                resultDTO.setMessage("【成功】管理员密码登录");
                log.warn("callback get administrator login warn,{}", resultDTO.getMessage());
            } else {
                resultDTO.setCode(100);
                resultDTO.setMessage("【失败】管理员用户名或密码错误！！");
                log.error("callback get administrator login error,{}", resultDTO.getMessage());
            }
            return JSONObject.toJSONString(resultDTO);
        }
    }


    /**
     * 退出登录
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        log.warn("【成功】退出登录");
        return "redirect:/index";
    }
}
