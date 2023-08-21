package com.inventorysystem.api.dto;


import org.springframework.stereotype.Component;

@Component
public class ResetDto {

    private String username;
    private String safetyPin;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSafetyPin() {
        return safetyPin;
    }

    public void setSafetyPin(String safetyPin) {
        this.safetyPin = safetyPin;
    }
}
