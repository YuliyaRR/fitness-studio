package by.it_academy.mail.web;

import by.it_academy.mail.core.mail.EmailDetails;
import by.it_academy.mail.service.api.IMailService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping(path = "/mails")
public class MailInnerController {
    private final IMailService mailService;

    public MailInnerController(IMailService mailService) {
        this.mailService = mailService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void sendMail(@Valid @RequestBody EmailDetails emailDetails) {
        mailService.sendSimpleEmail(emailDetails);
    }

}
