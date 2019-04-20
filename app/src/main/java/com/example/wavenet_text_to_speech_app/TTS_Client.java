/** The Text-To-Speech Client Class
 * Generates the audio file of a given text
 */
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

// Imports for filesystem
import java.io.FileOutputStream;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class TTS_Client extends AsyncTask<String, Void, String> {
    private static final String TAG = "TTS_Client";
    private Context context;

    public TTS_Client (Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            Log.d(TAG, "Started Create_Client()'s doInBackGround()");
            Log.d(TAG, "params[0] passed over : " + params[0]);
            Create_Client(params[0]);
            Log.d(TAG, "Completed Create_Client()'s doInBackGround()");
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

    /** Saves the result audio file Bytestream (Android) */
    public void saveAudio(com.google.protobuf.ByteString rawAudioFile) {
        String filename = "output.mp3";
        FileOutputStream fos = null;

        try {
            fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write(rawAudioFile.toByteArray());
            Log.d(TAG, "Audio content written to file: " +
                    context.getFilesDir() + "/" + filename);
        } catch (Exception e) {
            Log.e(TAG, "Failed to write audio output file: " + e.toString());
        }
    }

    public void Create_Client(String text) throws Exception {
        // Credentials in $GOOGLE_APPLICATION_CREDENTIALS environment variable
        // Instantiates a client
        Log.d(TAG,"Inside Create_Client()");

        try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
            // Set the text input to be synthesized
            SynthesisInput input = SynthesisInput.newBuilder()
                    .setText(text)
                    .build();

            Log.d(TAG, "TTS_Client successfully loaded.");

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

            // Write the response to the output file (PC)
//            try (OutputStream out = new FileOutputStream("output.mp3")) {
//                out.write(audioContents.toByteArray());
//                Log.d(TAG, "Audio content written to file: output.mp3");
//            }
            /** Android save to file */
            saveAudio(audioContents);

        }
        catch (Exception e) {
            Log.e(TAG, "Failed to Create_Client(): \n" + e.toString());
        }

        Log.d(TAG, "Completed Create_Client()");
    }
}