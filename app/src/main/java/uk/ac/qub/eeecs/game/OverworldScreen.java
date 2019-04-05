package uk.ac.qub.eeecs.game;

import android.graphics.Color;

import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetStore;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.util.GraphicsHelper;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.game.cardDemo.CardDemoScreen;


/**
 * An exceedingly basic menu screen with a couple of touch buttons
 *
 * @version 1.0
 */
public class OverworldScreen extends GameScreen {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////
    private final float LEVEL_WIDTH = 480.0f;
    private final float LEVEL_HEIGHT = 270.0f;
    /**
     * Define the buttons for playing the 'games'
     */
    private LayerViewport mLayerViewport;
    private ScreenViewport mScreenViewport;
    private PushButton mLocationButton;
    private PushButton mLocationButton1;
    private PushButton mLocationButton2;
    private PushButton mLocationButton3;
    private PushButton mLocationButton4;
    private GameObject mOverworldMap;

    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Create a simple menu screen
     *
     * @param game Game to which this screen belongs
     */
    public OverworldScreen(Game game) {
        super("OverworldScreen", game);

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

        // Load in the bitmaps used on the main menu screen
        AssetStore assetManager = mGame.getAssetManager();


        assetManager.loadAndAddBitmap("LocationArrow", "img/LocationArrow.png");
        assetManager.loadAndAddBitmap("OverworldMap", "img/OverworldMap.png");

        // Define the spacing that will be used to position the buttons
        int spacingX = game.getScreenWidth() / 5;
        int spacingY = game.getScreenHeight() / 3;

        mOverworldMap = new GameObject(LEVEL_WIDTH / 2.0f,
                LEVEL_HEIGHT / 2.0f, LEVEL_WIDTH, LEVEL_HEIGHT, getGame()
                .getAssetManager().getBitmap("OverworldMap"), this);

        // Create the trigger buttons
        mLocationButton = new PushButton(
                spacingX * 1.0f, spacingY * 1.5f, spacingX*.16f, spacingY*0.1f, "LocationArrow", this);
        mLocationButton1 = new PushButton(
                spacingX * 2.0f, spacingY * 1.5f, spacingX*.16f, spacingY*0.1f, "LocationArrow", this);
        mLocationButton2 = new PushButton(
                spacingX * 3.0f, spacingY * 1.5f, spacingX*.16f, spacingY*0.1f, "LocationArrow", this);
        mLocationButton3 = new PushButton(
                spacingX * 4.0f, spacingY * 1.5f, spacingX*.16f, spacingY*0.1f, "LocationArrow", this);
        mLocationButton4 = new PushButton(
                spacingX * 5.0f, spacingY * 1.5f, spacingX*.16f, spacingY*0.1f, "LocationArrow", this);


    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Update the menu screen
     *
     * @param elapsedTime Elapsed time information
     */
    @Override
    public void update(ElapsedTime elapsedTime) {

        // Process any touch events occurring since the update
        Input input = mGame.getInput();

        List<TouchEvent> touchEvents = input.getTouchEvents();
        if (touchEvents.size() > 0) {





            // Update each button and transition if needed
            mLocationButton.update(elapsedTime);
            mLocationButton1.update(elapsedTime);
            mLocationButton2.update(elapsedTime);
            mLocationButton3.update(elapsedTime);
            mLocationButton4.update(elapsedTime);

            if (mLocationButton.isPushTriggered())
                mGame.getScreenManager().setAsCurrentScreen("cardDemoScreen");

            else if (mLocationButton1.isPushTriggered())
                mGame.getScreenManager().setAsCurrentScreen("cardDemoScreen");
            else if (mLocationButton2.isPushTriggered())
                mGame.getScreenManager().setAsCurrentScreen("cardDemoScreen");
            else if (mLocationButton3.isPushTriggered())
                mGame.getScreenManager().setAsCurrentScreen("cardDemoScreen");
            else if (mLocationButton4.isPushTriggered())
                mGame.getScreenManager().setAsCurrentScreen("cardDemoScreen");
        }

    }



    /**
     * Remove the current game screen and then change to the specified screen
     *
     * @param screen game screen to become active
     */
    private void changeToScreen(GameScreen screen) {
        mGame.getScreenManager().removeScreen(this.getName());
        mGame.getScreenManager().addScreen(screen);
    }

    /**
     * Draw the menu screen
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {


        // Clear the screen and draw the buttons
        graphics2D.clear(Color.WHITE);
        mOverworldMap.draw(elapsedTime, graphics2D, mLayerViewport, mScreenViewport);
        mLocationButton.draw(elapsedTime, graphics2D, null, null);
        mLocationButton1.draw(elapsedTime, graphics2D, null, null);
        mLocationButton2.draw(elapsedTime, graphics2D, null, null);
        mLocationButton3.draw(elapsedTime, graphics2D, null, null);
        mLocationButton4.draw(elapsedTime, graphics2D, null, null);


    }
}
