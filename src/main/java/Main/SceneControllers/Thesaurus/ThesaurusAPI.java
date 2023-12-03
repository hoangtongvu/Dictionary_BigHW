package Main.SceneControllers.Thesaurus;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ThesaurusAPI {
    public static JSONObject thesaurus(String word) {
        //String word = "";
        String apiURL = "https://api.api-ninjas.com/v1/thesaurus?word=" + word;
        String apiKey = "jWtGX6M7EGJA5Ye2ZmZ1fw==GRKT4LDQIQniLvzC";
        JSONObject res = new JSONObject("{\"hypernyms\":[],\"synonyms\":[],\"antonyms\":[],\"hyponyms\":[]}");
        try {
            URL url = new URL(apiURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-Api-Key", apiKey);

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }
                reader.close();
                res = new JSONObject(response.toString());
            } else {
                System.out.println("Error: " + responseCode + " " + connection.getResponseMessage());
            }
            connection.disconnect();
        } catch (IOException e) {
            System.out.println("No internet connection!");
        }
        return res;
    }
}
