package com.springdata.demo.payload.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springdata.demo.entity.AdditionalData;
import com.springdata.demo.entity.LoginUser;

import java.util.HashMap;
import java.util.Map;

public class UserRsponse {

    private LoginUser user;
    private AdditionalData additionalData;

    public UserRsponse(LoginUser user, AdditionalData additionalData) {
        this.user = user;
        this.additionalData = additionalData;
    }

    public LoginUser getUser() {
        return user;
    }

    public void setUser(LoginUser user) {
        this.user = user;
    }

    public AdditionalData getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(AdditionalData additionalData) {
        this.additionalData = additionalData;
    }

    // Custom method to transform JSON structure
    public Map<String, Object> getCustomResponse() {
        Map<String, Object> response = new HashMap<>();

        // Serialize the `user` object into a map (JSON)
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> userMap = objectMapper.convertValue(user, Map.class);

        // Add `additionalData` inside the `user` map
        userMap.put("additionalData", additionalData);

        // Include the complete `user` object in the final response
        response.put("user", userMap);
        return response;
    }
}
