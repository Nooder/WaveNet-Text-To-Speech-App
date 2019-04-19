package com.example.wavenet_text_to_speech_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.wavenet_text_to_speech_app.MESSAGE";
    private static final String TAG = "MainActivity";
    public String google_credentials = Google_Credentials.GOOGLE_CREDENTIALS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Save credentials file and set environment to point to it
        save();
        load();
        setEnvironment();
    }

    /** Called when the 'Send' button is pressed */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText)findViewById(R.id.editText);
        String message = editText.getText().toString();
        Log.d(TAG, "Inside sendMessage: " + message);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    /** Saves a file to the Internal Storage (Hidden) */
    public void save() {
        String filename = "Google_Credentials.json";
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write(google_credentials.getBytes());
            Log.d(TAG, "Wrote file: " + getFilesDir().toString() + '/' + filename);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

    }

    /** Loads a file from the Internal Storage */
    public void load() {
        String filename = "Google_Credentials.json";
        FileInputStream fis = null;

        try {
            fis = openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            Log.d(TAG, "File successfully loaded:\n" + sb.toString());
        }
        catch (Exception e) {
            Log.e(TAG, "Failed to load file:\n" + e.toString());
        }
    }

    /** Sets the GOOGLE_APPLICATION_CREDENTIALS environment variable for the api credentials */
    public void setEnvironment() {
        String filename = getFilesDir() + "/" + "Google_Credentials.json";
        try {
            android.system.Os.setenv("GOOGLE_APPLICATION_CREDENTIALS",
                    filename, true);
        }
        catch (Exception e) {
            Log.e(TAG, "Failed to set environment variable:\n" + e.toString());
        }
    }


}
