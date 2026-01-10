package com.sbecom.service;

import com.sbecom.dto.AddressDTO;
import com.sbecom.dto.UserRequest;
import com.sbecom.dto.UserResponse;
import com.sbecom.model.Address;
import com.sbecom.model.User;
import com.sbecom.repository.UserRepo;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
public class UserService {

    private final UserRepo userRepo;

    public void addUser(UserRequest userRequest) {
        User user = new User();
        updateUserFromUserRequest(userRequest,user);
        userRepo.save(user);
    }

    public List<UserResponse> getAllUsers() {
        return userRepo.findAll()
                .stream()
                .map(this::mapToUserResponse)
                .toList();
    }

    public Optional<UserResponse> getUser(Long id) {
        return userRepo.findById(id)
                .map(this::mapToUserResponse);
    }

    public boolean updateUser(UserRequest userRequest,Long id) {
        return userRepo.findById(id)
                .map(existingUser -> {
                    updateUserFromUserRequest(userRequest,existingUser);
                    userRepo.save(existingUser);
                    return true;
                })
                .orElse(false);
    }

    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    public void updateUserFromUserRequest(UserRequest userRequest,User user){
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        if (userRequest.getAddress() != null){
            Address address = new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setState(userRequest.getAddress().getState());
            address.setZipcode(userRequest.getAddress().getZipcode());
            address.setCity(userRequest.getAddress().getCity());
            address.setCountry(userRequest.getAddress().getCountry());
            user.setAddress(address);

        }

    }
    private UserResponse mapToUserResponse(User user){
        UserResponse response = new UserResponse();
        response.setId(String.valueOf(user.getId()));
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());

        if (user.getAddress() != null) {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipcode(user.getAddress().getZipcode());
            response.setAddress(addressDTO);
        }
        return response;
    }

}
