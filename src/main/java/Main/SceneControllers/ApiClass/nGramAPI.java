package Main.SceneControllers.ApiClass;

import Interfaces.ApiClient;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class nGramAPI implements ApiClient {
    private final String startYear = String.valueOf(Year.now().minusYears(200));
    private final String endYear = String.valueOf(Year.now());
    private static List<Integer> year;
    private static List<Double> usageByPercent;
    private static XYChart.Series<String, Number> series;
    private static nGramAPI instance;

    private nGramAPI() {
        year = new ArrayList<>();
        usageByPercent = new ArrayList<>();
        series = new XYChart.Series<>();
    }

    public static nGramAPI getInstance() {
        if (instance == null) {
            instance = new nGramAPI();
        }
        return instance;
    }

    @Override
    public String get(String endpoint) throws IOException {
        String path = "https://books.google.com/ngrams/json?content=" + endpoint
                + "&year_start=" + startYear
                + "&year_end=" + endYear + "&corpus=en-" + endYear + "&smoothing=3";

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
            usageByPercent.add(usageJSON.getDouble(i));
        }

        for (int i = 0; i < usageByPercent.size(); i++) {
            year.add(Integer.parseInt(startYear) + i);
        }
    }

    @Override
    public String post(String endpoint, String body) throws IOException {
        return null;
    }

    public XYChart.Series<String, Number> getSeries(String word) throws IOException {
        usageByPercent.clear();
        year.clear();
        processData(get(word));
        series.getData().clear();
        //Populate chart
        for (int i = 0; i < year.size(); i++) {
            XYChart.Data data = new XYChart.Data<String, Number>(year.get(i).toString(), usageByPercent.get(i));
            series.getData().add(data);
        }

        for (int i = 0; i < year.size(); i++) {
            XYChart.Data data = series.getData().get(i);
            Tooltip.install(data.getNode(), new Tooltip("Year: " + data.getXValue() + " " +  data.getYValue()));
        }
        return series;
    }
}
