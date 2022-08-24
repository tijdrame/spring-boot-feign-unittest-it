package com.emard.dao;

import com.emard.model.User;
import com.emard.utils.Gender;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class FakeDao implements UserDao{

    private Map<UUID, User> database;
    public FakeDao() {
        database = new HashMap<>();
        UUID uuid = UUID.randomUUID();
        database.put(uuid, new User(uuid, "Joe", "jones", Gender.MALE, 22,
                "joe.jones@gmail.com"));
        uuid = UUID.randomUUID();
        database.put(uuid, new User(uuid, "Sarah", "Doe", Gender.FEMALE, 28,
                "sarah.doe@gmail.com"));
    }
    @Override
    public List<User> selectAllUsers() {
        return new ArrayList<>(database.values());
    }

    @Override
    public Optional<User> selectUser(UUID userId) {
        return Optional.ofNullable(database.get(userId));
    }

    @Override
    public int updateUser(User user) {
        database.put(user.getUserId(), user);
        return 1;
    }

    @Override
    public int removeUser(UUID userId) {
        database.remove(userId);
        return 1;
    }

    @Override
    public int insertUser(UUID uuid, User user) {
        database.put(uuid, user);
        return 1;
    }
}
