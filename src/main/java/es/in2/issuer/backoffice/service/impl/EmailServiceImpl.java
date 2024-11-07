package es.in2.issuer.backoffice.service.impl;

import es.in2.issuer.backoffice.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Override
    public void sendTransactionCodeForCredentialOffer(String to, String link, String firstName) throws MessagingException {
        firstName = firstName.replace("\"", "");
        final String finalName = firstName;


            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom("digitalidentitysupport@dome-marketplace.eu");
            helper.setTo(to);
            helper.setSubject("Credential Offer");

            Context context = new Context();
            context.setVariable("link", link);
            context.setVariable("name", finalName);
            context.setVariable("walletUrl", "https://wallet-url.com"); // todo: use wallet url from config
            String htmlContent = templateEngine.process("credential-offer-email", context);
            helper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);
    }

}
