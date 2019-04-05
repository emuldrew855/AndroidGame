package uk.ac.qub.eeecs.game;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.R;

/**
 * Sample demo game that is create within the MainActivity class
 *
 * @version 1.0
 */
public class DemoGame extends Game {

    // Test commit with new SSD

    /**
     * Create a new demo game
     */
    public DemoGame() {
        super();
    }

    /*
     * (non-Javadoc)
     *
     * @see uk.ac.qub.eeecs.gage.Game#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Go with a default 30 UPS/FPS
        setTargetFramesPerSecond(30);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Call the Game's onCreateView to get the view to be returned.
        View view = super.onCreateView(inflater, container, savedInstanceState);

        // Create and add a stub game screen to the screen manager. We don't
        // want to do this within the onCreate method as the menu screen
        // will layout the buttons based on the size of the view.


        /*
        Created by Grace on 13/11/2017.
        Sprint 2- User story 18. Load the splash screen when the app laods.
         */
        SplashScreen stubMenuScreen = new SplashScreen(this);


        mScreenManager.addScreen(stubMenuScreen);

        return view;
    }

    @Override
    public boolean onBackPressed() {
        // If we are already at the menu screen then exit
        if (mScreenManager.getCurrentScreen().getName().equals("MenuScreen"))
            return false;

        // Go back to the menu screen
        getScreenManager().removeScreen(mScreenManager.getCurrentScreen().getName());
        MenuScreen menuScreen = new MenuScreen(this);
        getScreenManager().addScreen(menuScreen);

        /*User story 26
        By Grace 20/10/17
        The background music is stopped when the user hits back

        Used in Sprint 4, User Story 11*/
        mScreenManager.getCurrentScreen().getGame().getAssetManager().getMusic("Background").stop();
        return true;
    }
}