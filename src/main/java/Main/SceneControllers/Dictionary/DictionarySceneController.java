package Main.SceneControllers.Dictionary;

import Dictionary.DicManager;
import Main.FxmlFileManager;
import Main.SceneControllers.BaseSceneController;
import Main.SceneControllers.IHasNavPane;
import Main.application.App;
import Main.SceneControllers.Translate.TextToSpeech;
import Word.WordBlock;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import Dictionary.SearchHistory;

import static java.util.Collections.binarySearch;
import static java.util.Collections.sort;


public class DictionarySceneController extends BaseSceneController implements Initializable, IHasNavPane {
    private static final List<WordBlock> starredWordList = new ArrayList<>();
    private static final List<String> starredWordStringList = new ArrayList<>();
    private static final List<String> wordHistoryList = new ArrayList<>();
    public static List<WordBlock> getStarredWordList() {
        return starredWordList;
    }
    private  List<String> possibleSuggestions = new ArrayList<>();


    @FXML
    protected Button starButton;
    @FXML
    private TextField searchBar;
    @FXML
    protected WebView webView;
    @FXML
    protected ListView<String> starredWordListView;
    @FXML
    protected ListView<String> historyListView;
    @FXML
    protected Button editButton;
    @FXML
    protected Button soundButton;

    public ListView<String> getHistoryListView() {
        return historyListView;
    }

    @FXML
    public void editCurrentWord() {
        try {
            FxmlFileManager.SwitchScene(FxmlFileManager.getInstance().editWordSceneController);
            FxmlFileManager.getInstance().editWordSceneController.loadWordOnPane(currentWordBlock.getWord());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void switchScene(Parent newScene) {
        Stage primaryStage = App.getPrimaryStage();
        primaryStage.getScene().setRoot(newScene);
        primaryStage.show();
    }

    private static WordBlock currentWordBlock = null;

    private final String cssPath = getClass().getResource("/css/htmlStyle.css").toExternalForm();
    private final String  styleSheet = "<link rel=\"stylesheet\" href=\"" + cssPath + "\">";

    private WebEngine webEngine;


    @FXML
    protected AnchorPane root;
    @FXML
    protected AnchorPane contentAnchorPane;

    public void setupWebView(String content) {
        String encoding = "<meta charset=\"UTF-8\">";
        webEngine.loadContent("<html><body>" + styleSheet + encoding + content + "</body></html>");
//        System.out.println("<html><body>" + styleSheet + encoding + content + "</body></html>");
    }


    @Override
    public void StartShow()
    {

    }

    @Override
    public void EndShow()
    {

    }

    @FXML
    public void LookupWord() throws SQLException {
        //System.out.println("null");

        WordBlock lookUpRes = null;
        if (searchBar.getText() != null && !searchBar.getText().isEmpty()) {
            lookUpRes = DicManager.getInstance().searchWordBlock(searchBar.getText());
        }

        if (lookUpRes == null) {
            //Do nothing
        } else {
            currentWordBlock = lookUpRes;
            lookUpRes.loadData(lookUpRes.getWordID());
            setupWebView(lookUpRes.GetInfo());
            SearchHistory.getInstance().updateHistory(lookUpRes.getWord());

            historyListView.getItems().clear();
            if (!SearchHistory.getInstance().getWordHistory().isEmpty()) {
                historyListView.getItems().addAll(SearchHistory.getInstance().getWordHistory());
            }
            enableTasks();
        }
    }

    private void enableTasks() {
        soundButton.setDisable(false);
        starButton.setDisable(false);

        if (currentWordBlock != null && currentWordBlock.isEditable()) {
            editButton.setDisable(false);
        } else {
            editButton.setDisable(true);
        }
    }

    @FXML
    public void addToStarList() throws SQLException {
        if (currentWordBlock != null) {
            currentWordBlock.setStarred(!currentWordBlock.isStarred());
            currentWordBlock.starInDatabase(currentWordBlock.isStarred());

            if (currentWordBlock.isStarred()) {
                starredWordList.add(currentWordBlock);
                sort(starredWordList);
            } else {
                starredWordList.remove(currentWordBlock);
            }
        }
        starredWordStringList.clear();
        starredWordListView.getItems().clear();
        for (WordBlock wordBlock : starredWordList) {
            starredWordStringList.add(wordBlock.getWord());
        }
        sort(starredWordStringList);
        starredWordListView.getItems().addAll(starredWordStringList);
    }

    @FXML
    public void OnTextChange() {
        String text = searchBar.getText();
        if (text.isEmpty()) return;
        text = text.toLowerCase();
        this.possibleSuggestions = DicManager.getInstance().getDicWordSearcher().Search(text);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        webEngine = webView.getEngine();
        webEngine.loadContent("<html><body>" + styleSheet + "</body></html>");
        //blurPane.setVisible(false);
        if (currentWordBlock == null) {
            editButton.setDisable(true);
            soundButton.setDisable(true);
            starButton.setDisable(true);
        }
        try {
            DicManager.getInstance().getDicWordLoader().LoadFromDatabase();
            DicManager.getInstance().getRecentlySearchedWordManager().getRecentlySearchedWordLoader().Load();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        AutoCompletionBinding<String> auto = TextFields.bindAutoCompletion(this.searchBar,
                new Callback<AutoCompletionBinding.ISuggestionRequest, Collection<String>>() {
                    @Override
                    public Collection<String> call(AutoCompletionBinding.ISuggestionRequest iSuggestionRequest) {
                        if (searchBar.getText().length() <= 1) {
                            return Collections.emptyList();
                        }
                        return possibleSuggestions;
                    }
                }
        );

        for (WordBlock wordBlock : starredWordList) {
            starredWordStringList.add(wordBlock.getWord());
        }
        starredWordListView.getItems().addAll(starredWordStringList);

        if (!SearchHistory.getInstance().getWordHistory().isEmpty()) {
            historyListView.getItems().addAll(SearchHistory.getInstance().getWordHistory());
        }

        auto.setDelay(50);
        //sync width with textField
        auto.prefWidthProperty().bind(searchBar.widthProperty());
    }

    public WordBlock getStarredWord(String word) {
        return starredWordList.get(binarySearch(starredWordList, new WordBlock(word,"")));
    }

    @FXML
    public void listViewMouseClicked(MouseEvent event) throws SQLException {
        if (event.getClickCount() > 1) {
            String currentlySelectedItem = starredWordListView.getSelectionModel().getSelectedItem().toString();
            WordBlock starredWord = getStarredWord(currentlySelectedItem);
            currentWordBlock = starredWord;
            enableTasks();
            starredWord.loadData(starredWord.getWordID());
            setupWebView(starredWord.GetInfo());
        }
    }

    @FXML
    public void getWordFromHistory(MouseEvent event) throws SQLException {
        if (event.getClickCount() > 1) {
            String selectedItem = historyListView.getSelectionModel().getSelectedItem();
            WordBlock word = DicManager.getInstance().searchWordBlock(selectedItem);
            currentWordBlock = word;
            enableTasks();
            word.loadData(word.getWordID());
            setupWebView(word.GetInfo());
        }
    }

    @FXML
    public void clearHistory() {
        historyListView.getItems().clear();
        SearchHistory.getInstance().clearHistory();
    }

    @FXML
    public void onSoundButton() {
        if (currentWordBlock != null) {
            TextToSpeech.EnTextToSpeech(currentWordBlock.getWord());
        }
    }


//    public static void main(String[] arg) {
////        String str = "helloo";
////        String str2 = str;
////        str += "h";
////        System.out.println(str == str2);
//    }
}
