package uk.ac.qub.eeecs.game.cardDemo.Options;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetStore;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.audio.Music;
import uk.ac.qub.eeecs.gage.engine.audio.Sound;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.game.MenuScreen;
import uk.ac.qub.eeecs.gage.util.ToastMessages;

/*Created by Grace 40172213
 *Reference/s: Code for playing background music/button clicks has been adapted from Unimon54 & code on stack overflow
 *Code for toast messages has been adapted from Unimon54
 *Worked on slider element with another friend in the class & using stack overflow
*/

public class OptionsScreen extends GameScreen {
    private final float LEVEL_WIDTH = 1000.0f;
    private final float LEVEL_HEIGHT = 1000.0f;
    private final int MIN_BRIGHTNESS = 0;
    private final int MAX_BRIGHTNESS = 100;
    private ScreenViewport mScreenViewport;
    private LayerViewport mLayerViewport;

    //Variables to play the background music & button clicks
    public static boolean soundEnabled = true;
    private boolean continuePlaying;
    private Sound buttonClick;

    //private MenuScreen backingMusic;
    //private MenuScreen backingMusic = new MenuScreen(mGame);

    Context context;

    //Background image for this screen
    private GameObject mOptionsBG;
    //Text saying "Options" is saved as a button to be displayed as a title for this screen
    private PushButton optionsText;
    /*Slider for the brightness option to be adjusted by the user is initiated.
    *Text saying "Brightness" also saved as a button.
    */
    private Slider brightnessSlider;
    private PushButton brightnessText;
    /*Buttons for the music to be turned on and off by the user are initiated.
    *Text saying "Music" also saved as a button.
     */
    private PushButton mMusicOn;
    private PushButton mMusicOff;
    private PushButton musicText;
    //Back button to return to main menu
    private PushButton mBack;

    //Toast messages created
    private ToastMessages mToastMessages;

    public OptionsScreen(Game mGame, String name) {
        super("OptionsScreen", mGame);
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

        //Set up variable for toast messages
        mToastMessages = new ToastMessages(getGame().getActivity().getApplicationContext());
        //Background music is set to continue playing
        continuePlaying= true;

        // backingMusic = new MenuScreen(MenuScreen,mGame);
        //backingMusic = new MenuScreen(mGame);

        //Set up asset store
        AssetStore assetManager = mGame.getAssetManager();
        //Load in background image
        assetManager.loadAndAddBitmap("OptionsBackground", "img/SplashScreen.png");
        //Load in back button image
        assetManager.loadAndAddBitmap("Back", "img/buttons/button_back.png");
        //Load in text stating "Options" for the screen's title
        assetManager.loadAndAddBitmap("optionsText", "img/text/text_options.png");
        //Load in text stating "Brightness" for beside brightness adjusting slider
        assetManager.loadAndAddBitmap("brightnessText", "img/text/text_brightness.png");
        //Load in the bitmap for the slider when its empty
        assetManager.loadAndAddBitmap("emptyProgressBar", "img/progressBar/progressBar_empty.png");
        //Load in the bitmap for the slider when its full
        assetManager.loadAndAddBitmap("fullProgressBar", "img/progressBar/progressBar_full.png");
        //Load in button for turning music on
        assetManager.loadAndAddBitmap("musicOff", "img/buttons/button_music_off.png");
        //Load in button for turning music off
        assetManager.loadAndAddBitmap("musicOn", "img/buttons/button_music_on.png");
        //Load in text stating "Music" for beside music on and off buttons
        assetManager.loadAndAddBitmap("musicText", "img/text/text_music.png");

        // Load in the sounds used on the menu screen
        assetManager.loadAndAddSound("buttonClick", "music/buttonclick.mp3");
        buttonClick = mGame.getAssetManager().getSound("buttonClick");

        //Background image variables defined
        mOptionsBG = new GameObject(LEVEL_WIDTH / 2.0f,
                LEVEL_HEIGHT / 2.0f, LEVEL_WIDTH, LEVEL_HEIGHT, getGame().getAssetManager().getBitmap("OptionsBackground"), this);

        //Define the spacing that will be used to position the buttons
        int spacingX = mGame.getScreenWidth() / 5;
        int spacingY = mGame.getScreenHeight() / 3;

        //Title text stating "Options" is defined
        optionsText = new PushButton(spacingX * 2.5f, spacingY * 0.6f, spacingX * 1.95f, spacingY*0.65f, "optionsText", this);

        // Set up text painter with styles
        Paint sliderPainter = new Paint();
        sliderPainter.setTextSize(60);
        sliderPainter.setColor(Color.BLACK);
        sliderPainter.setTextAlign(Paint.Align.CENTER);
        //Slider for adjusting brightness is created and its params defined, as well as text stating "Brightness"
        brightnessSlider = new Slider(
                MIN_BRIGHTNESS,
                MAX_BRIGHTNESS,
                MAX_BRIGHTNESS / 2,
                sliderPainter,
                spacingX * 1.5f,
                spacingY * 1.35f,
                800,
                50,
                "emptyProgressBar",
                "fullProgressBar",
                this,
                false);
        brightnessText = new PushButton(spacingX * 3.6f, spacingY * 1.35f, spacingX * 1.75f, spacingY * 0.5f, "brightnessText", this);

        //Music on and off buttons are created and their variables defined, as well as text stating "Music"
        mMusicOn = new PushButton(spacingX * 1.25f, spacingY * 2.1f, spacingX * 0.65f, spacingX * 0.4f, "musicOn", this);
        mMusicOff = new PushButton(spacingX * 4.0f, spacingY * 2.1f, spacingX * 0.65f, spacingX * 0.4f, "musicOff", this);
        musicText = new PushButton(spacingX * 2.6f, spacingY * 2.1f, spacingX * 1.5f, spacingY * 0.3f, "musicText", this);

        //Back button to return to main menu is created and its variables defined
        mBack = new PushButton(spacingX * 0.5f, spacingY * 2.5f, spacingX * 0.75f, spacingY * 0.75f, "Back", this);
    }

