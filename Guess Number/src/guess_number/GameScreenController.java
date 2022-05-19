/*
 To change this license header, choose License Headers in Project Properties.
 To change this template file, choose Tools | Templates
 and open the template in the editor.
 */
package guess_number;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

/**

 @author EMAM
 */
public class GameScreenController implements Initializable {

    @FXML
    private Group train;

    @FXML
    private Button about;

    @FXML
    private Button back;

    @FXML
    private Button go;

    @FXML
    private Group car1;

    @FXML
    private Button n0;

    @FXML
    private Button y0;

    @FXML
    private Button y1;

    @FXML
    private Button n1;

    @FXML
    private Button n2;

    @FXML
    private Button y2;

    @FXML
    private Button y3;

    @FXML
    private Button n3;

    @FXML
    private Button y4;

    @FXML
    private Button n4;

    @FXML
    private Button y5;

    @FXML
    private Button n5;


    @FXML
    void back(MouseEvent event) {
        Platform.exit();
    }


    @FXML
    void go(MouseEvent event) {
        move();
    }


    @FXML
    void isNo(MouseEvent event) throws Exception {
        nextCard();
    }


    @FXML
    void isYes(MouseEvent event) throws Exception {
        result += num;
        nextCard();
    }

    int num;
    int result;


    @Override
    public void initialize(URL url , ResourceBundle rb) {
        result = 0;
        num = 1;
    }


    private void move() {
        double x = train.getTranslateX();
//        train.setTranslateX(x - 565);
        new Timeline(new KeyFrame(Duration.seconds(1) ,
                new KeyValue(train.translateXProperty() , x - 565)
        )).play();

    }


    private void nextCard() throws Exception {
        if ( num == 32 ) {
            ResultScreenController.num = result;
            GuessNumber.openScreen("ResultScreen.fxml");
        } else {
            move();
        }
        num *= 2;
    }

}
