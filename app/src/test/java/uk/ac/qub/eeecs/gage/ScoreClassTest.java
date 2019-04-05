package uk.ac.qub.eeecs.gage;

import android.graphics.Bitmap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import uk.ac.qub.eeecs.gage.engine.AssetStore;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.cardDemo.Cards.Card;
import uk.ac.qub.eeecs.game.cardDemo.Cards.Deck;
import uk.ac.qub.eeecs.game.cardDemo.Cards.Hand;
import uk.ac.qub.eeecs.game.cardDemo.Cards.HeroCard;
import uk.ac.qub.eeecs.game.cardDemo.Cards.MinionCard;
import uk.ac.qub.eeecs.game.cardDemo.Cards.SpellCard;
import uk.ac.qub.eeecs.game.cardDemo.Cards.StrengthCard;
import uk.ac.qub.eeecs.game.cardDemo.PlayerScore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by Edward Muldrew on 28/11/2017.
 * This class is used specifically to test the card class
 */



public class ScoreClassTest {

    @Mock
    GameScreen gameScreen = Mockito.mock(GameScreen.class);
    Game game = Mockito.mock(Game.class);

    @Mock
    private Bitmap cardBitmap;
    String expectedBitmap = "Turret";
    HeroCard testCard;
    SpellCard mockCard;
    private GameObject mBoardBG;
    PlayerScore testScore;

    @Mock
    private AssetStore assetManager;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(gameScreen.getGame()).thenReturn(game);
        when(game.getAssetManager()).thenReturn(assetManager);
        when(assetManager.getBitmap(expectedBitmap)).thenReturn(cardBitmap);

        testScore = new PlayerScore(0, Hand.UserType.USER);
    }

    // Test to check if test score variable has been instantiated correctly using getters
    @Test
    public void testScoreInstantiation() {
        assertEquals(testScore.getUserType(), Hand.UserType.USER);
        assertEquals(testScore.getScore(),0);
    }

    // Test to see if score changes when it is update
    @Test
    public void checkScoreChange() {
        assertEquals(testScore.getScore(),0);
        testScore.updateScore(10);
        assertEquals(testScore.getScore(),10);
    }

    //Test to see if set score method workds
    @Test
    public void checkSetScore() {
        assertEquals(testScore.getScore(),0);
        int scoreToBeSet = 20;
        testScore.setScore(scoreToBeSet);
        assertEquals(testScore.getScore(),scoreToBeSet);
    }

}

