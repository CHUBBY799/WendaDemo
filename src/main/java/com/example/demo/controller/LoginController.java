package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;


@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    UserService userService;

    @RequestMapping(path = {"/reg/"}, method = {RequestMethod.POST})
    public String reg(@RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam(value = "rememberme", defaultValue = "false") boolean rememberme,
                      Model model,
                      HttpServletResponse response) {
        try {
            Map<String, Object> map = userService.regist(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                if (rememberme) {
                    cookie.setMaxAge(3600 * 24 * 5);
                }
                response.addCookie(cookie);
                return "redirect:/";
            } else {
                model.addAllAttributes(map);
                return "login";
            }
        } catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            model.addAttribute("msg", "服务器错误");
            return "login";

        }


    }

    @RequestMapping(path = {"/login/"}, method = RequestMethod.POST)
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model,
                        @RequestParam("rememberme") boolean rememberme,
                        HttpServletResponse response,
                        @RequestParam(value = "next",required = false) String next) {
        try {
            Map<String, Object> map = userService.login(username, password);

            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                if (rememberme) {

                    cookie.setMaxAge(3600 * 24 * 5);
                }
                response.addCookie(cookie);
                if (!StringUtils.isBlank(next)){

                    return "redirect:"+next;
                }
                return "redirect:/";
            } else {
                model.addAllAttributes(map);
                return "login";
            }

        } catch (Exception e) {
            logger.error("登陆异常" + e.getMessage());
            model.addAttribute("msg","登陆异常");
            return "login";
        }
    }

    @RequestMapping(path = {"/reglogin"}, method = {RequestMethod.GET,RequestMethod.POST})
    public String reglogin(@RequestParam(value = "next",required = false) String next,Model model) {
        model.addAttribute("next",next);
        return "login";
    }
    @RequestMapping(path = {"/logout"},method ={RequestMethod.POST,RequestMethod.GET})
    public String logout(@CookieValue("ticket") String ticket){
        userService.logoutTicket(ticket);
        return "redirect:/";

    }
}
