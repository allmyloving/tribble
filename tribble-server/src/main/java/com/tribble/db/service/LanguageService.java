package com.tribble.db.service;

import com.tribble.db.entity.Language;

import java.util.List;

public interface LanguageService {

    boolean add(Language lang);

    boolean update(Language lang);

    boolean delete(Language lang);

    Language findLanguageByCode(String code);

    List<Language> getAllLanguages();
}
