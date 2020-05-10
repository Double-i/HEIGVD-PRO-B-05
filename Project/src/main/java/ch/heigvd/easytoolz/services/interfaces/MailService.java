package ch.heigvd.easytoolz.services.interfaces;

public interface MailService {
    void sendSimpleMessage(String to, String subject, String text);
}
