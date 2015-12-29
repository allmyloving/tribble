package com.tribble.db.service.impl;

import com.tribble.db.dao.UserDao;
import com.tribble.db.entity.User;
import com.tribble.db.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDao userDao;

    @Override
    public void add(User user) {
        userDao.save(user);
    }

    @Override
    public void update(User user) {
        userDao.save(user);
    }

    @Override
    public void delete(User user) {
        userDao.remove(user);
    }

    @Override
    public User findUserById(Long id) {
        return userDao.find(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public User findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    @Override
    public User register(String userName, String email, String password, String passwordConfirmation) {
        if (!password.equals(passwordConfirmation)) {
           // throw new IncorrectPasswordException("incorrectPassword");
        }
        User u = new User();
        u.setEmail(email);
        u.setPassword(password);
        add(u);
        return u;
    }

    @Override
    public User login(String email, String password) {
        User user = userDao.findUserByEmail(email);
        if (user == null || !user.getPassword().equals(password)) {
            return null;
        }
        return user;
    }
}

