package by.it_academy.mail.web;

import by.it_academy.mail.core.mail.EmailDetails;
import by.it_academy.mail.service.api.IMailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping(path = "/mails")
@RequiredArgsConstructor
public class MailInnerController {
    private final IMailService mailService;
    @RequestMapping(method = RequestMethod.POST)
    public void saveMail(@Valid @RequestBody EmailDetails emailDetails) {
        mailService.save(emailDetails);
    }

}
