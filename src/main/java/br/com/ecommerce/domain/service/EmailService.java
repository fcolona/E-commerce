package br.com.ecommerce.domain.service;

import br.com.ecommerce.domain.model.User;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@EnableAsync
@ConditionalOnExpression(value = "!'${spring.mail.username}'.isBlank()")
public class EmailService {
    private Environment env;

    @Async
    public SimpleMailMessage constructEmail(String subject, String body, User user) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        email.setFrom(env.getProperty("spring.mail.username"));
        return email;
    }
}
