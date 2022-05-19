/*
 To change this license header, choose License Headers in Project Properties.
 To change this template file, choose Tools | Templates
 and open the template in the editor.
 */
package image.puzzle.start_screen;

import image.puzzle.ImagePuzzle;
import image.puzzle.images_screen.ImagesScreenController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 FXML Controller class

 @author EMAM
 */
public class StartScreenController implements Initializable {

    @FXML
    void back(MouseEvent event) {
        Platform.exit();
    }


    @FXML
    void clicked(MouseEvent event) throws IOException {
        ImagesScreenController.id = ((Node)event.getSource()).getId();
        ImagePuzzle.openScreen(ImagePuzzle.IMAGES);
    }


    @FXML
    void entered(MouseEvent event) {
        ((Node) event.getSource()).setScaleX(1.1);
        ((Node) event.getSource()).setScaleY(1.1);
    }


    @FXML
    void exited(MouseEvent event) {
        ((Node) event.getSource()).setScaleX(1);
        ((Node) event.getSource()).setScaleY(1);
    }


    /**
     Initializes the controller class.

     @param url
     @param rb
     */
    @Override
    public void initialize(URL url , ResourceBundle rb) {
        // TODO
    }

}
