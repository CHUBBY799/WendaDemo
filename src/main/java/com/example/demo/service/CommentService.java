package com.example.demo.service;

import com.example.demo.dao.CommentDAO;
import com.example.demo.model.Comment;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

@Service
public class CommentService {
    @Autowired
    CommentDAO commentDAO;
    @Autowired
    SensitiveService sensitiveService;
    public int addComment(Comment comment){
        comment.setContent(sensitiveService.filter(HtmlUtils.htmlEscape(comment.getContent())));
        return commentDAO.addComment(comment);
    }
    public Comment selectCommentByEntityId(int entityId){
        return commentDAO.selectCommentByEntityId(entityId);
    }
}
