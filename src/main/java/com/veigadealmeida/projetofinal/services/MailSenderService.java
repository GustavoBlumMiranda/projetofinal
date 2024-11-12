package com.veigadealmeida.projetofinal.services;

import com.veigadealmeida.projetofinal.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MailSenderService {
    private final JavaMailSender mailSender;

    @Autowired
    public MailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPasswordResetEmail(String appUrl, Locale locale, String token, Usuario user) {
        String resetLink = appUrl + "/resetsenha/exibir?token=" + token;
        String emailContent = "Clique no seguinte link para definir uma nova senha: " + resetLink;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Alteração de senha");
        message.setText(emailContent);

        mailSender.send(message);
    }
}
