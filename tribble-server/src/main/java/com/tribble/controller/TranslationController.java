package com.tribble.controller;


import com.tribble.db.entity.Language;
import com.tribble.db.entity.Translation;
import com.tribble.db.entity.User;
import com.tribble.db.service.LanguageService;
import com.tribble.db.service.TranslationService;
import com.tribble.db.service.UserService;
import com.tribble.exception.BadRequestException;
import com.tribble.exception.LanguageNotFoundException;
import com.tribble.exception.UserNotFoundException;
import com.tribble.translation.GoogleTranslationHandler;
import com.tribble.translation.TranslationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/translate")
public class TranslationController {

    @Autowired
    private UserService userService;
    @Autowired
    private TranslationService translationService;
    @Autowired
    private LanguageService languageService;
    @Autowired
    private TranslationHandler translationHandler;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void addTranslation(@RequestParam("userId") Long userId,
                               @RequestParam(value = "sourceLang", required = false) String sourceLang,
                               @RequestParam(value = "targetLang", required = false) String targetLang,
                               @RequestParam("source") String source,
                               @RequestParam("target") String target) {
        System.out.println("creating translation..");
        Translation t = new Translation();
        Language language = null;
        if (sourceLang != null) {
            language = languageService.findLanguageByCode(sourceLang);
            if (language == null) {
                throw new LanguageNotFoundException("Source language is incorrect");
            }
            t.setSourceLang(language);
        }
        if (targetLang != null) {
            language = languageService.findLanguageByCode(targetLang);
            if (language == null) {
                throw new LanguageNotFoundException("Target language is incorrect");
            }
            t.setTargetLang(language);
        }
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("user not");
        }
        t.setUser(user);
        t.setSource(source);
        t.setTarget(target);
        translationService.add(t);
    }

    @RequestMapping("/get")
    public String translate(@RequestParam(value = "source", required = false) String source,
                            @RequestParam("target") String target,
                            @RequestParam("q") String q) {
        String response = null;
        URI uri = translationHandler.buildURI(source, target, q);
        System.out.println("uri ==> " + uri);
        System.out.println("RESPONSE");
        try {
            HttpURLConnection  connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod("GET");

            int code = connection.getResponseCode();
            System.out.println("\nSending 'GET' request to URI : " + uri);
            System.out.println("Response Code : " + code);
            response = readStream(connection);
        } catch (IOException e) {
            throw new BadRequestException();
        }

        System.out.println(response);
        return response;
    }

    @RequestMapping("/all/{userId}")
    public List<Translation> getAll(@PathVariable("userId") long userId) {
        if (userService.findUserById(userId) == null) {
            throw new UserNotFoundException();
        }
        return translationService.getAllTranslationsByUserId(userId);
    }

    private String readStream(HttpURLConnection connection) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();
        return sb.toString();
    }
}
