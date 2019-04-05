package uk.ac.qub.eeecs.game;

import android.graphics.Bitmap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetStore;
import uk.ac.qub.eeecs.gage.engine.ScreenManager;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.cardDemo.CardDemoScreen;
import uk.ac.qub.eeecs.game.cardDemo.Cards.Card;
import uk.ac.qub.eeecs.game.cardDemo.Cards.HeroCard;
import uk.ac.qub.eeecs.game.cardDemo.Cards.MinionCard;
import uk.ac.qub.eeecs.game.cardDemo.Cards.SpellCard;
import uk.ac.qub.eeecs.game.cardDemo.Cards.StrengthCard;
import uk.ac.qub.eeecs.game.cardDemo.Turn;
import uk.ac.qub.eeecs.game.cardDemo.Utilities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by Edward Muldrew on 28/11/2017.
 * This class is used specifically to test the Utilities class
 */



public class UtilitiesClassTest {

    @Mock
    GameScreen gameScreen = Mockito.mock(GameScreen.class);
    Game game = Mockito.mock(Game.class);
    ScreenManager screenManager;

    @Mock
    MinionCard testCard;
    private Bitmap cardBitmap;
    String expectedBitmap = "Turret";
    Utilities utilitesTester;
    HashMap<Integer, String> userTurnRecord = new HashMap<Integer, String>();
    HashMap<Integer, String> aiTurnRecord = new HashMap<Integer, String>(); // default turn record
    public Turn turnRecord = new Turn(userTurnRecord, aiTurnRecord);
    GameScreen cardDemoScreen;
    float setPositionY = 24;

    @Mock
    private AssetStore assetManager;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(gameScreen.getGame()).thenReturn(game);
        when(game.getAssetManager()).thenReturn(assetManager);
        when(assetManager.getBitmap(expectedBitmap)).thenReturn(cardBitmap);
        screenManager = new ScreenManager();
        when(game.getScreenManager()).thenReturn(screenManager);
        utilitesTester = new Utilities(gameScreen,game);
        utilitesTester.loadAndAddAseests(game);
        cardDemoScreen = new CardDemoScreen(game,"cardDemoScreen");
        game.getScreenManager().addScreen(cardDemoScreen);
        game.getScreenManager().setAsCurrentScreen("cardDemoScreen");
        testCard = new MinionCard(1,"TestCard", Card.Subject.COMPUTER_SCIENCE,
                Card.Rarity.COMMON,"TestCard","SyringeMinion",gameScreen, StrengthCard.Row.Middle,4);
    }

    @Test()
    public void testRoundUserFinish() {
        assertEquals(game.getScreenManager().getCurrentScreen().getName(), "cardDemoScreen");
        turnRecord.getUserTurnRecord().put(0,"true");
        turnRecord.getAiTurnRecord().put(0,"false");
        turnRecord.getUserTurnRecord().put(1,"false");
        utilitesTester.roundUserFinish(turnRecord,1,game);
        assertEquals(game.getScreenManager().getCurrentScreen().getName(), "roundEndScreen");
    }


  /*  @Test()
    public void testCardRowChangeMethod() {
    assertEquals(testCard.getPositionY(),setPositionY,1.0);
     float returnedY = utilitesTester.checkCardRow(testCard,testCard.getPositionX(),testCard.getPositionY());
        // As test card is a minion card with there row set to middle the returned y position should be 155
        assertEquals(returnedY, 155,1.0);

    }*/



}


