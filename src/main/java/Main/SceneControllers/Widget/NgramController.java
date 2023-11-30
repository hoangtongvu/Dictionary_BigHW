package Main.SceneControllers.Widget;

import Main.ApiClient;
import Main.ProjectDirectory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class NgramController implements ApiClient {
    @FXML
    protected LineChart<String, Number> ngramChart;
    @FXML
    protected AnchorPane root;
    private static FXMLLoader loader;
    @Override
    public String get(String endpoint) throws IOException {
        return null;
    }

    @Override
    public String post(String endpoint, String body) throws IOException {
        return null;
    }

    public void initialize() throws IOException {
        String path = "https://books.google.com/ngrams/json?content=Churchill&year_start=1800&year_end=2000&corpus=26&smoothing=3";
        URL url = new URL(path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setDoOutput(true);

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }

        System.out.println(stringBuilder.toString());
        JSONArray jsonArray = new JSONArray(stringBuilder.toString());
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        System.out.println(jsonObject.get("ngram"));

        JSONArray time = jsonObject.getJSONArray("timeseries");

        List<Double> timeSeries = new ArrayList<>();

        for (int i = 0; i < time.length(); i++) {
            timeSeries.add(time.getDouble(i)* 1e9);
            System.out.println(time.getDouble(i));
        }
        System.out.println(timeSeries.size());

        List<Integer> year = new ArrayList<>();
        for (int i = 0; i < timeSeries.size(); i++) {
            year.add(1800 + i);
        }

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Year");
        yAxis.setLabel("Usage");

//        ngramChart = new LineChart<>(xAxis, yAxis);
        ngramChart.setTitle("Word usage by year");
        XYChart.Series<String, Number> series = new XYChart.Series<String,Number>();
        series.setName("My words");

        for (int i = 0; i < timeSeries.size(); i++) {
            series.getData().add(new XYChart.Data<String, Number>(year.get(i).toString(), timeSeries.get(i)));
        }

        ngramChart.getData().add(series);
    }

    public void addToParent(Pane parent) {
        AnchorPane.setTopAnchor(root, 0.0);
        AnchorPane.setBottomAnchor(root, 0.0);
        AnchorPane.setLeftAnchor(root, 0.0);
        AnchorPane.setRightAnchor(root, 0.0);
        parent.getChildren().addAll(root);
    }

    public static NgramController loadInstance() throws IOException {
        String absolutePath = ProjectDirectory.resourcesPath + "\\fxml\\Widget\\Ngrams.fxml";
        URL fxmlURL = Paths.get(absolutePath).toUri().toURL();
        loader = new FXMLLoader(fxmlURL);
        loader.load();
        return loader.getController();
    }

}
