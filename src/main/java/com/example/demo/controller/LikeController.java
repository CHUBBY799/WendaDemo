package com.example.demo.controller;

import com.example.demo.model.EntityType;
import com.example.demo.model.HostHolder;
import com.example.demo.service.LikeService;
import com.example.demo.util.JedisAdapter;
import com.example.demo.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LikeController {
@Autowired
LikeService likeService;
    private static final Logger logger= LoggerFactory.getLogger(LikeController.class);


    @RequestMapping(path = "/like",method = RequestMethod.POST)
    @ResponseBody
    public String addlike(@RequestParam("commentId") int commentId){

        if(HostHolder.getUser()==null) {
            return WendaUtil.getJSONString(999);

        }
         long i=likeService.addlikebyset(EntityType.COMMENT_TYPE,commentId,HostHolder.getUser().getId());
            return WendaUtil.getJSONString(0,String.valueOf(i));


    }
    @RequestMapping(path = "/dislike",method = RequestMethod.POST)
    @ResponseBody
    public String dislike(@RequestParam("commentId") int commentId){

            if(HostHolder.getUser()==null) {
                return WendaUtil.getJSONString(999);

            }
            long i=likeService.dellikebyset(EntityType.COMMENT_TYPE,commentId,HostHolder.getUser().getId());
            return WendaUtil.getJSONString(0,String.valueOf(i));


    }
}
