package uk.ac.qub.eeecs.game.cardDemo;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.method.Touch;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetStore;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.engine.input.TouchHandler;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.game.MenuScreen;
import uk.ac.qub.eeecs.game.Round;
import uk.ac.qub.eeecs.game.cardDemo.Cards.Card;
import uk.ac.qub.eeecs.game.cardDemo.Cards.Deck;
import uk.ac.qub.eeecs.game.cardDemo.Cards.Hand;
import uk.ac.qub.eeecs.game.cardDemo.Cards.HeroCard;
import uk.ac.qub.eeecs.game.cardDemo.Cards.MinionCard;
import uk.ac.qub.eeecs.game.cardDemo.Cards.SpellCard;
import uk.ac.qub.eeecs.game.cardDemo.Cards.StrengthCard;
import uk.ac.qub.eeecs.game.cardDemo.Containers.HandContainer;
import uk.ac.qub.eeecs.game.cardDemo.Options.OptionsScreen;
import uk.ac.qub.eeecs.game.cardDemo.Utilities;


/**
 * This class is used to represent the User's screen at which they will decide to place or pass cards
 *
 * @version 1.0
 */
public class CardDemoScreen extends GameScreen {
    // Class Members
    private ScreenViewport mScreenViewport;
    private LayerViewport mLayerViewport;
    Utilities helperUtilities = new Utilities(this, mGame);
    private ArrayList<Card> activeCards = new ArrayList<>();
    private ArrayList<Card> compSciCards = new ArrayList<Card>();  // Create Computer Science Array List of Cards
    private Deck userDeck = new Deck(1, "User Deck", compSciCards, this); // Create User Deck
    private Hand userHand;
    HashMap<Integer, String> userTurnRecord = new HashMap<Integer, String>();
    HashMap<Integer, String> aiTurnRecord = new HashMap<Integer, String>(); // default turn record
    public Turn turnRecord = new Turn(userTurnRecord, aiTurnRecord);
    public PlayerScore userScore = new PlayerScore(0, Hand.UserType.USER);
    private PlayerScore defaultScore = new PlayerScore(0, Hand.UserType.AI); // default player score
    private Round round = new Round("Round1", turnRecord, userScore, defaultScore);
    private GameScreen aiDemoScreen = new AiDemoScreen(mGame, "aiDemoScreen", round);
    private GameScreen roundEndScreen = new RoundEndScreen(mGame, "roundEndScreen", round);

    private String player1DeckSize;
    public String player1Score;

    private GameObject mBoardBG;
    private GameObject passCheckPrompt;
    private GameObject userTurnPrompt;
    private GameObject exitPrompt;
    private GameObject cardPlacePrompt;

    private PushButton passButton;
    private PushButton yesButton;
    private PushButton noButton;
    private PushButton exitButton;

    boolean passPrompt = false;
    boolean aiTurnPrompt = false;
    boolean exitToMainPrompt = false;
    boolean cardPlace = false;

    Paint yesNoPaintVariable = new Paint();
    private final float LEVEL_WIDTH = 480.0f;
    private final float LEVEL_HEIGHT = 270.0f;
    private double playerStartTime;
    int spacingX;
    int spacingY;
    private int graveyardNumber = 0;
    int turnRecordPosition = 0;

    // Constructors

