package azmiu.library.controller;

import azmiu.library.service.abstraction.MailService;
import azmiu.library.service.concurate.MailServiceHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class MailController {


    private final MailService mailService;

    @PostMapping("/send")
    public String sendEmail() {
        mailService.sendMail("javadevoloper8@gmail.com", "Test Subject", "Test Body");
        return "Mail göndərildi!";
    }
}
