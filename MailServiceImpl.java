package com.project.demo.service.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.project.demo.model.mail.Mail;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class MailServiceImpl {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private Configuration freemarkerConfig;

    public void sendSimpleMessage(Mail mail,String flag) throws MessagingException, IOException, TemplateException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,StandardCharsets.UTF_8.name());

        //helper.addAttachment("logo.png", new ClassPathResource("memorynotfound-logo.png"));

        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");
        Template t=null;
        if("post".equalsIgnoreCase(flag)) {
        	t = freemarkerConfig.getTemplate("post-email-template.ftl");
        }
        else if("invest".equalsIgnoreCase(flag)) {
        	t = freemarkerConfig.getTemplate("invest-email-template.ftl");
        }
        	
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, mail.getModel());

        helper.setTo(mail.getTo());
        helper.setText(html, true);
        helper.setSubject(mail.getSubject());
        helper.setFrom(mail.getFrom());

        emailSender.send(message);
    }

}
