import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 * Created by brandon on 11/10/2016.
 */
class Credits extends BasicGameState {

    private Image options;

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        options = new Image("res/credits.png");
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
         options.draw(0, 0);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        int x = Mouse.getX();
        int y = Mouse.getY();
        if((x > 42 && x < 83) && (y < 32 && y > 12) || (x > 8 && x < 41) && (y < 42 && y > 3)){
            if(Mouse.isButtonDown(0)) {
                stateBasedGame.enterState(3, new FadeOutTransition(), new FadeInTransition());
            }
        }
        if((x > 400 && x < 436) && (y < 512 && y > 476)){
            if(Mouse.isButtonDown(0)) {
                if(Options.getFrom() == 0) stateBasedGame.enterState(0, new FadeOutTransition(), new FadeInTransition());
                if(Options.getFrom() == 2) stateBasedGame.enterState(2, new FadeOutTransition(), new FadeInTransition());
            }
        }
    }

    @Override
    public int getID() {
        return 1;
    }
}
