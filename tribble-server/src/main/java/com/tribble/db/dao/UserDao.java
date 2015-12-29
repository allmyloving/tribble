package com.tribble.db.dao;

import com.tribble.db.entity.User;
import com.googlecode.genericdao.dao.hibernate.GenericDAO;

public interface UserDao extends GenericDAO<User, Long>{

    User findUserByEmail(String email);
}
