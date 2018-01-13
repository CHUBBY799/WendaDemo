package com.example.demo.controller;

import com.example.demo.model.EntityType;
import com.example.demo.model.Question;
import com.example.demo.model.ViewObject;
import com.example.demo.service.FollowService;
import com.example.demo.service.QuestionService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    UserService userService;
    @Autowired
    QuestionService questionService;
    @Autowired
    FollowService followService;

    public List<ViewObject> getQuestions(int userId,int offset,int limit){
          List<Question> questionList=questionService.getLatestQuestions(userId,offset,limit);
          List<ViewObject> vos=new ArrayList<>();
          for(Question question:questionList){
              ViewObject vo=new ViewObject();
              vo.set("question",question);
              vo.set("user",userService.getUserByid(question.getUserId()));
              vo.set("fanscount",followService.getFanscount(EntityType.QUESTION_TYPE,question.getId()));
              vos.add(vo);
          }
          return vos;
    }
    @RequestMapping(path = {"/","/index"})
    public String index(Model model){
        followService.follow(17,EntityType.USER_TYPE,12);
        model.addAttribute("vos",getQuestions(0,0,10));

    return "index";

}
    @RequestMapping(path={"/user/{userID}"})
    public String userIndex(Model model, @PathVariable("userID") int userID){
        model.addAttribute("vos",getQuestions(userID,0,10));
        return "index";
    }

}
