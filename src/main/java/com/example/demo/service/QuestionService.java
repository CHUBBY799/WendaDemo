package com.example.demo.service;

import com.example.demo.dao.QuestionDAO;
import com.example.demo.model.HostHolder;
import com.example.demo.model.Question;
import com.example.demo.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuestionService {
    @Autowired
    QuestionDAO questionDAO;
    @Autowired
    SensitiveService sensitiveService;
    public List<Question> getLatestQuestions(int userId, int offset, int limit) {
        return questionDAO.selectLatestQuestions(userId, offset, limit);
    }
    public int addQuestion(String title,String content){
        Map<String,Object> map=new HashMap<>();
        Question question=new Question();

        question.setTitle(sensitiveService.filter(HtmlUtils.htmlEscape(title)));
        question.setContent(sensitiveService.filter(HtmlUtils.htmlEscape(content)));
        question.setCreatedDate(new Date());
        // 敏感词过滤
        if(HostHolder.getUser()==null){
            question.setUserId(WendaUtil.ANONYMOUS_USERID);
        }else {
            question.setUserId(HostHolder.getUser().getId() );
        }
        return questionDAO.addQuestion(question)>0?question.getId():0;
    }
}
