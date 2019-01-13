package com.betroc.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity
public class VerificationToken {

    private static final int EXPIRATION = 60 * 24;//TODO to put in the application.properties

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @NotNull
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    @NotNull
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expiryDate;

    //this failed user only when email modification
    @Size(max = 40)
    @Email
    private String newEmail;

    public VerificationToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.expiryDate = this.calculateExpiryDate(EXPIRATION);
        this.newEmail = "";
    }

    public VerificationToken( String token, User user, String newEmail) {
        this.token = token;
        this.user = user;
        this.newEmail = newEmail;
        this.expiryDate = this.calculateExpiryDate(EXPIRATION);
    }

    public VerificationToken(){}

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }
}
