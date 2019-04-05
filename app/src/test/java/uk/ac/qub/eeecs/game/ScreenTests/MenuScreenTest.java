package uk.ac.qub.eeecs.game.ScreenTests;

import android.graphics.Bitmap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetStore;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.ScreenManager;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.game.MenuScreen;
import uk.ac.qub.eeecs.game.cardDemo.Options.OptionsScreen;
import uk.ac.qub.eeecs.game.OverworldScreen;
import uk.ac.qub.eeecs.game.cardDemo.CardDemoScreen;
import uk.ac.qub.eeecs.game.cardDemo.TutorialScreen;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

//Created by Grace 40172213

@RunWith(MockitoJUnitRunner.class)
public class MenuScreenTest {
    @Mock
    Game game;
    ScreenManager screenManager;

    @Mock
    AssetStore assetManager;
    @Mock
    Bitmap bitmap;

    @Mock
    Input input;

    @Mock
    ScreenViewport mScreenViewport;
    LayerViewport mLayerViewport;

    //Set up test data
    @Before
    public void setup() {
        screenManager = new ScreenManager();
        when(game.getScreenManager()).thenReturn(screenManager);

        when(game.getAssetManager()).thenReturn(assetManager);
        when(assetManager.getBitmap(any(String.class))).thenReturn(bitmap);

        when(game.getInput()).thenReturn(input);
    }

    //Test to check screen dimensions are loaded correctly
    @Test
    public void loadScreenDimensions() {
        mScreenViewport = new ScreenViewport(0, 0, game.getScreenWidth(),
                game.getScreenHeight());

        if (mScreenViewport.width > mScreenViewport.height)
            mLayerViewport = new LayerViewport(240.0f, 240.0f
                    * mScreenViewport.height / mScreenViewport.width, 240,
                    240.0f * mScreenViewport.height / mScreenViewport.width);
        else
            mLayerViewport = new LayerViewport(240.0f * mScreenViewport.height
                    / mScreenViewport.width, 240.0f, 240.0f
                    * mScreenViewport.height / mScreenViewport.width, 240);

    }

    //Test to check background image asset is loaded into asset store correctly
    @Test
    public void testBackgroundImageAsset() {
        assetManager.loadAndAddBitmap("MenuBackground", "img/SplashScreen.png");
    }

    //Test to check start button image asset is loaded into asset store correctly
    @Test
    public void testStartButtonImageAsset() {
        assetManager.loadAndAddBitmap("StartButton", "img/buttons/button_start.png");
    }

    //Test to check options button image asset is loaded into asset store correctly
    @Test
    public void testOptionsButtonImageAsset() {
        assetManager.loadAndAddBitmap("OptionsButton", "img/buttons/button_options.png");
    }

    //Test to check tutorial button image asset is loaded into asset store correctly
    @Test
    public void testTutorialButtonImageAsset() {
        assetManager.loadAndAddBitmap("TutorialButton", "img/buttons/button_tutorial.png");
    }

    //Test to check overworld button image asset is loaded into asset store correctly
    @Test
    public void testOverworldButtonImageAsset() {
        assetManager.loadAndAddBitmap("LocationArrow", "img/LocationArrow.png");
    }

    //Test to check that the user is transitioned to the cardDemoScreen when they click the start button
    @Test
    public void testStartButtonTransition() {
        //Setup test data
        MenuScreen menuScreen = new MenuScreen(game);
        game.getScreenManager().addScreen(menuScreen);
        CardDemoScreen cardDemoScreen = new CardDemoScreen(game,"");

        int spacingX = game.getScreenWidth() / 5;
        int spacingY = game.getScreenHeight() / 3;

        TouchEvent touchPosition = new TouchEvent();
        touchPosition.x = spacingX / 5 * 2.5f; //Position of start button
        touchPosition.y = spacingY / 3 * 0.75f;
        touchPosition.type = TouchEvent.TOUCH_DOWN;
        List<TouchEvent> touchEvents = new ArrayList<>();
        touchEvents.add(touchPosition);
        when(input.getTouchEvents()).thenReturn(touchEvents);

        ElapsedTime elapsedTime = new ElapsedTime();

        //Call method
        menuScreen.update(elapsedTime);

        //Check return
        assertEquals(game.getScreenManager().getCurrentScreen().getName(), cardDemoScreen.getName());
    }

