package Main;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InputManager 
{
    
    private Scene scene;

    public InputManager(Scene mainScene)
    {
        this.scene = mainScene;
        this.AddInputKeys();
    }

    private void AddInputKeys()
    {
        this.scene.addEventHandler
        (
            KeyEvent.KEY_PRESSED,
            (key) -> {
                if (key.getCode() == KeyCode.ENTER) {
                    System.out.println("Enter pressed");
                }
            } 
        );
    }


}
