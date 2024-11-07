package es.in2.issuer.backoffice.service;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendTransactionCodeForCredentialOffer(String to, String link, String firstName) throws MessagingException;
}
