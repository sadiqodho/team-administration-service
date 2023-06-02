package com.team.administration.services;

import com.team.administration.enums.Position;
import com.team.administration.exceptions.ResourceNotFoundException;
import com.team.administration.models.User;
import com.team.administration.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    private UserService userService;
    private User user;

    private final String USER_NOT_FOUND_EXCEPTION = "User not found Exception: ";

    @BeforeEach
    void setUp() {
        userService = new UserService(this.userRepository);
        user = new User();
        user.setId(1L);
        user.setFirstName("MyFirstName");
        user.setFirstName("MyLastName");
        user.setPosition(Position.DEVELOPER);
        user.setGitHubProfileURL("https://github.com/xyz");
    }

    @Test
    void canGetAllUsers() {
        Pageable pageable = PageRequest.of(1, 10);
        userService.getAllUsers(pageable.getPageNumber(), pageable.getPageSize());
        verify(userRepository).findAll(pageable);
    }

    @Test
    void getUserByIdFound() {
        BDDMockito.given(userRepository.findById(anyLong()))
                .willReturn(Optional.of(user));
        userService.getUserById(anyLong());
        verify(userRepository).findById(anyLong());
    }

    @Test
    void getUserByIdNotFoundException() {
        BDDMockito.given(userRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        assertThatThrownBy(()-> userService.getUserById(anyLong()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(USER_NOT_FOUND_EXCEPTION);
    }

    @Test
    void canAddUser() {
        userService.addUser(user);
        ArgumentCaptor<User> userArgumentCaptor =
                ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser).isEqualTo(user);
    }

    @Test
    void updateUserNotFoundException() {
        BDDMockito.given(userRepository.existsById(anyLong()))
                .willReturn(false);
        assertThatThrownBy(()-> userService.updateUser(user))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(USER_NOT_FOUND_EXCEPTION);
        verify(userRepository, never()).save(any());
    }

    @Test
    void cabUpdateUser() {
        BDDMockito.given(userRepository.existsById(anyLong()))
                .willReturn(true);

        userService.updateUser(user);
        ArgumentCaptor<User> userArgumentCaptor =
                ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser).isEqualTo(user);
    }

    @Test
    void removeUserNotFoundException() {
        BDDMockito.given(userRepository.existsById(anyLong()))
                .willReturn(false);
        assertThatThrownBy(()-> userService.removeUser(user.getId()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(USER_NOT_FOUND_EXCEPTION);
        verify(userRepository, never()).save(any());
    }

    @Test
    void canRemoveUser() {
        BDDMockito.given(userRepository.existsById(anyLong()))
                .willReturn(true);
        userService.removeUser(anyLong());
        verify(userRepository).deleteById(anyLong());
    }
}