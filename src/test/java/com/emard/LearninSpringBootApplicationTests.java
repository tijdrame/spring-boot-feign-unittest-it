package com.emard;

import com.emard.clientproxy.UserProxyV1;
import com.emard.model.User;
import com.emard.utils.Gender;
import lombok.AllArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

//@AllArgsConstructor
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class LearninSpringBootApplicationTests {

    @Autowired
    private UserProxyV1 userProxyV1;
    @Test
    void itShouldFetchAllUsers() {
        List<User> users = userProxyV1.fetchUser("MALE", 100);
        assertThat(users).hasSize(1);
    }

    @Test
    void itShouldCreateUser() {
        UUID uuid =UUID.randomUUID();
        User user = new User(uuid, "Tij", "Dramé", Gender.MALE,
                44, "tij@gmail.com");
        ResponseEntity<Integer> result = userProxyV1.createUser(user);
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        ResponseEntity<?> responseEntity = userProxyV1.fetchUser(uuid);
        System.out.println("resp==== "+responseEntity.getBody().toString());
        //assertThat((responseEntity.getBody())).usingRecursiveComparison().isEqualTo(user);
    }

}
