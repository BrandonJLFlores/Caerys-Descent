
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

/**
 * Created by brandon on 11/16/2016.
 */
abstract class Character {

    private Image image;
    private int width, height;
    private float x, y, yspeed;
    private Rectangle rect;

    Image getImage() {
        return image;
    }

    void setImage(Image image) {
        this.image = image;
    }

    Rectangle getRect() {
        return rect;
    }

    void updateRect() {
        rect.setBounds(x, y, width, height);
    }

    float getX() {
        return x;
    }

    void setX(float x) {
        this.x = x;
    }

    float getY() {
        return y;
    }

    void setY(float y) {
        this.y = y;
    }

    float getYspeed() {
        return yspeed;
    }

    void setYspeed(float yspeed) {
        this.yspeed = yspeed;
    }

    int getWidth() {
        return width;
    }

    int getHeight() {
        return height;
    }

    Character(Image image, float x , float y, float yspeed){
        this.image = image;
        this.x = x;
        this.y = y;
        this.yspeed = yspeed;
        width = image.getWidth();
        height = image.getHeight();
        rect = new Rectangle(x, y, width, height);
    }
}
