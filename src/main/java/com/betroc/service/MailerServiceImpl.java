package com.betroc.service;

import com.betroc.model.Image;
import com.betroc.model.Mail;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MailerServiceImpl implements MailerService {

    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    private ImageStorageService imageStorageService;

    @Qualifier("freeMarkerConfiguration")
    @Autowired
    private Configuration freemarkerConfig;


    @Override
    public void sendSimpleMessage(String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);

    }




    @Override
    public void sendSimpleMessageWithHtml(Mail mail, List<String> imageName) throws IOException, TemplateException, MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        if(imageName.size()>0) {
            Resource resource= null;
            for(String img: imageName) {
                resource = imageStorageService.loadFileAsResource(img);
                helper.addAttachment("image.jpg", resource);
            }
        }

        Template t = freemarkerConfig.getTemplate("emailValidateAd.html");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, mail.getModel());

        helper.setTo(mail.getTo());
        helper.setText(html, true);
        helper.setSubject(mail.getSubject());
        helper.setFrom(mail.getFrom());

        emailSender.send(message);
    }

    @Override
    public void preparingEmailAndSend(String sendTo, String subject, String title, String description, String categorie,
                                      List<String> imgUrl, long id, String uriOfRequest, String serverURL) throws TemplateException, IOException, MessagingException {
        Mail mail = new Mail();
        mail.setFrom("no-reply@betroc.com");
        mail.setTo(sendTo);
        mail.setSubject(subject);

        Map model = new HashMap();
        model.put("Title", title);
        model.put("Description", description);
        model.put("idAnnonce", id);
        model.put("categorie", categorie);
        model.put("uriOfRequest", uriOfRequest);
        model.put("serverURL", serverURL);
        //model.put("signature", "https://memorynotfound.com");
        mail.setModel(model);

        this.sendSimpleMessageWithHtml(mail, imgUrl);
    }
}
