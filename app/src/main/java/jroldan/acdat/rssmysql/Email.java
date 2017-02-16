package jroldan.acdat.rssmysql;

public class Email {
    private String from;
    private String password;
    private String to;
    private String subject;
    private String message;
    public Email(String from, String password, String to, String subject, String message) {
        super();
        this.from = from;
        this.password = password;
        this.to = to;
        this.subject = subject;
        this.message = message;
    }
    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getTo() {
        return to;
    }
    public void setTo(String to) {
        this.to = to;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}