package Main.SceneControllers.Widget;

import Main.ApiClient;
import Main.ProjectDirectory;
import com.google.gson.JsonObject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Connection;

public class SpotifyController implements ApiClient {
    private String tokenType;
    private String tokenKey;

    @FXML
    protected WebView webView;
    @FXML
    protected AnchorPane spotifyPane;

    private WebEngine webEngine = new WebEngine();
    public void setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public static SpotifyController loadInstance() throws IOException {
        String absolutePath = ProjectDirectory.resourcesPath + "\\fxml\\Widget\\Spotify.fxml";
        URL fxmlURL = Paths.get(absolutePath).toUri().toURL();
        FXMLLoader loader = new FXMLLoader(fxmlURL);
        loader.load();

        return loader.getController();
    }

    public void addToParent(Pane root) {
        root.getChildren().add(this.spotifyPane);
    }

    @Override
    public String get(String endpoint) throws IOException {
        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", tokenType + " " + tokenKey);
        connection.setDoOutput(true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String responseLine;
        StringBuilder stringBuilder = new StringBuilder();

        while ((responseLine = reader.readLine()) != null) {
            stringBuilder.append(responseLine).append("\n");
        }

        return stringBuilder.toString();
    }

    @Override
    public String post(String endpoint, String body) throws IOException {
        HttpURLConnection connection = null;
        URL url = new URL(endpoint);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        OutputStream os = connection.getOutputStream();
        os.write(body.getBytes());
        os.flush();
        os.close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            if (!line.isEmpty()) {
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }

    public void initialize() {
        AnchorPane.setRightAnchor(spotifyPane, 0d);
        AnchorPane.setLeftAnchor(spotifyPane, 0d);
        AnchorPane.setTopAnchor(spotifyPane, 0d);
        AnchorPane.setBottomAnchor(spotifyPane, 0d);

        String clientID = "a1db047c2d53469db16d0433a5c3c320";
        String clientSecret = "7ddc3e8b55c742d891c23dc5201dd2ac";

        try {
            String tokenInfo = post("https://accounts.spotify.com/api/token"
                    , "grant_type=client_credentials&client_id=" + clientID +"&client_secret=" + clientSecret);
            JSONObject object = new JSONObject(tokenInfo);
            setTokenKey(object.getString("access_token"));
            setTokenType(object.getString("token_type"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            String response = get("https://open.spotify.com/embed/playlist/4IWWgBiwsH6gbOEYbKv4M9?utm_source=generator&theme=0");
            webEngine = webView.getEngine();
            webEngine.loadContent(response);
            System.out.println(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
