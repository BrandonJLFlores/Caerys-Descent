

import org.newdawn.slick.Image;


/**
 * Created by brandon on 11/16/2016.
 */
class Player extends Character {

    private int lives, pup;
    private float xspeed;
    private float score;
    private boolean isAlive, hitPUP;

    void move() {
        if (getX() + getXspeed() > 0 && getX() + getXspeed() < Game.getLoc().getX() - getWidth())
            setX(getX() + getXspeed());
    }

    boolean isHitPUP() {
        return hitPUP;
    }

    void setHitPUP(boolean hitPUP) {
        this.hitPUP = hitPUP;
    }

    void whatPUP(int pup){
        this.pup = pup;
    }

    int getPUP(){
        return pup;
    }

    void addScore(float score){
        this.score += score;
    }

    int getScore(){
        return (int) score;
    }

    void setScore(float score) {
        this.score = score;
    }

    void setAlive(boolean alive) {
        isAlive = alive;
    }

    void addLife(){
        if(lives < 5) lives++;
        else score += 300;
    }

    void minusLife(){
        if(lives == 1) isAlive = false;
        else lives --;
    }

    int getLives(){
        return lives;
    }

    void setLives(int lives){
        this.lives = lives;
    }

    boolean isAlive() {
        return isAlive;
    }

    private float getXspeed() {
        return xspeed;
    }

    void setXspeed(float xspeed) {
        this.xspeed = xspeed;
    }

    Player(Image image, float x , float y){
        super(image, x, y, 0.08f);
        xspeed = 0;
        score = 0;
        lives = 3;
        isAlive = true;
        hitPUP = false;
    }
}
