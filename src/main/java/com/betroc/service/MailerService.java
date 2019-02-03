package com.betroc.service;

import com.betroc.model.Image;
import com.betroc.model.Mail;
import freemarker.template.TemplateException;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public interface MailerService {
    public void sendSimpleMessage(String to, String subject, String text);

    void sendSimpleMessageWithHtml(Mail mail, List<String> imageName) throws IOException, TemplateException, MessagingException;

    void preparingEmailAndSend(String sendTo, String subject, String title,
                               String description, String categorie, List<String> imgUrl,
                               long id, String uriOfRequest, String serverURL) throws TemplateException, IOException, MessagingException;

}
