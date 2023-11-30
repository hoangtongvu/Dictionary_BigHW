package Main.SceneControllers.Widget;

import Main.ApiClient;
import Main.ProjectDirectory;
import Main.SceneControllers.Dictionary.DictionarySceneController;
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
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class NgramController implements ApiClient {
    @FXML
    protected LineChart<String, Number> ngramChart;
    @FXML
    protected AnchorPane root;
    private static FXMLLoader loader;

    private String startYear = String.valueOf(Year.now().minusYears(200));
    private String endYear = String.valueOf(Year.now());
    private static List<Integer> year = new ArrayList<>();
    private static List<Double> usageByPercent = new ArrayList<>();

    //Create series
    final NumberAxis xAxis = new NumberAxis();
    final NumberAxis yAxis = new NumberAxis();

    @Override
    public String get(String endpoint) throws IOException {
        String path = "https://books.google.com/ngrams/json?content=" + endpoint
                + "&year_start=" + startYear
                + "&year_end=" + endYear + "&corpus=en-2019&smoothing=3";

        //Open connection
        URL url = new URL(path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        //Set request type to GET
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);


        //Read input stream
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }

        return stringBuilder.toString();
    }

    public void processData(String data) {
        JSONArray resultArray = new JSONArray(data);
        JSONObject resultObject = resultArray.getJSONObject(0); //The only JSON object from request
        /*  Result contains:
            time series: an jsonArray of time stamps
            type: NGRAM
            ngram: the word that we are searching for
         */

        JSONArray usageJSON = resultObject.getJSONArray("timeseries");

        for (int i = 0; i < usageJSON.length(); i++) {
            usageByPercent.add(usageJSON.getDouble(i)* 1e9);
        }

        for (int i = 0; i < usageByPercent.size(); i++) {
            year.add(Integer.parseInt(startYear) + i);
        }
    }

    @Override
    public String post(String endpoint, String body) throws IOException {
        return null;
    }

    public void initialize() throws IOException {
        processData(get("boob"));

        xAxis.setLabel("Year");
        yAxis.setLabel("Usage");

        ngramChart.setTitle("Word usage by year");
        XYChart.Series<String, Number> series = new XYChart.Series<String,Number>();
        series.setName("My words");

        //Populate chart
        for (int i = 0; i < year.size(); i++) {
            series.getData().add(new XYChart.Data<String, Number>(year.get(i).toString(), usageByPercent.get(i)));
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
