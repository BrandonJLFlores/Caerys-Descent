import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.*;
import java.io.IOException;
import java.util.Random;

/**
 * Created by brandon on 11/16/2016.
 */
class Play extends BasicGameState {

    private Animation runningLeft, runningRight, idle, steadyLeft, steadyRight, start;
    private Image player, bg, lifeCount, black, pauseScreen, gameover;
    private Sound gover;
    private Music bgmusic;
    private Image[] platform, lifeAmount;
    private Block[] block;
    private Sound[] hitSound;
    private Player mplayer;
    private Powerup p;
    private Block temp;
    private Random rand = new Random();
    private boolean onTop, pupSpawned, hitTop, hitSpike, hitPUP, hitSlow, hitPoints, PLAY, START, changeSpeed, flag = true, land = false, over = false, musicplay = true;
    private float time, totalTime, pupTimeSlow, pupTimePoints;
    private int level, height, width, x, y;
    private java.awt.Font text;
    private org.newdawn.slick.UnicodeFont uniFont;

    private int[] duration = {100, 100, 100, 100, 100, 100, 100};
    private int[] duration2 = {300, 300};
    private int[] duration3 = {600, 600, 400, 400, 800, 1500};

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        try {
            text = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("res/Font/pixelated.ttf"));
            text = text.deriveFont(java.awt.Font.BOLD, 16.f);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        gameover = new Image("res/gameover.png");
        gover = new Sound("res/Sound/GameOver.wav");
        bgmusic = new Music("res/Sound/BackgroundMusic.wav");

        uniFont = new org.newdawn.slick.UnicodeFont(text);
        uniFont.addAsciiGlyphs();
        org.newdawn.slick.font.effects.ColorEffect a = new org.newdawn.slick.font.effects.ColorEffect();
        uniFont.getEffects().add(a);
        uniFont.loadGlyphs();

        hitSound = new Sound[4];
        hitSound[0] = new Sound("res/Sound/Land.wav");
        hitSound[1] = new Sound("res/Sound/Pickup.wav");
        hitSound[2] = new Sound("res/Sound/Pickup2.wav");
        hitSound[3] = new Sound("res/Sound/TakeDamage.wav");
        resetBools();

        height = gameContainer.getHeight();
        width = gameContainer.getWidth();

        Image[] runLeft = new Image[7];                                                                  Image[] runRight = new Image[7];
        runLeft[0] = new Image("res/Animation/run_LEFT/run_00 left.png");             runRight[0] = new Image("res/Animation/run_RIGHT/run_00 right.png");
        runLeft[1] = new Image("res/Animation/run_LEFT/run_01 left.png");             runRight[1] = new Image("res/Animation/run_RIGHT/run_01 right.png");
        runLeft[2] = new Image("res/Animation/run_LEFT/run_02 left.png");             runRight[2] = new Image("res/Animation/run_RIGHT/run_02 right.png");
        runLeft[3] = new Image("res/Animation/run_LEFT/run_03 left.png");             runRight[3] = new Image("res/Animation/run_RIGHT/run_03 right.png");
        runLeft[4] = new Image("res/Animation/run_LEFT/run_04 left.png");             runRight[4] = new Image("res/Animation/run_RIGHT/run_04 right.png");
        runLeft[5] = new Image("res/Animation/run_LEFT/run_05 left.png");             runRight[5] = new Image("res/Animation/run_RIGHT/run_05 right.png");
        runLeft[6] = new Image("res/Animation/run_LEFT/run_06 left.png");             runRight[6] = new Image("res/Animation/run_RIGHT/run_06 right.png");

        Image[] Idle = new Image[2];                                                                     Image[] Idle2 = new Image[2];
        Idle[0] = new Image("res/Animation/idle_RIGHT/idle_closeEyes_right.png");     Idle2[0] = new Image("res/Animation/idle_LEFT/idle_closeEyes_left.png");
        Idle[1] = new Image("res/Animation/idle_RIGHT/idle_openEyes_right.png");      Idle2[1] = new Image("res/Animation/idle_LEFT/idle_openEyes_left.png");

        Image blank = new Image("res/blank.png");
        Image ready = new Image("res/ready1.png");

        Image[] starto = new Image[6];
        starto[0] = ready;      starto[1] = blank;
        starto[2] = ready;      starto[3] = blank;
        starto[4] = ready;      starto[5] = new Image("res/ready3.png");

