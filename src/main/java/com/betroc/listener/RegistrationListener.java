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

        VerificationToken verificationToken = new VerificationToken(token, user);
        verificationTokenRepository.save(verificationToken);

        String recipientAddress = user.getEmail();

        String subject = "Registration Confirmation";

        String confirmationUrl
                = event.getAppUrl()+"/registrationConfirm?token=" + token;

        mailerService.sendSimpleMessage(recipientAddress,subject, confirmationUrl);

    }


}
