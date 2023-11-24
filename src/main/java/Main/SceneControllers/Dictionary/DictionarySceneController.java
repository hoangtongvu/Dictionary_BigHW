package Main.SceneControllers.Dictionary;

import Dictionary.DicManager;
import Main.SceneControllers.NavigationPane.NavigationPaneSceneController;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Callback;
import javafx.util.Duration;
import netscape.javascript.JSObject;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;


public class DictionarySceneController implements Initializable {

    //private Timer timer;
    //private TimerTask timerTask;

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
    private WebEngine webEngine;

    @FXML
    protected AnchorPane root;

    private final String cssPath = getClass().getResource("/css/htmlStyle.css").toExternalForm();
    private final String  styleSheet = "<link rel=\"stylesheet\" href=\"" + cssPath + "\">";

    public void setupWebView(String content) {

        String encoding = "<meta charset=\"UTF-8\">";
        webEngine.loadContent("<html><body>" + styleSheet + encoding + content + "</body></html>");
    }

    @FXML
    public void LookupWord() throws SQLException {
        //System.out.println("null");

        String lookUpRes = DicManager.getInstance().LookUpWord(searchBar.getText());
        if (lookUpRes == null) {
            //Do nothing
        } else {
            setupWebView(lookUpRes);
        }

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
        blurPane.setVisible(false);
        webEngine = webView.getEngine();
        webEngine.loadContent("<html><body>" + styleSheet + "</body></html>");
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
        
        auto.setDelay(50);


        //sync width with textField
        auto.prefWidthProperty().bind(searchBar.widthProperty());
    }
}
