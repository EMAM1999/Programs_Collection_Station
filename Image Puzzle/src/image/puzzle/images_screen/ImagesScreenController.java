/*
 To change this license header, choose License Headers in Project Properties.
 To change this template file, choose Tools | Templates
 and open the template in the editor.
 */
package image.puzzle.images_screen;

import image.puzzle.ImagePuzzle;
import image.puzzle.game_screen.GameScreenController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 FXML Controller class

 @author EMAM
 */
public class ImagesScreenController implements Initializable {

    public static String id;

    @FXML
    private Pane imagesBoard;


    @FXML
    void back(MouseEvent event) throws IOException {
        ImagePuzzle.openScreen(ImagePuzzle.START);
    }


    /**
     Initializes the controller class.

     @param url
     @param rb
     */
    @Override
    public void initialize(URL url , ResourceBundle rb) {
        listTheImages();
    }


    private void listTheImages() {
        File f = new File(ImagePuzzle.imagesFolder.getAbsolutePath() + "/" + id.toLowerCase());
        File[] files = f.listFiles();
        for ( int i = 0 ; i < files.length ; i++ ) {
            if ( files[i].isFile() ) {
                try {
                    ImageView img = new ImageView(new Image(new FileInputStream(files[i])));
                    img.setFitHeight(140);
                    img.setFitWidth(175);
                    img.setPreserveRatio(false);
                    img.setTranslateX(192 * (i % 4));
                    img.setTranslateY(150 * (i / 4));
                    img.setCursor(Cursor.HAND);
                    img.setOnMouseClicked((event) -> {
                        try {
                            GameScreenController.image = img;
                            ImagePuzzle.openScreen(ImagePuzzle.GAME);
                        } catch ( IOException ex ) {
                        }
                    });
                    imagesBoard.getChildren().add(img);
                    imagesBoard.setPrefHeight(150 + 150 * (i / 4));
                } catch ( FileNotFoundException ex ) {
                }
            }
        }
    }

}
