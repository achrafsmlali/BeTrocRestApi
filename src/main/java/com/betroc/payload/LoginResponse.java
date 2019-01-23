package com.betroc.payload;

import javax.validation.constraints.NotBlank;

public class LoginResponse {

    @NotBlank
    private long idUser;

    @NotBlank
    private String jwt;

    @NotBlank
    private String tokenType = "Bearer";

    public LoginResponse(@NotBlank long idUser, @NotBlank String jwt) {
        this.idUser = idUser;
        this.jwt = jwt;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
