package com.example.sitemate;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class UserRegistrationApiRequest {

    private String name;
    private String email;
    private String password;

    public UserRegistrationApiRequest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String sendUserRegistrationRequest() throws IOException, JSONException {
        int maxTries = 3; // Maximum number of attempts for this request
        for (int attempt = 1; attempt <= maxTries; attempt++) {
            try {
                // Create the URL and open connection
                URL url = new URL("http://192.168.1.224:3000/registerUser");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                httpURLConnection.setConnectTimeout(10000); // 10 seconds Connection Timeout
                httpURLConnection.setReadTimeout(10000); // 10 seconds Read Timeout

                // Create the JSON input with user info
                JSONObject jsonInput = new JSONObject();
                jsonInput.put("name", this.name);
                jsonInput.put("email", this.email);
                jsonInput.put("password", this.password);

                // Write the JSON to the request body
                try (OutputStream os = httpURLConnection.getOutputStream()) {
                    byte[] input = jsonInput.toString().getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                // Check the response code
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                    // Request was sent successfully
                    BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    Log.d("UserRegistrationApiRequest", "Response from server: " + response);
                    return response.toString();
                } else {
                    Log.e("UserRegistrationApiRequest", "Failed to send data on attempt " + attempt + ": HTTP error code: " + responseCode);
                }


            } catch (SocketTimeoutException e) {
                Log.e("UserRegistrationApiRequest", "Connection timed out on attempt " + attempt + ": " + e.getMessage());
                if (attempt == maxTries) throw e; // Rethrow on the last attempt
            } catch (IOException e) {
                Log.e("UserRegistrationApiRequest", "IO Exception on attempt " + attempt + ": " + e.getMessage());
                if (attempt == maxTries) throw e; // Rethrow on the last attempt
            } catch (Exception e) {
                Log.e("UserRegistrationApiRequest", "Error sending user data on attempt " + attempt + ": " + e.getMessage());
                if (attempt == maxTries) throw e; // Rethrow on the last attempt
            }

            // Delay before retrying with exponential backoff
            try {
                Thread.sleep(1000 * attempt); // Increase delay with each attempt (exponential backoff)
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }

        Log.e("UserRegistrationApiRequest", "Request failed after " + maxTries + " attempts.");
        return "Request failed after " + maxTries + " attempts.";
    }
}
