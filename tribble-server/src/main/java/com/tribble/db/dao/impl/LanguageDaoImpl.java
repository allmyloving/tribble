package com.tribble.db.dao.impl;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.tribble.db.dao.LanguageDao;
import com.tribble.db.entity.Language;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
@Transactional
public class LanguageDaoImpl extends GenericDAOImpl<Language, Long> implements LanguageDao {

    @Autowired
    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    public Language findLanguageByCode(String code) {
        Criteria criteria = getSession().createCriteria(Language.class);
        criteria.add(Restrictions.eq("code", code));
        return (Language) criteria.uniqueResult();
    }
}
