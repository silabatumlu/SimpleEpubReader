package com.example.silabatumlu.simpleepubreader;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.Resources;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.epub.EpubReader;

public class MainActivity extends AppCompatActivity {

    public static final String BOOK_NAME = "The Silver Chair.epub";
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);

        loadChapter(0);
    }

    public void loadChapter(int chapterNo){
        AssetManager assetManager = getAssets();
        try {
            InputStream epubInputStream = assetManager.open(BOOK_NAME);
            Book book = (new EpubReader()).readEpub(epubInputStream);

            List<TOCReference> tocReferences = book.getTableOfContents().getTocReferences();

            Resource resource = tocReferences.get(chapterNo).getResource();

            loadHtmlData(new String(resource.getData()));

        } catch (IOException e) {
            Log.e("epublib", e.getMessage());
        }
    }

    public void loadHtmlData(String htmlString){
        webView.loadData(htmlString, "text/html", "utf-8");
    }
}
