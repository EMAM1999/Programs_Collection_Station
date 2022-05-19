/*
 To change this license header, choose License Headers in Project Properties.
 To change this template file, choose Tools | Templates
 and open the template in the editor.
 */
package guess_number;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**

 @author EMAM
 */
public class GuessNumber extends Application {

    static Stage stage;


    @Override
    public void start(Stage s) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("GameScreen.fxml"));

        Scene scene = new Scene(root);
        stage = s;
        s.setScene(scene);
        s.show();
    }


    public static void openScreen(String path) throws Exception {
        Parent root = FXMLLoader.load(GuessNumber.class.getResource(path));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    /**
     @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
