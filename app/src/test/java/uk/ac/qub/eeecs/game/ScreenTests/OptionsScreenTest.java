package uk.ac.qub.eeecs.game.ScreenTests;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetStore;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.ScreenManager;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.game.MenuScreen;
import uk.ac.qub.eeecs.game.cardDemo.Options.OptionsScreen;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

//Created by Grace 40172213

@RunWith(MockitoJUnitRunner.class)
public class OptionsScreenTest {
    @Mock
    Context mMockContext = Mockito.mock(Context.class);
    @Mock
    PushButton object1 = Mockito.mock(PushButton.class);
    @Mock
    SharedPreferences sharedPrefs = Mockito.mock(SharedPreferences.class);
    @Mock
    SharedPreferences.Editor editor = Mockito.mock(SharedPreferences.Editor.class);
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

    boolean sharedPrefFlag = true;

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
        assetManager.loadAndAddBitmap("OptionsBackground", "img/SplashScreen.png");
    }

    //Test to back button image asset is loaded into asset store correctly
    @Test
    public void testBackButtonImageAsset() {
        assetManager.loadAndAddBitmap("Back", "img/buttons/button_back.png");
    }

    //Test to check options text image asset is loaded into asset store correctly
    @Test
    public void testOptionsTextImageAsset() {
        assetManager.loadAndAddBitmap("optionsText", "img/text/text_options.png");
    }

    //Test to check brightness text image asset is loaded into asset store correctly
    @Test
    public void testBrightnessTextImageAsset() {
        assetManager.loadAndAddBitmap("brightnessText", "img/text/text_brightness.png");
    }

    //Test to check empty progress bar image asset is loaded into asset store correctly
    @Test
    public void testEmptyProgBarImageAsset() {
        assetManager.loadAndAddBitmap("emptyProgressBar", "img/progressBar/progressBar_empty.png");
    }

    //Test to check full progress bar image asset is loaded into asset store correctly
    @Test
    public void testFullProgBarImageAsset() {
        assetManager.loadAndAddBitmap("fullProgressBar", "img/progressBar/progressBar_full.png");
    }

    //Test to check volume off button image asset is loaded into asset store correctly
    @Test
    public void testVolumeOffButtonImageAsset() {
        assetManager.loadAndAddBitmap("VolumeOff", "img/buttons/button_volumeOff.png");
    }

    //Test to check volume on button image asset is loaded into asset store correctly
    @Test
    public void testVolumeOnButtonImageAsset() {
        assetManager.loadAndAddBitmap("VolumeOn", "img/buttons/button_volumeOn.png");
    }

    //Test to check music text image asset is loaded into asset store correctly
    @Test
    public void testMusicTextImageAsset() {
        assetManager.loadAndAddBitmap("musicText", "img/text/text_music.png");
    }

    //Test to check that the user is transitioned to the menuScreen when they click the back button
    @Test
    public void testBackButtonTransition() {
        //Setup test data
        OptionsScreen optionsScreen = new OptionsScreen(game,"");
        game.getScreenManager().addScreen(optionsScreen);
        MenuScreen mainMenu = new MenuScreen(game);

        int spacingX = game.getScreenWidth() / 5;
        int spacingY = game.getScreenHeight() / 3;

        TouchEvent touchPosition = new TouchEvent();
        touchPosition.x = spacingX / 5 * 0.5f; //Position of back button
        touchPosition.y = spacingY / 3 * 2.5f;
        touchPosition.type = TouchEvent.TOUCH_DOWN;
        List<TouchEvent> touchEvents = new ArrayList<>();
        touchEvents.add(touchPosition);
        when(input.getTouchEvents()).thenReturn(touchEvents);

        ElapsedTime elapsedTime = new ElapsedTime();

        //Call method
        optionsScreen.update(elapsedTime);

        //Check return
        assertEquals(game.getScreenManager().getCurrentScreen().getName(), mainMenu.getName());
    }
}