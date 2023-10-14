package Main.SceneControllers.Dictionary;

import Dictionary.DicManager;
import Main.SceneControllers.Standard.StandardScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.util.*;


public class MainSceneController extends StandardScene implements Initializable {

    //private Timer timer;
    //private TimerTask timerTask;

    private AutoCompletionBinding autoCompletionBinding;

    private  List<String> possibleSuggestions = new ArrayList<>();

    @FXML
    private ScrollPane contentScrollPane;

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
        String lookUpRes = DicManager.getInstance().LookUpWord(this.searchBar.getText());//Cant find cause dic load default on button click
        Text text = new Text(lookUpRes);
        this.contentScrollPane.setContent(text);

    }


    @FXML
    public void OnTextChange() {
        try {
            String text = searchBar.getText();
            //List<String> suggestions = DicManager.getInstance().getDicWordSearcher().Search(text);
            //System.out.println(suggestions);


//        if (this.autoCompletionBinding != null) this.autoCompletionBinding.dispose();
//        this.autoCompletionBinding = TextFields.bindAutoCompletion(this.tfTitle, suggestions);
            this.possibleSuggestions = DicManager.getInstance().getDicWordSearcher().Search(text);
        } catch (Exception ignored) {

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        blurPane.setVisible(false);
        try {
            DicManager.getInstance().getDicWordLoader().DefaultLoad();
            
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
        
        auto.setDelay(0);
        
        //sync width with textField
        auto.prefWidthProperty().bind(this.searchBar.widthProperty());

//        TextFields.bindAutoCompletion(this.tfTitle, input ->
//            {
//                if (tfTitle.getText().length() <= 1) return Collections.emptyList();
//                return this.possibleSuggestions;
//            }
//        );

    }

}
