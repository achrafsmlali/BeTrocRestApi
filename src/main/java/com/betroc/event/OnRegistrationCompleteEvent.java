package com.betroc.event;

import com.betroc.model.User;
import org.springframework.context.ApplicationEvent;
//this class is useed to confirm the user email when creating a new account or when modifying an exiting account email to reconfirm the email
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private User user;

    private String appUrl;

    //this field is used only when email modification
    private String  newEmail;

    public OnRegistrationCompleteEvent(User user,String url) {
        super(user);
        this.appUrl = url;
        this.user = user;
        this.newEmail ="";
    }

    public OnRegistrationCompleteEvent( User user, String appUrl, String newEmail) {
        super(user);
        this.user = user;
        this.appUrl = appUrl;
        this.newEmail = newEmail;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }
}
