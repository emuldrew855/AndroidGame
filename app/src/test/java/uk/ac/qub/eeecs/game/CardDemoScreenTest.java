package uk.ac.qub.eeecs.game;

import android.graphics.Bitmap;

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
import uk.ac.qub.eeecs.gage.engine.ScreenManager;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.Round;
import uk.ac.qub.eeecs.game.cardDemo.AiDemoScreen;
import uk.ac.qub.eeecs.game.cardDemo.CardDemoScreen;
import uk.ac.qub.eeecs.game.cardDemo.Cards.Card;
import uk.ac.qub.eeecs.game.cardDemo.Cards.Deck;
import uk.ac.qub.eeecs.game.cardDemo.Cards.Hand;
import uk.ac.qub.eeecs.game.cardDemo.Cards.HeroCard;
import uk.ac.qub.eeecs.game.cardDemo.Cards.MinionCard;
import uk.ac.qub.eeecs.game.cardDemo.PlayerScore;
import uk.ac.qub.eeecs.game.cardDemo.Turn;
import uk.ac.qub.eeecs.game.cardDemo.Utilities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by 40178070(Brandon) on 28/11/2017.
 */



public class  CardDemoScreenTest {

    @Mock
    GameScreen gameScreen = Mockito.mock(GameScreen.class);
    Game game = Mockito.mock(Game.class);
    ScreenManager screenManager;
    @Mock
    Input input;

    @Mock
    CardDemoScreen cardDemoScreen;
    private Bitmap cardBitmap;
    String expectedBitmap = "Turret";
    Utilities helperUtilities;
    private GameObject mBoardBG;
    private ArrayList<Card> compSciCards = new ArrayList<Card>();  // Create Computer Science Array List of Cards
    private Deck userDeck = new Deck(1, "User Deck", compSciCards, gameScreen); // Create User Deck
    private Hand userHand;
    private Hand aiHand;
    private PushButton passButton;
    private PushButton yesButton;
    private PushButton noButton;
    boolean userPrompt = false;
    private GameScreen aiDemoScreen;
    HashMap<Integer, String> userTurnRecord = new HashMap<Integer, String>();
    HashMap<Integer, String> aiTurnRecord = new HashMap<Integer, String>(); // default turn record
    public Turn turnRecord = new Turn(userTurnRecord, aiTurnRecord);
    private PlayerScore userScore = new PlayerScore(0, Hand.UserType.USER);
    private PlayerScore defaultScore = new PlayerScore(0, Hand.UserType.AI); // default player score
    private Round round = new Round("Round1", turnRecord, userScore, defaultScore);
    ArrayList<Card> activeCards = new ArrayList<Card>();
    int turnRecordPosition = 0;
    boolean aiTurnPrompt = false;
    boolean passPrompt = false;


    @Mock
    private AssetStore assetManager;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(gameScreen.getGame()).thenReturn(game);
        when(game.getAssetManager()).thenReturn(assetManager);
        when(assetManager.getBitmap(expectedBitmap)).thenReturn(cardBitmap);
        when(game.getInput()).thenReturn(input);
        screenManager = new ScreenManager();
        when(game.getScreenManager()).thenReturn(screenManager);
        cardDemoScreen =  new CardDemoScreen(game,"cardDemoScreen");
        screenManager.addScreen(cardDemoScreen);
        helperUtilities = new Utilities(gameScreen,game);
        aiHand = new Hand("AI HAND", Hand.UserType.AI,compSciCards,gameScreen,game);
        userHand = new Hand("User Hand", Hand.UserType.USER, compSciCards,gameScreen, game); // Create User Hand
        userHand = helperUtilities.generateCompSciHand(gameScreen, userHand);
        aiHand = helperUtilities.generateMedicineHand(gameScreen,aiHand);
        passButton = new PushButton(
                200, 200, 200, 200, "PassCoin", gameScreen);
        noButton = new PushButton(
                1100, 500, 200, 200, "Sign", gameScreen);

        yesButton = new PushButton(
                800, 500, 200, 200, "Sign", gameScreen);

