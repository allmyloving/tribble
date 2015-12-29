package com.tribble.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.tribble.R;
import com.tribble.TribbleApp;
import com.tribble.util.query.Query;
import com.tribble.util.query.QueryTask;
import com.tribble.util.query.TranslateQuery;
import com.tribble.util.Util;

import java.io.IOException;
import java.io.InputStream;
import java.text.BreakIterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.epub.EpubReader;

public class MainActivity extends ActionBarActivity {

    private final String LOG_TAG = getClass().getSimpleName();

    private final String FILE_NAME = "charlie_small.epub";

    private TextView textView;

    private PopupWindow popupWindow;

    private final int DEFINITION = 8;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textViewReader);

        AssetManager assetManager = getAssets();

        InputStream inputStream;
        try {
            inputStream = assetManager.open(FILE_NAME);
            Book ebook = new EpubReader().readEpub(inputStream);
            final String text = Util.readEntireBook(ebook);

            //webview.loadData(text, "text/html", "utf-8");
            //textView.setText(Html.fromHtml(text));
            setText(Html.fromHtml(text).toString().trim());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void showPopup(String text) {
        Log.d(LOG_TAG, "trying to show popup");
        LayoutInflater inflater = (LayoutInflater)
                this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popupWindow = new PopupWindow(
                inflater.inflate(R.layout.popup_translate, null, false),
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true);

        popupWindow.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        popupWindow.setOutsideTouchable(true);

        TextView popupText = (TextView) popupWindow.getContentView().findViewById(R.id.popupText);
        popupText.setText(text);

        setUpPopupButton(text);

        popupWindow.showAtLocation(this.findViewById(R.id.mainLinear), Gravity.CENTER, 0, 0);
    }

    private void setUpPopupButton(final String text){
        Button translateButton = (Button) popupWindow.getContentView().findViewById(R.id.submitTranslate);
        final TextView popupOut = (TextView) popupWindow.getContentView().findViewById(R.id.popupTextOut);

        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = new TranslateQuery(text, null, TribbleApp.getInstance().getDefaultLang());
                String url = query.build();
                QueryTask task = new QueryTask();
                try {
                    task.execute("GET", url).get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }

                List<String> result = task.getResult();
                if (result != null) {
                    Log.d(LOG_TAG, result.toString());
                    popupOut.setText(task.getResult().get(0));
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = null;
        switch (id) {
            case R.id.action_login:
                Toast.makeText(this, "Redirection to login", Toast.LENGTH_SHORT).show();
                intent = new Intent(MainActivity.this, LoginActivity.class);
                break;
            case R.id.action_settings:
                Toast.makeText(this, "Redirection to settings", Toast.LENGTH_SHORT).show();
                intent = new Intent(MainActivity.this, SettingsActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
        return true;
    }
    private void setText(String definition) {
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(definition, TextView.BufferType.SPANNABLE);

        Spannable spans = (Spannable) textView.getText();
        BreakIterator iterator = BreakIterator.getWordInstance(Locale.US);
        iterator.setText(definition);
        int start = iterator.first();
        for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator
                .next()) {
            String possibleWord = definition.substring(start, end);
            if (Character.isLetterOrDigit(possibleWord.charAt(0))) {
                ClickableSpan clickSpan = getClickableSpan(possibleWord);
                spans.setSpan(clickSpan, start, end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    private ClickableSpan getClickableSpan(final String word) {
        return new ClickableSpan() {
            final String mWord;

            {
                mWord = word;
            }

            @Override
            public void onClick(View widget) {
                Log.d("tapped on:", mWord);
                showPopup(mWord);
            }

            public void updateDrawState(TextPaint ds) {
                ds.setColor(getResources().getColor(R.color.darkGrey));
                ds.setUnderlineText(false);
                super.updateDrawState(ds);
            }
        };
    }
}

