package by.it_academy.fitnessstudio.service;


import by.it_academy.fitnessstudio.core.dto.EmailDetails;
import by.it_academy.fitnessstudio.repositories.api.MailEntityRepository;
import by.it_academy.fitnessstudio.service.api.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MailService implements IMailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String username;// = "final.project.jd2@gmail.com";

    private final MailEntityRepository repository;


    //private ExecutorService executorService = Executors.newFixedThreadPool(5);

    public MailService(MailEntityRepository repository) {
        this.repository = repository;
    }



    @Override
    public void sendSimpleEmail(EmailDetails emailDetails) {
        //TODO: написать отправку почту в параллельном потоке

        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(username);
            mailMessage.setTo(emailDetails.getRecipient());
            mailMessage.setText(emailDetails.getMsgBody());
            mailMessage.setSubject(emailDetails.getSubject());

            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            throw e;
        }

    }

  //  @Override
   /* public MailCodeEntity get(String mail) {
        if(mail == null || mail.isBlank() || mail.isEmpty()){
            throw new InvalidInputServiceMultiException("Email not entered");//TODO:изменить на помещение в список ошибок
        }

        Optional<MailCodeEntity> mailCodeById = repository.findById(mail);

        if (mailCodeById.isEmpty()) {
            throw new NotFoundDataBaseException("This email was not found in the database");//TODO:изменить на помещение в список ошибок
        }

        return mailCodeById.get();


    }*/
}
