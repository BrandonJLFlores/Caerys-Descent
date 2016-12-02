import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

/**
 * Created by brandon on 11/16/2016.
 */
class Powerup {

    private float x, y;
    private Rectangle rect;
    private Image[] powerUP;
    private int width, height, type;

    void setType(int type){
        this.type = type;
    }
        
    Rectangle getRect() {
        return rect;
    }

    Image[] getPowerUP() {
        return powerUP;
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

    int getHeight() {
        return height;
    }

    void updateRect() {
        this.rect.setBounds(x, y, width, height);
    }

    Powerup(Image[] powerUP){
        this.powerUP = powerUP;
        width = powerUP[0].getWidth();
        height = powerUP[0].getHeight();
        x = 0;
        y = 0;
        rect = new Rectangle(x, y, width, height);
    }

    int getType() {
        return type;
    }
}
