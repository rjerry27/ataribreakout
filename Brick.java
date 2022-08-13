package sample;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.*;

public class Brick {

    Rectangle2D rect;
    Image image;
    int hp;

    public Brick(Image image, Rectangle2D rect, int hp) {
        this.image = image;
        this.rect = rect;
        this.hp = hp;
    }

    public Image getImage() {

        return image;

    }

    public Rectangle2D getRect() {

        return rect;
    }

    public double getX() {

        return rect.getMinX();
}

    public double getY(){

        return rect.getMaxY();
    }

    public void hit(){

        hp--;

    }


    public int getHp(){

        return hp;
    }





}

