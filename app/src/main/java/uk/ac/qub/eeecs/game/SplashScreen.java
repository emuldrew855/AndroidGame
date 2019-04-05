package uk.ac.qub.eeecs.game;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
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

//Created by Grace 40172213

public class SplashScreen extends GameScreen {
    private final float LEVEL_WIDTH = 1000.0f;
    private final float LEVEL_HEIGHT = 1000.0f;
    private ScreenViewport mScreenViewport;
    private LayerViewport mLayerViewport;

    Context context;

    //Background image for this screen
    private GameObject mSplashBG;
    //Team logo (displayed as a button)
    private PushButton mLogo;

    /*
     * Create a splash screen
     *
     * @param mGame instance to which this game screen belongs
    */
    public SplashScreen(Game mGame) {
        super("SplashScreen", mGame);
        context = mGame.getContext();

        mScreenViewport = new ScreenViewport(0, 0, mGame.getScreenWidth(),
                mGame.getScreenHeight());

        if (mScreenViewport.width > mScreenViewport.height)
            mLayerViewport = new LayerViewport(240.0f, 240.0f
                    * mScreenViewport.height / mScreenViewport.width, 240,
                    240.0f * mScreenViewport.height / mScreenViewport.width);
        else
            mLayerViewport = new LayerViewport(240.0f * mScreenViewport.height
                    / mScreenViewport.width, 240.0f, 240.0f
                    * mScreenViewport.height / mScreenViewport.width, 240);

        //Set up asset store.
        AssetStore assetManager = mGame.getAssetManager();
        //Load in background image
        assetManager.loadAndAddBitmap("SplashBackground", "img/SplashScreen.png");
        //Load in logo
        assetManager.loadAndAddBitmap("Logo", "img/blackbox_logo.png");

        //Background image variables defined.
        mSplashBG = new GameObject(LEVEL_WIDTH / 2.0f,
                LEVEL_HEIGHT / 2.0f, LEVEL_WIDTH, LEVEL_HEIGHT, getGame().getAssetManager().getBitmap("SplashBackground"), this);

        //Define the spacing that will be used to position the buttons
        int spacingX = mGame.getScreenWidth() / 5;
        int spacingY = mGame.getScreenHeight() / 3;

        //Logo "button" is created and its variables defined
        mLogo = new PushButton(spacingX * 2.5f, spacingY * 1.5f, spacingX*2.1f, spacingY, "Logo", this);
    }

    /**
     * Update the splash screen
     *
     * @param elapsedTime Elapsed time information
     */
    @Override
    public void update(ElapsedTime elapsedTime) {
        // Process any touch events occurring since the update
        Input input = mGame.getInput();

        List<TouchEvent> touchEvents = input.getTouchEvents();
        if (touchEvents.size() > 0) {
            // Just check the first touch event that occurred in the frame.
            // It means pressing the screen with several fingers may not
            // trigger a 'button', but, hey, it's an exceedingly basic menu.
            TouchEvent touchEvent = touchEvents.get(0);
            //If the user touches anywhere on the screen once, they are taken to the menu screen
            changeToScreen(new MenuScreen(mGame));
            Log.d("Splash Screen", "Touch Triggered");
        }
    }

    /**
     * Remove this screen and return to the main menu screen
     *
     * @param screen game screen to become active
     */
    private void changeToScreen(GameScreen screen) {
        mGame.getScreenManager().removeScreen(this.getName());
        mGame.getScreenManager().addScreen(screen);
        mGame.getScreenManager().setAsCurrentScreen(screen.getName());
    }

    /**
     * Draw the splash screen
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        //Clear the screen
        graphics2D.clear(Color.rgb(25, 25, 112));
        graphics2D.clipRect(mScreenViewport.toRect());

        //Background image and logo "button" drawn to screen
        mSplashBG.draw(elapsedTime, graphics2D, mLayerViewport, mScreenViewport);
        mLogo.draw(elapsedTime,graphics2D,null,null);
    }
}


