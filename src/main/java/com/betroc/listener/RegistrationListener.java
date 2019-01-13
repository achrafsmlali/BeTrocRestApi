package com.betroc.listener;

import com.betroc.event.OnRegistrationCompleteEvent;
import com.betroc.model.User;
import com.betroc.model.VerificationToken;
import com.betroc.repository.VerificationTokenRepository;
import com.betroc.service.MailerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    @Autowired
    private MailerService mailerService;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {

        User user = event.getUser();
        String token = UUID.randomUUID().toString();

        //if is't about updating an email of an existing account
        String newEmail = event.getNewEmail();

        VerificationToken verificationToken;
        String recipientAddress;
        String subject;

        if (newEmail.equals("")) { //creating a token for email verification for a new user
            verificationToken = new VerificationToken(token, user);
            recipientAddress = user.getEmail();
            subject = "Registration Confirmation";
        }else { //creating a token for email update of an existing user
            verificationToken = new VerificationToken(token, user, newEmail);
            recipientAddress = newEmail;
            subject = "New mail Confirmation";
        }

        verificationTokenRepository.save(verificationToken);

        String confirmationUrl
                = event.getAppUrl()+"/confirmRegistrationOrEmailUpdate?token=" + token;

        mailerService.sendSimpleMessage(recipientAddress,subject, confirmationUrl);

    }


}
