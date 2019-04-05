package uk.ac.qub.eeecs.game;

import android.graphics.Bitmap;
import android.test.UiThreadTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetStore;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.Round;
import uk.ac.qub.eeecs.game.cardDemo.Cards.Card;
import uk.ac.qub.eeecs.game.cardDemo.Cards.Deck;
import uk.ac.qub.eeecs.game.cardDemo.Cards.Hand;
import uk.ac.qub.eeecs.game.cardDemo.Cards.HeroCard;
import uk.ac.qub.eeecs.game.cardDemo.Cards.SpellCard;
import uk.ac.qub.eeecs.game.cardDemo.Cards.StrengthCard;
import uk.ac.qub.eeecs.game.cardDemo.PlayerScore;
import uk.ac.qub.eeecs.game.cardDemo.Turn;
import uk.ac.qub.eeecs.game.cardDemo.Utilities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by Edward Muldrew on 28/11/2017.
 * This class is used to specifically test the hand class
 */


public class HandClassTest {

    @Mock
    GameScreen gameScreen = Mockito.mock(GameScreen.class);
    Game game = Mockito.mock(Game.class);
    @Mock
    Input input;

    @Mock
    private Bitmap cardBitmap;
    String expectedBitmap = "Turret";
    private GameObject mBoardBG;
    private ArrayList<Card> compSciCards = new ArrayList<Card>();  // Create Computer Science Array List of Cards
    private Hand userHand;
    Utilities helperUtilities;
    ArrayList<Card> activeCards = new ArrayList<Card>();
    HashMap<Integer, String> userTurnRecord = new HashMap<Integer, String>();
    HashMap<Integer, String> aiTurnRecord = new HashMap<Integer, String>(); // default turn record
    public Turn turnRecord = new Turn(userTurnRecord, aiTurnRecord);
    private PlayerScore userScore = new PlayerScore(0, Hand.UserType.USER);
    private PlayerScore defaultScore = new PlayerScore(0, Hand.UserType.AI); // default player score
    private Round round = new Round("Round1", turnRecord, userScore, defaultScore);


