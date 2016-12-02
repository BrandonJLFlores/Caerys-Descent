
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by brandon on 11/10/2016.
 */
public class Game extends StateBasedGame {

    private static final String gameTitle = "Caery's Descent";
    private static final int menu = 0;
    private static final int credits = 1;
    private static final int play = 2;
    private static final int options = 3;
    private static final int load = 4;


    private Game(String gameTitle){
        super(gameTitle);
        this.addState(new Menu());
        this.addState(new Credits());
        this.addState(new Play());
        this.addState(new Options());
        this.addState(new Load());
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        if (gameContainer instanceof AppGameContainer) {
            AppGameContainer appContainer = (AppGameContainer) gameContainer;
            gameContainer.setShowFPS(false);
            if (!appContainer.isFullscreen()) {
                gameContainer.setIcon("res/idle_openEyes_right.png");
            }
        }

        this.getState(menu).init(gameContainer, this);
        this.getState(credits).init(gameContainer, this);
        this.getState(play).init(gameContainer, this);
        this.getState(options).init(gameContainer, this);
        this.getState(load).init(gameContainer, this);
        this.enterState(load);
    }

    static Coordinates getLoc(){
        return new Coordinates(450, 525);
    }

    public static void main(String[] args) {
        AppGameContainer mainGame;
        try{
            mainGame = new AppGameContainer(new Game(gameTitle));
            mainGame.setDisplayMode(450, 525, false);
            mainGame.start();
        }catch (SlickException e){
            e.printStackTrace();
        }
    }
}
