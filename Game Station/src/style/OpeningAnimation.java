/*
 To change this license header, choose License Headers in Project Properties.
 To change this template file, choose Tools | Templates
 and open the template in the editor.
 */
package style;

import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.util.Duration;

/**

 @author EMAM
 */
public final class OpeningAnimation {

    public static enum Style {
        IN_OUT, SPREAD, ZOOM, FADE, TORNADO, DISTRIBUTION, TRAIN
    }

    private double speed;
    private Style style;
    private final Timeline openTimeLine;
    private final Timeline closeTimeLine;
    private final List<Node> nodes;
    private final double distance;
    private double startAngle;
    private double totalAngle;


    public OpeningAnimation(Style style , double startAngle , double totalAngle , double distance , double speed , List<Node> nodes) {
        this.startAngle = startAngle;
        this.totalAngle = totalAngle;
        this.distance = distance;
        this.speed = speed;
        this.nodes = nodes;
        this.openTimeLine = new Timeline();
        this.closeTimeLine = new Timeline();
        setStyle(style);
    }


    public void setNextStyle() {
        switch ( getStyle() ) {
            case DISTRIBUTION: setStyle(Style.SPREAD);
                break;
            case SPREAD: setStyle(Style.IN_OUT);
                break;
            case IN_OUT: setStyle(Style.TORNADO);
                break;
            case TORNADO: setStyle(Style.ZOOM);
                break;
            case ZOOM: setStyle(Style.FADE);
                break;
            case FADE: setStyle(Style.TRAIN);
                break;
            case TRAIN: setStyle(Style.DISTRIBUTION);
                break;
        }
    }


    public double getSpeed() {
        return speed;
    }


    public void setSpeed(double speed) {
        this.speed = speed;
        this.closeTimeLine.setRate(speed);
        this.openTimeLine.setRate(speed);
    }


    public Style getStyle() {
        return style;
    }


    public void setStyle(Style style) {
        this.style = style;
        initTimeLine();
    }


    public void setAngle(double startAngle , double angle) {
        this.startAngle = startAngle;
        this.totalAngle = angle;
        initTimeLine();
    }


    private double getTheNodeX(int i) {
        int n = nodes.size();
//        int suitableNumber = suitableNumber();
//        if ( suitableNumber > n ) {
        double elementAngle = i * totalAngle / n;
        double elementFinalAngle = elementAngle + startAngle;
        return -distance * Math.sin(Math.toRadians(elementFinalAngle));
//        } else {
//            double elementAngle = (i % suitableNumber) * 45;
//            double elementFinalAngle = elementAngle + startAngle;
//            return -((2 + i / suitableNumber) * distance) * Math.sin(Math.toRadians(elementFinalAngle));
//        }
    }


    private double getTheNodeY(int i) {
        int n = nodes.size();
//        int suitableNumber = suitableNumber();
//        if ( suitableNumber > n ) {
        double elementAngle = i * totalAngle / n;
        double elementFinalAngle = elementAngle + startAngle;
        return -distance * Math.cos(Math.toRadians(elementFinalAngle));
//        } else {
//            double elementAngle = (i % suitableNumber) * 45;
//            double elementFinalAngle = elementAngle + startAngle;
//            return -((2 + i / suitableNumber) * distance) * Math.cos(Math.toRadians(elementFinalAngle));
//        }
    }


    private int suitableNumber() {
        return (int) ((totalAngle - 1) / 45 + 1);
    }


    private void initTimeLine() {
        switch ( style ) {
            case IN_OUT: initInOutStyleTimeLine();
                break;
            case SPREAD: initSpreadStyleTimeLine();
                break;
            case ZOOM: initZoomStyleTimeLine();
                break;
            case FADE: initFadeStyleTimeLine();
                break;
            case TORNADO: initTornadoStyleTimeLine();
                break;
            case DISTRIBUTION: initDistributionStyleTimeLine();
                break;
            case TRAIN: initTrainStyleTimeLine();
                break;
        }
    }


    public Timeline getCloseTimeLine() {
        return closeTimeLine;
    }


    public Timeline getOpenTimeLine() {
        return openTimeLine;
    }