    //Test to check that the user is transitioned to the optionsScreen when they click the options button
    @Test
    public void testOptionsButtonTransition() {
        //Setup test data
        MenuScreen menuScreen = new MenuScreen(game);
        game.getScreenManager().addScreen(menuScreen);
        OptionsScreen optionsScreen = new OptionsScreen(game,"");

        int spacingX = game.getScreenWidth() / 5;
        int spacingY = game.getScreenHeight() / 3;

        TouchEvent touchPosition = new TouchEvent();
        touchPosition.x = spacingX / 5 * 2.5f; //Position of options button
        touchPosition.y = spacingY / 3 * 1.5f;
        touchPosition.type = TouchEvent.TOUCH_DOWN;
        List<TouchEvent> touchEvents = new ArrayList<>();
        touchEvents.add(touchPosition);
        when(input.getTouchEvents()).thenReturn(touchEvents);

        ElapsedTime elapsedTime = new ElapsedTime();

        //Call method
        menuScreen.update(elapsedTime);

        //Check return
        assertEquals(game.getScreenManager().getCurrentScreen().getName(), optionsScreen.getName());
    }


    //Test to check that the user is transitioned to the TutorialScreen when they click the tutorial button
    @Test
    public void testTutorialButtonTransition() {
        //Setup test data
        MenuScreen menuScreen = new MenuScreen(game);
        game.getScreenManager().addScreen(menuScreen);
        TutorialScreen tutorialScreen = new TutorialScreen(game);

        int spacingX = game.getScreenWidth() / 5;
        int spacingY = game.getScreenHeight() / 3;

        TouchEvent touchPosition = new TouchEvent();
        touchPosition.x = spacingX / 5 * 2.5f; //Position of tutorial button
        touchPosition.y = spacingY / 3 * 2.25f;
        touchPosition.type = TouchEvent.TOUCH_DOWN;
        List<TouchEvent> touchEvents = new ArrayList<>();
        touchEvents.add(touchPosition);
        when(input.getTouchEvents()).thenReturn(touchEvents);

        ElapsedTime elapsedTime = new ElapsedTime();

        //Call method
        menuScreen.update(elapsedTime);

        //Check return
        assertEquals(game.getScreenManager().getCurrentScreen().getName(), tutorialScreen.getName());
    }

    //Test to check that the user is transitioned to the OverworldScreen when they click the overworld button@Test
    public void testOverworldButtonTransition() {
        //Setup test data
        MenuScreen menuScreen = new MenuScreen(game);
        game.getScreenManager().addScreen(menuScreen);
        OverworldScreen overworldScreen = new OverworldScreen(game);

        int spacingX = game.getScreenWidth() / 5;
        int spacingY = game.getScreenHeight() / 3;

        TouchEvent touchPosition = new TouchEvent();
        touchPosition.x = spacingX / 5 * 0.5f; //Position of overworld button
        touchPosition.y = spacingY / 3 * 2.55f;
        touchPosition.type = TouchEvent.TOUCH_DOWN;
        List<TouchEvent> touchEvents = new ArrayList<>();
        touchEvents.add(touchPosition);
        when(input.getTouchEvents()).thenReturn(touchEvents);

        ElapsedTime elapsedTime = new ElapsedTime();

        //Call method
        menuScreen.update(elapsedTime);

        //Check return
        assertEquals(game.getScreenManager().getCurrentScreen().getName(), overworldScreen.getName());
    }
}
