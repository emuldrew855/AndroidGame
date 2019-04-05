package uk.ac.qub.eeecs.game.ScreenTests;

import android.graphics.Bitmap;
import android.util.Log;

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
import uk.ac.qub.eeecs.game.SplashScreen;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

//Created by Grace 40172213

@RunWith(MockitoJUnitRunner.class)
public class SplashScreenTest {

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
        assetManager.loadAndAddBitmap("SplashBackground", "img/SplashScreen.png");
    }

    //Test to check logo image asset is loaded into asset store correctly
    @Test
    public void testLogoAsset() {
        assetManager.loadAndAddBitmap("Logo", "img/blackbox_logo.png");
    }

    //Test to check background image variables are defined correctly
    @Test
    public void defineBGImageVariables() {
        //  mSplashBG = new GameObject(LEVEL_WIDTH / 2.0f,
        //         LEVEL_HEIGHT / 2.0f, LEVEL_WIDTH, LEVEL_HEIGHT, getGame().getAssetManager().getBitmap("SplashBackground"), this);
    }

    //Test to check the variables that set up the screen's spacing are initialised correctly
    @Test
    public void testScreenSpacing() {
        int spacingX = game.getScreenWidth() / 5;
        int spacingY = game.getScreenHeight() / 3;
    }

    //Test to check logo image variables are defined correctly
    @Test
    public void testLogoVariables() {
        int spacingX = game.getScreenWidth() / 5;
        int spacingY = game.getScreenHeight() / 3;

        //PushButton mLogo = new PushButton(spacingX * 2.5f, spacingY * 1.5f, spacingX*2.1f, spacingY, "Logo","SplashScreen");
    }

    //Test to check touch input is recognised
    @Test
    public void testInput() {
        //Process any touch events occurring since the update
        Input input = game.getInput();

        List<TouchEvent> touchEvents = input.getTouchEvents();
        if (touchEvents.size() > 0) {
            // Just check the first touch event that occurred in the frame.
            // It means pressing the screen with several fingers may not
            // trigger a 'button', but, hey, it's an exceedingly basic menu.
            TouchEvent touchEvent = touchEvents.get(0);
            Log.d("Splash Screen", "Touch Triggered");
        }
    }

    //Test changeToScreen method (for MenuScreen)
    @Test
    public void testChangeToScreenMethod() {
        //game.getScreenManager().removeScreen(this.getName());
        game.getScreenManager().addScreen(new MenuScreen(game));
        game.getScreenManager().setAsCurrentScreen(new MenuScreen(game).getName());
    }

    //Test to check that the user is transitioned to the menuScreen when they click the logo image
    @Test
    public void testCorrectMenuScreenTransition() {
        //Setup test data
        SplashScreen splashScreen = new SplashScreen(game);
        game.getScreenManager().addScreen(splashScreen);
        MenuScreen mainMenu = new MenuScreen(game);

        int spacingX = game.getScreenWidth() / 5;
        int spacingY = game.getScreenHeight() / 3;

        TouchEvent touchPosition = new TouchEvent();
        touchPosition.x = spacingX * 2.5f; //Position of logo button
        touchPosition.y = spacingY * 1.5f;
        touchPosition.type = TouchEvent.TOUCH_DOWN;
        List<TouchEvent> touchEvents = new ArrayList<>();
        touchEvents.add(touchPosition);
        when(input.getTouchEvents()).thenReturn(touchEvents);

        ElapsedTime elapsedTime = new ElapsedTime();

        //Call method
        splashScreen.update(elapsedTime);

        //Check return
        assertEquals(game.getScreenManager().getCurrentScreen().getName(), mainMenu.getName());
    }

    //Test to check that the user is transitioned to the menuScreen when they click a random area on the screen
    @Test
    public void testTransitionAtRandomLocation1() {
        //Setup test data
        SplashScreen splashScreen = new SplashScreen(game);
        game.getScreenManager().addScreen(splashScreen);
        MenuScreen mainMenu = new MenuScreen(game);

        TouchEvent touchPosition = new TouchEvent();
        touchPosition.x = 500; //Random location
        touchPosition.y = 500;
        touchPosition.type = TouchEvent.TOUCH_DOWN;
        List<TouchEvent> touchEvents = new ArrayList<>();
        touchEvents.add(touchPosition);
        when(input.getTouchEvents()).thenReturn(touchEvents);

        ElapsedTime elapsedTime = new ElapsedTime();

        //Call method
        splashScreen.update(elapsedTime);

        //Check return
        assertEquals(game.getScreenManager().getCurrentScreen().getName(), mainMenu.getName());
    }

    //Test to check that the user is transitioned to the menuScreen when they click a random area on the screen
    @Test
    public void testTransitionAtRandomLocation2() {
        //Setup test data
        SplashScreen splashScreen = new SplashScreen(game);
        game.getScreenManager().addScreen(splashScreen);
        MenuScreen mainMenu = new MenuScreen(game);

        TouchEvent touchPosition = new TouchEvent();
        touchPosition.x = 2; //Random location
        touchPosition.y = 2;
        touchPosition.type = TouchEvent.TOUCH_DOWN;
        List<TouchEvent> touchEvents = new ArrayList<>();
        touchEvents.add(touchPosition);
        when(input.getTouchEvents()).thenReturn(touchEvents);

        ElapsedTime elapsedTime = new ElapsedTime();

        //Call method
        splashScreen.update(elapsedTime);

        //Check return
        assertEquals(game.getScreenManager().getCurrentScreen().getName(), mainMenu.getName());
    }
}
