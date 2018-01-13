package com.example.demo.controller;

import com.example.demo.model.EntityType;
import com.example.demo.model.HostHolder;
import com.example.demo.model.User;
import com.example.demo.model.ViewObject;
import com.example.demo.service.FollowService;
import com.example.demo.service.UserService;
import com.example.demo.util.Entityname;
import com.example.demo.util.WendaUtil;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FollowController {
    private static final Logger log= LoggerFactory.getLogger(FollowController.class);
    @Autowired
    FollowService followService;
    @Autowired
    UserService userService;
    @RequestMapping(path = {"/followUser"},method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String followUser(@RequestParam("userId") int userId){
        if(HostHolder.getUser()==null){
            return WendaUtil.getJSONString(999);
        }
        boolean ret=followService.follow(HostHolder.getUser().getId(), EntityType.USER_TYPE,userId);
        return  WendaUtil.getJSONString(ret?0:1,String.valueOf(followService.getFollowcount(EntityType.USER_TYPE,HostHolder.getUser().getId())));

    }
    @RequestMapping(path = {"/unfollowUser"},method = {RequestMethod.POST})
    @ResponseBody
    public String unfollowUser(@RequestParam("userId") int userId){
        if(HostHolder.getUser()==null){
            return WendaUtil.getJSONString(999);
        }
        boolean ret=followService.disfollow(HostHolder.getUser().getId(), EntityType.USER_TYPE,userId);
        return  WendaUtil.getJSONString(ret?0:1,String.valueOf(followService.getFollowcount(EntityType.USER_TYPE,HostHolder.getUser().getId())));

    }
    @RequestMapping(path={"/user/{uid}/followers"},method = RequestMethod.GET)
    public String followers(@PathVariable("uid") int uid, Model model){
        User user = userService.getUserByid(uid);
        model.addAttribute("curuser",user);
        model.addAttribute("followcount",followService.getFollowcount(uid,EntityType.USER_TYPE));
        List<Integer> list=followService.getfollow(uid,EntityType.USER_TYPE,0);
        List<ViewObject> vos=new ArrayList<>();
        for(Integer integer:list){
            User user1=userService.getUserByid(integer);
            ViewObject vo=new ViewObject();
            vo.set("user",user1);
            vo.set("followed",followService.isfollow(uid,EntityType.USER_TYPE,user1.getId()));
            vo.set("followcount",followService.getFollowcount(user1.getId(),EntityType.USER_TYPE));
            vo.set("fanscount",followService.getFanscount(EntityType.USER_TYPE,user1.getId()));
            vos.add(vo);
        }
        model.addAttribute("vos",vos);
        return "followers";
    }
    @RequestMapping(path={"/user/{uid}/followees"},method = RequestMethod.GET)
    public String followees(@PathVariable("uid") int uid, Model model){
        User user = userService.getUserByid(uid);
        model.addAttribute("curuser",user);
        model.addAttribute("followcount",followService.getFollowcount(uid,EntityType.USER_TYPE));
        List<Integer> list=followService.getfans(EntityType.USER_TYPE,uid,10);
        List<ViewObject> vos=new ArrayList<>();
        for(Integer integer:list){
            User user1=userService.getUserByid(integer);
            ViewObject vo=new ViewObject();
            vo.set("user",user1);
            vo.set("followed",followService.isfollow(uid,EntityType.USER_TYPE,user1.getId()));
            vo.set("followcount",followService.getFollowcount(user1.getId(),EntityType.USER_TYPE));
            vo.set("fanscount",followService.getFanscount(EntityType.USER_TYPE,user1.getId()));
            vos.add(vo);
        }
        model.addAttribute("vos",vos);
        return "followees";
    }
}
