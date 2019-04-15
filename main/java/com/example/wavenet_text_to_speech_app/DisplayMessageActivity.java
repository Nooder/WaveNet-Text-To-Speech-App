package com.example.wavenet_text_to_speech_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {
    private static final String TAG = "DisplayMessageActivity";
    public TTS_Client ttsClient = new TTS_Client();
    public HTML_Parser htmlParser = new HTML_Parser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Get the intent that started this activity, and get the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as it's text
        TextView textView = findViewById(R.id.textView);
//        textView.setText(message);
        try {
            Log.d(TAG, "Inside DisplayMessageActivity Try block...");
            String result = htmlParser.execute(message).get();
            textView.setText(result);
            ttsClient.execute(result);
        } catch (Exception e) {
            // failed background task
            Log.e(TAG, e.toString());
        }
    }
}
