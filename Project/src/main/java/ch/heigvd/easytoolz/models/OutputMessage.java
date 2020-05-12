package ch.heigvd.easytoolz.models;

public class OutputMessage extends ChatMessage {

    private String time;

    public OutputMessage(final String from, final String text, final String time) {
        setSender(from);
        setContent(text);
        this.time = time;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) { this.time = time; }
}