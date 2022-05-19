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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

/**
 FXML Controller class

 @author EMAM
 */
public class ResultScreenController implements Initializable {

    static int num;

    @FXML
    private Button playBtn;

    @FXML
    private HBox result;

    @FXML
    private ImageView leftDigit;

    @FXML
    private ImageView rightDigit;


    @FXML
    void playAgain(ActionEvent event) throws Exception {
        GuessNumber.openScreen("GameScreen.fxml");
    }


    /**
     Initializes the controller class.

     @param url
     @param rb
     */
    @Override
    public void initialize(URL url , ResourceBundle rb) {
        int ld = num / 10;
        int rd = num % 10;
        leftDigit.setImage(new Image(getClass().getResource("../images/number (" + ld + ").png").toString()));
        rightDigit.setImage(new Image(getClass().getResource("../images/number (" + rd + ").png").toString()));
        leftDigit.setScaleX(0);
        leftDigit.setScaleY(0);
        rightDigit.setScaleX(0);
        rightDigit.setScaleY(0);

        showResult();
//        showPlayAgainBtn();
    }


//    private void hidePlayAgainBtn() {
//        result.setTranslateY(0);
//        result.setScaleX(1);
//        result.setScaleY(1);
//        playBtn.setTranslateY(0);
//    }


//    private void showPlayAgainBtn() {
//        new Timeline(
//                new KeyFrame(Duration.seconds(1) ,
//                        new KeyValue(result.translateYProperty() , -300) ,
//                        new KeyValue(result.scaleXProperty() , 0.5) ,
//                        new KeyValue(result.scaleYProperty() , 0.5)
//                ) ,
//                new KeyFrame(Duration.seconds(1) ,
//                        new KeyValue(playBtn.translateYProperty() , -200)
//                )
//        ).play();
//    }


    private void showResult() {
        new Timeline(new KeyFrame(Duration.seconds(1) ,
                new KeyValue(leftDigit.scaleXProperty() , 1) ,
                new KeyValue(leftDigit.scaleYProperty() , 1) ,
                new KeyValue(rightDigit.scaleXProperty() , 1) ,
                new KeyValue(rightDigit.scaleYProperty() , 1)
        )).play();
    }

}
