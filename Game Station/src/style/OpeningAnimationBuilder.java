/*
 To change this license header, choose License Headers in Project Properties.
 To change this template file, choose Tools | Templates
 and open the template in the editor.
 */
package style;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import style.OpeningAnimation.Style;

/**

 @author EMAM
 */
public final class OpeningAnimationBuilder {

    private Style style = Style.IN_OUT;
    private List<Node> nodes = new ArrayList<>();
    private double speed = 1;
    private double distance = 200;
    private double startAngle = 0;
    private double totalAngle = 360;


    public OpeningAnimationBuilder(List<Node> nodes) {
        this.nodes = nodes;
    }


    public OpeningAnimationBuilder withStyle(Style style) {
        this.style = style;
        return this;
    }


    public OpeningAnimationBuilder withSpeed(double speed) {
        this.speed = speed;
        return this;
    }


    public OpeningAnimationBuilder addNodes(List<Node> nodes) {
        this.nodes.addAll(nodes);
        return this;
    }


    public OpeningAnimationBuilder withDistance(double distance) {
        this.distance = distance;
        return this;
    }


    public OpeningAnimationBuilder withStartAngle(double startAngle) {
        this.startAngle = startAngle;
        return this;
    }


    public OpeningAnimationBuilder withTotalAngle(double totalAngle) {
        this.totalAngle = totalAngle;
        return this;
    }


    public OpeningAnimation build() {
        return new OpeningAnimation(style , startAngle , totalAngle , distance , speed , nodes);
    }

}
