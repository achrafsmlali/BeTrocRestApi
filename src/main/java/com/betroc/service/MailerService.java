package com.betroc.service;

public interface MailerService {
    public void sendSimpleMessage(String to, String subject, String text);
}
