import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 * Created by brandon on 11/18/2016.
 */
public class Options extends BasicGameState {

    private Image options;
    private static int from;

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        options = new Image("res/instructions.png");

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        options.draw(0, 0);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        int x = Mouse.getX();
        int y = Mouse.getY();
        if((x > 366 && x < 406) && (y < 31 && y > 13) || (x > 407 && x < 442) && (y < 42 && y > 3)){
            if(Mouse.isButtonDown(0)) {
                stateBasedGame.enterState(1, new FadeOutTransition(), new FadeInTransition());
            }
        }
        if((x > 400 && x < 436) && (y < 512 && y > 476)){
            if(Mouse.isButtonDown(0)) {
                if(from == 0) stateBasedGame.enterState(0, new FadeOutTransition(), new FadeInTransition());
                if(from == 2) stateBasedGame.enterState(2, new FadeOutTransition(), new FadeInTransition());
            }
        }
    }

    @Override
    public int getID() {
        return 3;
    }

    Options(){ from = -1;}

    static void setFrom(int i){
        from = i;
    }

    static int getFrom(){
        return from;
    }
}
