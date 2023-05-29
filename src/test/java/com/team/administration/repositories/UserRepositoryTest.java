package com.team.administration.repositories;

import com.team.administration.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    private String URL = "https://github.com/xyz";
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setFirstName("MyFirstName");
        user.setLastName("MyLastName");
        user.setPosition("MyPosition");
        user.setGitHubProfileURL(URL);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void doesGitHubProfileURLExits() {
        userRepository.save(user);
        boolean expected = userRepository.doesGitHubProfileURLExits(URL);
        assertThat(expected).isTrue();
    }

    @Test
    void doesNotGitHubProfileURLExits() {
        boolean expected = userRepository.doesGitHubProfileURLExits(URL);
        assertThat(expected).isFalse();
    }

    @Test
    void doesGitHubProfileURLExitsWithUserId() {
        userRepository.save(user);
        boolean expected = userRepository.doesGitHubProfileURLExits(URL, user.getId());
        assertThat(expected).isFalse();
    }
}