
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by brandon on 12/1/2016.
 */
class Load extends BasicGameState {

    private Animation load;
    private int[] duration1 = {300, 300, 300, 300, 100, 100, 100, 100, 100, 100, 100, 200, 400, 400, 1000, 1000};
    private float time = 0;

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        Image[] loading = new Image[16];
        for(int i = 1; i <= 16; i++){
            loading[i - 1] = new Image("res/Loading/Loading" + i + ".png");
        }
        load = new Animation(loading, duration1, true);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        load.draw(0, 0);
        if(time >= 4900) stateBasedGame.enterState(0);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        time += i;
    }

    @Override
    public int getID() {
        return 4;
    }
}
