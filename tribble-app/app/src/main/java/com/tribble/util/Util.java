package com.tribble.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.Spine;
import nl.siegmann.epublib.domain.SpineReference;

public class Util {

    public static String readEntireBook(Book book){
        String line, linez = null;
        Spine spine = book.getSpine();
        Resource res;
        List<SpineReference> spineList = spine.getSpineReferences();

        int count = spineList.size();
        int start = 0;

        StringBuilder string = new StringBuilder();
        for (int i = start; count > i; i = i + 1) {
            res = spine.getResource(i);

            try {
                InputStream is = res.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                try {
                    while ((line = reader.readLine()) != null) {
                        linez = string.append(line + "\n").toString();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return linez;

    }
}
