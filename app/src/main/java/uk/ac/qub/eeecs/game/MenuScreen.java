package uk.ac.qub.eeecs.game;

import android.content.Context;
import android.graphics.Color;

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
import uk.ac.qub.eeecs.game.cardDemo.CardDemoScreen;
import uk.ac.qub.eeecs.game.cardDemo.Options.OptionsScreen;
import uk.ac.qub.eeecs.game.cardDemo.TutorialScreen;
import uk.ac.qub.eeecs.game.cardDemo.Utilities;
import uk.ac.qub.eeecs.gage.util.ToastMessages;

import static uk.ac.qub.eeecs.game.cardDemo.Options.OptionsScreen.soundEnabled;

/*Created by Grace 40172213
*Reference/s: Code for playing background music/button clicks has been adapted from Unimon54 & code on stack overflow
 *Code for toast messages has been adapted from Unimon54
 */

/**
 * An exceedingly basic menu screen with a couple of touch buttons
 *
 * @version 1.0
 */
public class MenuScreen extends GameScreen {
    private final float LEVEL_WIDTH = 1000.0f;
    private final float LEVEL_HEIGHT = 1000.0f;
    private ScreenViewport mScreenViewport;
    private LayerViewport mLayerViewport;

    public Utilities helperUtilities = new Utilities(this, mGame);

    // Create an instance of the Options Screen
    private GameScreen optionsScreen = new OptionsScreen(mGame, "optionsScreen");

    //Background image for this screen
    private GameObject mMenuBG;
    //Buttons to start the game, select options or view the tutorial are initiated
    private PushButton mStartButton;
    private PushButton mOptionsButton;
    private PushButton mTutorialButton;
    //Will be removed soon when overworld is accessed automatically from start button
   // private PushButton mOverworldButton;

    Context context;

    public Utilities getHelperUtilities() {
        return helperUtilities;
    }

    //Variables created to play the background music & button clicks
    private Music backing;
    private Sound buttonClick;
    private boolean playSong;
    private boolean keepPlaying;
    private boolean playing;

    //Toast messages created
    private ToastMessages mToastMessages;

    /**
     * Create a simple menu screen
     *
     * @param mGame Game to which this screen belongs
     */
    public MenuScreen(Game mGame) {
        super("MenuScreen", mGame);
        context = mGame.getContext();

        // Adding options screen initialised above
        mGame.getScreenManager().addScreen(optionsScreen);

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

        //Set up asset store
        AssetStore assetManager = mGame.getAssetManager();
        //Load in background image
        assetManager.loadAndAddBitmap("MenuBackground", "img/SplashScreen.png");
        //Load in start, options and tutorial button images
        assetManager.loadAndAddBitmap("StartButton", "img/buttons/button_start.png");
        assetManager.loadAndAddBitmap("OptionsButton", "img/buttons/button_options.png");
        assetManager.loadAndAddBitmap("TutorialButton", "img/buttons/button_tutorial.png");
        //Will be removed soon when overworld is accessed automatically from start button
        //assetManager.loadAndAddBitmap("LocationArrow", "img/LocationArrow.png");

        //Background image variables defined
        mMenuBG = new GameObject(LEVEL_WIDTH / 2.0f,
                LEVEL_HEIGHT / 2.0f, LEVEL_WIDTH, LEVEL_HEIGHT, getGame().getAssetManager().getBitmap("MenuBackground"), this);

        //Set up variable for toast messages
        mToastMessages = new ToastMessages(getGame().getActivity().getApplicationContext());
        //Set up variables for starting the background music
        playSong = true;
        keepPlaying = false;
        playing = false;

        //Load in the background music
        assetManager.loadAndAddMusic("backgroundMusic", "music/cardmusic.mp3");
        backing = mGame.getAssetManager().getMusic("backgroundMusic");

        // Load in the sounds used on the menu screen
        assetManager.loadAndAddSound("buttonClick", "music/buttonclick.mp3");
        buttonClick = mGame.getAssetManager().getSound("buttonClick");

        //Play background music (!backing.isPlaying() used so it doesn't overlap tracks if the user goes back to menu screen at any point)
        //if (soundEnabled == true && !backing.isPlaying()) {
       /* if (soundEnabled == true) {
            playMusic();
        } else {
            pauseMusic();
        }*/

        //  if (this.getGame().getAssetManager().getMusic("Background").isPlaying() == false) {
        //      this.getGame().getAssetManager().getMusic("Background").play();
        //      this.getGame().getAssetManager().getMusic("Background").setLopping(true);
        //  }

        //Define the spacing that will be used to position the buttons
        int spacingX = mGame.getScreenWidth() / 5;
        int spacingY = mGame.getScreenHeight() / 3;

        //Start, options and tutorial buttons are created and their variables defined
        mStartButton = new PushButton(
                spacingX * 2.5f, spacingY * 0.75f, spacingX * 1.6f, spacingY * 0.5f, "StartButton", this);
        mOptionsButton = new PushButton(
                spacingX * 2.5f, spacingY * 1.5f, spacingX * 1.6f, spacingY * 0.5f, "OptionsButton", this);
        mTutorialButton = new PushButton(
                spacingX * 2.5f, spacingY * 2.25f, spacingX * 1.6f, spacingY * 0.5f, "TutorialButton", this);

        //Will be removed soon when overworld is accessed automatically from start button
      //  mOverworldButton = new PushButton(
      //          spacingX * 0.5f, spacingY * 2.55f, spacingX * 0.5f, spacingY * 0.75f, "LocationArrow", this);
    }

