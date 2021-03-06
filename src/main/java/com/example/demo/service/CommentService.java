package com.example.demo.service;

import com.example.demo.dao.CommentDAO;
import com.example.demo.dao.UserDAO;
import com.example.demo.model.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentDAO commentDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    SensitiveService sensitiveService;
    @Autowired
    LikeService likeService;
    public int addComment(Comment comment){
        comment.setContent(sensitiveService.filter(HtmlUtils.htmlEscape(comment.getContent())));
        return commentDAO.addComment(comment);
    }
    public Comment selectCommentByEntityId(int entityId){
        return commentDAO.selectCommentByEntityId(entityId);
    }
    public List<ViewObject> selectLastestComment(int id,int entityType){
        List<ViewObject> list=new ArrayList<>();
        List<Comment> list1=commentDAO.selectLastestComment(id, entityType);
        for(Comment comment:list1){
            User user=userDAO.selectById(comment.getUserId());
            ViewObject vo=new ViewObject();
            vo.set("user",user);
            vo.set("comment",comment);
           vo.set("likecount",(likeService.countLikebyset(EntityType.COMMENT_TYPE,comment.getId())));
           if (HostHolder.getUser()==null)
           vo.set("islike",0);
           else {
               vo.set("islike",(likeService.islikememberbyset(EntityType.COMMENT_TYPE,comment.getId(),HostHolder.getUser().getId())==true?1:-1));
           }
            list.add(vo);

        }
        return list;
    }
}
