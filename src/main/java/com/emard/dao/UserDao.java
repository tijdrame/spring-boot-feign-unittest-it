package com.emard.dao;

import com.emard.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDao {
    List<User> selectAllUsers();
    Optional<User> selectUser(UUID userId);
    int updateUser(User user);
    int removeUser(UUID userId);
    int insertUser(UUID uuid, User user);
}
