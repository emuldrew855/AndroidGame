package uk.ac.qub.eeecs.game.cardDemo;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

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
import uk.ac.qub.eeecs.game.GameRecord;
import uk.ac.qub.eeecs.game.Round;
import uk.ac.qub.eeecs.game.cardDemo.Cards.Card;
import uk.ac.qub.eeecs.game.cardDemo.Cards.Deck;
import uk.ac.qub.eeecs.game.cardDemo.Cards.Hand;
import uk.ac.qub.eeecs.game.cardDemo.Containers.HandContainer;


/**
 * Created by Edward Muldrew on the 19/02/2018. This class is used to show the end score of each round between the user and they AI.
 * It will also be used to make decisions on if a new round needs to be created or someone has won the game.
 *
 * @version 1.0
 */
public class RoundEndScreen extends GameScreen {
    // Instance Variables
    private ScreenViewport mScreenViewport;
    private LayerViewport mLayerViewport;
    Utilities helperUtilities = new Utilities(this, mGame);
    private GameScreen gameEndScreen;
    private GameObject mBoardBG;
    Round round;
    int userScore;
    int aiScore;
    int userOverallScore = 0;
    int aiOverallScore = 0;
    String result = "";
    String roundName = "";
    GameRecord gameRecord;
    ArrayList<Round> roundRecord = new ArrayList<Round>();

    private final float LEVEL_WIDTH = 480.0f;
    private final float LEVEL_HEIGHT = 270.0f;
    int roundRecordIndex = 1;
    int spacingX;
    int spacingY;
    private PushButton newRoundButton;


    // /////////////////////////////////////////////////////////////////////////
    // Constructors

    /**
     * Create the Round End screen
     *
     * @param game Game to which this screen belongs
     */
    public RoundEndScreen(Game game, String name, Round round) {
        super(name, game);
        this.round = round; // assign round variable to passed in round
        userScore = round.getUserScore().getScore();
        aiScore = round.getAiScore().getScore();
        gameRecord = new GameRecord(roundRecord, userOverallScore, aiOverallScore);
        gameEndScreen = new GameEndScreen(game, "gameEndScreen", gameRecord); // Create Game End screen screen
        // Removing game ending screen and adding it stop any bugs on new game creation
        mGame.getScreenManager().removeScreen(gameEndScreen.getName());
        mGame.getScreenManager().addScreen(gameEndScreen);
        // Create viewports
        spacingX = mGame.getScreenWidth() / helperUtilities.aspectRatioX;
        spacingY = mGame.getScreenHeight() / helperUtilities.aspectRatioY;
        mScreenViewport = new ScreenViewport(0, 0, game.getScreenWidth(), game.getScreenHeight());
        mLayerViewport = new LayerViewport(game.getScreenWidth() / 2, game.getScreenHeight() / 2, game.getScreenWidth() / 2, game.getScreenHeight() / 2);
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
                .getAssetManager().getBitmap("RoundEnd"), this);

        newRoundButton = new PushButton(
                950, 650, 500, spacingY * 0.4f, "Sign", this);
    }

    // Methods
    // /////////////////////////////////////////////////////////////////////////
    private void boardSetUp(IGraphics2D graphics2D) {
        // Create the screen to black and define a clip based on the viewport
        graphics2D.clear(Color.WHITE);
        graphics2D.clipRect(mScreenViewport.toRect());
        //draws background to board
        AssetStore assetManager = mGame.getAssetManager();
        assetManager.loadAndAddBitmap("RoundEnd", "img/RoundEnd.jpg");
        Bitmap background = mGame.getAssetManager().getBitmap("RoundEnd");
        graphics2D.drawBitmap(background, null, mScreenViewport.toRect(), null);
    }

    /**
     * This method calculates the result of a round and returns a string
     *
     * @returns a string result which will be printed out on screen
     */
    public String resultCheck() {
        if (userScore > aiScore) {
            return result = "Win!";
        } else if (userScore == aiScore) {
            return result = "Tie!";
        } else {
            return result = "Loss!";
        }
    }

    public void gameEnd() { // Check to see who has won the respective round
        if (resultCheck() == "Win!") {
            userOverallScore++;
        } else if (resultCheck() == "Tie!") {
            userOverallScore++;
            aiOverallScore++;
        } else if (resultCheck() == "Loss!") {
            aiOverallScore++;
        }
        gameRecord.setAiOVerallScore(aiOverallScore);
        gameRecord.setUserOverallScore(userOverallScore);
        // Check
        if (userOverallScore >= 2 && aiOverallScore < 2 || aiOverallScore >= 2 && userOverallScore < 2) {
            mGame.getScreenManager().setAsCurrentScreen("gameEndScreen");
        } else if (userOverallScore >= 2 && aiOverallScore == 0 && aiOverallScore >= 2 && userOverallScore == 2) {
            mGame.getScreenManager().setAsCurrentScreen("gameEndScreen");
        } else if (userOverallScore == 3 && aiOverallScore == 3) {
            mGame.getScreenManager().setAsCurrentScreen("gameEndScreen");
        } else {
            mGame.getScreenManager().setAsCurrentScreen("cardDemoScreen");
        }
    }

    /**
     * Update the card demo screen
     *
     * @param elapsedTime Elapsed time information
     */
    @Override
    public void update(ElapsedTime elapsedTime) {
        Input input = mGame.getInput();
        List<TouchEvent> touchEvents = input.getTouchEvents();
        userScore = round.getUserScore().getScore();
        aiScore = round.getAiScore().getScore();
        newRoundButton.update(elapsedTime);
        if (touchEvents.size() > 0) {
            if (roundName == "Round3") { // Check to see if all 3 rounds have passed then game should end
                gameEnd();
            } else if (newRoundButton.isPushTriggered()) {
                roundName = "Round" + Integer.toString(roundRecordIndex);
                gameRecord.getRoundRecord().add(roundRecordIndex - 1, new Round(roundName, round.getTurnRecord(), new PlayerScore(round.getUserScore().score, Hand.UserType.USER)
                        , new PlayerScore(round.getAiScore().score, Hand.UserType.AI)));
                roundRecordIndex++; // increment the round record by one
                roundName = "Round" + Integer.toString(roundRecordIndex);
                round.setRoundName(roundName);
                gameEnd(); // check to see if any user has one
            }
        }
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
        boardSetUp(graphics2D);
        newRoundButton.draw(elapsedTime, graphics2D);
        graphics2D.drawText("New Round", 830, 690, paintVariable);
        graphics2D.drawText(resultCheck(), 1300, 480, paintVariable);
        graphics2D.drawText(String.valueOf(userScore), 780, 630, paintVariable);
        graphics2D.drawText(String.valueOf(aiScore), 1080, 630, paintVariable);
    }
}