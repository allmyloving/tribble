package com.tribble.controller;

import com.tribble.db.entity.User;
import com.tribble.exception.IncorrectEmailPasswordException;
import com.tribble.exception.UserNotFoundException;
import com.tribble.util.PasswordHash;
import com.tribble.db.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger LOG = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void createUser(@RequestParam("email") String email,
                           @RequestParam("password") String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(PasswordHash.createHash(password));
        // try catching SQL Exception
        userService.add(user);
        LOG.info(String.format("User created ==> %s", user));
    }

    @RequestMapping("/get/{userId}")
    public User getUser(@PathVariable("userId") long id) {
        User user = userService.findUserById(id);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public long authorizeUser(@RequestParam("email") String email,
                              @RequestParam("password") String password){
        User user = userService.findUserByEmail(email);
        if(user == null || !PasswordHash.validatePassword(password, user.getPassword())){
            LOG.info(String.format("Authentication failed for user %s", email));
            throw new IncorrectEmailPasswordException();
        }
        LOG.info(String.format("Authentication successful for user %s", email));
        return user.getId();
    }
}
