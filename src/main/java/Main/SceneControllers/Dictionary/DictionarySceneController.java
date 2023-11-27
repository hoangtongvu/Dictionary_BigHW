package Main.SceneControllers.Dictionary;

import Dictionary.DicManager;
import Main.FxmlFileManager;
import Main.SceneControllers.NavigationPane.NavigationPaneSceneController;
import Main.application.App;
import Word.WordBlock;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import Dictionary.SearchHistory;

import static java.util.Collections.binarySearch;
import static java.util.Collections.sort;


public class DictionarySceneController implements Initializable {

    //private Timer timer;
    //private TimerTask timerTask;

    private static List<WordBlock> starredWordList = new ArrayList<>();
    private static List<String>    starredWordStringList = new ArrayList<>();
    private static List<String>    wordHistoryList = new ArrayList<>();

    public static List<WordBlock> getStarredWordList() {
        return starredWordList;
    }

    private  List<String> possibleSuggestions = new ArrayList<>();
    @FXML
    private TextField searchBar;
    @FXML
    protected AnchorPane drawerMenu;
    @FXML
    protected Pane blurPane;
    @FXML
    protected ImageView menuButton;
    @FXML
    protected WebView webView;
    @FXML
    protected ListView<String> starredWordListView;
    @FXML
    protected ListView<String> historyListView;
    @FXML
    protected Button editButton;

    public static WordBlock getCurrentWordBlock() {
        return currentWordBlock;
    }

    @FXML
    public void editCurrentWord() {
        try {
            FxmlFileManager.getInstance().editWordSceneController.loadWordOnPane(currentWordBlock.getWord());
            switchScene(FxmlFileManager.getInstance().editWordScene);
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
    }


    @FXML
    public void LookupWord() throws SQLException {
        //System.out.println("null");

        WordBlock lookUpRes = null;
        if (searchBar.getText() != null && !searchBar.getText().equals("")) {
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

            if (currentWordBlock != null && currentWordBlock.isEditable()) {
                editButton.setDisable(false);
            } else {
                editButton.setDisable(true);
            }
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
        blurPane.setVisible(false);
        if (currentWordBlock == null) {
            editButton.setDisable(true);
        }
        try {
            DicManager.getInstance().getDicWordLoader().LoadFromDatabase();
            DicManager.getInstance().getRecentlySearchedWordManager().getRecentlySearchedWordLoader().Load();

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            NavigationPaneSceneController.LoadInstance().AddNavPaneComponentsToRoot(this.root);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        AutoCompletionBinding auto = TextFields.bindAutoCompletion(this.searchBar,
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
            starredWord.loadData(starredWord.getWordID());
            setupWebView(starredWord.GetInfo());
        }
    }

    @FXML
    public void getWordFromHistory(MouseEvent event) throws SQLException {
        if (event.getClickCount() > 1) {
            String selectedItem = historyListView.getSelectionModel().getSelectedItem().toString();
            WordBlock word = DicManager.getInstance().searchWordBlock(selectedItem);
            currentWordBlock = word;
            word.loadData(word.getWordID());
            setupWebView(word.GetInfo());
        }
    }

    @FXML
    public void clearHistory() {
        historyListView.getItems().clear();
        SearchHistory.getInstance().clearHistory();
    }

//    public static void main(String[] arg) {
////        String str = "helloo";
////        String str2 = str;
////        str += "h";
////        System.out.println(str == str2);
//    }
}
