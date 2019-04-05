package uk.ac.qub.eeecs.game.cardDemo.Cards;

import java.util.ArrayList;

import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.Round;

/**
 * Created by Edward Muldrew on 27/11/2017. ~ User Story 12: Develop & Implement Inheritance Hierarchy
 * The SpellCard extends the card class and has the cardAbility instance variable with getters and setters as well as the effect on the board method
 */

public class SpellCard extends Card  {
    // Enums
    public enum Ability {
        DAMAGE, DOUBLE_POINTS, INCREASE
    }

    // Instance Variables
    private Ability cardAbility;
    // Constructor
    public SpellCard(int cardID, String cardName, Subject cardSubject, Rarity cardRarity, String cardDescription, String bitmapName, GameScreen gameScreen, Ability cardAbility) {
        super(cardID, cardName, cardSubject, cardRarity, cardDescription, bitmapName, gameScreen);
        this.cardAbility = cardAbility;
    }

    public SpellCard(SpellCard copy, String bitmapName, GameScreen gameScreen) {
        super(copy,bitmapName,gameScreen);
        this.cardAbility = copy.cardAbility;
    }



    public void undoIncreaseSpell() {
    }
    // Methods
    public void userSpellCard(ElapsedTime elapsedTime, Round round, ArrayList<Card> activeCards) {
        int total = 0;
        switch (this.cardAbility) {
            case INCREASE:
                for(Card selectedCard: activeCards) {
                    if(selectedCard.getClass() == MinionCard.class || selectedCard.getClass() == HeroCard.class) {
                        ((StrengthCard)selectedCard).setCardStrength(((StrengthCard) selectedCard).getCardStrength() * 2);
                        total += ((StrengthCard)selectedCard).getCardStrength();
                        round.getUserScore().setScore(total);
                    }
                }
                break;

            case DAMAGE:
                round.getAiScore().updateScore(-3);
                break;

            case DOUBLE_POINTS:
                round.getUserScore().updateScore(round.getUserScore().getScore());
                break;
            default: // something
                break;
        }
    }

    public void aiSpellCard(ElapsedTime elapsedTime, Round round, ArrayList<Card> activeCards) {
        int total = 0;
        switch (this.cardAbility) {
            case INCREASE:
                for(Card selectedCard: activeCards) {
                    if(selectedCard.getClass() == MinionCard.class || selectedCard.getClass() == HeroCard.class) {
                        ((StrengthCard)selectedCard).setCardStrength(((StrengthCard) selectedCard).getCardStrength() * 2);
                        total += ((StrengthCard)selectedCard).getCardStrength();
                        round.getAiScore().setScore(total);
                    }
                }
                break;

            case DAMAGE:
                round.getUserScore().updateScore(-3);
                break;

            case DOUBLE_POINTS:
                round.getAiScore().updateScore(round.getAiScore().getScore());
                break;
            default: // something
                break;
        }
    }


    // Getters
    public Ability getCardAbility() {
        return cardAbility;
    }

    // Setters
    public void setCardAbility(Ability cardAbility) {
        this.cardAbility = cardAbility;
    }
}