package Main.SceneControllers.Dictionary;

import Dictionary.DicManager;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.util.*;


public class MainSceneController implements Initializable {

    //private Timer timer;
    //private TimerTask timerTask;

    private  List<String> possibleSuggestions = new ArrayList<>();

    @FXML
    TextArea textArea;

    @FXML
    private TextField searchBar;

    @FXML
    void btnOKClicked(ActionEvent event) {
        //Stage mainWindow = (Stage) this.ttTitle.getScene().getWindow(); 
        //String title = this.ttTitle.getText();
        //mainWindow.setTitle(title);

        // this.timer = new Timer();
        // this.timerTask = new TimerTask() 
        // {
            
        //     @Override
        //     public void run()
        //     {
        //         String text = ttTitle.getText();
        //         System.out.println(text);
        //     }
        // };

        //this.timer.scheduleAtFixedRate(timerTask, 0, 500);


    }

    @FXML
    public void LookupWord() {
        //System.out.println("null");
        String lookUpRes = DicManager.getInstance().LookUpWord(searchBar.getText());//Cant find cause dic load default on button click
        Text text = new Text(lookUpRes);
        textArea.setText(text.getText());
    }


    @FXML
    public void OnTextChange() {

        String text = searchBar.getText();
        if (text.isEmpty()) return;
        this.possibleSuggestions = DicManager.getInstance().getDicWordSearcher().Search(text);
    }

    @FXML
    ImageView menuButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        blurPane.setVisible(false);
        textArea.setWrapText(true);
        textArea.setEditable(false);

        try {
            DicManager.getInstance().getDicWordLoader().DefaultLoad();
            DicManager.getInstance().getRecentlySearchedWordManager().getRecentlySearchedWordLoader().Load();
            
        } catch (Exception e) {
            System.out.println("Can't load dic file");
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
        auto.prefWidthProperty().bind(this.searchBar.widthProperty());

//        TextFields.bindAutoCompletion(this.tfTitle, input ->
//            {
//                if (tfTitle.getText().length() <= 1) return Collections.emptyList();
//                return this.possibleSuggestions;
//            }
//        );

    }

    /**This part is for side menu*/
    @FXML
    public void initialize() {
        blurPane.setVisible(false);
    }

    @FXML
    protected AnchorPane drawerMenu;
    @FXML
    protected Pane blurPane;

    /**Activate drawer menu translateTransition for drawer menu, fadeTransition for blurPane.*/
    @FXML
    protected void onMenuButton() {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5),drawerMenu);
        translateTransition.setByX(+235);
        translateTransition.play();

        blurPane.setVisible(true);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5),blurPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    /**setOnFinished(lambdaExpression) to wait for the blur animation to finish before set blurPane invisible,
     * otherwise, blurPane will disappear immediately.*/
    @FXML
    protected void onMenuExit() {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5),drawerMenu);
        translateTransition.setByX(-235);
        translateTransition.play();

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5),blurPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        fadeTransition.setOnFinished(event -> {blurPane.setVisible(false);});
    }
}
