package uk.ac.qub.eeecs.game.cardDemo.Containers;

import android.graphics.Bitmap;

import java.util.ArrayList;

import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.cardDemo.Cards.Card;

/**
 * Created by 40178070(Brandon) on 15/01/2018.
 */

public class HandContainer extends Container {

    //Constructor
    public HandContainer(float x, float y, float width, float height, int maxNoOfCards, GameScreen gameScreen) {
        super(x, y, maxNoOfCards*width, height, maxNoOfCards,  gameScreen);
        setMaxNoOfCards(9);
        setContainerType(ContainerType.HAND);
    }

    //Adds a card to the hand container list of cards
    public void addCard(ArrayList<Card> currentCards, int posOfCard) {
        addHandContainer(currentCards.get(posOfCard), posOfCard);
    }

    //Adds a card to the hand container (not finished)
    private void addHandContainer(Card card, int posOfCard) {

    }
}
