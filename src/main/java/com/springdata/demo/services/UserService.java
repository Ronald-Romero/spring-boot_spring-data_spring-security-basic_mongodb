package com.springdata.demo.services;

import com.springdata.demo.entity.AdditionalData;
import com.springdata.demo.entity.LoginUser;
import com.springdata.demo.payload.response.UserRsponse;
import com.springdata.demo.repository.AdditionalDataRepository;
import com.springdata.demo.repository.UserRepository;
import com.springdata.demo.repository.UserRepositoryCustom;
import com.springdata.demo.utilities.Messages;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdditionalDataRepository additionalDataRepository;

    @Autowired
    private UserRepositoryCustom userRepositoryCustom;


    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Map<String, Object>> getAllUsersWithAdditionalData() {
        List<Document> result = userRepositoryCustom.findUsersWithAdditionalData();
        if (result == null || result.isEmpty()) {
            throw new IllegalArgumentException(Messages.getMessage(Messages.MessageKey.NO_USER_DATA_EXISTS));
        }
        return convertToUserResponse(result);
    }

    /*
    * **** Option 1 *****
    *  Query the union of 2 documents (user / Additional_data).
     */
    public List<Map<String, Object>> getUserWithAdditionalDataOption1(String id) {
        List<Document> result = userRepositoryCustom.findUsersWithAdditionalData(id);
        if (result == null || result.isEmpty()) {
            throw new IllegalArgumentException(Messages.getMessage(Messages.MessageKey.USER_ID_NO_FOUND));
        }
        return convertToUserResponse(result);
    }

    /*
     * **** Opción 2 *****
     *  Query the union of 2 documents (user / Additional_data).
     */
    public Map<String, Object> getUserWithAdditionalDataOption2(String id){

        LoginUser loginUser = userRepository.findById(id)
                .map(user -> {
                    user.setPwd("**********");
                    return user;
                })
                .orElseThrow(() -> new IllegalArgumentException(Messages.getMessage(Messages.MessageKey.USER_ID_NO_FOUND)));

            AdditionalData additionalData = additionalDataRepository.findByLoginUserId(loginUser.getId()).orElse(null);
            return new UserRsponse(loginUser,additionalData).getCustomResponse();
    }

    public LoginUser userRegistration(LoginUser user){
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException(Messages.getMessage(Messages.MessageKey.USER_EXISTS, user.getUsername()));
        }
        user.setPwd(passwordEncoder.encode(user.getPwd()));
        return userRepository.save(user);
    }

    public AdditionalData setAdditionalData(AdditionalData data){
        if (!userRepository.existsById(data.getLoginUserId())) {
            throw new RuntimeException(Messages.getMessage(Messages.MessageKey.USER_NO_FOUND));
        }
        if (additionalDataRepository.existsByLoginUserId(data.getLoginUserId())) {
            throw new RuntimeException(Messages.getMessage(Messages.MessageKey.ADDITIONAL_DATA_EXISTS));
        }
        return additionalDataRepository.save(data);
    }

    public String updateAdditionalDate(AdditionalData data){
        if (!additionalDataRepository.existsByLoginUserId(data.getLoginUserId())) {
            throw new RuntimeException(Messages.getMessage(Messages.MessageKey.USER_NO_ADDITIONAL_DATA));
        }
        additionalDataRepository.save(data);
        return Messages.getMessage(Messages.MessageKey.USER_UPDATED);
    }

    public String deleteUser(String id){
        // Validate if the user exists
        LoginUser user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(Messages.getMessage(Messages.MessageKey.USER_NO_FOUND)));

        // We deleted the user
        userRepository.deleteById(id);

        // We check for additional data and delete it if it exists.
        additionalDataRepository.findByLoginUserId(user.getId())
                .ifPresent(additionalData -> additionalDataRepository.deleteById(additionalData.getId()));

        return Messages.getMessage(Messages.MessageKey.USER_DELETE,user.getUsername());
    }

    // Method to convert the list of documents to UserResponse
    private List<Map<String, Object>> convertToUserResponse(List<Document> result) {
        // Iterate and map each document to the UserResponse object
        return result.stream()
                .map(document -> {
                    // Extraer datos del documento para LoginUser
                    LoginUser user = new LoginUser(
                            document.getString("id"),
                            document.getString("username"),
                            document.getString("pwd"),
                            document.getList("roles", String.class)
                    );
                    // Extract data from the document for AdditionalData
                    Document additionalInfoList = ((List<Document>) document.get("additionalData")).get(0);

                    AdditionalData additionalData = new AdditionalData(
                            additionalInfoList.getObjectId("_id").toString(),
                            additionalInfoList.getString("address"),
                            additionalInfoList.getString("state"),
                            additionalInfoList.getString("city"),
                            additionalInfoList.getString("phone_number"),
                            additionalInfoList.getString("email"),
                            additionalInfoList.getString("login_user_id")
                    );

                    // Construct and return UserResponse
                    return new UserRsponse(user, additionalData).getCustomResponse();
                })
                .collect(Collectors.toList()); // Collect the UserResponse list
    }
}
