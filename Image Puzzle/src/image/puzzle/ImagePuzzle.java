/*
 To change this license header, choose License Headers in Project Properties.
 To change this template file, choose Tools | Templates
 and open the template in the editor.
 */
package image.puzzle;

import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**

 @author EMAM
 */
public class ImagePuzzle extends Application {

    public static final String START = "start_screen\\StartScreen.fxml";
    public static final String GAME = "game_screen\\GameScreen.fxml";
    public static final String IMAGES = "images_screen\\ImagesScreen.fxml";

    public static File imagesFolder;
    public static Stage stage;


    @Override
    public void start(Stage s) throws Exception {
        imagesFolder = new File("src\\image\\puzzle\\images");

        Parent root = FXMLLoader.load(getClass().getResource("start_screen/StartScreen.fxml"));
        Scene scene = new Scene(root);
        stage = s;
        s.setResizable(false);
        s.setScene(scene);
        s.show();
    }


    /**
     @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }


    public static void openScreen(String path) throws IOException {
        Parent root = FXMLLoader.load(ImagePuzzle.class.getResource(path));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
