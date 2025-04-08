package azmiu.library.service.abstraction;

public interface MailService {
    void sendMail(String to, String subject, String text);
}
