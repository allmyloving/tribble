package com.tribble.controller;

import com.tribble.db.entity.Language;
import com.tribble.db.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/lang")
public class LanguageController {

    @Autowired
    private LanguageService languageService;

    @RequestMapping("/all")
    public List<String> getAll() {
        List<String> result = new ArrayList<>();
        for (Language lang : languageService.getAllLanguages()) {
            result.add(lang.getCode());
        }
        System.out.println(result);
        return result;
    }
}
