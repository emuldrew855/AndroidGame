package uk.ac.qub.eeecs.game.cardDemo;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.List;

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
import uk.ac.qub.eeecs.game.MenuScreen;
import uk.ac.qub.eeecs.game.Round;


/**
 * Created by Edward Muldrew on the 26/02/2018. This class is used to show feedback from the end of the game and show who won and the respective scores.
 * The class will then naviagate the user back to the original home screen.
 *
 * @version 1.0
 */
public class GameEndScreen extends GameScreen {
    // Instance Variables
    private ScreenViewport mScreenViewport;
    private LayerViewport mLayerViewport;
    Utilities helperUtilities = new Utilities(this, mGame);

    private GameObject mBoardBG;
    GameRecord gameRecord;
    private PushButton finishGameButton;

    private final float LEVEL_WIDTH = 480.0f;
    private final float LEVEL_HEIGHT = 270.0f;
    int spacingX;
    int spacingY;


    // /////////////////////////////////////////////////////////////////////////
    // Constructors

    /**
     *
     * @param game
     * @param name - name of the game screen
     * @param gameRecord - takes in a game record object to decide who has won the game
     */
    public GameEndScreen(Game game, String name, GameRecord gameRecord) {
        super(name, game);
        this.gameRecord = gameRecord;
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
                .getAssetManager().getBitmap("GameEndScreen"), this);

        finishGameButton = new PushButton(
                950, 780, 500, spacingY * 0.4f, "Sign", this);
    }


    // Methods
    private void boardSetUp(IGraphics2D graphics2D) {
        // Create the screen to black and define a clip based on the viewport
        graphics2D.clear(Color.WHITE);
        graphics2D.clipRect(mScreenViewport.toRect());
        //draws background to board
        AssetStore assetManager = mGame.getAssetManager();
        assetManager.loadAndAddBitmap("GameEndScreen", "img/GameEndScreen.jpg");
        Bitmap background = mGame.getAssetManager().getBitmap("GameEndScreen");
        graphics2D.drawBitmap(background, null, mScreenViewport.toRect(), null);
    }


    /**
     *   This method checks the score of the overall game and checks who won
     * @returns a string based off the results
     */
    public String resultCheck() {
        if (gameRecord.getUserOverallScore() > gameRecord.getAiOVerallScore()) {
            return "Win!";
        } else if (gameRecord.getUserOverallScore() == gameRecord.getAiOVerallScore()) {
            return "Tie!";
        } else {
            return "Loss!";
        }
    }

    /**
     * Update the game end screen- only thing that can be updated is the user choosing to finish the game
     *
     * @param elapsedTime Elapsed time information
     */
    @Override
    public void update(ElapsedTime elapsedTime) {
        Input input = mGame.getInput();
        List<TouchEvent> touchEvents = input.getTouchEvents();
        finishGameButton.update(elapsedTime);
        if (touchEvents.size() > 0) {
            if (finishGameButton.isPushTriggered()) { // If user clicks finish game button
                helperUtilities.changeToScreen(new MenuScreen(mGame), this, mGame);
            }
        }
    }

    /**
     * Draw the game end screens
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
        // Draw overall scores
        graphics2D.drawText(String.valueOf(gameRecord.getUserOverallScore()), 840, 450, paintVariable);
        graphics2D.drawText("-", 905, 465, paintVariable);
        graphics2D.drawText(String.valueOf(gameRecord.getAiOVerallScore()), 990, 450, paintVariable);
        graphics2D.drawText(resultCheck(), 1300, 380, paintVariable);

        // Draw round scores
        graphics2D.drawText("ROUND 1: ", 450, 540, paintVariable);
        graphics2D.drawText(String.valueOf(gameRecord.getRoundRecord().get(0).getUserScore().getScore()), 830, 540, paintVariable);
        graphics2D.drawText("-", 905, 540, paintVariable);
        graphics2D.drawText(String.valueOf(gameRecord.getRoundRecord().get(0).getAiScore().getScore()), 980, 540, paintVariable);
        graphics2D.drawText("ROUND 2: ", 450, 640, paintVariable);
        graphics2D.drawText(String.valueOf(gameRecord.getRoundRecord().get(1).getUserScore().getScore()), 830, 640, paintVariable);
        graphics2D.drawText("-", 905, 640, paintVariable);
        graphics2D.drawText(String.valueOf(gameRecord.getRoundRecord().get(1).getAiScore().getScore()), 980, 640, paintVariable);

        if (gameRecord.getRoundRecord().size() > 2) { // If there is more than 2 rounds draw round 3 on screen
            graphics2D.drawText("ROUND 3: ", 450, 740, paintVariable);
            graphics2D.drawText(String.valueOf(gameRecord.getRoundRecord().get(2).getUserScore().getScore()), 830, 740, paintVariable);
            graphics2D.drawText("-", 905, 740, paintVariable);
            graphics2D.drawText(String.valueOf(gameRecord.getRoundRecord().get(2).getAiScore().getScore()), 980, 740, paintVariable);
        }
        // Draw new finish game button
        finishGameButton.draw(elapsedTime, graphics2D);
        graphics2D.drawText("Finish Game", 830, 820, paintVariable);
    }
}