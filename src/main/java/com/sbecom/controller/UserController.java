package com.sbecom.controller;

import com.sbecom.model.User;
import com.sbecom.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(("/api/user"))
@Data
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody User user){
        userService.addUser(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User Added Successfully");
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getAllUsers());
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUser(id));
    }

    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody User user){
        userService.updateUser(user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("User Updated Successfully");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("User Deleted Successfully");
    }


}
