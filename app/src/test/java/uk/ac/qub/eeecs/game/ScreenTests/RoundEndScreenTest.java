package uk.ac.qub.eeecs.game.ScreenTests;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetStore;
import uk.ac.qub.eeecs.gage.engine.ScreenManager;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.Round;
import uk.ac.qub.eeecs.game.cardDemo.Cards.Hand;
import uk.ac.qub.eeecs.game.cardDemo.PlayerScore;
import uk.ac.qub.eeecs.game.cardDemo.RoundEndScreen;
import uk.ac.qub.eeecs.game.cardDemo.Turn;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by Edward Muldrew on 06/03/2018.
 * This test is used to specifically test the Round End Screen
 */

public class RoundEndScreenTest {
    @Mock
    GameScreen gameScreen = Mockito.mock(GameScreen.class);
    Game game = Mockito.mock(Game.class);
    ScreenManager screenManager;
    HashMap<Integer, String> userTurnRecord;
    HashMap<Integer, String> aiTurnRecord;
    public Turn turnRecord;
    private PlayerScore userScore;
    private PlayerScore aiScore;
    private Round round;
    RoundEndScreen roundEndScreen;
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

        userTurnRecord = new HashMap<Integer, String>();
        aiTurnRecord = new HashMap<Integer, String>();
        userScore = new PlayerScore(2, Hand.UserType.USER);
        aiScore = new PlayerScore(0, Hand.UserType.AI);
        Turn turnRecord = new Turn(userTurnRecord, aiTurnRecord);
        round = new Round("Round1", turnRecord, userScore, aiScore);
        roundEndScreen = new RoundEndScreen(game,"RoundEndScreen",round);
        game.getScreenManager().addScreen(roundEndScreen);
        game.getScreenManager().setAsCurrentScreen("RoundEndScreen");
    }

    // This test is to check the Round End Screen is instantiated correctly
    @Test
    public void testRoundEndScreenInstantiation() {
        assertEquals(roundEndScreen.getName(), "RoundEndScreen");
        assertEquals(roundEndScreen.getGame(),game);
    }

    // This test is to check if the result check method is working correctly
    @Test
    public void resultCheckMethodTest() {
        aiScore.setScore(5);
        userScore.setScore(2);
        round.setAiScore(aiScore);
        round.setUserScore(userScore);
        RoundEndScreen testRoundEndLoss = new RoundEndScreen(game,"testRoundEndLoss",round);
        assertEquals(testRoundEndLoss.resultCheck(),"Loss!");
    }

    // This test is to check the game end method
    @Test
    public void testGameEndMethod() {
        assertEquals(game.getScreenManager().getCurrentScreen().getName(),"RoundEndScreen");
        roundEndScreen.gameEnd();
        roundEndScreen.gameEnd();
        roundEndScreen.gameEnd();
        assertEquals(game.getScreenManager().getCurrentScreen().getName(),"gameEndScreen");
    }

}