    /**
     *
     * @param game - game object passed through
     * @param name - name give to the given screen
     */
    public CardDemoScreen(Game game, String name) {
        super(name, game);
        // Create viewports
        mScreenViewport = new ScreenViewport(0, 0, game.getScreenWidth(), game.getScreenHeight());
        mLayerViewport = new LayerViewport(game.getScreenWidth() / 2, game.getScreenHeight() / 2, game.getScreenWidth() / 2, game.getScreenHeight() / 2);
        userHand = new Hand("User Hand", Hand.UserType.USER, compSciCards, this, game); // Create User Hand
        // Removing screens to ensure no bugs occur during new game creation
        mGame.getScreenManager().removeScreen(aiDemoScreen.getName());
        mGame.getScreenManager().removeScreen(roundEndScreen.getName());
        mGame.getScreenManager().addScreen(aiDemoScreen);
        mGame.getScreenManager().addScreen(roundEndScreen);
        // Create the layer viewport, taking into account the orientation
        // and aspect ratio of the screen.
        if (mScreenViewport.width > mScreenViewport.height)
            mLayerViewport = new LayerViewport(240.0f, 240.0f
                    * mScreenViewport.height / mScreenViewport.width, 240,
                    240.0f * mScreenViewport.height / mScreenViewport.width);
        else
            mLayerViewport = new LayerViewport(240.0f * mScreenViewport.height
                    / mScreenViewport.width, 240.0f, 240.0f
                    * mScreenViewport.height / mScreenViewport.width, 240);

        //Load the background for the screen
        mBoardBG = new GameObject(LEVEL_WIDTH / 2.0f,
                LEVEL_HEIGHT / 2.0f, LEVEL_WIDTH, LEVEL_HEIGHT, getGame()
                .getAssetManager().getBitmap("Board"), this);
        // Generates the user hand and deck
        userHand = helperUtilities.generateCompSciHand(this, userHand);
        userDeck = helperUtilities.generateCompSciDeck(this);


        spacingX = mGame.getScreenWidth() / helperUtilities.aspectRatioX;
        spacingY = mGame.getScreenHeight() / helperUtilities.aspectRatioY;
        //Instantiate Game objects
        passCheckPrompt = new GameObject(950,
                400, LEVEL_WIDTH, LEVEL_HEIGHT, getGame()
                .getAssetManager().getBitmap("PassCheck"), this);

        userTurnPrompt = new GameObject(950,
                400, LEVEL_WIDTH, LEVEL_HEIGHT, getGame()
                .getAssetManager().getBitmap("UserTurn"), this);

        exitPrompt = new GameObject(950,
                400, LEVEL_WIDTH, LEVEL_HEIGHT, getGame()
                .getAssetManager().getBitmap("ExitPrompt"), this);

        cardPlacePrompt = new GameObject(950,
                400, LEVEL_WIDTH, LEVEL_HEIGHT, getGame()
                .getAssetManager().getBitmap("CardPlacePrompt"), this);

        //Instantiate buttons
        noButton = new PushButton(1100, 500, spacingX * 0.4f, spacingY * 0.4f, "Sign", this);

        yesButton = new PushButton(800, 500, spacingX * 0.4f, spacingY * 0.4f, "Sign", this);

        exitButton = new PushButton(50, 50, 100, 100, "Back", this);

        passButton = new PushButton(spacingX * 4.8f, spacingY * 2.75f, spacingX * 0.4f, spacingY * 0.4f, "PassCoin", this);
    }


    // Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * This method draws the blank board screen
     *
     * @param graphics2D
     */
    private void boardSetUp(IGraphics2D graphics2D) {
        // Create the screen to black and define a clip based on the viewport
        graphics2D.clear(Color.WHITE);
        graphics2D.clipRect(mScreenViewport.toRect());
        //draws background to board
        AssetStore assetManager = mGame.getAssetManager();
        assetManager.loadAndAddBitmap("Board", "img/board.png");
        Bitmap background = mGame.getAssetManager().getBitmap("Board");
        graphics2D.drawBitmap(background, null, mScreenViewport.toRect(), null);
    }

    // New round method for Card Demo Screen, method resets board and active playing cards
    // Also draws one new card to users hand.
    public void newRound() {
        if (round.getRoundName().equals("Round2") || round.getRoundName().equals("Round3")) {
            this.userScore.setScore(0);
            for (int i = activeCards.size() - 1; i >= 0; i--) { // Removes active cards from board
                activeCards.remove(i);
                graveyardNumber++;
            }
            HashMap<Integer, String> newUserTurnRecord = new HashMap<Integer, String>();
            HashMap<Integer, String> newAiTurnRecord = new HashMap<Integer, String>(); // default turn record
            turnRecord = new Turn(newUserTurnRecord, newAiTurnRecord);
            round.setTurnRecord(turnRecord);
            this.turnRecordPosition = 0;
            userDeck.removeCardFromDeck(userHand);
            userHand = helperUtilities.setCardXPosition(userHand);
            round.setRoundName("roundTwo");
        }
    }

