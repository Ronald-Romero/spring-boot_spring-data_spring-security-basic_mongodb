package com.springdata.demo.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document(collection = "users")
public class LoginUser {

    @MongoId(FieldType.OBJECT_ID)
    private String id;
    @Field("username")
    @Indexed(unique = true)
    @NotBlank
    private String username;
    @Field("pwd")
    @NotBlank
    private String pwd;
    @Field("roles")
    @NotEmpty
    private List<String> roles;

    public LoginUser(String id, String username, String pwd, List<String> roles) {
        this.id = id;
        this.username = username;
        this.pwd = pwd;
        this.roles = roles;
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

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
