package uk.ac.qub.eeecs.game.cardDemo;

import android.graphics.Color;
import android.graphics.Paint;

import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.gage.world.Sprite;
import uk.ac.qub.eeecs.game.cardDemo.Cards.Hand;

/**
 * Created by Edward Muldrew on 01/02/2018.
 * This class is used to set and update the score for a round or game for our app
 */

public class PlayerScore  {
    //Instance Variables
    int score;
    Hand.UserType userType;

    // Constructor
    public PlayerScore(int score, Hand.UserType userType) {
        this.score = score;
        this.userType = userType;
    }


    // Getters
    public int getScore() {
        return score;
    }

    public Hand.UserType getUserType() {
        return userType;
    }

    // Setters
    public void setUserType(Hand.UserType userType) {
        this.userType = userType;
    }

    public void setScore(int score) {
        this.score = score;
    }

    //Methods

    /**
     * This method takes an integer value and appends it to the current score
     *
     * @param updatedScore
     */
    public void updateScore(int updatedScore) {
        this.score += updatedScore;
    }

}
