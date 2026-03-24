package com.cognizant.greencity.dto.user;

import jakarta.validation.constraints.Size;

public class UserUpdateRequest {
    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String phone;

    @Size(max = 255)
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

