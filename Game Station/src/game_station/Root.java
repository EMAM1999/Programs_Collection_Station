/*
 To change this license header, choose License Headers in Project Properties.
 To change this template file, choose Tools | Templates
 and open the template in the editor.
 */
package game_station;

import style.OpeningAnimation;
import static game_station.MainStation.stage;
import java.util.ArrayList;
import java.util.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.util.Duration;
import style.OpeningAnimationBuilder;

/**

 @author EMAM
 */
public class Root extends StackPane {

/////////////controlling values/////////////
    private final double DISTANCE_BETWEEN_NODES_CENTER = 190;
    private final double ANIMATION_SPEED = 3;
    private final double SECONDS_BEFORE_AUTO_CLOSE = 30;
    private final double CENTER_HEIGHT = 150;
    private final double CENTER_WIDTH = 150;
    private final double NODE_HEIGHT = 100;
    private final double NODE_WIDTH = 100;
    private final double SCALE_RAISE = 0.05;
///////////////////////////////////////

    private final int OPENED = 1;
    private final int CLOSED = -1;
    private final int NONE = 0;

    private final ImageView center;
    private final ArrayList<Node> images;

    private final OpeningAnimation animation;
    private int opened;
    private boolean wasDraged;
    private boolean draggable;
    private double xOffset;
    private double yOffset;

    private final Timeline autoCloseExpantion;


    public Root(double width , double height , Map<String , String> nodesURL) {
        wasDraged = false;
        draggable = true;
        opened = CLOSED;

        setPrefSize(width , height);
        this.center = new ImageView();
        this.images = new ArrayList<>();
        initNodes(nodesURL);

        animation = new OpeningAnimationBuilder(this.images)
                .withStyle(OpeningAnimation.Style.TRAIN)
                .withDistance(DISTANCE_BETWEEN_NODES_CENTER)
                .withSpeed(ANIMATION_SPEED)
                .build();
        autoCloseExpantion = new Timeline(new KeyFrame(Duration.seconds(SECONDS_BEFORE_AUTO_CLOSE) , ($) -> close()));
    }


    private void initCenter(String centerUrl) {
        this.center.setImage(new Image(centerUrl));
        this.center.setOnMouseClicked((e) -> click(e));
        this.center.setOnMousePressed((e) -> pressed(e));
        this.center.setOnMouseReleased((e) -> released(e));
        this.center.setOnMouseDragged((e) -> drag(e));
        this.center.setOnMouseEntered((e) -> entered(e));
        this.center.setOnMouseExited((e) -> exited(e));
        this.center.setFitHeight(CENTER_HEIGHT);
        this.center.setFitWidth(CENTER_WIDTH);
        this.center.setPreserveRatio(true);
        this.getChildren().add(this.center);
    }


    private void initNodes(Map<String , String> imgsURL) {
        imgsURL.keySet().forEach((url) -> {
            if ( imgsURL.get(url).equalsIgnoreCase("center") ) {
                initCenter(url);
            } else {
                ImageView img = new ImageView(new Image(url));
//                img = ImageClipper.clipImage(img , 20);
                img.setId(imgsURL.get(url));
                img.setFitHeight(NODE_HEIGHT);
                img.setFitWidth(NODE_WIDTH);
                img.setPreserveRatio(true);
                img.setOnMouseEntered((e) -> entered(e));
                img.setOnMouseExited((e) -> exited(e));
                img.setOnMouseClicked((e) -> openGame(e));
                this.getChildren().add(0 , img);
                this.images.add(img);
            }
        });
        setImagesVisibility(false);
    }


    private void click(MouseEvent event) {
        if ( wasDraged ) {
            wasDraged = false;
            return;
        }
        if ( event.getButton() == MouseButton.PRIMARY ) {
            if ( event.isControlDown() ) {
                exitPlatform();
            } else if ( event.isShiftDown() ) {
                draggable = !draggable;
            } else {
                openCloseAction();
            }
        } else if ( event.getButton() == MouseButton.SECONDARY ) {
            if ( opened == OPENED ) {
                changeAnimationStyle();
            } else if ( opened == CLOSED ) {
                exitPlatform();
            }
        }
    }


