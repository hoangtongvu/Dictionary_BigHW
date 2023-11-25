package Main.SceneControllers.Dictionary;

import Dictionary.DicManager;
import Main.SceneControllers.NavigationPane.NavigationPaneSceneController;
import Word.WordBlock;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Callback;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

import static java.util.Collections.binarySearch;
import static java.util.Collections.sort;


public class DictionarySceneController implements Initializable {

    //private Timer timer;
    //private TimerTask timerTask;

    private static List<WordBlock> starredWordList = new ArrayList<>();
    private static List<String>    starredWordStringList = new ArrayList<>();
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
    ImageView menuButton;
    @FXML
    WebView webView;
    @FXML
    protected ListView<String> starredWordListView;

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

        WordBlock lookUpRes = DicManager.getInstance().searchWordBlock(searchBar.getText());
        if (lookUpRes == null) {
            //Do nothing
        } else {
            currentWordBlock = lookUpRes;
            lookUpRes.loadData(lookUpRes.getWordID());
            setupWebView(lookUpRes.GetInfo());
        }
    }

    @FXML
    public void addToStarList() throws SQLException {
        if (currentWordBlock != null) {
            currentWordBlock.setStarred(!currentWordBlock.isStarred());
            currentWordBlock.starInDatabase(currentWordBlock.isStarred());

            if (currentWordBlock.isStarred()) {
                starredWordList.add(currentWordBlock);
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
                        if (searchBar.getText().length() <= 1) return Collections.emptyList();
                        return possibleSuggestions;
                    }
                }
        );

        for (WordBlock wordBlock : starredWordList) {
            starredWordStringList.add(wordBlock.getWord());
        }
        starredWordListView.getItems().addAll(starredWordStringList);
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
}
