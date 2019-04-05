package uk.ac.qub.eeecs.game.cardDemo.Containers;

import android.graphics.Bitmap;

import uk.ac.qub.eeecs.gage.world.GameScreen;

/**
 * Created by 40178070(Brandon) on 15/01/2018.
 */

public class HeroContainer extends Container {

    //Constructor
    public HeroContainer(float x, float y, float width, float height, int maxNoOfCards, GameScreen gameScreen) {
        super(x, y, width, height, maxNoOfCards, gameScreen);
        setMaxNoOfCards(1);


    }
}
