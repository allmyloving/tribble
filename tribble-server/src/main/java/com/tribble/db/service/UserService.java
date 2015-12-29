package com.tribble.db.service;

import com.tribble.db.entity.User;

import java.util.List;

public interface UserService {

    void add(User user);

    void update(User user);

    void delete(User user);

    User findUserById(Long id);

    List<User> getAllUsers();

    User findUserByEmail(String email);

    User register(String userName, String email, String password, String passwordConfirmation);

    User login(String email,String password);

}
