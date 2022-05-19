/*
 To change this license header, choose License Headers in Project Properties.
 To change this template file, choose Tools | Templates
 and open the template in the editor.
 */
package image.puzzle.game_screen;

import image.puzzle.ImagePuzzle;
import static image.puzzle.ImagePuzzle.IMAGES;
import image.puzzle.PuzzleBoard;
import image.puzzle.PuzzleBoard.Piece;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**

 @author EMAM
 */
public class GameScreenController implements Initializable {

    public static ImageView image;

    @FXML
    private Pane board;

    public static int rows = 5;
    public static int cols = 5;


    @FXML
    void back(MouseEvent event) throws IOException {
        ImagePuzzle.openScreen(IMAGES);
    }


    @Override
    public void initialize(URL url , ResourceBundle rb) {
        List<Piece> pieces = PuzzleBoard.createPuzzle(image.getImage() , board.getPrefWidth() , board.getPrefHeight() , rows , cols);
        randomizeThePieces(pieces);
        Pane p = new Pane();
        p.getChildren().addAll(pieces);
        p.setPrefSize(board.getPrefWidth() , board.getPrefHeight());
        board.getChildren().add(p);
    }


    private void randomizeThePieces(List<Piece> pieces) {
        for ( Piece piece : pieces ) {
            double minX = 700 - piece.getCorrectX();
            double maxX = minX + 300;
            double x = Math.random() * (maxX - minX) + minX;
            double minY = -50 - piece.getCorrectY();
            double maxY = minY + 550;
            double y = Math.random() * (maxY - minY) + minY;
            piece.setTranslateX(x);
            piece.setTranslateY(y);
            System.out.println(piece.getLayoutX());
            System.out.println(x);
        }
    }

}
