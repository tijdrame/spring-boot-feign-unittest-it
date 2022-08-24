package com.emard.resource;

import com.emard.clientproxy.UserProxyV1;
import com.emard.model.User;
import com.emard.utils.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/feign")
public class TestFeign {
    @Autowired UserProxyV1 userProxyV1;

    /*@GetMapping
    public List<User> fetchUser(@RequestParam("gender") String gender,
                                @RequestParam("ageLessThan") Integer ageLessThan){
        return userProxyV1.fetchUser(gender, ageLessThan);
    }*/

    @PostMapping
    public ResponseEntity<Integer> createUser(@RequestBody User user) {
        //User user = new User(UUID.randomUUID(), "Tij", "Dramé", Gender.MALE,
          //      44, "tij@gmail.com");
        ResponseEntity<Integer> result = userProxyV1.createUser(user);
        return result;
    }
}
