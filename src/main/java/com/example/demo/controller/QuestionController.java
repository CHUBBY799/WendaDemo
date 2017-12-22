package com.example.demo.controller;

import com.example.demo.dao.QuestionDAO;
import com.example.demo.model.Comment;
import com.example.demo.model.EntityType;
import com.example.demo.model.Question;
import com.example.demo.model.ViewObject;
import com.example.demo.service.CommentService;
import com.example.demo.service.QuestionService;
import com.example.demo.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    QuestionService questionService;
    @Autowired
    QuestionDAO questionDAO;
    @Autowired
    CommentService commentService;
    @RequestMapping(path = "/question/add",method = RequestMethod.POST)
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title,
                              @RequestParam("content") String content) {
          try{
              if(questionService.addQuestion(title,content)>0)
                  return WendaUtil.getJSONString(0);

          }
          catch (Exception e){
              logger.error("增加问题失败",e.getMessage());
          }

             return WendaUtil.getJSONString(1,"失败");

    }
    @RequestMapping(path = "/question/{id}",method = RequestMethod.GET)
    public String questionDetail(@PathVariable("id") int id,Model model){
        Question question=questionDAO.selectQustionById(id);
        model.addAttribute(question);
        List<ViewObject> vos=commentService.selectLastestComment(id,EntityType.QUESTION_TYPE);
        model.addAttribute("vos",vos);
        return "detail";
    }


}
