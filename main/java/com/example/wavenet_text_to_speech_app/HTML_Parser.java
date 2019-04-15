package com.example.wavenet_text_to_speech_app;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
// JSoup imports
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTML_Parser extends AsyncTask<String, Void, String> {

    private static final String TAG = "HTML_Parser";
    public Document returnedHtml;
    public ArrayList<String> listParagraphs;
    public String stringParagraphs;
    public HTML_Parser() {
        listParagraphs = new ArrayList<String>();
    }

    @Override
    protected String doInBackground(String... params) {
        Log.d(TAG, "Inside doInBackground()");
        get_url(params[0]);
        Log.d(TAG, "Completed get_url()...");
        parse_html();
        Log.d(TAG,"Completed parse_html()\n");
        Log.d(TAG, this.stringParagraphs);
        return this.stringParagraphs;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }

    public void parse_html() {
        try {
            Elements paragraphs = returnedHtml.getElementsByTag("p");
            for (Element paragraph : paragraphs) {
                listParagraphs.add(paragraph.text());
            }
            stringParagraphs = String.join("\n\n", listParagraphs);
        } catch (Exception e) {
            Log.d(TAG, "Could not parse html: " + e.toString());
        }
    }

    public void get_url(String url) {
        try {
            this.returnedHtml = Jsoup.connect(url)
                    .timeout(0).get();
        } catch (Exception e) {
            Log.d(TAG,"Could not get url: " + e.toString());
        }
    }
}