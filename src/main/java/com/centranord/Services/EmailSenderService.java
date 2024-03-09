package com.centranord.Services;
import com.centranord.Repository.UserRepository;
import com.centranord.Entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private  UserRepository repository;

    public void sendSimpleEmail(String toEmail, String subject, String body) throws MessagingException {

        Optional<User> userOptional = repository.findByEmail(toEmail);

        if (userOptional.isPresent()) {

            User user = userOptional.get();


            String emailContent = "Bonjour " + user.getLastName() + " " + user.getEmail() + ",<br/><br/>" +
                    "Voici vos informations :<br/>" +
                    "Prénom : " + user.getLastName() + "<br/>" +

                    "Email : " + user.getEmail() + "<br/><br/>" +
                    "Cliquez sur ce bouton pour restarter votre mot de passe :<br/>" +
                    "<a href='http://localhost:4200/restartPssword'><button style='background-color: #4CAF50; border: none; color: white; padding: 15px 32px; text-align: center; text-decoration: none; display: inline-block; font-size: 16px; margin: 4px 2px; cursor: pointer;'>Reset your password</button></a>";


            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            try {
                helper.setTo(toEmail);
                helper.setSubject(subject);
                helper.setText(emailContent, true);
                helper.setFrom("maleksaidi492@gmail.com");
            } catch (MessagingException e) {
                e.printStackTrace();

            }


            mailSender.send(message);
            System.out.println("Mail Send...");
        } else {

            System.out.println("L'utilisateur avec l'adresse e-mail " + toEmail + " n'existe pas.");

        }
    }


}
