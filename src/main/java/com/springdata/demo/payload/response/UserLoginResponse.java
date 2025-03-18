package com.springdata.demo.payload.response;

import com.springdata.demo.entity.LoginUser;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class UserLoginResponse {

    @NotBlank
    private String id;
    @NotBlank
    private String username;
    @NotBlank
    private List<String> roles;

    public UserLoginResponse(LoginUser user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.roles = user.getRoles();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
