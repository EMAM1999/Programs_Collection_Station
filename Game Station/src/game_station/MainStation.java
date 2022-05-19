/*
 To change this license header, choose License Headers in Project Properties.
 To change this template file, choose Tools | Templates
 and open the template in the editor.
 */
package game_station;

import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.stage.*;

/**
 @author EMAM
 */
public class MainStation extends Application {

    public static Stage stage;

    private Map<String , String> nodesURL;


    @Override
    public void start(Stage s) throws Exception {
        initURLs();
//        Parent root = FXMLLoader.load(getClass().getResource("StationMainPage.fxml"));
        Parent root = initRoot(500 , 500 , nodesURL);
        Scene scene = new Scene(root , Color.TRANSPARENT);
        stage = new Stage(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();
    }


    public static void openScreen(String screenId) {
        switch ( screenId.toLowerCase() ) {
            case "tic tac toe":
                break;
            case "snake":
                break;
            case "puzzle":
                break;
            case "guessing":
                break;
            case "snakes and ladders":
                break;
            default:
                throw new AssertionError("The id is not matched");
        }
    }


    /**
     @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }


    private Parent initRoot(double width , double height , Map<String , String> nodesURL) {
        return new Root(width , height , nodesURL);
    }


    private void initURLs() {
        nodesURL = new HashMap<>();
        nodesURL.put(getClass().getResource("..\\images\\center.png").toString() , "center");
        nodesURL.put(getClass().getResource("..\\images\\tic tac toe.jpg").toString() , "tic tac toe");
        nodesURL.put(getClass().getResource("..\\images\\snake.png").toString() , "snake");
        nodesURL.put(getClass().getResource("..\\images\\puzzle.png").toString() , "puzzle");
        nodesURL.put(getClass().getResource("..\\images\\guessing.png").toString() , "guessing");
        nodesURL.put(getClass().getResource("..\\images\\snakes and ladders.png").toString() , "snakes and ladders");
    }

}