    private void initFadeStyleTimeLine() {
        System.out.println("initFadeStyleTimeLine");
        openTimeLine.getKeyFrames().clear();
        closeTimeLine.getKeyFrames().clear();
        int n = nodes.size();

        for ( int i = 0 ; i < n ; i++ ) {
            nodes.get(i).setTranslateX(getTheNodeX(i));
            nodes.get(i).setTranslateY(getTheNodeY(i));
            nodes.get(i).setOpacity(0);
        }
//          open animation
        KeyFrame[] frames = new KeyFrame[n];
        for ( int i = 0 ; i < n ; i++ ) {
            frames[i] = new KeyFrame(Duration.seconds(1 / (speed)) ,
                    new KeyValue(nodes.get(i).opacityProperty() , 1)
            );
        }
        openTimeLine.getKeyFrames().addAll(frames);
//          close animation
        for ( int i = 0 ; i < n ; i++ ) {
            frames[i] = new KeyFrame(Duration.seconds(1 / (speed)) ,
                    new KeyValue(nodes.get(i).opacityProperty() , 0)
            );
        }
        closeTimeLine.getKeyFrames().addAll(frames);
    }


    private void initInOutStyleTimeLine() {
        System.out.println("initInOutStyleTimeLine");
        openTimeLine.getKeyFrames().clear();
        closeTimeLine.getKeyFrames().clear();
        int n = nodes.size();

        for ( int i = 0 ; i < n ; i++ ) {
            nodes.get(i).setTranslateX(0);
            nodes.get(i).setTranslateY(0);
        }

//          open animation
        KeyFrame[] frames = new KeyFrame[n];
        for ( int i = 0 ; i < n ; i++ ) {
            frames[i] = new KeyFrame(Duration.seconds(1 / (speed)) ,
                    new KeyValue(nodes.get(i).translateXProperty() , getTheNodeX(i)) ,
                    new KeyValue(nodes.get(i).translateYProperty() , getTheNodeY(i))
            );
        }
        openTimeLine.getKeyFrames().addAll(frames);
//          close animation
        for ( int i = 0 ; i < n ; i++ ) {
            frames[i] = new KeyFrame(Duration.seconds(1 / (speed)) ,
                    new KeyValue(nodes.get(i).translateXProperty() , 0) ,
                    new KeyValue(nodes.get(i).translateYProperty() , 0)
            );
        }
        closeTimeLine.getKeyFrames().addAll(frames);
    }


    private void initZoomStyleTimeLine() {
        System.out.println("initZoomStyleTimeLine");
        openTimeLine.getKeyFrames().clear();
        closeTimeLine.getKeyFrames().clear();
        int n = nodes.size();

        for ( int i = 0 ; i < n ; i++ ) {
            nodes.get(i).setTranslateX(getTheNodeX(i));
            nodes.get(i).setTranslateY(getTheNodeY(i));
            nodes.get(i).setScaleX(0);
            nodes.get(i).setScaleY(0);
        }
//          open animation
        KeyFrame[] frames = new KeyFrame[n];
        for ( int i = 0 ; i < n ; i++ ) {
            frames[i] = new KeyFrame(Duration.seconds(1 / (speed)) ,
                    new KeyValue(nodes.get(i).scaleXProperty() , 1) ,
                    new KeyValue(nodes.get(i).scaleYProperty() , 1)
            );
        }
        openTimeLine.getKeyFrames().addAll(frames);
//          close animation
        for ( int i = 0 ; i < n ; i++ ) {
            frames[i] = new KeyFrame(Duration.seconds(1 / (speed)) ,
                    new KeyValue(nodes.get(i).scaleXProperty() , 0) ,
                    new KeyValue(nodes.get(i).scaleYProperty() , 0)
            );
        }
        closeTimeLine.getKeyFrames().addAll(frames);
    }


