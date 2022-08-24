package com.emard.resource;

import com.emard.model.User;
import com.emard.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@AllArgsConstructor@Slf4j
@Component
@Path("/api/v1/users")
public class UserResourceRestEasy {
    private final UserService userService;

    @GET
    @Produces(APPLICATION_JSON)
    public List<User> fetchUser(@RequestParam(value = "gender", required = false) String gender,
                                @RequestParam(value="ageLessThan", required = false) Integer ageLessThan){
        log.info("Gender [{}]", gender);
        log.info("Age [{}]", ageLessThan);
        return userService.getAllUsers(Optional.ofNullable(gender),
                Optional.ofNullable(ageLessThan));
    }

    @GET
    @Path("{userUid}")
    public Response fetchUser(@PathVariable("userUid") UUID userUid){
        Optional<User> user = userService.getUser(userUid);
        if(user.isPresent()){
            return Response.ok(user.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorMessage("user "+userUid + " was not found"))
                .build();
    }

    @POST
    public Response createUser(User user){
        Integer result = userService.insertUser(user);
        return getIntegerResponse(result);
    }

    @PUT
    public Response updateUser(User user){
        int result = userService.updateUser(user);
        return getIntegerResponse(result);
    }

    @DELETE
    @Path("{uuid}")
    public Response deleteUser(@PathParam("uuid") UUID uuid) {
        int result = userService.removeUser(uuid);
        return getIntegerResponse(result);
    }

    private static Response getIntegerResponse(Integer i) {
        if(i == 1) return Response.ok().build();
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    @Data
    @AllArgsConstructor
    class ErrorMessage {
        String errorMessage;
    }
}
