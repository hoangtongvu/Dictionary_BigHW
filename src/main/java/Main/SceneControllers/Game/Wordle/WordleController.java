package Main.SceneControllers.Game.Wordle;

import Game.GamesCtrl;
import Game.Wordle.WordleCtrl;

import Interfaces.IHasBackButton;

import Main.SceneControllers.BaseSceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;


public class WordleController extends BaseSceneController implements Initializable, IHasBackButton {


    @FXML
    private TextField guessBox;

    @FXML
    private Label report;

    @FXML
    private Label box00 = new Label();

    @FXML
    private Label box01 = new Label();

    @FXML
    private Label box02 = new Label();

    @FXML
    private Label box03 = new Label();

    @FXML
    private Label box04 = new Label();

    @FXML
    private Label box10 = new Label();

    @FXML
    private Label box11 = new Label();

    @FXML
    private Label box12 = new Label();

    @FXML
    private Label box13 = new Label();

    @FXML
    private Label box14 = new Label();

    @FXML
    private Label box20 = new Label();

    @FXML
    private Label box21 = new Label();

    @FXML
    private Label box22 = new Label();

    @FXML
    private Label box23 = new Label();

    @FXML
    private Label box24 = new Label();

    @FXML
    private Label box30 = new Label();

    @FXML
    private Label box31 = new Label();

    @FXML
    private Label box32 = new Label();

    @FXML
    private Label box33 = new Label();

    @FXML
    private Label box34 = new Label();

    @FXML
    private Label box40 = new Label();

    @FXML
    private Label box41 = new Label();

    @FXML
    private Label box42 = new Label();

    @FXML
    private Label box43 = new Label();

    @FXML
    private Label box44 = new Label();

    @FXML
    private Label box50 = new Label();

    @FXML
    private Label box51 = new Label();

    @FXML
    private Label box52 = new Label();

    @FXML
    private Label box53 = new Label();

    @FXML
    private Label box54 = new Label();

    @FXML
    private AnchorPane DialogBox;

    private String answer;
    private final String green = "#538D4E";
    private final String yellow = "#F1D669";

    private int guesses;

    private WordleCtrl wordleCtrl;


    @Override
    public void StartShow() {

    }

    @Override
    public void EndShow() {

    }

    @Override
    public void update() {

    }

    public void generateWord() throws FileNotFoundException {
        Random random = new Random();
        int n = random.nextInt(3104) + 1;
        File file = new File("src/main/resources/data/wordsForWordle.txt");
        Scanner sc = new Scanner(file);

        for (int i = 1; i <= n; i++) {
            answer = sc.nextLine().toUpperCase();
        }
        System.out.println(answer);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DialogBox.setVisible(false);
        this.wordleCtrl = GamesCtrl.getInstance().getWordleCtrl();
        guesses = 0;
        try {
            generateWord();
        } catch (FileNotFoundException e) {
            System.out.println("wordsForWordle not found");
        }
    }

    @FXML
    public void onSubmitButtonClick(ActionEvent e) throws NullPointerException, FileNotFoundException {
        Label[] row = getLabels(guesses);
        int[] state = {-1, -1, -1, -1, -1};
        String text = guessBox.getText().toUpperCase();

        if (text.length() == 5 && findWord(text)) {
            report.setText("");
            int[] arr = new int[26];
            for (int i = 0; i < 5; i++) {
                arr[answer.charAt(i) - 65] += 1;
                //row[i].setStyle("-fx-background-color: white");
            }

            for (int j = 0; j < 5; j++) {
                if (text.substring(j, j + 1).equals(answer.substring(j, j + 1)) && arr[text.charAt(j) - 65] > 0) {
                    state[j] = 1;
                    arr[answer.charAt(j) - 65] -= 1;
                }
            }

            for (int j = 0; j < 5; j++) {
                if (answer.indexOf(text.charAt(j)) > -1 && arr[text.charAt(j) - 65] > 0) {
                    if (state[j] != 1) {
                        state[j] = 0;
                        arr[text.charAt(j) - 65] -= 1;
                    }
                }
            }

            for (int i = 0; i < 5; i++) {
                if (state[i] == 1) row[i].setStyle("-fx-background-color: " + green);
                if (state[i] == 0) row[i].setStyle("-fx-background-color: " + yellow);
                row[i].setText(text.substring(i, i + 1));
            }
            guessBox.clear();

            if (text.equals(answer)) {
                report.setText("Congratulations! You win!");
                DialogBox.setVisible(true);
                return;
            }
            guesses += 1;
        } else if(text.length() != 5) {
            report.setText("Please enter a 5-letter word!");
        } else if(!findWord(text)) {
            report.setText("Not in word list!");
        }

        if(guesses == 6) {
            report.setText("You lose! The correct answer is: " + answer);
            DialogBox.setVisible(true);
        }
    }

    private Label[] getLabels(int number) {
        Label[] row = new Label[5];
        if (number == 0) row = new Label[]{box00, box01, box02, box03, box04};
        if (number == 1) row = new Label[]{box10, box11, box12, box13, box14};
        if (number == 2) row = new Label[]{box20, box21, box22, box23, box24};
        if (number == 3) row = new Label[]{box30, box31, box32, box33, box34};
        if (number == 4) row = new Label[]{box40, box41, box42, box43, box44};
        if (number == 5) row = new Label[]{box50, box51, box52, box53, box54};
        return row;
    }

    private boolean findWord(String text) throws FileNotFoundException {
        File file = new File("src/main/resources/data/wordsForWordle.txt");
        Scanner sc = new Scanner(file);
        String str = "";
        for (int i = 1; i <= 3104; i++) {
            str = sc.nextLine().toUpperCase();
            if (str.equals(text)) {
                return true;
            }
        }
        return false;
    }

    private void clearLabels() {
        Label[] row;
        for (int i = 0; i <= 5; i++) {
            row = getLabels(i);
            for (int j = 0; j < 5; j++) {
                row[j].setText("");
                row[j].setStyle("-fx-background-color: white");
            }
        }
    }
    @FXML
    public void playAgain(ActionEvent e) throws FileNotFoundException {
        clearLabels();
        generateWord();
        guesses = 0;
        report.setText("");
        DialogBox.setVisible(false);
    }

    public void noPlayAgain(ActionEvent e) {
        DialogBox.setVisible(false);
    }
}