    private void initTornadoStyleTimeLine() {
        System.out.println("initTornadoStyleTimeLine");
        openTimeLine.getKeyFrames().clear();
        closeTimeLine.getKeyFrames().clear();
        int n = nodes.size();

        for ( int i = 0 ; i < n ; i++ ) {
            nodes.get(i).setTranslateX(0);
            nodes.get(i).setTranslateY(0);
            nodes.get(i).setScaleX(0);
            nodes.get(i).setScaleY(0);
            nodes.get(i).setRotate(0);
        }
//          open animation
        KeyFrame[] frames = new KeyFrame[n];
        for ( int i = 0 ; i < n ; i++ ) {
            frames[i] = new KeyFrame(Duration.seconds(1 / speed) ,
                    new KeyValue(nodes.get(i).translateXProperty() , getTheNodeX(i)) ,
                    new KeyValue(nodes.get(i).translateYProperty() , getTheNodeY(i)) ,
                    new KeyValue(nodes.get(i).scaleXProperty() , 1) ,
                    new KeyValue(nodes.get(i).scaleYProperty() , 1) ,
                    new KeyValue(nodes.get(i).rotateProperty() , 720)
            );
        }
        openTimeLine.getKeyFrames().addAll(frames);
//          close animation
        for ( int i = 0 ; i < n ; i++ ) {
            frames[i] = new KeyFrame(Duration.seconds(1 / speed) ,
                    new KeyValue(nodes.get(i).translateXProperty() , 0) ,
                    new KeyValue(nodes.get(i).translateYProperty() , 0) ,
                    new KeyValue(nodes.get(i).scaleXProperty() , 0) ,
                    new KeyValue(nodes.get(i).scaleYProperty() , 0) ,
                    new KeyValue(nodes.get(i).rotateProperty() , 0)
            );
        }
        closeTimeLine.getKeyFrames().addAll(frames);
    }


    private void initSpreadStyleTimeLine() {
        System.out.println("initSpreadStyleTimeLine");
        openTimeLine.getKeyFrames().clear();
        closeTimeLine.getKeyFrames().clear();
        int n = nodes.size();

        for ( int i = 0 ; i < n ; i++ ) {
            nodes.get(i).setTranslateX(0);
            nodes.get(i).setTranslateY(0);
            nodes.get(i).setScaleX(0);
            nodes.get(i).setScaleY(0);
        }
//          open animation
        int animationNum = 2;
        KeyFrame[] frames = new KeyFrame[n * animationNum];
        for ( int i = 0, j = 0 ; i < n ; i++ , j += animationNum ) {
            frames[j] = new KeyFrame(Duration.seconds(1 / (speed * 2)) ,
                    new KeyValue(nodes.get(i).translateXProperty() , getTheNodeX(0)) ,
                    new KeyValue(nodes.get(i).translateYProperty() , getTheNodeY(0)) ,
                    new KeyValue(nodes.get(i).scaleXProperty() , 1) ,
                    new KeyValue(nodes.get(i).scaleYProperty() , 1)
            );
            frames[j + 1] = new KeyFrame(Duration.seconds((i + 1) / (speed * 2)) ,
                    new KeyValue(nodes.get(i).translateXProperty() , getTheNodeX(i)) ,
                    new KeyValue(nodes.get(i).translateYProperty() , getTheNodeY(i))
            );
        }
        openTimeLine.getKeyFrames().addAll(frames);
//          close animation
        for ( int i = 0, j = 0 ; i < n ; i++ , j += animationNum ) {
            frames[j] = new KeyFrame(Duration.seconds(1 / (speed * 2)) ,
                    new KeyValue(nodes.get(i).translateXProperty() , getTheNodeX(0)) ,
                    new KeyValue(nodes.get(i).translateYProperty() , getTheNodeY(0))
            );
            frames[j + 1] = new KeyFrame(Duration.seconds(2 / (speed * 2)) ,
                    new KeyValue(nodes.get(i).translateXProperty() , 0) ,
                    new KeyValue(nodes.get(i).translateYProperty() , 0) ,
                    new KeyValue(nodes.get(i).scaleXProperty() , 0) ,
                    new KeyValue(nodes.get(i).scaleYProperty() , 0)
            );
        }
        closeTimeLine.getKeyFrames().addAll(frames);
    }


