package uk.ac.qub.eeecs.game.ScreenTests;

import android.graphics.Bitmap;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.Parameterized;
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
import uk.ac.qub.eeecs.game.StudentsUnion.Direction;
import uk.ac.qub.eeecs.game.cardDemo.Utilities;
import uk.ac.qub.eeecs.game.cardDemo.Cards.PlayerSprite;
import uk.ac.qub.eeecs.gage.world.Sprite;
import uk.ac.qub.eeecs.game.StudentsUnion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;


public class StudentsUnionTest {
    @Mock
    GameScreen gameScreen = Mockito.mock(GameScreen.class);
    Game game = Mockito.mock(Game.class);
    ScreenManager screenManager;
    private Bitmap spriteBitmap;
    private GameScreen StudentsUnion;
    private String expectedBitmapName1 = "UpStill";
    private String expectedBitmapName2 = "UpLeft";
    private String expectedBitmapName3 = "UpRight";
    private String expectedBitmapName4 = "DownStill";
    private String expectedBitmapName5 = "DownLeft";
    private String expectedBitmapName6 = "DownRight";
    private String expectedBitmapName7 = "LeftStill";
    private String expectedBitmapName8 = "LeftLeft";
    private String expectedBitmapName9 = "LeftRight";
    private String expectedBitmapName10 = "RightStill";
    private String expectedBitmapName11 = "RightLeft";
    private String expectedBitmapName12 = "RightRight";
    Direction direction1 = Direction.UP;
    Direction direction2 = Direction.DOWN;
    Direction direction3 = Direction.LEFT;
    Direction direction4 = Direction.RIGHT;
    PlayerSprite mockSprite = null;
    private PushButton upButton;
    private PushButton downButton;
    private PushButton leftButton;
    private PushButton rightButton;
    private double timeSpentWalking;
    private Game mGame;
    private PlayerSprite playerSprite;
    private float positionY = 20.f;
    private float positionX = 20.f;
    //private ElapsedTime elapsedTime;



    @Mock
    private AssetStore assetManager;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
        when(gameScreen.getGame()).thenReturn(game);
        when(game.getAssetManager()).thenReturn(assetManager);
        when(assetManager.getBitmap(expectedBitmapName1)).thenReturn(spriteBitmap);
        when(assetManager.getBitmap(expectedBitmapName2)).thenReturn(spriteBitmap);
        when(assetManager.getBitmap(expectedBitmapName3)).thenReturn(spriteBitmap);
        when(assetManager.getBitmap(expectedBitmapName4)).thenReturn(spriteBitmap);
        when(assetManager.getBitmap(expectedBitmapName5)).thenReturn(spriteBitmap);
        when(assetManager.getBitmap(expectedBitmapName6)).thenReturn(spriteBitmap);
        when(assetManager.getBitmap(expectedBitmapName7)).thenReturn(spriteBitmap);
        when(assetManager.getBitmap(expectedBitmapName8)).thenReturn(spriteBitmap);
        when(assetManager.getBitmap(expectedBitmapName9)).thenReturn(spriteBitmap);
        when(assetManager.getBitmap(expectedBitmapName10)).thenReturn(spriteBitmap);
        when(assetManager.getBitmap(expectedBitmapName11)).thenReturn(spriteBitmap);
        when(assetManager.getBitmap(expectedBitmapName12)).thenReturn(spriteBitmap);
        when(playerSprite.getPositionY()).thenReturn(positionY);
        when(playerSprite.getPositionX()).thenReturn(positionX);
        screenManager = new ScreenManager();
        when(game.getScreenManager()).thenReturn(screenManager);
        upButton = new PushButton(
                200.f, 300.f, 10.f, 10.f, "UpArrow", gameScreen);
        downButton = new PushButton(
                200.f, 100.f, 10.f, 10.f, "DownArrow",  gameScreen);
        leftButton = new PushButton(
                100.f, 200.f, 10.f, 10.f, "LeftArrow",  gameScreen);
        rightButton = new PushButton(
                300.f, 200.f, 10.f, 10.f, "RightArrow",  gameScreen);

