/*
 To change this license header, choose License Headers in Project Properties.
 To change this template file, choose Tools | Templates
 and open the template in the editor.
 */
package image.puzzle;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**

 @author EMAM
 */
public class PuzzleBoard {

    public static List<Piece> createPuzzle(Image img ,
            double width , double height ,
            int numberOfColumns , int numberOfRows) {

        double pieceWidth = width / numberOfColumns;
        double pieceHeight = height / numberOfRows;
        final List<Piece> pieces = new ArrayList<>();
        for ( int col = 0 ; col < numberOfColumns ; col++ ) {
            for ( int row = 0 ; row < numberOfRows ; row++ ) {
//                double x = col * pieceWidth;
//                double y = row * pieceHeight;
                final Piece piece = new Piece(img , col , row ,
                        row > 0 , col > 0 , row < numberOfRows - 1 , col < numberOfColumns - 1 ,
                        pieceWidth , pieceHeight , width , height);
                pieces.add(piece);
            }
        }
        return pieces;
    }

    /**
     Node that represents a puzzle piece
     */
    public static class Piece extends Group {

        private final double pieceWidth;
        private final double pieceHeight;

        private final double correctX;
        private final double correctY;
        private final boolean hasTopTab;
        private final boolean hasLeftTab;
        private final boolean hasBottomTab;
        private final boolean hasRightTab;
        private double startDragX;
        private double startDragY;
        private Shape pieceStroke;
        private Shape pieceClip;
        private ImageView imageView = new ImageView();


        public Piece(Image img , final double colNum , final double rowNum ,
                boolean topTab , boolean leftTab , boolean bottomTab , boolean rightTab ,
                double pieceWidth , double pieceHeight ,
                double deskWidth , double deskHeight) {

            this.pieceWidth = pieceWidth;
            this.pieceHeight = pieceHeight;
            this.correctX = colNum * pieceWidth;
            this.correctY = rowNum * pieceHeight;
            this.hasTopTab = topTab;
            this.hasLeftTab = leftTab;
            this.hasBottomTab = bottomTab;
            this.hasRightTab = rightTab;
            // configure clip
            pieceClip = createPiece();
            pieceClip.setFill(Color.WHITE);
            pieceClip.setStroke(null);
            // add a stroke
            pieceStroke = createPiece();
            pieceStroke.setFill(null);
            pieceStroke.setStroke(Color.BLACK);
            // create image view
            imageView.setImage(img);
            imageView.setClip(pieceClip);
            imageView.setFitWidth(deskWidth);
            imageView.setFitHeight(deskHeight);
            setFocusTraversable(true);
            getChildren().addAll(imageView , pieceStroke);
            // turn on caching so the jigsaw piece is fasr to draw when dragging
            setCache(true);
            // start in inactive state
            setActive();
            // add listeners to support dragging
            setOnMousePressed((MouseEvent me) -> {
                toFront();
                startDragX = getTranslateX() - me.getSceneX();
                startDragY = getTranslateY() - me.getSceneY();
            });
            setOnMouseReleased((MouseEvent me) -> {
                double x = 0.15 * this.pieceWidth;
                double y = 0.15 * this.pieceHeight;
                if ( getTranslateX() < x && getTranslateX() > -x ) {
                    if ( getTranslateY() < y && getTranslateY() > -y ) {
                        setTranslateX(0);
                        setTranslateY(0);
                        setInactive();
                    }
                }
            });
            setOnMouseDragged((MouseEvent me) -> {
                double newTranslateX = startDragX + me.getSceneX();
                double newTranslateY = startDragY + me.getSceneY();
//                double minTranslateX = -0.45f* this.pieceWidth - correctX;
//                double maxTranslateX = (deskWidth - pieceWidth + 50f) - correctX;
//                double minTranslateY = -0.30f-0.45f* this.pieceHeight - correctY;
//                double maxTranslateY = (deskHeight - pieceHeight + 70f) - correctY;
//                if ( (newTranslateX > minTranslateX)
//                        && (newTranslateX < maxTranslateX)
//                        && (newTranslateY > minTranslateY)
//                        && (newTranslateY < maxTranslateY) ) 
                setTranslateX(newTranslateX);
                setTranslateY(newTranslateY);
            });
        }


