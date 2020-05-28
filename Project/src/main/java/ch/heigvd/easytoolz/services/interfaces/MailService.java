package ch.heigvd.easytoolz.services.interfaces;

public interface MailService {
    /**
     * send a simple message
     * @param to the email of the recipient
     * @param subject the name of the subject
     * @param text the text
     */
    void sendSimpleMessage(String to, String subject, String text);
}
