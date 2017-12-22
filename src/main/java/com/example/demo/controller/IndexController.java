package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

////@Controller
//public class IndexController {
//    @Autowired
//    MessageService messageService;
//    @RequestMapping(path={"/","/index"})
//    @ResponseBody
//    public String index(HttpServletRequest request,HttpSession session){
//        return messageService.print(12);
//    }
//
//    @RequestMapping(path={"/profile/{user}/{password}"})
//    @ResponseBody
//    public String profile(@PathVariable("user") String user,
//                          @PathVariable("password") String password,
//                          @RequestParam(value="type") int type,
//                          @RequestParam(value="value") int value
//                          ){
//        return String.format("user is %s,password is %s,type is %d,password is %d",user,password,type,value);
//
//    }
//    @RequestMapping(path={"/flt"})
//    public String template(Model model){
//        model.addAttribute("value","qiuxinyi");
//        List<Integer> list= Arrays.asList(new Integer[]{1,23,5});
//        model.addAttribute("list",list);
//        model.addAttribute("user",new User("jack"));
//        return "home";
//    }
//    @RequestMapping(path={"/request"})
//    @ResponseBody
//    public String request(HttpServletRequest httpServletRequest,
//                          HttpServletResponse httpServletResponse,
//                          HttpSession session,
//                          @CookieValue("JSESSIONID") String sessionid
//                          )  {
//        StringBuilder str=new StringBuilder();
//        str.append("method:"+httpServletRequest.getMethod()+"<br>");
//        str.append("URI:"+httpServletRequest.getRequestURI()+"<br>");
//        str.append("pathinfo"+httpServletRequest.getPathInfo()+"<br>");
//        str.append("querystring"+httpServletRequest.getQueryString()+"<br>");
//        Enumeration enumeration=httpServletRequest.getHeaderNames();
//        while(enumeration.hasMoreElements()){
//            str.append(enumeration.nextElement()+"<br>");
//        }
//        str.append("host:"+httpServletRequest.getHeader("host")+"<br>");
//        str.append("servletpath"+httpServletRequest.getServletPath()+"<br>");
//        httpServletResponse.addCookie(new Cookie("user","jack"));
//        if(httpServletRequest.getCookies()!=null)
//        for(Cookie cookie:httpServletRequest.getCookies()){
//            str.append("cookie:"+cookie.getName()+"value:"+cookie.getValue());
//        }
//        httpServletResponse.addHeader("jack","a");
//        str.append("<br>"+httpServletRequest.getAttribute("a"));
//
//
////        str.append(""+httpServletResponse.getStatus())
//        return str.toString();
//
//    }
//    @RequestMapping(path = {"/redirect/{recode}"})
//    public RedirectView redirect(HttpServletRequest request,
//                                 HttpSession session,
//                                 @PathVariable("recode") int recode){
//       session.setAttribute("a","b");
//        RedirectView redirectView=new RedirectView("/",true);
//        if(recode==301)
//            redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
//        return redirectView;
//    }
//    @RequestMapping(path = {"/admin"})
//    @ResponseBody
//    public String admin(@RequestParam("admin") int admin){
//        if(admin==205)
//        return "admin";
//        else
//            throw new IllegalArgumentException();
//    }
//    @ExceptionHandler
//    @ResponseBody
//    public String error(Exception e){
//        return "error";
//    }
//
//}
