package com.springdata.demo.controllers;

import com.springdata.demo.entity.AdditionalData;
import com.springdata.demo.entity.LoginUser;
import com.springdata.demo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Gets the list of all users along with their additional data.
     *
     * @return ResponseEntity containing a list of users enriched with their
     *         additional data in case of success, or an appropriate error message in
     *         case of failure.
     *
     * Example of request:
     * GET /api/admin/all
     *
     * Example of a successful response:
     * [
     *   {
     *     "user": {
     *       "id": "67cc84138f9fd492440ff1cc",
     *       "username": "Ronald",
     *       "email": "ronald@prueba.com",
     *       "pwd": "**********"
     *     },
     *     "additionalData": {
     *       "id": "67cf01bb1655a3b0548c17eb",
     *       "address": "Street 1 with Street 2",
     *       "state": "Example state",
     *       "city": "Example city",
     *       "phone_number": "0123456789",
     *       "loginUserId": "67cc84138f9fd492440ff1cc"
     *     }
     *   },
     *   {
     *     "user": {
     *       "id": "67aa12345aabbccddee",
     *       "username": "Maria",
     *       "email": "maria@prueba.com",
     *       "pwd": "**********"
     *     },
     *     "additionalData": {
     *       "id": "67cf67890bbccccdd123",
     *       "address": "Street 3 with Avenue 4",
     *       "state": "Another state",
     *       "city": "Another city",
     *       "phone_number": "0987654321",
     *       "loginUserId": "67aa12345aabbccddee"
     *     }
     *   }
     * ]
     */
    @GetMapping("/admin/all")
    public ResponseEntity<?> getAllUser(){
        return ResponseEntity.ok(userService.getAllUsersWithAdditionalData());
    }

    /**
     * Obtains additional information about a user by their 'id' identifier.
     *
     * @param id Identificador único del usuario a consultar. Este valor debe ser
     *           proporcionado como parte de la URL del endpoint.
     * @return ResponseEntity containing additional user data in case of success,
     *         or an appropriate error message in case of failure.
     *
     * Example of request:
     * GET /api/user/infoone/{id}
     *
     * Example of a successful response:
     * {
     *	  "user": {
     *		 "id": "67cc84138f9fd492440ff1cc",
     *		 "username": "Ronald",
     *		 "email": "ronald@prueba.com",
     *		 "pwd": "**********"
     *		 "additionalData": {
     *		 	"id": "67cf01bb1655a3b0548c17eb",
     *		 	"address": "Street 1 with Street 2",
     *		 	"state": "Example state",
     *		 	"city": "Example city",
     *		 	"phone_number": "0123456789",
     *		 	"loginUserId": "67cc84138f9fd492440ff1cc"
     *		 }
     *	  }
     * }
     */
    @GetMapping("/user/infoone/{id}")
    public ResponseEntity<?> getInfoUser_1(@PathVariable final String id){
        return ResponseEntity.ok(userService.getUserWithAdditionalDataOption1(id));
    }

    /**
     * Obtains additional information about a user by their 'id' identifier.
     *
     * @param id Unique identifier of the user to query. This value must be
     *           provided as part of the endpoint URL.
     * @return ResponseEntity containing additional user data in case of success,
     *         or an appropriate error message in case of failure.
     *
     * Example of request:
     * GET /api/user/infotwo/{id}
     *
     * Example of a successful response:
     * {
     *	  "user": {
     *		 "id": "67cc84138f9fd492440ff1cc",
     *		 "username": "Ronald",
     *		 "email": "ronald@prueba.com",
     *		 "pwd": "**********"
     *		 "additionalData": {
     *		 	"id": "67cf01bb1655a3b0548c17eb",
     *		 	"address": "Street 1 with Street 2",
     *		 	"state": "Example state",
     *		 	"city": "Example city",
     *		 	"phone_number": "0123456789",
     *		 	"loginUserId": "67cc84138f9fd492440ff1cc"
     *		 }
     *	  }
     * }
     */
    @GetMapping("/user/infotwo/{id}")
    public ResponseEntity<?> getInfoUser_2(@PathVariable final String id){
        return ResponseEntity.ok(userService.getUserWithAdditionalDataOption2(id));
    }


    /**
     * Endpoint to establish additional data associated with a user.
     *
     * @param data Object of type AdditionalData received in the request body (RequestBody).
     *        This object contains the additional information to be associated with the user and is
     *        automatically validated using the @Valid annotation to ensure that it meets the defined constraints.
     * @return Returns a ResponseEntity with the result of the operation:
     *         - 141 / 5,000
     * If the operation is successful, it returns the result provided by the
     *           userService.setAdditionalData(data) method with an HTTP code 200 (OK).
     *
     * Example of request:
     * POST /api/user/additionaldata
     */
    @PostMapping("/user/additionaldata")
    public ResponseEntity<?> setAdditionalData(@Valid @RequestBody AdditionalData data){
        return ResponseEntity.ok(userService.setAdditionalData(data));
    }

    /**
     * Endpoint to update additional data associated with a user.
     *
     * @param data Object of type AdditionalData received in the request body (RequestBody).
     *        This object contains the additional information to be updated for the user
     *        and is automatically validated using the @Valid annotation to
     *        ensure it meets the defined constraints.
     * @return Returns a ResponseEntity with the result of the operation:
     *         - If the operation is successful, it returns the result provided by the
     *           userService.updateAdditionalDate(data) method with an HTTP code 200 (OK).
     *
     * Example of request:
     * PUT /api/user/update/additionaldata
     */
    @PutMapping("/user/update/additionaldate")
    public ResponseEntity<?> updateAdditionalDate(@Valid @RequestBody AdditionalData data){
        return ResponseEntity.ok(userService.updateAdditionalDate(data));
    }

    /**
     * Endpoint to delete a specific user identified by their ID.
     *
     * @param id Unique identifier of the user to be deleted, received as a path variable.
     *        This parameter is automatically validated using the @Valid annotation
     *        to ensure it meets the defined requirements.
     * @return Returns a ResponseEntity with the result of the operation:
     *         - If the operation is successful, it returns the result provided by the
     *           userService.deleteUser(id) method with an HTTP code 200 (OK).
     *
     * Example of request:
     * DELETE /api/user/delete/{id}
     */
    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<?> deleteUser(@Valid @PathVariable String id){
        return ResponseEntity.ok(userService.deleteUser(id));
    }
}
