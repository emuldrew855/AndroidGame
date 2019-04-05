package uk.ac.qub.eeecs.game.cardDemo;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetStore;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.game.Round;
import uk.ac.qub.eeecs.game.cardDemo.Cards.Card;
import uk.ac.qub.eeecs.game.cardDemo.Cards.Deck;
import uk.ac.qub.eeecs.game.cardDemo.Cards.Hand;
import uk.ac.qub.eeecs.game.cardDemo.Cards.HeroCard;
import uk.ac.qub.eeecs.game.cardDemo.Cards.MinionCard;
import uk.ac.qub.eeecs.game.cardDemo.Cards.SpellCard;
import uk.ac.qub.eeecs.game.cardDemo.Cards.StrengthCard;
import uk.ac.qub.eeecs.game.cardDemo.Containers.HandContainer;



public class AiDemoScreen extends GameScreen {
    // Class Members
    private ScreenViewport mScreenViewport;
    private LayerViewport mLayerViewport;
    Utilities helperUtilities = new Utilities(this, mGame);

    private GameObject mBoardBG;
    private ArrayList<Card> activeCards = new ArrayList<>(); // active ai cards placed on the screen
    private ArrayList<Card> medicineCards = new ArrayList<Card>();  // Create Medicine Array List of Cards
    private Deck aiDeck = new Deck(2, "AI Deck", medicineCards, this); // Create AI Deck
    private Hand aiHand;
    private PlayerScore aiScore = new PlayerScore(0, Hand.UserType.AI);
    HashMap<Integer, String> aiTurnRecord = new HashMap<Integer, String>(); // keeps record the ai;s turns
    Round round;
    Turn turnRecord;
    private PushButton passButton;

    boolean completeAiTurn = true;

    private final float LEVEL_WIDTH = 480.0f;
    private final float LEVEL_HEIGHT = 270.0f;
    int turnRecordPosition = 0;
    int graveyardNumber = 0;
    int positionInHand;
    private double playerStartTime;


    // Constructors

    /**
     * Create the AI Demo game screen
     *
     * @param game Game to which this screen belongs
     */
    public AiDemoScreen(Game game, String name, Round round) {
        super(name, game);
        // Create viewports
        mScreenViewport = new ScreenViewport(0, 0, game.getScreenWidth(), game.getScreenHeight());
        mLayerViewport = new LayerViewport(game.getScreenWidth() / 2, game.getScreenHeight() / 2, game.getScreenWidth() / 2, game.getScreenHeight() / 2);
        aiHand = aiHand = new Hand("AI Hand", Hand.UserType.AI, medicineCards, this, game); // Create AI Hand
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

        // Assign variables to the passed in parameters
        this.turnRecord = round.getTurnRecord();
        this.round = round;
        round.setAiScore(aiScore);
        helperUtilities.loadAndAddAseests(mGame);
        aiHand = helperUtilities.generateMedicineHand(this, aiHand);
        aiDeck = helperUtilities.generateMedicineDeck(this);

        int spacingX = mGame.getScreenWidth() / 5;
        int spacingY = mGame.getScreenHeight() / 3;

        passButton = new PushButton(
                spacingX * 4.8f, spacingY * 2.75f, spacingX * 0.4f, spacingY * 0.4f, "PassCoin", this);

        //Load the background for the screen
        mBoardBG = new GameObject(LEVEL_WIDTH / 2.0f,
                LEVEL_HEIGHT / 2.0f, LEVEL_WIDTH, LEVEL_HEIGHT, getGame()
                .getAssetManager().getBitmap("Board"), this);
    }

    // Methods
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

