package com.tribble.db.dao.impl;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.tribble.db.dao.TranslationDao;
import com.tribble.db.entity.Translation;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class TranslationDaoImpl extends GenericDAOImpl<Translation, Long> implements TranslationDao {

    @Autowired
    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    public List<Translation> getAllTranslationsByUserId(Long userId) {
        Criteria criteria = getSession().createCriteria(Translation.class);
        criteria.add(Restrictions.eq("user.id", userId)); //user.id
        return criteria.list();
    }
}
