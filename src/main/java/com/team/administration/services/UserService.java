package com.team.administration.services;

import com.team.administration.exceptions.ResourceNotFoundException;
import com.team.administration.models.User;
import com.team.administration.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final String USER_NOT_FOUND_EXCEPTION = "User not found Exception: ";

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<User> getAllUsers(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return userRepository.findAll(pageable);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_EXCEPTION + userId));
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new ResourceNotFoundException(USER_NOT_FOUND_EXCEPTION + user.getId());
        }
        return userRepository.save(user);
    }

    public void removeUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(USER_NOT_FOUND_EXCEPTION + userId);
        }
        userRepository.deleteById(userId);
    }
}