    private void initDistributionStyleTimeLine() {
        System.out.println("initDistributionStyleTimeLine");
        openTimeLine.getKeyFrames().clear();
        closeTimeLine.getKeyFrames().clear();
        int n = nodes.size();

        for ( int i = 0 ; i < n ; i++ ) {
            nodes.get(i).setTranslateX(0);
            nodes.get(i).setTranslateY(0);
            nodes.get(i).setScaleX(0);
            nodes.get(i).setScaleY(0);
        }
//          open animation
        int animationNum = 2;
        KeyFrame[] frames = new KeyFrame[n * animationNum];
        for ( int i = 0, j = 0 ; i < n ; i++ , j += animationNum ) {
            frames[j] = new KeyFrame(Duration.seconds((i) / (speed * n)) ,
                    new KeyValue(nodes.get(i).translateXProperty() , 0) ,
                    new KeyValue(nodes.get(i).translateYProperty() , 0) ,
                    new KeyValue(nodes.get(i).scaleXProperty() , 0) ,
                    new KeyValue(nodes.get(i).scaleYProperty() , 0)
            );
            frames[j + 1] = new KeyFrame(Duration.seconds((i + 1) / (speed * n)) ,
                    new KeyValue(nodes.get(i).translateXProperty() , getTheNodeX(i)) ,
                    new KeyValue(nodes.get(i).translateYProperty() , getTheNodeY(i)) ,
                    new KeyValue(nodes.get(i).scaleXProperty() , 1) ,
                    new KeyValue(nodes.get(i).scaleYProperty() , 1)
            );
        }
        openTimeLine.getKeyFrames().addAll(frames);
//          close animation
        for ( int i = 0, j = 0 ; i < n ; i++ , j += animationNum ) {
            frames[j] = new KeyFrame(Duration.seconds((i) / (speed * n)) ,
                    new KeyValue(nodes.get(i).translateXProperty() , getTheNodeX(i)) ,
                    new KeyValue(nodes.get(i).translateYProperty() , getTheNodeY(i)) ,
                    new KeyValue(nodes.get(i).scaleXProperty() , 1) ,
                    new KeyValue(nodes.get(i).scaleYProperty() , 1)
            );
            frames[j + 1] = new KeyFrame(Duration.seconds((i + 1) / (speed * n)) ,
                    new KeyValue(nodes.get(i).translateXProperty() , 0) ,
                    new KeyValue(nodes.get(i).translateYProperty() , 0) ,
                    new KeyValue(nodes.get(i).scaleXProperty() , 0) ,
                    new KeyValue(nodes.get(i).scaleYProperty() , 0)
            );
        }
        closeTimeLine.getKeyFrames().addAll(frames);
    }


    private void initTrainStyleTimeLine() {
        System.out.println("initTrainStyleTimeLine");
        openTimeLine.getKeyFrames().clear();
        closeTimeLine.getKeyFrames().clear();
        int n = nodes.size();

        for ( int i = 0 ; i < n ; i++ ) {
            nodes.get(i).setTranslateX(0);
            nodes.get(i).setTranslateY(0);
            nodes.get(i).setScaleX(0);
            nodes.get(i).setScaleY(0);
        }
        double subTime = 2 / (speed * n);

//          open animation
        int animationNum = n;
        KeyFrame[] frames = new KeyFrame[n * animationNum];
        for ( int i = 0, j = 0 ; i < n ; i++ , j += animationNum ) {
            frames[j] = new KeyFrame(Duration.seconds(subTime) ,
                    new KeyValue(nodes.get(i).translateXProperty() , getTheNodeX(0)) ,
                    new KeyValue(nodes.get(i).translateYProperty() , getTheNodeY(0)) ,
                    new KeyValue(nodes.get(i).scaleXProperty() , 1) ,
                    new KeyValue(nodes.get(i).scaleYProperty() , 1)
            );
            for ( int k = 1 ; k < animationNum ; k++ ) {
                int x = Math.max(i - (n - (k + 1)) , 0);
                frames[j + k] = new KeyFrame(Duration.seconds((k + 1) * subTime) ,
                        new KeyValue(nodes.get(i).translateXProperty() , getTheNodeX(x)) ,
                        new KeyValue(nodes.get(i).translateYProperty() , getTheNodeY(x))
                );
            }
        }
        openTimeLine.getKeyFrames().addAll(frames);
//          close animation
        for ( int i = 0, j = 0 ; i < n ; i++ , j += animationNum ) {
            for ( int k = 0 ; k < animationNum - 1 ; k++ ) {
//                int x = Math.max(i - (n - (k + 1)) , 0);
                int x = Math.max(i - (k + 1) , 0);
                frames[j + k] = new KeyFrame(Duration.seconds((k + 1) * subTime) ,
                        new KeyValue(nodes.get(i).translateXProperty() , getTheNodeX(x)) ,
                        new KeyValue(nodes.get(i).translateYProperty() , getTheNodeY(x))
                );
            }
            frames[animationNum - 1] = new KeyFrame(Duration.seconds(animationNum * subTime) ,
                    new KeyValue(nodes.get(i).translateXProperty() , 0) ,
                    new KeyValue(nodes.get(i).translateYProperty() , 0) ,
                    new KeyValue(nodes.get(i).scaleXProperty() , 0) ,
                    new KeyValue(nodes.get(i).scaleYProperty() , 0)
            );
        }
        closeTimeLine.getKeyFrames().addAll(frames);
    }

}
