package com.tribble.db.dao;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;
import com.tribble.db.entity.Language;

public interface LanguageDao extends GenericDAO<Language, Long>{

    Language findLanguageByCode(String code);
}