    //Methods used for background music (See Music class under Gage>Engine>Audio)

    public boolean isPlaySong() {
        return playSong;
    }

    public void setPlaySong(boolean playSong) {
        this.playSong = playSong;
    }

    public void initialiseMusic(){
        if(!getKeepPlaying()){
            backing.play();
            backing.setLopping(true);
        }
    }

    public void playBacking(){
        if(getKeepPlaying())
            backing.unPause();
        setPlaySong(true);
    }

    public void pauseBacking(){
        setKeepPlaying(true);
        backing.pause();
        setPlaySong(false);
    }

    public void setKeepPlaying(boolean input){
        this.keepPlaying = input;
    }

    public boolean getKeepPlaying(){
        return keepPlaying;
    }
    /*
     * This method starts the background music
     * The music will repeat on a loop
     * Music settings can be altered in the settings screen
     */
    public void backingMusic() {
        backing.setLopping(true);
    }

    public void playMusic() {
        //playSong = true;
        backing.unPause();
    }

    public void pauseMusic() {
        //playSong = false;
        backing.pause();
    }

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
     * Update the menu screen
     *
     * @param elapsedTime Elapsed time information
     */
    @Override
    public void update(ElapsedTime elapsedTime) {

        //Process any touch events occurring since the update
        Input input = mGame.getInput();

        List<TouchEvent> touchEvents = input.getTouchEvents();
        if (touchEvents.size() > 0) {
            // Just check the first touch event that occurred in the frame.
            // It means pressing the screen with several fingers may not
            // trigger a 'button', but, hey, it's an exceedingly basic menu.
            TouchEvent touchEvent = touchEvents.get(0);

            //Update each button and transition if needed
            mStartButton.update(elapsedTime);
            mOptionsButton.update(elapsedTime);
            mTutorialButton.update(elapsedTime);

            //Will be removed soon when overworld is accessed automatically from start button
            //mOverworldButton.update(elapsedTime);

            //If the start button is clicked
            if (mStartButton.isPushTriggered()) {
                //A clicking sound is played
                backingClickSound();
                buttonClickSound(buttonClick, "buttonclick");
                //A toast message is displayed
                mToastMessages.showMessage("Start Game");
                //Transition to the cardDemoScreen
                changeToScreen(new CardDemoScreen(mGame, "cardDemoScreen"));
            }
            //If the options button is clicked
            else if (mOptionsButton.isPushTriggered()) {
                //A clicking sound is played
                backingClickSound();
                buttonClickSound(buttonClick, "buttonclick");
                //A toast message is displayed
                mToastMessages.showMessage("Options");
                //Transition to the OptionsScreen
                changeToScreen(new OptionsScreen(mGame, "optionsScreen"));
            }
            //If the tutorial button is clicked
            else if (mTutorialButton.isPushTriggered()) {
                //A clicking sound is played
                backingClickSound();
                buttonClickSound(buttonClick, "buttonclick");
                //A toast message is displayed
                mToastMessages.showMessage("Tutorial");
                //Transition to the TutorialScreen
                changeToScreen(new TutorialScreen(mGame));
            }

            //Will be removed soon when overworld is accessed automatically from start button
           /* else if (mOverworldButton.isPushTriggered()) {
                //A clicking sound is played
                backingClickSound();
                buttonClickSound(buttonClick, "buttonclick");
                //A toast message is displayed
                mToastMessages.showMessage("Overworld");
                //Transition to the OverworldScreen
                changeToScreen(new OverworldScreen(mGame));
            }*/
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
        mGame.getScreenManager().setAsCurrentScreen(screen.getName());
    }

    /**
     * Draw the menu screen
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        // Clear the screen
        graphics2D.clear(Color.WHITE);

        //Background image, start, options and tutorial buttons drawn to screen
        mMenuBG.draw(elapsedTime, graphics2D, mLayerViewport, mScreenViewport);
        mStartButton.draw(elapsedTime, graphics2D, null, null);
        mOptionsButton.draw(elapsedTime, graphics2D, null, null);
        mTutorialButton.draw(elapsedTime, graphics2D, null, null);
        //Will be removed soon when overworld is accessed automatically from start button
        //mOverworldButton.draw(elapsedTime, graphics2D, null, null);
    }
}
