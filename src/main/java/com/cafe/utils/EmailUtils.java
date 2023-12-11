package com.cafe.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class EmailUtils {
  /* JavaMailSender : interface spring pour envoyer les msg */
    @Autowired
    private JavaMailSender emailSender ;

    public void sendSimpleMsg(String des , String subject , String msg , List<String> list){
        /* SimpleMailMessage : utilisé pour les msg simples que texte */
        SimpleMailMessage message = new SimpleMailMessage() ;
       message.setFrom("amalghannoudi@gmail.com");
       message.setTo(des);
       message.setSubject(subject);
       message.setText(msg);
       if (list != null && list.size()>0)
            message.setCc(getCcArray(list));
        emailSender.send(message);

    }
    private String[] getCcArray(List<String> ccList){
        String[] cc = new String[ccList.size()];
        for (int i=0 ; i<ccList.size();i++){
            cc[i] =ccList.get(i) ;
        }
        return  cc ;
    }
    public void forgotMail(String des,String subject,String password)throws MessagingException{
        /* MimeMessage : est une classe de Java Mail API qui représente un message MIME: peut contenir des structures complexes
        tq pieces jointe , structure html ..)
         */
        /*helper: une classe d'assistance fournie par Spring pour simplifier la construction d'objets MimeMessage*/
        MimeMessage message = emailSender.createMimeMessage() ;
        MimeMessageHelper helper = new MimeMessageHelper(message,true) ;
        helper.setFrom("amalghannoudi@gmail.com");
        helper.setTo(des);
        helper.setSubject(subject);
        String htmlMsg="<p><b>Your Login details for Cafe Management System</b><br><b>Email: </b>  "+ des + " <br><b>Password: </b> " + password + "<br><a href=\\\"http://localhost:4200/\\\">Click here to login</a></p>" ;
       message.setContent(htmlMsg,"text/html");
       emailSender.send(message);
    }
}
