package com.tribble.db.dao.impl;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.tribble.db.dao.UserDao;
import com.tribble.db.entity.User;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class UserDaoImpl extends GenericDAOImpl<User, Long> implements UserDao {

    @Autowired
    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    public User findUserByEmail(String email){
        Criteria criteria = getSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("email", email));

        return (User) criteria.uniqueResult();
    }
}
