package de.ssherlock.control.backing;

import de.ssherlock.business.service.MailService;
import de.ssherlock.global.transport.Mail;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.logging.Logger;
@Named
@RequestScoped
public class RegistrationBean {

    private String userName;
    private String firstName;
    private String lastName;
    private String passWord;
    private String email;
    @Inject
    private Logger logger;
    @Inject
    private MailService mailService;

    public RegistrationBean() {}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void sendMail() {
        Mail mail = new Mail(email, userName);
        mailService.sendMail(mail);
    }
}