    @Mock
    private AssetStore assetManager;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(gameScreen.getGame()).thenReturn(game);
        when(game.getAssetManager()).thenReturn(assetManager);
        when(assetManager.getBitmap(expectedBitmap)).thenReturn(cardBitmap);
        when(game.getInput()).thenReturn(input);
        userHand = new Hand("UserHand", Hand.UserType.USER, compSciCards, gameScreen,game); // Create User Deck
        helperUtilities = new Utilities(gameScreen, game);
        userHand = helperUtilities.generateCompSciHand(gameScreen, userHand);
    }

    // This test checks to see if the hand object UserHand has been instantiated correctly
    @Test
    public void checkHandInstantiation() {
        assertEquals(userHand.getHandName(), "UserHand");
        assertEquals(userHand.getTypeOfHand(), Hand.UserType.USER);
        assertEquals(userHand.getCardsInHand().size(), 7);
    }

    // This test checks to see if the method setCardsInHand is working properly
    @Test
    public void checkSetCardsInHand() {
        ArrayList<Card> testArrayList = new ArrayList<Card>();  // Creates array list for testing purposes
        userHand.setCardsInHand(testArrayList);
        assertTrue(userHand.getCardsInHand().equals(testArrayList));
    }

    // This test checks to see

    // This test is to check if the pop up card method works
    @Test
    public void checkIfPopUpCard() {
        assertEquals(24, userHand.getCardsInHand().get(0).getPositionY(), 2.0f);
        TouchEvent touchPosition = new TouchEvent();
        touchPosition.x = 80; // position x of first card
        touchPosition.y = 1000; // position y of first card
        touchPosition.type = TouchEvent.TOUCH_DOWN;
        List<TouchEvent> touchEvents = new ArrayList<>();
        touchEvents.add(touchPosition);
        when(input.getTouchEvents()).thenReturn(touchEvents);
        ElapsedTime elapsedTime = new ElapsedTime();
        PlayerScore playerScore = new PlayerScore(0, Hand.UserType.USER);
        userHand.update(elapsedTime, round, 1,activeCards);

        assertEquals(30, userHand.getCardsInHand().get(0).getPositionY(), 2.0f);
    }

    // This test is to check if the pop down card method works
    @Test
    public void checkIfPopDownCard() {
        assertEquals(24, userHand.getCardsInHand().get(0).getPositionY(), 2.0f);
        TouchEvent touchPosition = new TouchEvent();
        touchPosition.x = 80; // position x of first card
        touchPosition.y = 1000; // position y of first card
        touchPosition.type = TouchEvent.TOUCH_DOWN;
        List<TouchEvent> touchEvents = new ArrayList<>();
        touchEvents.add(touchPosition);
        when(input.getTouchEvents()).thenReturn(touchEvents);
        ElapsedTime elapsedTime = new ElapsedTime();
        PlayerScore playerScore = new PlayerScore(0, Hand.UserType.USER);
        userHand.update(elapsedTime, round, 1,activeCards);
        assertEquals(30, userHand.getCardsInHand().get(0).getPositionY(), 2.0f);

        userHand.popDownCard(round,activeCards);

        assertEquals(24, userHand.getCardsInHand().get(0).getPositionY(), 2.0f);
    }

    // This test is to check if the move card metho works
    @Test
    public void checkIfMoveCard() {
        assertEquals(24, userHand.getCardsInHand().get(0).getPositionY(), 2.0f);
        TouchEvent touchPosition = new TouchEvent();
        touchPosition.x = 80; // position x of first card
        touchPosition.y = 1000; // position y of first card
        touchPosition.type = TouchEvent.TOUCH_DOWN;
        List<TouchEvent> touchEvents = new ArrayList<>();
        touchEvents.add(touchPosition);
        when(input.getTouchEvents()).thenReturn(touchEvents);
        ElapsedTime elapsedTime = new ElapsedTime();
        PlayerScore playerScore = new PlayerScore(0, Hand.UserType.USER);
        userHand.update(elapsedTime, round, 1,activeCards);
        assertEquals(30, userHand.getCardsInHand().get(0).getPositionY(), 2.0f);

        TouchEvent touchPosition2 = new TouchEvent();
        touchPosition2.x = 991;
        touchPosition2.y = 80;
        touchEvents.add(touchPosition2);
        float x = touchPosition2.x / 4;
        float y = 270 - (touchPosition2.y / 4);
        userHand.update(elapsedTime, round, 1,activeCards);
        assertEquals(230, userHand.getCardsInHand().get(0).getPositionY(), 2.0f);
        assertEquals(x, userHand.getCardsInHand().get(0).getPositionX(), 2.0f);
    }

    // This test checks to see if a User can place a card if they have placed all their respective cards
    @Test
    public void checkCardPlaceOnEmptyHand() {
        for (int i = userHand.getCardsInHand().size() - 1; i >= 0; i--) {
            userHand.getCardsInHand().remove(i);
        }
        assertEquals(userHand.getCardsInHand().size(), 0);
        ElapsedTime elapsedTime = new ElapsedTime();
        round.getTurnRecord().getUserTurnRecord().put(0, "true");
        userHand.update(elapsedTime, round, 0,activeCards);
        assertEquals(round.getTurnRecord().getUserTurnRecord().get(0), "false");
    }


    // Check to see if the remove card method removes a card from the deck and also adds it to the active cards
    @Test
    public void testRemoveCardMethod() {
        ArrayList<Card> activeCards = new ArrayList<>();
        assertEquals(userHand.getCardsInHand().size(),7); // Tests to check before hand
        assertEquals(activeCards.size(),0);
        this.checkIfMoveCard(); // method to run to show card has been moved
        userHand.removeCard(activeCards); // run to see if any cards are removed
        assertEquals(userHand.getCardsInHand().size(),6);
        assertEquals(activeCards.size(),1);
    }
}

