package uk.ac.qub.eeecs.game.cardDemo.Containers;

import java.util.ArrayList;

import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.cardDemo.Cards.Card;

/**
 * Created by 40178070(Brandon) on 15/01/2018.
 */

public class GraveContainer extends Container {

    //Constructor
    public GraveContainer(float x, float y, float width, float height, int maxNoOfCards, GameScreen gameScreen) {
        super(x, y, width, height, maxNoOfCards, gameScreen);
    }

    //Adds a card to the graveyard container list of cards
    public void addCard(ArrayList<Card> currentCards, int posOfCard) {
        addCardGraveyard(currentCards.get(posOfCard), posOfCard);
    }

    //Adds a card to the graveyard container (not finished)
    private void addCardGraveyard(Card card, int posOfCard) {
       
    }
}
