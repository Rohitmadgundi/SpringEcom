package com.sbecom.controller;

import com.sbecom.dto.UserRequest;
import com.sbecom.dto.UserResponse;
import com.sbecom.model.User;
import com.sbecom.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Data
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody UserRequest userRequest){
        userService.addUser(userRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User Added Successfully");
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        List<UserResponse> users = userService.getAllUsers();

        if(!users.isEmpty())
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(users);
        else
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(null);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id){
        return userService.getUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateUser(@RequestBody UserRequest user,
                                             @PathVariable Long id){
        boolean updated = userService.updateUser(user,id);
        return updated ? ResponseEntity.ok("User updated successfully")
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("User Deleted Successfully");
    }


}
