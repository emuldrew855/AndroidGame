package uk.ac.qub.eeecs.gage;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import uk.ac.qub.eeecs.gage.engine.AssetStore;
import uk.ac.qub.eeecs.gage.engine.ScreenManager;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.GameRecord;
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
 * This class is used to specifically test the GameRecord test
 */

public class  GameRecordClassTest {
    @Mock
    GameScreen gameScreen = Mockito.mock(GameScreen.class);
    Game game = Mockito.mock(Game.class);
    ScreenManager screenManager;
    ArrayList<Round> roundRecord = new ArrayList<Round>();
    int userOverallScore = 0;
    int aiOverallScore = 0;
    GameRecord gameRecord;
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
    }

    @Test
    public void testGameRecordInstantiation() {
        assert(gameRecord.getRoundRecord().equals(roundRecord));
        assertEquals(gameRecord.getUserOverallScore(),0);
        assertEquals(gameRecord.getAiOVerallScore(),0);

    }


}
