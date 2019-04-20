// Performs basic HTTP GET requests
package com.example.wavenet_text_to_speech_app;

// Imports
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

public class Get_Request {
    public void get_request(String url) {
        // Setup
        try {
            URL urlToRequest = new URL(url);
            String readLine = null;
            HttpURLConnection connection = (HttpURLConnection)
                    urlToRequest.openConnection();
            connection.setRequestMethod("GET");
            // For request properties
            // connection.setRequestProperty("userId", "abc123");
            int responseCode = connection.getResponseCode();

            // Check response
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                StringBuffer response = new StringBuffer();
                while ((readLine = in.readLine()) != null) {
                    response.append(readLine);
                }
                in.close();

                // Print the result
                System.out.println("GET Result: " + response.toString());
            }
            else {
                System.out.println("GET Request Failed...");
            }
        }
        catch (Exception e) {
            System.out.println("An error occurred: " + e.toString());
        }
    }

    // Get URL request and return result as a String
    public String getURL(String request) {
        String inputLine = "";
        try {
            URL url = new URL(request);
            URLConnection connection = url.openConnection();
            connection.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            while((inputLine = in.readLine()) != null)
                inputLine += inputLine;
        }
        catch (MalformedURLException e) {
            // new URL() failed...
        }
        catch (IOException e) {
            // openConnection() failed...
        }

        return inputLine;
    }
}