package es.in2.issuer.backoffice.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private TemplateEngine templateEngine;

    @InjectMocks
    private EmailServiceImpl emailService;

    @Test
    void testSendTransactionCodeForCredentialOffer() throws MessagingException {
        // Arrange
        String recipient = "test@example.com";
        String link = "http://example.com";
        String firstName = "\"John\"";
        String emailContent = "<html>Email Content</html>"; // Mocked template result

        // Mock the MimeMessage
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        // Mock TemplateEngine's process method to return the mocked email content
        when(templateEngine.process(eq("credential-offer-email"), any(Context.class))).thenReturn(emailContent);

        // Act
        emailService.sendTransactionCodeForCredentialOffer(recipient, link, firstName);

        // Assert
        // Verify that javaMailSender.createMimeMessage() was called once
        verify(javaMailSender, times(1)).createMimeMessage();

        // Verify that templateEngine.process was called with the expected template and context
        verify(templateEngine, times(1)).process(eq("credential-offer-email"), any(Context.class));

        // Verify that the email was sent using javaMailSender
        verify(javaMailSender, times(1)).send(mimeMessage);
    }
}