        StudentsUnion = new StudentsUnion(game);

    }

    void walk(Direction direction, PlayerSprite playerSprite, ElapsedTime elapsedTime, uk.ac.qub.eeecs.game.StudentsUnion.LastStepTaken lastStep)
    {
        timeSpentWalking = elapsedTime.totalTime;

        if(direction == Direction.UP)
        {
            playerSprite.setPosition(playerSprite.getPositionX(), playerSprite.getPositionY()+12.5f);
            if(timeSpentWalking+ 0.5 < elapsedTime.totalTime && lastStep == uk.ac.qub.eeecs.game.StudentsUnion.LastStepTaken.LEFT){
                playerSprite.setmBitmap(gameScreen.getGame().getAssetManager().getBitmap("UpLeft"));

            }
            else if(timeSpentWalking+ 0.5 < elapsedTime.totalTime && lastStep == uk.ac.qub.eeecs.game.StudentsUnion.LastStepTaken.RIGHT){
                playerSprite.setmBitmap(gameScreen.getGame().getAssetManager().getBitmap("UpRight"));
            }
        }
        else if(direction == Direction.DOWN)
        {
            playerSprite.setPosition(playerSprite.getPositionX(), playerSprite.getPositionY()-12.5f);
            if(timeSpentWalking+ 0.5 < elapsedTime.totalTime && lastStep == uk.ac.qub.eeecs.game.StudentsUnion.LastStepTaken.LEFT){
                playerSprite.setmBitmap(gameScreen.getGame().getAssetManager().getBitmap("DownLeft"));

            }
            else if(timeSpentWalking+ 0.5 < elapsedTime.totalTime && lastStep == uk.ac.qub.eeecs.game.StudentsUnion.LastStepTaken.RIGHT){
                playerSprite.setmBitmap(gameScreen.getGame().getAssetManager().getBitmap("DownRight"));
            }
        }
        else if(direction == Direction.LEFT)
        {
            playerSprite.setPosition(playerSprite.getPositionX()-12.5f, playerSprite.getPositionY());
            if(timeSpentWalking+ 0.5 < elapsedTime.totalTime && lastStep == uk.ac.qub.eeecs.game.StudentsUnion.LastStepTaken.LEFT){
                playerSprite.setmBitmap(gameScreen.getGame().getAssetManager().getBitmap("LeftLeft"));

            }
            else if(timeSpentWalking+ 0.5 < elapsedTime.totalTime && lastStep == uk.ac.qub.eeecs.game.StudentsUnion.LastStepTaken.RIGHT){
                playerSprite.setmBitmap(gameScreen.getGame().getAssetManager().getBitmap("LeftRight"));
            }
        }
        else if(direction == Direction.RIGHT)
        {
            playerSprite.setPosition(playerSprite.getPositionX()+12.5f, playerSprite.getPositionY());
            if(timeSpentWalking+ 0.5 < elapsedTime.totalTime && lastStep == uk.ac.qub.eeecs.game.StudentsUnion.LastStepTaken.LEFT){
                playerSprite.setmBitmap(gameScreen.getGame().getAssetManager().getBitmap("RightLeft"));

            }
            else if(timeSpentWalking+ 0.5 < elapsedTime.totalTime && lastStep == uk.ac.qub.eeecs.game.StudentsUnion.LastStepTaken.RIGHT){
                playerSprite.setmBitmap(gameScreen.getGame().getAssetManager().getBitmap("RightRight"));
            }
        }
    }
    uk.ac.qub.eeecs.game.StudentsUnion.LastStepTaken changeStep(uk.ac.qub.eeecs.game.StudentsUnion.LastStepTaken lastStep) {
        if(lastStep == uk.ac.qub.eeecs.game.StudentsUnion.LastStepTaken.LEFT){
            lastStep = uk.ac.qub.eeecs.game.StudentsUnion.LastStepTaken.RIGHT;

        }
        else if(lastStep == uk.ac.qub.eeecs.game.StudentsUnion.LastStepTaken.RIGHT){
            lastStep = uk.ac.qub.eeecs.game.StudentsUnion.LastStepTaken.LEFT;

        }
        return lastStep;
    }

    public void update(ElapsedTime elapsedTime) {

        // Process any touch events occurring since the update
        Input input = mGame.getInput();
        Direction direction;
        uk.ac.qub.eeecs.game.StudentsUnion.LastStepTaken lastStep = uk.ac.qub.eeecs.game.StudentsUnion.LastStepTaken.LEFT;
        List<TouchEvent> touchEvents = input.getTouchEvents();
        if (touchEvents.size() > 0) {

            // Just check the first touch event that occurred in the frame.
            // It means pressing the screen with several fingers may not
            // trigger a 'button', but, hey, it's an exceedingly basic menu.
            TouchEvent touchEvent = touchEvents.get(0);

            // Update each button and transition if needed
            upButton.update(elapsedTime);
            downButton.update(elapsedTime);
            leftButton.update(elapsedTime);
            rightButton.update(elapsedTime);
            //interactionButton.update(elapsedTime);


            playerSprite.update(elapsedTime);


            if (upButton.isPushTriggered()) {
                direction = Direction.UP;
                walk(direction, playerSprite, elapsedTime, lastStep);
                changeStep(lastStep);
            }
            else if (downButton.isPushTriggered()) {
                direction = Direction.DOWN;
                walk(direction, playerSprite, elapsedTime, lastStep);
                changeStep(lastStep);
            }
            else if (leftButton.isPushTriggered()) {
                direction = Direction.LEFT;
                walk(direction, playerSprite, elapsedTime, lastStep);
                changeStep(lastStep);
            }
            else if (rightButton.isPushTriggered()) {
                direction = Direction.RIGHT;
                walk(direction, playerSprite, elapsedTime, lastStep);
                changeStep(lastStep);
            }
            //else if (interactionButton.isPushTriggered())
            //interact(direction, playerSprite);
        }

    }

        //If up button is pressed and not held it should move the sprite 12.5f upwards
    @Test
    @Ignore
    public void testUpButton(){
    upButton.mPushTriggered = true;
    ElapsedTime elapsedTime = new ElapsedTime();
    update(elapsedTime);
    assertEquals(playerSprite.getPositionY(), 32.5f);
    }
    //If down button is pressed and not held it should move the sprite 12.5f downwards
    @Test
    @Ignore
    public void testDownButton(){
        downButton.mPushTriggered = true;
        ElapsedTime elapsedTime = new ElapsedTime();
        update(elapsedTime);
        assertEquals(playerSprite.getPositionY(), 7.5f);
    }

    //If left button is pressed and not held it should move the sprite 12.5f left
    @Test
    @Ignore
    public void testLeftButton(){
        leftButton.mPushTriggered = true;
        ElapsedTime elapsedTime = new ElapsedTime();
        update(elapsedTime);
        assertEquals(playerSprite.getPositionX(), 7.5f);
    }

    //If right button is pressed and not held it should move the sprite 12.5f right
    @Test
    @Ignore
    public void testRightButton(){
        rightButton.mPushTriggered = true;
        ElapsedTime elapsedTime = new ElapsedTime();
        update(elapsedTime);
        assertEquals(playerSprite.getPositionX(), 32.5f);
    }



}