        private Shape createPiece() {
            Shape shape = createPieceRectangle();

            double eclipseRadiusX = 0.175f * this.pieceWidth;
            double eclipseRadiusY = 0.15f * this.pieceHeight;

            double h = 0.08f * this.pieceHeight;
            double y1 = -this.pieceHeight / 2;
            double y2 = y1 + h;
            double x1 = -0.125 * this.pieceWidth;
            double x2 = -x1;
            double w = Math.abs(x1) + Math.abs(x2);

            if ( hasTopTab ) {
                shape = Shape.subtract(shape , createPieceTab(
                        0f , -0.31f * this.pieceHeight , eclipseRadiusX , eclipseRadiusY ,
                        x1 , y1 , w , h ,
                        x1 , y1 + h / 2 , h / 2 ,
                        x2 , y1 + h / 2 , h / 2));
            }
            if ( hasBottomTab ) {
                shape = Shape.union(shape , createPieceTab(
                        0f , 0.69f * this.pieceHeight , eclipseRadiusX , eclipseRadiusY ,
                        x1 , -y1 , w , h ,
                        x1 , -y1 + h / 2 , h / 2 ,
                        x2 , -y1 + h / 2 , h / 2));
            }

            w = 0.08f * this.pieceWidth;
            x1 = this.pieceWidth / 2;
            y1 = -0.125f * this.pieceHeight;
            y2 = -y1;
            h = Math.abs(y1) + Math.abs(y2);
            if ( hasRightTab ) {
                shape = Shape.union(shape , createPieceTab(
                        0.69f * this.pieceWidth , 0f * this.pieceHeight , eclipseRadiusX , eclipseRadiusY ,
                        x1 , y1 , w , h ,
                        x1 + w / 2 , y1 , w / 2 ,
                        x1 + w / 2 , y2 , w / 2));
            }
            if ( hasLeftTab ) {
                shape = Shape.subtract(shape , createPieceTab(
                        -0.31f * this.pieceWidth , 0f * this.pieceHeight , eclipseRadiusX , eclipseRadiusY ,
                        -x1 , y1 , w , h ,
                        -x1 + w / 2 , y1 , w / 2 ,
                        -x1 + w / 2 , y2 , w / 2));
            }
            shape.setTranslateX(correctX);
            shape.setTranslateY(correctY);
            shape.setLayoutX(0.5f * this.pieceWidth);
            shape.setLayoutY(0.5f * this.pieceHeight);
            return shape;
        }


        private Rectangle createPieceRectangle() {
            Rectangle rec = new Rectangle();
            rec.setX(-0.5 * this.pieceWidth);
            rec.setY(-0.5 * this.pieceHeight);
            rec.setWidth(pieceWidth);
            rec.setHeight(pieceHeight);
            return rec;
        }


        private Shape createPieceTab(double eclipseCenterX , double eclipseCenterY , double eclipseRadiusX , double eclipseRadiusY ,
                double rectangleX , double rectangleY , double rectangleWidth , double rectangleHeight ,
                double circle1CenterX , double circle1CenterY , double circle1Radius ,
                double circle2CenterX , double circle2CenterY , double circle2Radius) {
            Ellipse e = new Ellipse(eclipseCenterX , eclipseCenterY , eclipseRadiusX , eclipseRadiusY);
            Rectangle r = new Rectangle(rectangleX , rectangleY , rectangleWidth , rectangleHeight);
            Shape tab = Shape.union(e , r);
            Circle c1 = new Circle(circle1CenterX , circle1CenterY , circle1Radius);
            tab = Shape.subtract(tab , c1);
            Circle c2 = new Circle(circle2CenterX , circle2CenterY , circle2Radius);
            tab = Shape.subtract(tab , c2);
            return tab;
        }


        public void setActive() {
            setDisable(false);
            setEffect(new DropShadow());
            toFront();
        }


        public void setInactive() {
            setEffect(null);
            setDisable(true);
            toBack();
        }


        public double getCorrectX() {
            return correctX;
        }


        public double getCorrectY() {
            return correctY;
        }

    }

}
