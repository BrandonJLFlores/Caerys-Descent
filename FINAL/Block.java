

import org.newdawn.slick.Image;

import java.util.Random;

/**
 * Created by brandon on 11/16/2016.
 */
class Block extends Character {
    private int type;
    private boolean spawnLife, spawnSlow, spawnDpoints;
    private Powerup p;

    Powerup getP(){
        return p;
    }

    int getType() {
        return type;
    }

    void setType(int type) {
        this.type = type;
    }

    boolean isSpawnLife() {
        return spawnLife;
    }

    void setSpawnLife(boolean spawnLife) {
        this.spawnLife = spawnLife;
    }

    boolean isSpawnSlow() {
        return spawnSlow;
    }

    void setSpawnSlow(boolean spawnSlow) {
        this.spawnSlow = spawnSlow;
    }

    void setSpawnDpoints(boolean spawnDpoints) {
        this.spawnDpoints = spawnDpoints;
    }

    void clear(){
        spawnDpoints = false;
        spawnSlow = false;
        spawnLife = false;
    }

    void placeTop(int x){
        if(spawnLife || spawnDpoints || spawnSlow){
            p.setX(x);
            p.setY(getY() - p.getHeight());
        }
        p.updateRect();
    }

    boolean isHavePowerUp() {
        return spawnLife || spawnSlow || spawnDpoints;
    }

    Block(Powerup p, Image image, float x , float y){
        super(image, x, y, 0.05f);
        this.p = p;
        type = 0;
        spawnLife = false;
    }
}
