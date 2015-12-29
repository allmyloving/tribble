package com.tribble.db.dao;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;
import com.tribble.db.entity.Translation;

import java.util.List;

public interface TranslationDao extends GenericDAO<Translation, Long> {

    public List<Translation> getAllTranslationsByUserId(Long userId);

}
