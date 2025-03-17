package com.springdata.demo.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "Additional_data")
public class AdditionalData {

    @MongoId(FieldType.OBJECT_ID)
    private String id;
    @Field("address")
    private String address;
    @Field("state")
    private String state;
    @Field("city")
    private String city;
    @Field("phone_number")
    @Size(max = 10, message = "The phone number must not exceed 10 characters.")
    private String phone_number;
    @Field("email")
    @Indexed(unique = true)
    private String email;
    @Field("login_user_id")
    @Indexed(unique = true)
    @NotBlank
    private String loginUserId;

    public AdditionalData(String id, String address, String state, String city, String phone_number, String email, String loginUserId) {
        this.id = id;
        this.address = address;
        this.state = state;
        this.city = city;
        this.phone_number = phone_number;
        this.email = email;
        this.loginUserId = loginUserId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(String loginUserId) {
        this.loginUserId = loginUserId;
    }
}
