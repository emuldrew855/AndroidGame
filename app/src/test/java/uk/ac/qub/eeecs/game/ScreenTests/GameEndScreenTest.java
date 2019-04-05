package uk.ac.qub.eeecs.game.ScreenTests;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetStore;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.ScreenManager;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.GameRecord;
import uk.ac.qub.eeecs.game.MenuScreen;
import uk.ac.qub.eeecs.game.Round;
import uk.ac.qub.eeecs.game.cardDemo.GameEndScreen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by Edward Muldrew on 06/03/2018.
 * This class is used to specifically test the GameEndScreen
 */

public class GameEndScreenTest {
    @Mock
    GameScreen gameScreen = Mockito.mock(GameScreen.class);
    Game game = Mockito.mock(Game.class);
    ScreenManager screenManager;
    ArrayList<Round> roundRecord = new ArrayList<Round>();
    int userOverallScore = 0;
    int aiOverallScore = 0;
    GameEndScreen gameEndScreen;
    GameRecord gameRecord;
    private PushButton newGameButton;
    @Mock
    private AssetStore assetManager;



    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(gameScreen.getGame()).thenReturn(game);
        when(game.getAssetManager()).thenReturn(assetManager);
        screenManager = new ScreenManager();
        when(game.getScreenManager()).thenReturn(screenManager);

        gameRecord = new GameRecord(roundRecord, userOverallScore, aiOverallScore);
        gameEndScreen = new GameEndScreen(game, "GameEndScreen", gameRecord);
        newGameButton = new PushButton(
                950, 780, 500, 500, "Sign", gameScreen);
        game.getScreenManager().addScreen(gameEndScreen);
        game.getScreenManager().setAsCurrentScreen("GameEndScreen");
    }

    // This test checks to see if a GameEndScreen has been instantiated correctly and the respective variables are passed in ok
    @Test
    public void gameEndScreenInstantiation() {
        assertEquals(gameEndScreen.getName(),"GameEndScreen");
        assertEquals(gameRecord.getAiOVerallScore(), 0);
        assertEquals(gameRecord.getUserOverallScore(), 0);
    }
    private void changeToScreen(GameScreen screen) {
        game.getScreenManager().removeScreen(gameScreen.getName());
        game.getScreenManager().addScreen(screen);
        game.getScreenManager().setAsCurrentScreen(screen.getName());
    }


    public void update() {
        ElapsedTime elapsedTime = new ElapsedTime();
        TouchEvent touchPosition = new TouchEvent();
        touchPosition.x = 950; // position of x of button
        touchPosition.y = 780; // position y of button
        List<TouchEvent> touchEvents = new ArrayList<TouchEvent>();
        touchEvents.add(touchPosition);

        if (touchEvents.size() > 0) {
            if (newGameButton.isPushTriggered()) {
                game.getScreenManager().addScreen(new MenuScreen(game));
                game.getScreenManager().setAsCurrentScreen("MenuScreen");
            }
        }
    }

//    // Problem with options screen initialising
//    @Test
//    public void testNewGameButton() {
//        assertEquals(game.getScreenManager().getCurrentScreen().getName(),"GameEndScreen");
//        newGameButton.mPushTriggered = true;
//        update();
//        assertEquals(game.getScreenManager().getCurrentScreen().getName(),"MenuScreen");
//    }

    // Method checks the result check method is working correctly
    @Test
    public void testResultCheckMethod() {
        gameRecord.setUserOverallScore(2);
        gameRecord.setAiOVerallScore(1);
        assertEquals(gameEndScreen.resultCheck(), "Win!");
        gameRecord.setAiOVerallScore(2);
        gameRecord.setUserOverallScore(1);
        assertEquals(gameEndScreen.resultCheck(),"Loss!");
        gameRecord.setAiOVerallScore(0);
        gameRecord.setUserOverallScore(0);
        assertEquals(gameEndScreen.resultCheck(),"Tie!");
    }



}
