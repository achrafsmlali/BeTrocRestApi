package com.betroc.event;

import com.betroc.model.User;
import org.springframework.context.ApplicationEvent;

public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private User user;

    private String appUrl;

    public OnRegistrationCompleteEvent(User user,String url) {
        super(user);
        this.appUrl = url;
        this.user = user;

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
}
