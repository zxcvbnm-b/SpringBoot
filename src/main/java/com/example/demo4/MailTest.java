package com.example.demo4;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Date;

//导入包快捷键：alt+enter 加上类似eclipse crtl+1的功能
//虽然报错，但是发出去消息了

@Controller
class MailTest {
    // 我tm也做了一些修改我也想提交
    @Autowired
    TemplateEngine templateEngine;
    @Autowired
   private JavaMailSenderImpl javaMailSenderImpl;
  //我修改了一些东西

    public void sendSimpleMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("这是一封测试邮件");
        message.setFrom("abcdefg@qq.com");//发送者
        String []strs=new String[]{"1605659095@qq.com","123456@qq.com"};
        //可变变量其实就是数组？可以传数组
        ArrayList<String> arrayList = new ArrayList<String>();
        String[] objects = arrayList.toArray(new String[arrayList.size()]);//list转string字符串
        message.setTo(strs);
        //message.setTo("123456@qq.com","789456@qq.com");//可用有多个接收者
        message.setCc("37xxxxx37@qq.com");//抄送人
        message.setBcc("14xxxxx098@qq.com");//隐秘抄送人
        message.setSentDate(new Date());
        message.setText("这是测试邮件的正文");
        javaMailSenderImpl.send(message);
    }

    @RequestMapping("/send")
    public String test() throws  Exception{
        //复杂邮件发送
        MimeMessage mimeMessage = javaMailSenderImpl.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("这是一封测试邮件啊 啊啊");//主题
        helper.setFrom("499263481@qq.com");//发生方
        helper.setTo("1605659095@qq.com");//接受方
        helper.setSentDate(new Date());//发送日期
        Context context = new Context();
        //存值到html模板
        context.setVariable("username", "java知友");
        context.setVariable("num","123456");
        context.setVariable("salary", "99999");
        String process = templateEngine.process("mail.html", context);//直接发送一个mail.html过去
        helper.setText(process,true);//内容
        javaMailSenderImpl.send(mimeMessage);//发送邮件
        return  "redirect:https://mail.qq.com/";//跳转到外网
    }
}
