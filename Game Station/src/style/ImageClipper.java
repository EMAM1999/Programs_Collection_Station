/*
 To change this license header, choose License Headers in Project Properties.
 To change this template file, choose Tools | Templates
 and open the template in the editor.
 */
package style;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**

 @author EMAM
 */
public class ImageClipper {

     public static synchronized ImageView clipImage(ImageView imageView , int radius) {
          // set a clip to apply rounded border to the original image.
          Rectangle clip = new Rectangle(
                  imageView.getFitWidth() , imageView.getFitHeight()
          );
          clip.setArcWidth(radius);
          clip.setArcHeight(radius);
          imageView.setClip(clip);
          // snapshot the rounded image.
          SnapshotParameters parameters = new SnapshotParameters();
          parameters.setFill(Color.TRANSPARENT);
          WritableImage image = imageView.snapshot(parameters , null);
          // remove the rounding clip so that our effect can show through.
          imageView.setClip(null);
          imageView.setImage(image);
          return imageView;
     }

}
