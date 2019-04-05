package uk.ac.qub.eeecs.game;
import org.junit.Test;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetStore;
import uk.ac.qub.eeecs.gage.engine.ScreenManager;
import uk.ac.qub.eeecs.gage.world.GameScreen;
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
 * This class is used to specifically test the Turn Class
 */

public class TurnClassTest {

    @Mock
    GameScreen gameScreen = Mockito.mock(GameScreen.class);
    Game game = Mockito.mock(Game.class);
    ScreenManager screenManager;
    HashMap<Integer, String> userTurnRecord;
    HashMap<Integer, String> aiTurnRecord;
    private Turn turnRecord;

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
        turnRecord = new Turn(userTurnRecord, aiTurnRecord);
    }

    // Check to see if the Turn object has been instantiated correctly
    @Test
    public void testTurnObjectInstantiation() {
        assertTrue(turnRecord.getUserTurnRecord().equals(userTurnRecord));
        assertTrue(turnRecord.getAiTurnRecord().equals(aiTurnRecord));
    }

    // This test is to check if the constructor has succesfully set each of the hashmaps to 0
    @Test
    public void turnClassConstructor() {
        assertEquals(turnRecord.getAiTurnRecord().get(0),"null");
        assertEquals(turnRecord.getUserTurnRecord().get(0),"null");
    }
}