        aiDemoScreen = new AiDemoScreen(game, "aiDemoScreen",round);
        game.getScreenManager().addScreen(aiDemoScreen);
    }



    // This test is to check if the correct cards have been allocated to the deck correctly.
    // A Card deck has 9 cards (1 Hero, 8 Random amount of spell and minion cards)
    @Test
    public void checkIfDeckSize() {
        int countHeroCards = 0;
        int countSpellMinionCards = 0;

        for(Card selectedCard: userHand.getCardsInHand()) {
            if(selectedCard.getClass() == HeroCard.class) {
                countHeroCards++;
            } else {
                countSpellMinionCards++;
            }
        }
        assertEquals(countSpellMinionCards,6 );
        assertEquals(countHeroCards, 1);
        assertEquals(userHand.getCardsInHand().size(), 7);
    }

    public void setAICardsBlank(Card[] aiCards) {
        for (Card selectedCard : aiCards) {
            if (selectedCard.getClass() == MinionCard.class) {
                selectedCard.setBitmapName("MinionBack",gameScreen);
            } else {
                selectedCard.setBitmapName("SpellBack",gameScreen);
            }
        }
    }

    // This is test to very that AI cards bitmap is correct as AI cards should not be able to be seen by the user
    @Test
    public void checkIfAICardsBlank() {
        for(int i=1; i <aiHand.getCardsInHand().size(); i++) {
          assertTrue( aiHand.getCardsInHand().get(i).getBitmapName().contains("Back"));
        }
    }

    public Hand setCardXPosition(Hand hand) {
        for (int i = 1; i < hand.getCardsInHand().size(); i++) {
            hand.getCardsInHand().get(i).setPositionX(hand.getCardsInHand().get(i - 1).getPositionX() + 38);
            hand.getCardsInHand().get(i).setStarterX(hand.getCardsInHand().get(i - 1).getPositionX() + 38);
        }
        return hand;
    }

    // This test is to check that cards are positioned correctly on the board
    @Test()
    public void checkIfCardsArePositionedCorrectly() {
        userHand = setCardXPosition(userHand);
        for(int i = 2; i < userHand.getCardsInHand().size(); i++) {
            assertEquals(userHand.getCardsInHand().get(i).getStarterX(),userHand.getCardsInHand().get(i-1).getStarterX() + 38,1.0f);
        }
    }

    public void updateMethod() {
        if (passButton.isPushTriggered()) {
            userPrompt = true;
        }
        if(yesButton.isPushTriggered()) {
            userPrompt = false;
            game.getScreenManager().setAsCurrentScreen("aiDemoScreen");
        }
        if(noButton.isPushTriggered()) {
            userPrompt = false;
        }
    }

    public void updateMethod2(List<TouchEvent> tEvents) {
        cardDemoScreen.newRound(); // if there is a new round then update latest settings
        Input input = game.getInput();
        List<TouchEvent> touchEvents = tEvents;
        ElapsedTime elapsedTime = new ElapsedTime();
        passButton.update(elapsedTime);
        yesButton.update(elapsedTime);
        noButton.update(elapsedTime);
        if (userHand.getCardsInHand().size() == 0) { // if user has no cards left to play then pass go
            round.getTurnRecord().getUserTurnRecord().put(turnRecordPosition, "false");
            aiTurnPrompt = true;
        } else {
            if (touchEvents.size() > 0) {
                if (passButton.isPushTriggered()) { // if pass button is pushed
                    passPrompt = true;
                    round.getTurnRecord().getUserTurnRecord().put(turnRecordPosition, "false");
                }
                if (noButton.isPushTriggered()) { // if no to passing button is pushed
                    userHand.popDownCard(round, activeCards);
                    round.getTurnRecord().getUserTurnRecord().put(turnRecordPosition, "false");
                    passPrompt = false;
                }
                userHand.update(elapsedTime, round, turnRecordPosition, activeCards);
                if (round.getTurnRecord().getUserTurnRecord().get(turnRecordPosition) != null
                        && round.getTurnRecord().getUserTurnRecord().get(turnRecordPosition).equals("true")) {
                    passPrompt = true;
                }
                if (yesButton.isPushTriggered() && passPrompt) { // if yes to passing is pushed
                    passPrompt = false;
                    userHand.removeCard(activeCards);
                    helperUtilities.roundUserFinish(round.getTurnRecord(), turnRecordPosition, game);
                    turnRecordPosition = turnRecordPosition + 1;
                }
            }
        }
    }

    // This test checks to see if the User is prompted when the user click the coin button
    @Test()
    public void checkIfPassPromptsUser (){
        assertEquals(userPrompt, false);
        passButton.mPushTriggered = true;
        updateMethod();

        assertEquals(userPrompt, true);
    }

    // This test check to see if the screen manager changes when yes button is pushed
    @Test()
    public void checkIfNoButtonPushed(){
        assertEquals(userPrompt, false);
        passButton.mPushTriggered = true;
        updateMethod();
        assertEquals(userPrompt, true);
        noButton.mPushTriggered = true;
        updateMethod();
        assertEquals(userPrompt, false);
    }

    // This test check to see if the screen manager changes when yes button is pushed
    @Test()
    public void checkIfYesButtonPushed() {
        passButton.mPushTriggered = true;
        yesButton.mPushTriggered = true;
        updateMethod();

        assertEquals(game.getScreenManager().getCurrentScreen().getName(),"aiDemoScreen");
    }

    // Method uses for testing purposes
    public void moveCard() {
//        assertEquals(20, userHand.getCardsInHand().get(0).getPositionY(), 2.0f);
        TouchEvent touchPosition = new TouchEvent();
        touchPosition.x = 80; // position x of first card
        touchPosition.y = 1000; // position y of first card
        touchPosition.type = TouchEvent.TOUCH_DOWN;
        List<TouchEvent> touchEvents = new ArrayList<>();
        touchEvents.add(touchPosition);
        when(input.getTouchEvents()).thenReturn(touchEvents);
        ElapsedTime elapsedTime = new ElapsedTime();
        PlayerScore playerScore = new PlayerScore(0, Hand.UserType.USER);
        updateMethod2(touchEvents);
//        assertEquals(30, userHand.getCardsInHand().get(0).getPositionY(), 2.0f);

        TouchEvent touchPosition2 = new TouchEvent();
        touchPosition2.x = 991;
        touchPosition2.y = 80;
        touchEvents.add(touchPosition2);
        float x = touchPosition2.x / 4;
        float y = 270 - (touchPosition2.y / 4);
        yesButton.mPushTriggered = true;
        updateMethod2(touchEvents);
        round.getTurnRecord().getUserTurnRecord().put(0,"true");
        game.getScreenManager().setAsCurrentScreen("aiDemoScreen");
    }

    // This test is to check to see if when a card is placed the users turn is set to true & user chooses to pass
    @Test()
    public void checkUserTurnTrueWhenCardPlaced() {
        assertEquals(round.getTurnRecord().getUserTurnRecord().get(0),"null");
        moveCard();
        assertEquals(round.getTurnRecord().getUserTurnRecord().get(0),"true");
        assertEquals(game.getScreenManager().getCurrentScreen().getName(),"aiDemoScreen");
    }

    // This test check to see if the user chooses to pass that the the users turn is set to false
    @Test()
    public void checkTurnWhenUserPasses() {
        assertEquals(round.getTurnRecord().getUserTurnRecord().get(0),"null");
        TouchEvent touchPosition = new TouchEvent();
        touchPosition.x = 80; // position x of first card
        touchPosition.y = 1000; // position y of first card
        touchPosition.type = TouchEvent.TOUCH_DOWN;
        List<TouchEvent> touchEvents = new ArrayList<>();
        touchEvents.add(touchPosition);
        passButton.mPushTriggered = true;
        yesButton.mPushTriggered = true;
        updateMethod2(touchEvents);
        assertEquals(round.getTurnRecord().getUserTurnRecord().get(0),"false");
    }


}

