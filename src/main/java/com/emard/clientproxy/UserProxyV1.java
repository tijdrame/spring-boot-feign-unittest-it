package com.emard.clientproxy;

import com.emard.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.UUID;

//@Component
@FeignClient(name = "myTest", url = "http://localhost:8080/api/v1/users")
public interface UserProxyV1 {
    @GetMapping()
    List<User> fetchUser(@RequestParam(value = "gender", required = false) String gender,
                                @RequestParam(value = "ageLessThan", required = false) Integer ageLessThan);

    @GetMapping("{userUid}")
    ResponseEntity<?> fetchUser(@PathVariable("userUid") UUID userUid);

    @PostMapping
    ResponseEntity<Integer> createUser(@RequestBody User user);

    @PutMapping
    ResponseEntity<Integer> updateUser(@RequestBody User user);

    @DeleteMapping("{uuid}")
    ResponseEntity<Integer> deleteUser(@PathVariable("uuid") UUID uuid);
}
