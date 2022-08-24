package com.emard.service;

import com.emard.dao.FakeDao;
import com.emard.model.User;
import com.emard.utils.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.emard.utils.Gender.FEMALE;
import static com.emard.utils.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


class UserServiceTest {

    @Mock
    private FakeDao fakeDao;
    private UserService userService;
    @BeforeEach
    void setUp() {
        //initMocks deprecated
        MockitoAnnotations.openMocks(this);
        userService = new UserService(fakeDao);
    }

    @Test
    void shouldGetAllUsers() {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "Anna", "Montana", FEMALE,
                30, "anna.montana@gmail.com");
        List<User> users = List.of(user);
        given(fakeDao.selectAllUsers()).willReturn(users);

        List<User> allUsers = userService.getAllUsers(Optional.empty(), Optional.empty());
        assertThat(allUsers).hasSize(1);
        assertThat(allUsers.get(0)).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    void shouldGetAllUsersByParams() {
        UUID userId = UUID.randomUUID();
        User anna = new User(userId, "Anna", "Montana", FEMALE,
                30, "anna.montana@gmail.com");
        User joe = new User(userId, "Joe", "Doe", MALE,
                42, "joe.foe@gmail.com");
        List<User> users = List.of(anna, joe);
        given(fakeDao.selectAllUsers()).willReturn(users);

        List<User> allUsers = userService.getAllUsers(Optional.of("MALE"), Optional.of(100));
        assertThat(allUsers).hasSize(1);
        assertThat(allUsers.get(0)).usingRecursiveComparison().isEqualTo(joe);
    }

    @Test
    void shouldGetUser() {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "Anna", "Montana", FEMALE,
                30, "anna.montana@gmail.com");
        given(fakeDao.selectUser(userId)).willReturn(Optional.of(user));
        Optional<User> optionalUser = userService.getUser(userId);
        assertThat(optionalUser.isPresent()).isTrue();
        assertThat(optionalUser.get()).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    void shouldUpdateUser() {
        UUID userId = UUID.randomUUID();
        User annaUser = new User(userId, "Anna", "Montana", FEMALE,
                30, "anna.montana@gmail.com");

        given(fakeDao.selectUser(userId)).willReturn(Optional.of(annaUser));
        given(fakeDao.updateUser(annaUser)).willReturn(1);
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        int resul = userService.updateUser(annaUser);
        //pour verif si cett method a été appelé
        verify(fakeDao).selectUser(userId);
        verify(fakeDao).updateUser(captor.capture());
        assertThat(resul).isEqualTo(1);

        User user = captor.getValue();
        assertThat(user).usingRecursiveComparison().isEqualTo(annaUser);
    }

    @Test
    void shouldNotUpdateUser() {
        UUID userId = UUID.randomUUID();
        User annaUser = new User(userId, "Anna", "Montana", FEMALE,
                30, "anna.montana@gmail.com");

        given(fakeDao.selectUser(userId)).willReturn(Optional.empty());
        given(fakeDao.updateUser(annaUser)).willReturn(1);
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        try{
            userService.updateUser(annaUser);
        }catch (NotFoundException e) {
            assertThat(e).isNotNull();
        }

    }

    @Test
    void shouldRemoveUser() {
        UUID userId = UUID.randomUUID();
        User annaUser = new User(userId, "Anna", "Montana", FEMALE,
                30, "anna.montana@gmail.com");

        given(fakeDao.selectUser(userId)).willReturn(Optional.of(annaUser));
        given(fakeDao.removeUser(userId)).willReturn(1);
        int resul = userService.removeUser(userId);
        //pour verif si cett method a été appelé
        verify(fakeDao).selectUser(userId);
        verify(fakeDao).removeUser(userId);
        assertThat(resul).isEqualTo(1);

    }

    @Test
    void shouldInsertUser() {
        UUID userId = UUID.randomUUID();
        User annaUser = new User(userId, "Anna", "Montana", FEMALE,
                30, "anna.montana@gmail.com");

        given(fakeDao.insertUser(any(UUID.class), any(User.class))).willReturn(1);
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        int result = userService.insertUser(annaUser);
        assertThat(result).isEqualTo(1);

        verify(fakeDao).insertUser(eq(userId), captor.capture());
        User user = captor.getValue();
        annaUser = User.newUser(user.getUserId(), annaUser);
        assertThat(user).usingRecursiveComparison().isEqualTo(annaUser);
        given(fakeDao.selectAllUsers()).willReturn(List.of(annaUser));
        assertThat(fakeDao.selectAllUsers()).hasSize(1);

    }
}