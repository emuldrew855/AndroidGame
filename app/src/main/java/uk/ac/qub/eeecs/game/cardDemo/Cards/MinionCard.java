package uk.ac.qub.eeecs.game.cardDemo.Cards;

import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.world.GameScreen;

/**
 * Created by Edward Muldrew on 27/11/2017 ~ User Story 12: Develop & Implement Inheritance Hierarchy
 * This card inherits all methods from strength card
 */

public class MinionCard extends StrengthCard {
    // Constructor

    public MinionCard(int cardID, String cardName, Subject cardSubject, Rarity cardRarity, String cardDescription, String bitmapName, GameScreen gameScreen, Row cardRow, int cardStrength) {
        super(cardID, cardName, cardSubject, cardRarity, cardDescription, bitmapName, gameScreen, cardRow, cardStrength);
    }

    public MinionCard(StrengthCard copy, String bitmapName, GameScreen gameScreen) {
        super(copy, bitmapName, gameScreen);
    }
}
