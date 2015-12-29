package com.tribble.db.service;

import com.tribble.db.entity.Translation;

import java.util.List;

public interface TranslationService {

    void add(Translation t);

    void update(Translation t);

    void delete(Translation t);

    Translation findTranslationById(Long id);

    List<Translation> getAllTranslationsByUserId(Long userId);
}
