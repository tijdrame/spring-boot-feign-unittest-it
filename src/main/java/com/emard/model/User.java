package com.emard.model;

import com.emard.utils.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@AllArgsConstructor
@Getter @ToString
public class User {
    private final UUID userId;
    @NotNull(message = "First name required")
    private final String firstName;
    @NotNull(message = "Last name required")
    private final String lastName;
    private final Gender gender;
    @NotNull
    @Max(112)
    private final Integer age;
    @Email
    private final String email;

    public static User newUser(UUID uuid, User user) {
        user = new User(uuid, user.getFirstName(), user.getLastName(), user.getGender(),
                user.getAge(), user.getEmail());
        return user;
    }

}