    /**
     * Update the card demo screen
     *
     * @param elapsedTime Elapsed time information
     */
    @Override
    public void update(ElapsedTime elapsedTime) {
        newRound(); // if there is a new round then update latest settings
        Input input = mGame.getInput();
        List<TouchEvent> touchEvents = input.getTouchEvents();
        // Update buttons
        passButton.update(elapsedTime);
        yesButton.update(elapsedTime);
        noButton.update(elapsedTime);
        exitButton.update(elapsedTime);

        if (userHand.getCardsInHand().size() == 0) { // if user has no cards left to play then pass go
            round.getTurnRecord().getUserTurnRecord().put(turnRecordPosition, "false");
            if (!aiTurnPrompt) {
                playerStartTime = elapsedTime.totalTime;
                aiTurnPrompt = true;
                // check if 3 seconds has passed since player turn started
            } else if (playerStartTime + 3 < elapsedTime.totalTime) {
                aiTurnPrompt = false;
                mGame.getScreenManager().setAsCurrentScreen("aiDemoScreen");
            }
        } else { // If user does have cards to play
            if (touchEvents.size() > 0) { // If touch event has occurred
                if (exitButton.isPushTriggered()) { // if exit button is pushed
                    exitToMainPrompt = true;
                }
                if (exitToMainPrompt) {
                    if (yesButton.isPushTriggered()) { // If yes to choosing to exit
                        exitToMainPrompt = false;
                        helperUtilities.changeToScreen(new MenuScreen(mGame), this, mGame);
                    } else if (noButton.isPushTriggered() && exitToMainPrompt) { // If no to choosing to exit
                        exitToMainPrompt = false;
                    }
                }

                if (passButton.isPushTriggered()) { // If pass coin is pushed
                    passPrompt = true;
                    round.getTurnRecord().getUserTurnRecord().put(turnRecordPosition, "false");
                }
                if (passPrompt) { // If pass coin button is pushed
                    if (yesButton.isPushTriggered()) { // if yes to passing is pushed
                        passPrompt = false;
                        helperUtilities.roundUserFinish(round.getTurnRecord(), turnRecordPosition, mGame);
                        turnRecordPosition = turnRecordPosition + 1;
                    } else if (noButton.isPushTriggered()) { // If no to passing is pushed
                        passPrompt = false;
                    }
                }
                if (cardPlace) { // If user has placed a card
                    if (yesButton.isPushTriggered()) { // If user confirms they wish to place card
                        cardPlace = false;
                        userHand.removeCard(activeCards);
                        helperUtilities.roundUserFinish(round.getTurnRecord(), turnRecordPosition, mGame);
                        turnRecordPosition = turnRecordPosition + 1;
                    } else if (noButton.isPushTriggered()) { // If user declines to place card
                        cardPlace = false;
                        userHand.popDownCard(round, activeCards);
                        round.getTurnRecord().getUserTurnRecord().put(turnRecordPosition, "false");
                    }
                }
                if (!cardPlace && !passPrompt && !exitToMainPrompt) { // If user has not passed, exited or placed a card
                    userHand.update(elapsedTime, round, turnRecordPosition, activeCards); // allow user to place a card
                }
                if (round.getTurnRecord().getUserTurnRecord().get(turnRecordPosition) != null
                        && round.getTurnRecord().getUserTurnRecord().get(turnRecordPosition).equals("true")) { // if user chooses to place a card
                    cardPlace = true;
                }
            }
        }
    }

    /**
     * This method is used to draw the Yes/No text for the user to to then make a selection based off the prompt
     *
     * @param elapsedTime
     * @param graphics2D
     */
    public void drawYesNo(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        yesNoPaintVariable.setColor(Color.WHITE);
        yesNoPaintVariable.setFakeBoldText(true);
        yesNoPaintVariable.setTextSize(30);
        yesButton.draw(elapsedTime, graphics2D);
        noButton.draw(elapsedTime, graphics2D);
        graphics2D.drawText("Yes", 780, 530, yesNoPaintVariable);
        graphics2D.drawText("No", 1080, 530, yesNoPaintVariable);
    }

    /**
     * Draw the card demo screen
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        graphics2D.clear(Color.WHITE);
        Paint paintVariable = new Paint();
        paintVariable.setColor(Color.WHITE);
        paintVariable.setFakeBoldText(true);
        paintVariable.setTextSize(50);
        player1DeckSize = String.valueOf(this.userDeck.getDeckOfCards().size());
        player1Score = String.valueOf(this.userScore.getScore());
        boardSetUp(graphics2D);
        passButton.draw(elapsedTime, graphics2D);
        exitButton.draw(elapsedTime, graphics2D);
        graphics2D.drawText(String.valueOf(graveyardNumber), 1450, 1000, paintVariable);
        graphics2D.drawText(player1Score, 1820, 100, paintVariable);
        graphics2D.drawText(player1DeckSize, 1650, 1000, paintVariable);
        graphics2D.drawText("User", 800, 50, paintVariable);

        // Draw the user Hand
        for (Card selectedCard : userHand.getCardsInHand()) {
            selectedCard.draw(elapsedTime, graphics2D, mLayerViewport, mScreenViewport);
        }

        // Draw active cards played on board
        for (Card selectedCard : activeCards) {
            selectedCard.draw(elapsedTime, graphics2D, mLayerViewport, mScreenViewport);
        }

        // Prompt to double check the user wishes to pass
        if (passPrompt) {
            passCheckPrompt.draw(elapsedTime, graphics2D);
            drawYesNo(elapsedTime, graphics2D);
        }

        // Prompt to confirm is user wishes to exit to main menu
        if (exitToMainPrompt) {
            exitPrompt.draw(elapsedTime, graphics2D);
            drawYesNo(elapsedTime, graphics2D);
        }

        // Prompt with information that the ai will now take their turn
        if (aiTurnPrompt) {
            userTurnPrompt.draw(elapsedTime, graphics2D);
        }

        // Prompt to confirm or delcine card placement
        if (cardPlace) {
            cardPlacePrompt.draw(elapsedTime, graphics2D);
            drawYesNo(elapsedTime, graphics2D);
        }
    }
    public int getUserScore(){
        return userScore.getScore();
    }
}
