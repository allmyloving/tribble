package com.tribble.db.service.impl;

import com.tribble.db.dao.TranslationDao;
import com.tribble.db.entity.Translation;
import com.tribble.db.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TranslationServiceImpl implements TranslationService {

    @Autowired
    private TranslationDao translationDao;

    public void add(Translation t) {
        translationDao.save(t);
    }

    public void update(Translation t){
        translationDao.save(t);
    }

    public void delete(Translation t){
        translationDao.remove(t);
    }

    public Translation findTranslationById(Long id){
        return translationDao.find(id);
    }

    public List<Translation> getAllTranslationsByUserId(Long userId){
            return translationDao.getAllTranslationsByUserId(userId);
    }
}