    private void released(MouseEvent event) {
        if ( wasDraged ) {
            Rectangle2D screenBounds = Screen.getPrimary().getBounds();
            double ScreenY = screenBounds.getHeight();
            double ScreenX = screenBounds.getWidth();
            double x1 = stage.getX();
            double y1 = stage.getY();
            double x2 = x1 + stage.getWidth();
            double y2 = y1 + stage.getHeight();

            double[] angles = generateNewAngles(x1 , y1 , x2 , y2 , ScreenX , ScreenY);
            double startAngle = angles[0];
            double angle = angles[1];

            animation.setAngle(startAngle , angle);
            if ( opened == OPENED ) {
                refreshTheAnimation();
            }

        }
    }


    private void pressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }


    private void drag(MouseEvent event) {
        if ( draggable ) {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
            wasDraged = true;
        }
    }


    private void entered(MouseEvent event) {
        ((Node) event.getSource()).setScaleX(1 + SCALE_RAISE);
        ((Node) event.getSource()).setScaleY(1 + SCALE_RAISE);
        ((Node) event.getSource()).setEffect(new DropShadow(2 , 1 , 3 , Color.web("0x66666666")));
    }


    private void exited(MouseEvent event) {
        ((Node) event.getSource()).setScaleX(1);
        ((Node) event.getSource()).setScaleY(1);
        ((Node) event.getSource()).setEffect(null);
    }


    private void openGame(MouseEvent event) {
        String id = ((Node) event.getSource()).getId();
        MainStation.openScreen(id);
    }


    private void exitPlatform() {
        Platform.exit();
    }


    private double[] generateNewAngles(double x1 , double y1 , double x2 , double y2 , double ScreenX , double ScreenY) {
        double startAngle;
        double angle;
        int n = this.images.size();
        if ( y1 <= 0 ) {
            if ( x1 <= 0 ) {
                startAngle = -90;
                angle = -(90 + 90 / (n - 1));
            } else if ( x2 >= ScreenX ) {
                startAngle = 90;
                angle = 90 + 90 / (n - 1);
            } else {
                startAngle = 90;
                angle = 180 + 180 / (n - 1);
            }
        } else if ( y2 >= ScreenY ) {
            if ( x1 <= 0 ) {
                startAngle = 0;
                angle = -(90 + 90 / (n - 1));
            } else if ( x2 >= ScreenX ) {
                startAngle = 0;
                angle = 90 + 90 / (n - 1);
            } else {
                startAngle = -90;
                angle = 180 + 180 / (n - 1);
            }
        } else {
            if ( x1 <= 0 ) {
                startAngle = 0;
                ///////////
                angle = -(180 + 180 / (n - 1));
            } else if ( x2 >= ScreenX ) {
                startAngle = 0;
                angle = 180 + 180 / (n - 1);
            } else {
                startAngle = 0;
                angle = 360;
            }
        }
        return new double[] { startAngle , angle };
    }


    private void setImagesVisibility(boolean b) {
        images.forEach((img) -> {
            img.setVisible(b);
        });
    }


    private void openCloseAction() {
        int isOpened = opened;
        opened = NONE;
        setImagesVisibility(true);
        if ( isOpened == OPENED ) {
            close();
        } else if ( isOpened == CLOSED ) {
            open();
        }
    }


    private void open() {
        animation.getOpenTimeLine().setOnFinished((e) -> opened = OPENED);
        animation.getOpenTimeLine().play();
        startTimer();
    }


    private void close() {
        animation.getCloseTimeLine().setOnFinished((e) -> {
            setImagesVisibility(false);
            stopTimer();
            opened = CLOSED;
        });
        animation.getCloseTimeLine().play();
    }


    private void changeAnimationStyle() {
        this.animation.setNextStyle();
        refreshTheAnimation();
    }


    private void refreshTheAnimation() {
        stopTimer();
        opened = NONE;
        this.animation.getCloseTimeLine().setOnFinished((e) -> this.animation.getOpenTimeLine().play());
        this.animation.getOpenTimeLine().setOnFinished((e) -> {
            opened = OPENED;
            startTimer();
        });
        this.animation.getCloseTimeLine().play();
        this.animation.getOpenTimeLine().play();
    }


    private void stopTimer() {
        autoCloseExpantion.stop();
    }


    private void startTimer() {
        autoCloseExpantion.play();
    }

}
