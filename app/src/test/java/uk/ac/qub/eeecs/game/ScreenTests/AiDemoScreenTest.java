package uk.ac.qub.eeecs.game.ScreenTests;

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
import uk.ac.qub.eeecs.game.cardDemo.Cards.SpellCard;
import uk.ac.qub.eeecs.game.cardDemo.Cards.StrengthCard;
import uk.ac.qub.eeecs.game.cardDemo.PlayerScore;
import uk.ac.qub.eeecs.game.cardDemo.RoundEndScreen;
import uk.ac.qub.eeecs.game.cardDemo.Turn;
import uk.ac.qub.eeecs.game.cardDemo.Utilities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by 40178070(Brandon) on 28/11/2017.
 */


public class AiDemoScreenTest {

    @Mock
    GameScreen gameScreen = Mockito.mock(GameScreen.class);
    Game game = Mockito.mock(Game.class);
    ScreenManager screenManager;


    @Mock
    private Bitmap cardBitmap;
    String expectedBitmap = "Turret";
    Utilities helperUtilities;
    private GameObject mBoardBG;
    private ArrayList<Card> defaultArray = new ArrayList<Card>();  // Create Computer Science Array List of Cards
    private Deck aiDeck;
    private ArrayList<Card> activeCards = new ArrayList<>();
    private Hand userHand;
    private Hand aiHand;
    private PushButton passButton;
    private PushButton yesButton;
    private PushButton noButton;
    boolean userPrompt = false;
    boolean updateThis = true;
    int positionInHand = 0;
    int turnRecordPosition = 0;
    int graveyardNumber = 0;
    private double playerStartTime;
    private GameScreen cardDemoScreen;
    private GameScreen aiDemoScreen;
    private GameScreen roundEndScreen;
    HashMap<Integer, String> userTurnRecord = new HashMap<Integer, String>();
    HashMap<Integer, String> aiTurnRecord = new HashMap<Integer, String>(); // default turn record
    private PlayerScore aiScore = new PlayerScore(0, Hand.UserType.AI);
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
        screenManager = new ScreenManager();
        when(game.getScreenManager()).thenReturn(screenManager);

        helperUtilities = new Utilities(gameScreen, game);
        aiHand = new Hand("AI HAND", Hand.UserType.AI, defaultArray, gameScreen, game);
        userHand = new Hand("User Hand", Hand.UserType.USER, defaultArray, gameScreen, game); // Create User Hand
        aiDeck = new Deck(1, "Ai Deck", defaultArray, gameScreen); // Create User Deck
        userHand = helperUtilities.generateCompSciHand(gameScreen, userHand);
        aiHand = helperUtilities.generateMedicineHand(gameScreen, aiHand);
        aiDeck = helperUtilities.generateMedicineDeck(gameScreen);
        passButton = new PushButton(
                200, 200, 200, 200, "PassCoin", gameScreen);
        noButton = new PushButton(
                1100, 500, 200, 200, "Sign", gameScreen);

        yesButton = new PushButton(
                800, 500, 200, 200, "Sign", gameScreen);

