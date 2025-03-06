package com.lifeng.Service.impl;

import com.lifeng.Mapper.LoginMapper;
import com.lifeng.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.lifeng.Utils.RandomNumber;

@Service
public class EmailServiceImpl implements EmailService {
    private static final String subject="修改密码一次性代码";
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private RandomNumber randomNumber;

    public String sendSimpleEmail(String username) {
        String to = loginMapper.getEmail(username); // 查找邮箱
        String randomCode = randomNumber.generateRandomCode();  // 生成六位随机数
        String text = "您的验证码是：" + randomCode;  // 将验证码作为邮件内容
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("1514055091@qq.com"); // 发件人
        message.setTo(to);                       // 收件人
        message.setSubject(subject);             // 邮件主题
        message.setText(text);                   // 邮件正文
        mailSender.send(message);
        return randomCode; // 返回生成的验证码
    }

}
