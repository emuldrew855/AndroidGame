package uk.ac.qub.eeecs.game;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetStore;
import uk.ac.qub.eeecs.gage.engine.ScreenManager;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.GameRecord;
import uk.ac.qub.eeecs.game.Round;
import uk.ac.qub.eeecs.game.cardDemo.Cards.Hand;
import uk.ac.qub.eeecs.game.cardDemo.PlayerScore;
import uk.ac.qub.eeecs.game.cardDemo.Turn;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by Edward Muldrew on 06/03/2018.
 * This class is specifically used to test the Round class
 */

public class RoundClassTest {
    @Mock
    GameScreen gameScreen = Mockito.mock(GameScreen.class);
    Game game = Mockito.mock(Game.class);
    ScreenManager screenManager;
    HashMap<Integer, String> userTurnRecord;
    HashMap<Integer, String> aiTurnRecord;
    public Turn turnRecord;
    private PlayerScore userScore;
    private PlayerScore defaultScore;
    private Round round;
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
        userScore = new PlayerScore(0, Hand.UserType.USER);
        defaultScore =  defaultScore = new PlayerScore(0, Hand.UserType.AI);
        Turn turnRecord = new Turn(userTurnRecord, aiTurnRecord);
        round = new Round("Round1", turnRecord, userScore, defaultScore);
    }

    // This test is to test if a round has been instantiated properly
    @Test
    public void testRoundInstantiation() {
        assertEquals(round.getRoundName(), "Round1");
        assert(round.getTurnRecord().equals(turnRecord));
        assertTrue(round.getAiScore().equals(defaultScore));
        assertTrue(round.getUserScore().equals(userScore));
    }



}
