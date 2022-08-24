package com.emard.service;

import com.emard.dao.UserDao;
import com.emard.model.User;
import com.emard.utils.Gender;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserService {

    private final UserDao userDao;

    public List<User> getAllUsers(Optional<String> gender, Optional<Integer> ageLessThan) {
        List<User> list = userDao.selectAllUsers();
        if(gender.isPresent()){
            try {
                Gender theGender = Gender.valueOf(gender.get());
                list = list.stream().filter(u -> u.getGender().equals(theGender)).collect(Collectors.toList());
            }catch (IllegalArgumentException e){
            }
        }

        if(ageLessThan.isPresent())
            list = list.stream().filter(u -> u.getAge() < ageLessThan.get()).collect(Collectors.toList());
        return list;
    }

    public Optional<User> getUser(UUID userId) {
        return userDao.selectUser(userId);
    }

    public int updateUser(User user) {
        Optional<User> userDB = getUser(user.getUserId());
        if(userDB.isPresent()){
            return userDao.updateUser(user);
        }
        throw new NotFoundException("user "+ user.getUserId()+" not found");
    }

    public int removeUser(UUID uuid) {
        UUID userUid = getUser(uuid)
                .map(User::getUserId)
                .orElseThrow(()-> new NotFoundException("user "+ uuid+" not found"));
        return userDao.removeUser(userUid);
    }

    public int insertUser(User user) {
        UUID uuid = user.getUserId() == null ? UUID.randomUUID(): user.getUserId();
        //validateUser(user);
        return userDao.insertUser(uuid, User.newUser(uuid, user));
    }

    private static void validateUser(User user) {
        Objects.requireNonNull(user.getFirstName(), "First name required");
        Objects.requireNonNull(user.getLastName(), "Last name required");
        Objects.requireNonNull(user.getGender(), "Gender is required");
        Objects.requireNonNull(user.getAge(), "Age is required");
        Objects.requireNonNull(user.getEmail(), "Email is required");
    }
}
