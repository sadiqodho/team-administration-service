package com.team.administration.controllers;

import com.team.administration.models.User;
import com.team.administration.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "api/v1/users")
@Validated
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get all users with pagination
     *
     * @param pageNo   current page number
     * @param pageSize size of the page
     * @return Page with Generic type of User
     */
    @GetMapping
    public Page<User> getAllUsers(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return userService.getAllUsers(pageNo, pageSize);
    }

    /**
     * Get user by Id
     *
     * @param userId (long data type)
     * @return ResponseEntity with User content,
     * responses return with HTTP status codes 200 (user found),
     * 404 (user not found)
     */
    @GetMapping(value = "{userId}")
    public ResponseEntity<User> getUserById(
            @PathVariable(name = "userId") Long userId
    ) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    /**
     * Add User, with content type JSON
     *
     * @param user having properties firstName, lastName(required), position, gitHubProfileURL(unique)
     * @return ResponseEntity with User content if successfully added,
     * if user does not exist, it will return 404 with string message
     */
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody @Valid User user) {
        return ResponseEntity.ok(userService.addUser(user));
    }

    /**
     * Update existing user with given userId
     *
     * @param user contains id, firstName, lastName(required), position, gitHubProfileURL(unique)
     * @return ResponseEntity with User content if successfully updated
     * if userId does not exist, it will return 404 response
     */
    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody @Valid User user) {
        return ResponseEntity.ok(userService.updateUser(user));
    }

    /**
     * Remove existing user with given userId
     *
     * @param userId (long data type)
     */
    @DeleteMapping(value = "{userId}")
    public void deleteUser(@PathVariable(name = "userId") Long userId) {
        userService.removeUser(userId);
    }

}