    // New Round method which will draw a new card and remove any placed cards from previous round
    public void newRound() {
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

    /**
     * This method is used in deciding which card to play
     *
     * @param aiHand
     * @return true if there is a minion card present in the AI hand
     */
    public boolean checkIfMinionCard(Hand aiHand) {
        boolean ifMinionCard = false;
        for (Card selectedCard : aiHand.getCardsInHand()) {
            if (selectedCard.getClass() == MinionCard.class) {
                ifMinionCard = true;
            }
        }
        return ifMinionCard;
    }

    /**
     * This method decides which card to play
     * Refactored and changed by Edward Muldrew
     *
     * @param activeCards - takes in the current active cards on board
     * @return a card to be played
     */
    public Card decidePlay(ArrayList<Card> activeCards) {
        Card cardDecided = null;
        positionInHand = 0;
        for (int i = 0; i < aiHand.getCardsInHand().size(); i++) {
            if (activeCards.size() == 0 && checkIfMinionCard(aiHand)) { // First card played will always be a minion card if present
                if (aiHand.getCardsInHand().get(i).getClass() == MinionCard.class) {
                    cardDecided = aiHand.getCardsInHand().get(i);
                    positionInHand = aiHand.getCardsInHand().get(i).getCardID();
                }
            } else { // play any card
                cardDecided = aiHand.getCardsInHand().get(i);
                positionInHand = aiHand.getCardsInHand().get(i).getCardID();
            }
        }
        return cardDecided;
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
        boardSetUp(graphics2D);
        Paint paintVariable = new Paint();
        paintVariable.setColor(Color.WHITE);
        paintVariable.setFakeBoldText(true);
        paintVariable.setTextSize(50);

        String player2DeckSize = String.valueOf(this.aiDeck.getDeckOfCards().size());
        String player2Score = String.valueOf(this.aiScore.getScore());
        graphics2D.drawText(player2Score, 1820, 100, paintVariable);
        graphics2D.drawText(player2DeckSize, 1650, 1000, paintVariable);
        graphics2D.drawText("AI", 800, 50, paintVariable);
        graphics2D.drawText(String.valueOf(graveyardNumber), 1450, 1000, paintVariable);
        passButton.draw(elapsedTime, graphics2D);

        for (Card selectedCard : aiHand.getCardsInHand()) {    // Draw cards for the AI hand
            selectedCard.draw(elapsedTime, graphics2D, mLayerViewport, mScreenViewport);
        }

        for (Card selectedCard : activeCards) { // draw active cards
            selectedCard.draw(elapsedTime, graphics2D, mLayerViewport, mScreenViewport);
        }
    }

    /**
     * Update the card demo screen
     *
     * @param elapsedTime Elapsed time information
     */
    @Override
    public void update(ElapsedTime elapsedTime) {
        newRound(); // check to see if their is a new round
        if (round.getTurnRecord().getUserTurnRecord().get(turnRecordPosition).equals("false") && completeAiTurn) { // if user doesn't play
            round.getTurnRecord().getAiTurnRecord().put(turnRecordPosition, "false");
            round.setAiScore(aiScore);
        } else if (completeAiTurn) { // if user does play
            Card cardToPlay = decidePlay(activeCards); // Card to play is selected
            cardToPlay = helperUtilities.setAICardsNotBlank(cardToPlay, this); // Sets back of card not to be blank
            cardToPlay.setPosition(160, 185); // Set default position
            cardToPlay = helperUtilities.checkIfCardCollide(activeCards, cardToPlay.getPositionX(), cardToPlay.getPositionY(), cardToPlay);
            aiHand.getCardsInHand().remove(cardToPlay);
            activeCards.add(cardToPlay); // add played card to active cards
            // convert base class card and update score if minion or hero card
            if (cardToPlay.getClass() == MinionCard.class || cardToPlay.getClass() == HeroCard.class) {
                StrengthCard convertedCard = (StrengthCard) cardToPlay;
                round.getAiScore().updateScore(convertedCard.getCardStrength());
            } else { // Use spell card ability
                SpellCard convertedCard = (SpellCard) cardToPlay;
                convertedCard.aiSpellCard(elapsedTime, round, activeCards);
            }
            round.getTurnRecord().getAiTurnRecord().put(turnRecordPosition, "true");
        }

        if (completeAiTurn == true) {
            playerStartTime = elapsedTime.totalTime;
            completeAiTurn = false;
        }
        if (playerStartTime + 3 < elapsedTime.totalTime) {
            completeAiTurn = true;
            if (helperUtilities.roundAIFinish(round.getTurnRecord(), turnRecordPosition, mGame)) {
                mGame.getScreenManager().setAsCurrentScreen("roundEndScreen");
            } else {
                mGame.getScreenManager().setAsCurrentScreen("cardDemoScreen");
            }
            turnRecordPosition = turnRecordPosition + 1; // increment one for next turn
        }
    }



}