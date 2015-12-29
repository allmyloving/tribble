package com.tribble.db.service.impl;

import com.tribble.db.dao.LanguageDao;
import com.tribble.db.entity.Language;
import com.tribble.db.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class LanguageServiceImpl implements LanguageService {

    @Autowired
    private LanguageDao languageDao;

    public boolean  add(Language lang){
        return languageDao.save(lang);
    }

    public boolean  update(Language lang){
        return languageDao.save(lang);
    }

    public boolean  delete(Language lang){
        return languageDao.remove(lang);
    }

    public Language findLanguageByCode(String code){
        return languageDao.findLanguageByCode(code);
    }

    public List<Language> getAllLanguages(){
        return languageDao.findAll();
    }
}
