package uk.ac.qub.eeecs.game.cardDemo.Containers;

import android.graphics.Bitmap;

import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.cardDemo.Cards.Card;
import static uk.ac.qub.eeecs.game.cardDemo.Containers.ContainerType.DECK;

/**
 * Created by 40178070(Brandon) on 15/01/2018.
 */

public class DeckContainer extends Container {

    //Count of cards in deck
    private int currentCount;

    //Constructor
    public DeckContainer(float x, float y, float width, float height, int maxNoOfCards, GameScreen gameScreen) {
        super(x, y, width, height, maxNoOfCards, gameScreen);
        currentCount = maxNoOfCards;
        setContainerType(DECK);
    }

    //Reduce card count by one
    public void decrementCardCount() {
        this.currentCount--;
    }

    @Override
    //Checks if the deck container is empty
    public boolean isContainerEmpty() {
        if (currentCount == 0) {
            return true;
        }
        return false;
    }

    @Override
    //Get the number of cards in the deck
    public int getNumOfCardsInContainer() {
        return currentCount;
    }
}
