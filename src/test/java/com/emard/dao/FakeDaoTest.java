package com.emard.dao;

import com.emard.model.User;
import com.emard.utils.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.emard.utils.Gender.FEMALE;
import static org.assertj.core.api.Assertions.assertThat;

class FakeDaoTest {

    private FakeDao fakeDao;
    @BeforeEach
    void setUp() {
        fakeDao = new FakeDao();
    }

    @Test
    void shouldSelectAllUsers() {
        List<User> users = fakeDao.selectAllUsers();
        assertThat(users).hasSize(2);
        User user = users.get(0);
        assertThat(user.getFirstName()).isEqualTo("Joe");
    }

    @Test
    void shouldSelectUser() {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "Anna", "Montana", FEMALE,
                30, "anna.montana@gmail.com");
        fakeDao.insertUser(userId, user);
        assertThat(fakeDao.selectAllUsers()).hasSize(3);

        Optional<User> optUser = fakeDao.selectUser(userId);
        assertThat(optUser.isPresent()).isTrue();
        // assertThat(optUser.get()).isEqualToComparingFieldByField(user);
        assertThat(optUser.get()).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    void shouldUpdateUser() {
        UUID uuid = fakeDao.selectAllUsers().get(0).getUserId();
        User newJoe = new User(uuid, "Anna", "Montana", FEMALE,
                30, "anna.montana@gmail.com");
        fakeDao.updateUser(newJoe);
        Optional<User> optionalUser = fakeDao.selectUser(uuid);
        assertThat(optionalUser.isPresent()).isTrue();
        assertThat(fakeDao.selectAllUsers()).hasSize(2);
        assertThat(optionalUser.get()).usingRecursiveComparison().isEqualTo(newJoe);
    }

    @Test
    void shouldRemoveUser() {
        UUID uuid = fakeDao.selectAllUsers().get(0).getUserId();
        fakeDao.removeUser(uuid);
        assertThat(fakeDao.selectUser(uuid).isPresent()).isFalse();
        assertThat(fakeDao.selectAllUsers()).hasSize(1);
    }

    @Test
    void shouldInsertUser() {
        UUID uuid = UUID.randomUUID();
        User user = new User(uuid, "Anna", "Montana", FEMALE,
                30, "anna.montana@gmail.com");
        fakeDao.insertUser(uuid, user);
        assertThat(fakeDao.selectAllUsers()).hasSize(3);
        assertThat(fakeDao.selectUser(uuid).get()).usingRecursiveComparison().isEqualTo(user);
    }
}