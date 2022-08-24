package com.emard.resource;

import com.emard.model.User;
import com.emard.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1/users")
public class UserReource {
    private final UserService userService;

    @GetMapping("")
    public List<User> fetchUser(@QueryParam("gender") String gender,
                                @QueryParam("ageLessThan") Integer ageLessThan){
        log.info("Gender [{}]", gender);
        log.info("Age [{}]", ageLessThan);
        return userService.getAllUsers(Optional.ofNullable(gender),
                Optional.ofNullable(ageLessThan));
    }

    @GetMapping("{userUid}")
    public User fetchUser(@PathVariable("userUid") UUID userUid){
        /*Optional<User> user = userService.getUser(userUid);
        if(user.isPresent()){
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage("user "+userUid + " was not found"));*/
        return userService.getUser(userUid)
                .orElseThrow(()-> new NotFoundException("user "+ userUid+" not found"));
    }

    @PostMapping
    public ResponseEntity<Integer> createUser(@Valid @RequestBody User user){
        Integer result = userService.insertUser(user);
        return getIntegerResponseEntity(result);
    }


    @PutMapping
    public ResponseEntity<Integer> updateUser(@RequestBody User user){
        int result = userService.updateUser(user);
        return getIntegerResponseEntity(result);
    }

    @DeleteMapping("{uuid}")
    public ResponseEntity<Integer> deleteUser(@PathVariable("uuid") UUID uuid) {
        int result = userService.removeUser(uuid);
        return getIntegerResponseEntity(result);
    }
    private static ResponseEntity<Integer> getIntegerResponseEntity(Integer i) {
        if(i == 1) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }
    @Data
    @AllArgsConstructor
    class ErrorMessage {
        String errorMessage;
    }
}
