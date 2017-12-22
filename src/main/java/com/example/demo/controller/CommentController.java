package com.example.demo.controller;

import com.example.demo.model.Comment;
import com.example.demo.model.EntityType;
import com.example.demo.model.HostHolder;
import com.example.demo.service.CommentService;
import com.example.demo.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class CommentController {
    private static final Logger logger= LoggerFactory.getLogger(Comment.class);
    @Autowired
    CommentService commentService;
    @RequestMapping(path = "/addComment",method = RequestMethod.POST)
    public String addComment(@RequestParam("questionId") int id,
                             @RequestParam("content") String content){
        try{
            Comment comment=new Comment();
            comment.setContent(content);
            comment.setCreatedDate(new Date());
            comment.setEntityType(EntityType.QUESTION_TYPE);
            comment.setStatus(0);
            comment.setEntityId(id);
            if(HostHolder.getUser()!=null){
                comment.setUserId(HostHolder.getUser().getId());
            }else {
                comment.setUserId(WendaUtil.ANONYMOUS_USERID);
            }
            commentService.addComment(comment);

        }catch(Exception e) {
               logger.error("添加评论失败");
        }
        return "redirect:/question/"+String.valueOf(id);

    }


}