        black = new Image("res/black.png");
        bg = new Image("res/background1.png");
        player = new Image("res/player.png");
        lifeCount = new Image("res/heart1.png");
        pauseScreen = new Image("res/pause.png");

        block = new Block[5];

        Image [] powerUp = new Image[4];
        powerUp[0] = new Image("res/heart.png");
        powerUp[1] = new Image("res/slow.png");
        powerUp[2] = new Image("res/dpoints.png");

        lifeAmount = new Image[5];
        lifeAmount[0] = lifeCount;      lifeAmount[1] = lifeCount;
        lifeAmount[2] = lifeCount;      lifeAmount[3] = lifeCount;
        lifeAmount[4] = lifeCount;

        platform = new Image[2];
        platform[0] = new Image("res/base.png");
        platform[1] = new Image("res/spikes.png");
        p = new Powerup(powerUp);
        instantiate();

        start = new Animation(starto, duration3, true);
        steadyRight = new Animation(Idle, duration2, true);
        steadyLeft = new Animation(Idle2, duration2, true);
        runningLeft = new Animation(runLeft, duration, true);
        runningRight = new Animation(runRight, duration, true);
        idle = steadyLeft;
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.setFont(uniFont);

        if(mplayer.isAlive()){
            if(musicplay) {
                bgmusic.loop();
                musicplay = false;
            }
            graphics.drawImage(bg, 0, 0);
            idle.draw(mplayer.getX(), mplayer.getY());

            if(hitSlow) {
                graphics.setColor(Color.red);
                graphics.drawString("Slow : ", 12, 34);
                graphics.fillRect(12, 50, (10 - (pupTimeSlow / 1000)) * 10, 8);
            }
            if(hitPoints) {
                graphics.setColor(Color.blue);
                graphics.drawString("Double Points: ", 12, 60);
                graphics.fillRect(12, 76, (15 - (pupTimePoints / 1000)) * 8, 8);
            }
            for(Block b : block){
                if(b.isHavePowerUp() && !mplayer.isHitPUP()){
                    if(b.isSpawnLife()){
                        b.getP().getPowerUP()[0].draw(b.getP().getX(), b.getP().getY());
                    }else if(b.isSpawnSlow()){
                        b.getP().getPowerUP()[1].draw(b.getP().getX(), b.getP().getY());
                    }else{
                        b.getP().getPowerUP()[2].draw(b.getP().getX(), b.getP().getY());
                    }
                }
                if(mplayer.isHitPUP()) mplayer.setHitPUP(false);
                b.getImage().draw(b.getX(), b.getY());
            }
            black.draw(0, 500);
            for(int i = 0, j = 360; i < mplayer.getLives(); i++, j += lifeCount.getWidth() + 3){
                graphics.setColor(Color.white);
                graphics.drawString("LIVES : ", 300, 505);
                lifeAmount[i].draw(j, 507);
            }
            graphics.drawString("SCORE: " + mplayer.getScore(), 20, 505);
        }else{
            bgmusic.stop();
            over = true;
            gameover.draw(0, 0);
            if(flag){
                gover.play();
                flag = false;
            }
        }
        if(!PLAY) start.draw(0, 0);
        if(totalTime > 3400) PLAY = true;
        if(!START){
            pauseScreen.draw(0,0);
        }


    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int elapsedTime) throws SlickException {
        Input in = gameContainer.getInput();

        time += elapsedTime;
        totalTime += elapsedTime;
        x = Mouse.getX();
        y = Mouse.getY();

        if(!over){
            if(START){
                if(PLAY) {
                    if (hitSlow) {
                        if(pupTimeSlow >= 10000){
                            SLOW();
                        }else{
                            pupTimeSlow += elapsedTime;
                            setAllyspeed(0.05f);
                        }
                    }

                    if (hitPoints){
                        pupTimePoints += elapsedTime;
                        if(pupTimePoints >= 15000){
                            hitPoints = false;
                        }
                    }
                    if (in.isKeyDown(Input.KEY_ESCAPE)) {
                        START = false;
                    }

                    if (mplayer.isAlive()) {
                        if (!hitSpike && !hitTop && mplayer.getY() <= height - 25 - mplayer.getHeight()) {
                            if (in.isKeyDown(Input.KEY_LEFT)) {
                                idle = runningLeft;
                                mplayer.setXspeed(-0.15f);
                                mplayer.move();
                            } else if (in.isKeyDown(Input.KEY_RIGHT)) {
                                idle = runningRight;
                                mplayer.setXspeed(0.15f);
                                mplayer.move();
                            } else {
                                if (idle == runningLeft) idle = steadyLeft;
                                if (idle == runningRight) idle = steadyRight;
                            }
                        }

                        clearUps();
                        if(!hitSlow) speedCheck();
                        addPowerups();
                        moveBlocks();

                        if (hitPUP) {
                            if(mplayer.getPUP() == 1) {
                                mplayer.addLife();
                                hitSound[1].play();
                            }
                            if(mplayer.getPUP() == 2){
                                pupTimeSlow = 0;
                                hitSlow = true;
                                hitSound[2].play();
                            }
                            if(mplayer.getPUP() == 3){
                                pupTimePoints = 0;
                                hitPoints = true;
                                hitSound[2].play();
                            }
                            hitPUP = false;
                        }
                        if (hitTop) {
                            mplayer.minusLife();
                            hitTop = false;
                            hitSound[3].play();
                        }
                        if (onTop) {
                            if(land){
                                land = false;
                                hitSound[0].play();
                            }
                            mplayer.setY(temp.getY() - mplayer.getHeight());
                            time = 0;
                        } else {
                            mplayer.setY(mplayer.getY() + mplayer.getYspeed());
                            if(!hitPoints) mplayer.addScore(time / 10000);
                            if(hitPoints) mplayer.addScore(2 * time / 10000);
                            land = true;
                        }
                        mplayer.updateRect();
                    }
                }
            }else{
                pauseListener(stateBasedGame);
            }
        }else{
            gameOverListener();
        }


    }

    private void speedCheck() {
        if(mplayer.getScore() >= 500 && mplayer.getScore() <= 1500 && !changeSpeed){
            level = 1;
            setAllyspeed(0.1f);
            changeSpeed = true;
        }
        if(mplayer.getScore() > 1500 && mplayer.getScore() <= 2500 && changeSpeed){
            level = 2;
            setAllyspeed(0.15f);
            changeSpeed = false;
        }
        if(mplayer.getScore() > 2500 && mplayer.getScore() <= 4000 && !changeSpeed){
            level = 3;
            setAllyspeed(0.2f);
            changeSpeed = true;
        }
        if(mplayer.getScore() > 4000 && changeSpeed){
            level = 4;
            setAllyspeed(0.25f);
            changeSpeed = false;
        }
    }

    private void moveBlocks(){
        for(Block b : block){
            if(mplayer.getRect().intersects(b.getRect())){
                check(b);
            }

            if(mplayer.getY() >= 500) hitSpike = true;
            if(mplayer.getY() <= 5 || mplayer.getY() >= height){
                hitSpike = true;
            }
            if(b.getY() <= 0) {
                b.setY(height - 25);
                b.setX(randInt(0, width - b.getWidth()));
                if(hitSpike && b.getType() != 1){
                    mplayer.setY(b.getY() - mplayer.getHeight());
                    mplayer.setX(b.getX() + b.getWidth()/2);
                    hitTop = true;
                    hitSpike = false;
                }
            }
            if(b.isHavePowerUp()){
                b.placeTop((int)b.getX() + b.getWidth()/2);
                if(mplayer.getRect().intersects(b.getP().getRect())){
                    pupSpawned = false;
                    b.clear();
                    mplayer.setHitPUP(true);
                    mplayer.whatPUP(b.getP().getType());
                    hitPUP = true;
                }
            }
            b.setY(b.getY() - b.getYspeed());
            b.updateRect();
        }
    }

    private void check(Block b){
        if(mplayer.getY() + mplayer.getHeight() - 1 <= b.getY() && mplayer.getX() + mplayer.getWidth() >= b.getX() && mplayer.getX() <= b.getX() + b.getWidth()){
            if(b.getType() == 1){
                onTop = false;
                hitSpike = true;
            }else{
                temp = b;
                onTop = true;
            }
        }else{
            onTop = false;
        }
    }

    private void setAllyspeed(float f){
        for(Block b : block){
            b.setYspeed(f);
        }
    }

    private int randInt(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }

    private void clearUps(){
        for(Block b : block){
            if(b.getY() <= 0 && b.isHavePowerUp()){
                pupSpawned = false;
                b.clear();
                break;
            }
        }
    }

    private void addPowerups(){
        for(Block b : block){
            if(b.getY() <= 0 && !pupSpawned) {
                if(level == 1){
                    int x = randInt(1, 10);
                    if(x == 2 || x == 7){
                        b.setType(1);
                    }else {
                        b.setType(0);
                        if(x == 1 || x == 3 || x == 5 || x == 9){
                            whatPowerUp(b);
                            pupSpawned = true;
                        }
                    }
                }
                if(level == 2){
                    int x = randInt(1, 10);
                    if(x == 2 || x == 7 || x == 9){
                        b.setType(1);
                    }else{
                        b.setType(0);
                        if(!pupSpawned && x == 1 || x == 3 || x == 5){
                            whatPowerUp(b);
                            pupSpawned = true;
                        }
                    }
                }
                if(level == 3 || level == 4){
                    int x = randInt(1, 10);
                    if(x == 2 || x == 7 || x == 9){
                        b.setType(1);
                    }else{
                        b.setType(0);
                        if(!pupSpawned && x == 3 || x == 6){
                            whatPowerUp(b);
                            pupSpawned = true;
                        }
                    }
                }
                b.setImage(platform[b.getType()]);
                break;
            }
        }
    }

    private void whatPowerUp(Block b){
        int t = randInt(1,4);
        if(t == 2 || t == 3) {
            b.setSpawnLife(true);
            b.getP().setType(1);
        }
        if(t == 1) {
            b.setSpawnSlow(true);
            b.getP().setType(2);
        }
        if(t == 4) {
            b.setSpawnDpoints(true);
            b.getP().setType(3);
        }

    }

    private void reset(){
        mplayer.setLives(3);
        mplayer.setScore(0);
        mplayer.setAlive(true);
        instantiate();
        clearUps();
        setAllyspeed(0.05f);
        pupTimePoints = 0;
        pupTimeSlow = 0;
        time = 0;
        totalTime = 0;
        resetBools();
    }

    private void resetBools(){
        level = 0;              pupSpawned = false;
        PLAY = false;           hitSlow = false;
        hitPoints = false;      changeSpeed = false;
        hitPUP = false;         hitSpike = false;
        onTop = true;           hitTop = false;
        land = false;           START = true;
        flag = true;            over = false;
        musicplay = true;
    }

    private void instantiate(){
        int x = randInt(0, width - platform[0].getWidth());
        block[0] = new Block(p, platform[0], x, 100);
        temp = block[0];
        mplayer = new Player(player, x + platform[0].getWidth()/2, 100 - player.getHeight());
        for(int i = 1, j = 200; i < block.length; i++, j+=100){
            block[i] = new Block(p, platform[0], randInt(0, width - platform[0].getWidth()), j);
        }
    }

    private void SLOW(){
        hitSlow = false;
        if(mplayer.getScore() >= 500 && mplayer.getScore() <= 1500){
            changeSpeed = false;
        }
        if(mplayer.getScore() > 1500 && mplayer.getScore() <= 2500){
            changeSpeed = true;
        }
        if(mplayer.getScore() > 2500 && mplayer.getScore() <= 4000){
            changeSpeed = false;
        }
        if(mplayer.getScore() > 4000){
            changeSpeed = true;
        }
        speedCheck();
    }

    private void pauseListener(StateBasedGame stateBasedGame){
        if((x > 130 && x < 302) && (y < 285 && y > 246)){
            if(Mouse.isButtonDown(0)) {
                START = true;
            }
        }
        if((x > 130 && x < 302) && (y < 230 && y > 190)){
            if(Mouse.isButtonDown(0)) {
                start.restart();
                reset();
            }
        }
        if((x > 130 && x < 302) && (y < 170 && y > 130)){
            if(Mouse.isButtonDown(0)){
                stateBasedGame.enterState(3);
                Options.setFrom(2);
            }
        }
        if((x > 130 && x < 302) && (y < 108 && y > 70)){
            if(Mouse.isButtonDown(0)){
                System.exit(0);
            }
        }
    }

    private void gameOverListener(){
        if((x > 182 && x < 356) && (y < 312 && y > 270)){
            if(Mouse.isButtonDown(0)){
                start.restart();
                reset();
            }
        }
        if((x > 30 && x < 205) && (y < 130 && y > 88)){
            if(Mouse.isButtonDown(0)){
                System.exit(0);
            }
        }
    }

    @Override
    public int getID() {
        return 2;
    }
}
