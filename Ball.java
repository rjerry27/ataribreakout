package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.*;

public class Ball {

    Rectangle2D rect;
    Image image;

    public Ball(Image image, Rectangle2D rect) {
        this.image = image;
        this.rect = rect;
    }

    public Image getImage() {

        return image;

    }

    public Rectangle2D getRect() {

        return rect;
    }
}

