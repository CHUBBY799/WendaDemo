package com.example.demo.util;

import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.Map;
import java.util.Properties;

@Service
public class MailSender implements InitializingBean{
    private JavaMailSenderImpl mailSender;
    private static final Logger logger = LoggerFactory.getLogger(MailSender.class);


    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Override
    public void afterPropertiesSet() throws Exception {
        mailSender =new JavaMailSenderImpl();
        mailSender.setUsername("chubby799@163.com");
        mailSender.setPassword("13316392773qqq");
        mailSender.setHost("smtp.163.com");
        mailSender.setPort(465);
        mailSender.setProtocol("smtps");
        mailSender.setDefaultEncoding("utf8");
        Properties javaMailProperties =new Properties();
        javaMailProperties.put("mail.smtp.ssl.enable",true);
        mailSender.setJavaMailProperties(javaMailProperties);

    }

    public boolean sendWithHTMLTemplate(String to,String subject,String templateName,Map<String,Object> model){
        try{
            String nick= MimeUtility.encodeText("牛客网中级课");
            InternetAddress from=new InternetAddress(nick+"<chubby799@163.com>");
            MimeMessage mimeMessage=mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage);
            freeMarkerConfigurer.setDefaultEncoding("UTF-8");
            Template template=freeMarkerConfigurer.getConfiguration().getTemplate(templateName);
            String result=FreeMarkerTemplateUtils.processTemplateIntoString(template,model);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(result,true);
            mailSender.send(mimeMessage);
            return  true;


        }catch (Exception e){
            logger.error("发送邮件失败"+e.getMessage());
            return false;
        }

    }

}
