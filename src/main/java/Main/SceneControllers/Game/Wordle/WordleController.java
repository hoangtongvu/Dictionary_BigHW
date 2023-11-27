package Main.SceneControllers.Game.Wordle;

import Game.GamesCtrl;
import Game.Wordle.WordleCtrl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

public class WordleController implements Initializable {

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

    private String answer;
    private final String green = "#538D4E";
    private final String yellow = "#F1D669";

    private int guesses;

    private WordleCtrl wordleCtrl;

    public void generateWord() throws FileNotFoundException {
        Random random = new Random();
        int n = random.nextInt(3103) + 1;
        File file = new File("src/main/resources/data/wordsForWordle.txt");
        Scanner sc = new Scanner(file);

        for (int i = 1; i <= n; i++) {
            answer = sc.nextLine().toUpperCase();
        }
        System.out.println(answer);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.wordleCtrl = GamesCtrl.getInstance().getWordleCtrl();
        guesses = 0;
        try {
            generateWord();
        } catch (FileNotFoundException e) {
            System.out.println("wordsForWordle not found");
        }
    }

    @FXML
    public void onSubmitButtonClick(ActionEvent e) throws NullPointerException {
        Label[] row = getLabels();
        int[] state = {-1, -1, -1, -1, -1};
        String text = guessBox.getText().toUpperCase();

        if (text.length() == 5) {
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
                return;
            }
            guesses += 1;
        } else {
            report.setText("Please enter a 5-letter word!");
        }

        if(guesses == 6) {
            report.setText("You lose! The correct answer is: " + answer);
        }
    }

    private Label[] getLabels() {
        Label[] row = new Label[5];
        if (guesses == 0) row = new Label[]{box00, box01, box02, box03, box04};
        if (guesses == 1) row = new Label[]{box10, box11, box12, box13, box14};
        if (guesses == 2) row = new Label[]{box20, box21, box22, box23, box24};
        if (guesses == 3) row = new Label[]{box30, box31, box32, box33, box34};
        if (guesses == 4) row = new Label[]{box40, box41, box42, box43, box44};
        if (guesses == 5) row = new Label[]{box50, box51, box52, box53, box54};
        return row;
    }
}