    //Methods used for background music (See Music class under Gage>Engine>Audio)

    /*@Override
    public void pause() {
        super.pause();
        if(!continuePlaying)
            backingMusic.pauseMusic();
    }

    @Override
    public void resume() {
        super.resume();
        if(continuePlaying){
            backingMusic.playMusic();
        }
    }*/

    //Methods used for button clicks (See Sound class under Gage>Engine>Audio)

    public void buttonClickSound(Sound sound, String name) {
        AssetStore assetManager = mGame.getAssetManager();
        assetManager.loadAndAddSound(name, "music/" + name + ".mp3");
        sound = mGame.getAssetManager().getSound(name);
        sound.play();
        //sound.onCompletion();
    }

    public void backingClickSound(){
        buttonClick.play();
    }

    /**
     * Draw the options screen
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        //Clear the screen
        graphics2D.clear(Color.WHITE);
        graphics2D.clipRect(mScreenViewport.toRect());

        //Background image, back button, title, brightness & music text, slider and music buttons all drawn to screen
        mOptionsBG.draw(elapsedTime, graphics2D, mLayerViewport, mScreenViewport);
        mBack.draw(elapsedTime, graphics2D, null, null);
        optionsText.draw(elapsedTime, graphics2D, null, null);
        brightnessText.draw(elapsedTime, graphics2D, null, null);
        musicText.draw(elapsedTime, graphics2D, null, null);
        brightnessSlider.draw(elapsedTime, graphics2D, null, null);
        mMusicOn.draw(elapsedTime, graphics2D, null, null);
        mMusicOff.draw(elapsedTime, graphics2D, null, null);
    }

    /**
     * Update the options screen
     *
     * @param elapsedTime Elapsed time information
     */
    @Override
    public void update(ElapsedTime elapsedTime) {
       /* if(backingMusic.isPlaySong())
            backingMusic.playBacking();
*/
        // Process any touch events occurring since the update
        Input input = mGame.getInput();

        List<TouchEvent> touchEvents = input.getTouchEvents();
        mGame.getInput().getKeyEvents();
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            // Just check the first touch event that occurred in the frame.
            // It means pressing the screen with several fingers may not
            // trigger a 'button', but, hey, it's an exceedingly basic menu.
            TouchEvent touchEvent = touchEvents.get(i);

            // Update the back, music on and music off buttons
            mBack.update(elapsedTime);
            mMusicOff.update(elapsedTime);
            mMusicOn.update(elapsedTime);

            //If the back button is clicked
            if (mBack.isPushTriggered()) {
                //A clicking sound is played
                backingClickSound();
                buttonClickSound(buttonClick, "buttonclick");
                //A toast message is displayed
                mToastMessages.showMessage("Main Menu");
                //Transition to the MenuScreen
                changeToScreen(new MenuScreen(mGame));
            }
            //If the music off button is clicked & soundEnabled == true
            if (mMusicOff.isPushTriggered() && soundEnabled) {
                //A clicking sound is played
                backingClickSound();
                buttonClickSound(buttonClick, "buttonclick");
                //A toast message is displayed
                mToastMessages.showMessage("Music Off");
                //Set the variables below to false
                soundEnabled = false;
                continuePlaying = false;
                //backingMusic.pauseBacking();
            }
            //If the music off button is clicked & soundEnabled == false
            if (mMusicOn.isPushTriggered() && !soundEnabled) {
                //A clicking sound is played
                backingClickSound();
                buttonClickSound(buttonClick, "buttonclick");
                //A toast message is displayed
                mToastMessages.showMessage("Music On");
                //Set the variables below to true
                soundEnabled = true;
                continuePlaying = true;
                //backingMusic.playBacking();
            }
        }
    }

    /**
     * Remove this screen and then change to the specified screen
     *
     * @param screen game screen to become active
     */
    private void changeToScreen(GameScreen screen) {
        mGame.getScreenManager().removeScreen(this.getName());
        mGame.getScreenManager().addScreen(screen);
        mGame.getScreenManager().setAsCurrentScreen(screen.getName());
    }
}
