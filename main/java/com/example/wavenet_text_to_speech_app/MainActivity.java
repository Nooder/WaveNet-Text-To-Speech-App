package com.example.wavenet_text_to_speech_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.util.Log;
import android.widget.TabHost;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.wavenet_text_to_speech_app.MESSAGE";
    public static String GOOGLE_CREDENTIALS = "";
    private static final String TAG = "MainActivity";
    public TTS_Client ttsClient = new TTS_Client();
    public HTML_Parser htmlParser = new HTML_Parser();
    public String google_credentials = Google_Credentials.GOOGLE_CREDENTIALS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        save();
        load();
        setEnvironment();
        //createDirectory();
        //writeFile();
        //openFile();
    }

    /** Called when the 'Send' button is pressed */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText)findViewById(R.id.editText);
        String message = editText.getText().toString();
        Log.d(TAG, "Inside sendMessage: " + message);
//        try {
//            String result = htmlParser.execute(message).get();
//            intent.putExtra(EXTRA_MESSAGE, result);
//            startActivity(intent);
//        } catch (Exception e) {
//            // failed background task
//            Log.e(TAG, e.toString());
//        }
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void writeFile() {
        String FILENAME = "test-wavenet-file-credentials.txt";
        String name = "this is a test for writing";

        try {
            FileOutputStream fstream = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fstream.write(name.getBytes());
            //File file = new File(getFilesDir(), FILENAME);
            //Log.d(TAG, "File created: " + file.getAbsolutePath());
            fstream.close();
            Log.d(TAG, "Writing file to: " + getFilesDir().toString());
        } catch (Exception e) {
            Log.d(TAG, "Couldn't write file:\n" + e.toString());
        }
    }

    public void openFile() {
        String FILENAME = "test-wavenet-file-credentials.txt";
        String result = "";
        try {
            FileInputStream fstream = openFileInput(FILENAME);
            int in;
            while ((in = fstream.read()) != 0) {
                result += (char)in;
            }
            fstream.close();
            Log.d(TAG, "Read file result: " + result);
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }

    public void createDirectory() {
        File file = getFilesDir();
        Log.d(TAG, "getFilesDir() = " + file.toString());
        getDir("com.example.wavenet_text_to_speech_app", Context.MODE_PRIVATE);
        Log.d(TAG, "getDir() complete.");
    }

    public void save() {
        String filename = "Google_Credentials.json";
        String text = "This is a test message.";
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write(google_credentials.getBytes());
            Log.d(TAG, "Wrote file: " + getFilesDir().toString() + '/' + filename);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

    }

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

    public void setEnvironment() {
        String filename = "Google_Credentials.json";
        try {
            android.system.Os.setenv("GOOGLE_APPLICATION_CREDENTIALS",
                    filename, true);
        }
        catch (Exception e) {
            Log.e(TAG, "Failed to set environment variable:\n" + e.toString());
        }
    }

}