        cardDemoScreen = new CardDemoScreen(game, "cardDemoScreen");
        aiDemoScreen = new AiDemoScreen(game, "aiDemoScreen", round);
        roundEndScreen = new RoundEndScreen(game, "roundEndScreen", round);
        game.getScreenManager().addScreen(cardDemoScreen);
        game.getScreenManager().addScreen(aiDemoScreen);
        game.getScreenManager().addScreen(roundEndScreen);
        game.getScreenManager().setAsCurrentScreen(aiDemoScreen.getName());
    }


    public void updateMethod() {
        if (passButton.isPushTriggered()) {
            game.getScreenManager().setAsCurrentScreen("cardDemoScreen");
        }
    }

    // This test check to see if the screen manager changes when yes button is pushed
    @Test()
    public void checkIfYesButtonPushed() {
        passButton.mPushTriggered = true;
        yesButton.mPushTriggered = true;
        updateMethod();

        assertEquals(game.getScreenManager().getCurrentScreen().getName(), "cardDemoScreen");
    }

    public void newRound() { // New Round method which will draw a new card and remove any placed cards from previous round
        if (round.getRoundName() == "roundTwo") {
            this.aiScore.setScore(0);
            for (int i = activeCards.size() - 1; i >= 0; i--) {
                activeCards.remove(i);
                graveyardNumber++;
            }
            this.turnRecordPosition = 0;
            aiDeck.removeCardFromDeck(aiHand);
            aiHand = helperUtilities.setCardXPosition(aiHand);
            round.setRoundName("somethingRandom");
        }
    }

    public void update(ElapsedTime elapsedTime) {
        newRound();
        if (aiHand.getCardsInHand().size() == 0) { // if all cards have been played
            round.getTurnRecord().getUserTurnRecord().put(turnRecordPosition, "false");
            round.setAiScore(aiScore);
        }else if (round.getTurnRecord().getUserTurnRecord().get(turnRecordPosition).equals("false") && updateThis) { // if user doesn't play
            round.getTurnRecord().getAiTurnRecord().put(turnRecordPosition, "false");
            round.setAiScore(aiScore);
        } else if (updateThis) { // if user does play
            Card cardToPlay = decidePlay(activeCards);
            cardToPlay = helperUtilities.setAICardsNotBlank(cardToPlay, gameScreen);
            cardToPlay.setPosition(160, 185);
            cardToPlay = helperUtilities.checkIfCardCollide(activeCards, cardToPlay.getPositionX(), cardToPlay.getPositionY(), cardToPlay);
            aiHand.getCardsInHand().remove(cardToPlay);
            activeCards.add(cardToPlay);
            if (cardToPlay.getClass() == MinionCard.class || cardToPlay.getClass() == HeroCard.class) {
                StrengthCard convertedCard = (StrengthCard) cardToPlay;
                round.getAiScore().updateScore(convertedCard.getCardStrength());
            } else {
                SpellCard convertedCard = (SpellCard) cardToPlay;
                convertedCard.aiSpellCard(elapsedTime, round, activeCards);
            }
            round.getTurnRecord().getAiTurnRecord().put(turnRecordPosition, "true");
        }

        if (updateThis == true) {
            playerStartTime = elapsedTime.totalTime;
            updateThis = false;
        }

        if (helperUtilities.roundAIFinish(round.getTurnRecord(), turnRecordPosition, game)) {
            turnRecordPosition = turnRecordPosition + 1; // increment one for next turn
            game.getScreenManager().setAsCurrentScreen("roundEndScreen");
        } else {
            turnRecordPosition = turnRecordPosition + 1; // increment one for next turn
            game.getScreenManager().setAsCurrentScreen("cardDemoScreen");
        }
    }

    public Card decidePlay(ArrayList<Card> activeCards) {
        Card cardDecided = null;
        positionInHand = 0;
        for (Card selectedCard : aiHand.getCardsInHand()) {
            if (activeCards.size() == 0) {
                if (selectedCard.getClass() == MinionCard.class) {
                    cardDecided = selectedCard;
                    positionInHand = selectedCard.getCardID();
                }
            } else {
                cardDecided = selectedCard;
                positionInHand = selectedCard.getCardID();
            }
        }
        return cardDecided;
    }

    // This test checks to see if the AI plays a Minion card when active cards on board is 0
    @Test()
    public void checkMinionCardPlayedFirst() {
        Card testCard = decidePlay(activeCards);
        assertTrue(testCard.getClass() == MinionCard.class);
    }

    // Ai should pass on the condition that the user has also passed, therefor turn record should change and screen should change
    @Test()
    public void testIfAIPasses() {
        assertEquals(game.getScreenManager().getCurrentScreen().getName(), "aiDemoScreen");
        turnRecord.getUserTurnRecord().put(0, "false");
        ElapsedTime elapsedTime = new ElapsedTime();
        update(elapsedTime);
        assertEquals(turnRecord.getAiTurnRecord().get(0), "false");
        assertEquals(game.getScreenManager().getCurrentScreen().getName(), "roundEndScreen");
    }

    // if AI plays then the game screen should switch back to card demo screen
    @Test()
    public void testIfAIPlays() {
        assertEquals(game.getScreenManager().getCurrentScreen().getName(), "aiDemoScreen");
        turnRecord.getUserTurnRecord().put(0, "true");
        ElapsedTime elapsedTime = new ElapsedTime();
        update(elapsedTime);
        assertEquals(turnRecord.getAiTurnRecord().get(0), "true");
        assertEquals(game.getScreenManager().getCurrentScreen().getName(), "cardDemoScreen");
    }

    // This test checks to see if the AI Plays all its cards and the user has passed or has played of its cards
    @Test()
    public void testIfAIPlaysAllCards() {
        turnRecord.getUserTurnRecord().put(0,"false");
        for(int i =0; i < aiHand.getCardsInHand().size(); i++) {
            aiHand.getCardsInHand().remove(aiHand.getCardsInHand().get(i));
        }
        ElapsedTime elapsedTime = new ElapsedTime();
        update(elapsedTime);
        assertEquals(game.getScreenManager().getCurrentScreen().getName(), "roundEndScreen");
    }


    // Test to see if a card has been succesfully placed on the board
    @Test()
    public void testIfCardPlacedOnBoard() {
        assertEquals(activeCards.size(), 0);
        assertEquals(aiHand.getCardsInHand().size(), 7);
        ElapsedTime elapsedTime = new ElapsedTime();
        update(elapsedTime);
        assertTrue(round.getAiScore().getScore() > 0);
        assertEquals(aiHand.getCardsInHand().size(), 6);
        assertEquals(activeCards.size(), 1);
        assertEquals(activeCards.get(0).getClass(), MinionCard.class);
    }

    // Test to see if a new round is succesfully created
    @Test()
    public void testAINewRound() {
        // Before new round
        assertEquals(activeCards.size(), 0);
        assertEquals(graveyardNumber, 0);
        assertEquals(aiHand.getCardsInHand().size(), 7);
        assertEquals(aiDeck.getDeckOfCards().size(), 7);
        ElapsedTime elapsedTime = new ElapsedTime();
        update(elapsedTime);
        round.setRoundName("roundTwo");
        update(elapsedTime);
        // After New round
        assertEquals(activeCards.size(),0);
        assertEquals(graveyardNumber, 1);
        assertEquals(aiHand.getCardsInHand().size(), 7);
        assertEquals(aiDeck.getDeckOfCards().size(), 6);
    }


}

