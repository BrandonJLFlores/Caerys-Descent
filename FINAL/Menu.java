import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 * Created by brandon on 11/10/2016.
 */
class Menu extends BasicGameState {

    private Animation main;
    private int[] duration = {500, 500};
    private Sound monkey;

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        Image[] mainMenu = new Image[2];
        mainMenu[0] = new Image("res/menuBackGround.png");
        mainMenu[1] = new Image("res/menuBackGround1.png");

        monkey = new Sound("res/Sound/MonkeyLaugh.wav");
        main = new Animation(mainMenu, duration, true);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        main.draw(0, 0);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        int x = Mouse.getX();
        int y = Mouse.getY();

        if((x > 182 && x < 356) && (y < 355 && y > 314)){
            if(Mouse.isButtonDown(0)){
                monkey.play();
                stateBasedGame.enterState(2, new FadeOutTransition(), new FadeInTransition());
            }
        }
        if((x > 11 && x < 219) && (y < 220 && y > 173)){
            if(Mouse.isButtonDown(0)){
                stateBasedGame.enterState(3, new FadeOutTransition(), new FadeInTransition());
                Options.setFrom(0);
            }
        }
        if((x > 231 && x < 404) && (y < 80 && y > 40)){
            if(Mouse.isButtonDown(0)){
                System.exit(0);
            }
        }
    }

    @Override
    public int getID() {
        return 0;
    }
}
