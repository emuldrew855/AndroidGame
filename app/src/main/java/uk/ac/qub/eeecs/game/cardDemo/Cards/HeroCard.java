package uk.ac.qub.eeecs.game.cardDemo.Cards;

import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.world.GameScreen;

/**
 * Created by Edward Muldrew on 27/11/2017 ~ User Story 12: Develop & Implement Inheritance Hierarchy
 * This card inherits all methods from strength card. The only difference being the hero card can be placed on any row on the board.
 */

public class HeroCard extends StrengthCard {
    // Constructor
    public HeroCard(int cardID, String cardName, Subject cardSubject, Rarity cardRarity, String cardDescription, String bitmapName, GameScreen gameScreen, Row cardRow, int cardStrength) {
        super(cardID, cardName, cardSubject, cardRarity, cardDescription, bitmapName, gameScreen, cardRow, cardStrength);
    }
}

