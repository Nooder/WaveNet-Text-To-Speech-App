package com.example.wavenet_text_to_speech_app;

// Imports the Google Cloud client library
import com.google.cloud.texttospeech.v1.AudioConfig;
import com.google.cloud.texttospeech.v1.AudioEncoding;
import com.google.cloud.texttospeech.v1.SsmlVoiceGender;
import com.google.cloud.texttospeech.v1.SynthesisInput;
import com.google.cloud.texttospeech.v1.SynthesizeSpeechResponse;
import com.google.cloud.texttospeech.v1.TextToSpeechClient;
import com.google.cloud.texttospeech.v1.VoiceSelectionParams;
import com.google.protobuf.ByteString;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import android.app.Activity;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

public class TTS_Client extends AsyncTask<String, Void, String> {
    private static final String TAG = "TTS_Client";

    @Override
    protected String doInBackground(String... params) {
        try {
            Log.d(TAG, "Inside Create_Client()");
            Create_Client(params[0]);
        } catch (Exception e){
            // failed
            Log.d(TAG, e.toString());
        }

        return "TODO";
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }

    private void TTS_Client() {

    }

    public void Create_Client(String text) throws Exception {
        // Credentials in $GOOGLE_APPLICATION_CREDENTIALS environment variable
        // Instantiates a client
        try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
            // Set the text input to be synthesized
            SynthesisInput input = SynthesisInput.newBuilder()
                    .setText(text)
                    .build();

            // Build the voice request and set the language codes
            VoiceSelectionParams voice = VoiceSelectionParams.newBuilder()
                    .setLanguageCode("en-US")
                    .setName("en-US-Wavenet-C")
                    .build();

            // Select the type of audio file you want returned
            AudioConfig audioConfig = AudioConfig.newBuilder()
                    .setAudioEncoding(AudioEncoding.MP3)
                    .build();

            // Perform the TTS request on the text input with
            // the selected voice parameters
            SynthesizeSpeechResponse response = textToSpeechClient
                    .synthesizeSpeech(input, voice, audioConfig);

            // Get the audio contents from the response
            ByteString audioContents = response.getAudioContent();

            // Write the response to the output file
            try (OutputStream out = new FileOutputStream("output.mp3")) {
                out.write(audioContents.toByteArray());
                Log.d(TAG, "Audio content written to file: output.mp3");
            }

            // Write the response to output file (Android internal storage)
            //File file = new File(this.getFilesDir(), "output.mp3");
        }


    }
}