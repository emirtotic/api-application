package com.covidtracker.service.dbServices;

public interface MailSenderService {

    void sendNewMail(String recipient, String subject, String body);

}
