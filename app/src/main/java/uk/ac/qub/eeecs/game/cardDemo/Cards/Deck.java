package uk.ac.qub.eeecs.game.cardDemo.Cards;

import java.util.ArrayList;

import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.Sprite;

/**
 * Created by Edward Muldrew on 18/01/2018 ~ Sprint 4 User Story 1 - Add Deck Class
 * This class represents all the cards at which the user has at any point during the game
 */

public class Deck extends Sprite {
    // Instance Variables
    private int deckID;
    private String deckName;
    private ArrayList<Card> deckOfCards = new ArrayList<Card>();

    //Constructors
    public Deck(int deckID, String deckName, ArrayList<Card> deckOfCards, GameScreen gameScreen) {
        super(gameScreen);
        this.deckID = deckID;
        this.deckName = deckName;
        this.deckOfCards = new ArrayList<Card>();
    }


    // Getters
    public int getDeckID() {
        return deckID;
    }

    public String getDeckName() {
        return deckName;
    }

    public ArrayList<Card> getDeckOfCards() {
        return deckOfCards;
    }

    // Stetters
    public void setDeckID(int deckID) {
        this.deckID = deckID;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public void setDeckOfCards(ArrayList<Card> deckOfCards) {
        this.deckOfCards = deckOfCards;
    }

    // Methods
    public void addCardToDeck(Card addCard) {
        this.deckOfCards.add(addCard);
    }

    public void removeCardFromDeck(Hand hand) {
        for (int j = 0; j < deckOfCards.size() - 1; j++) {
            hand.getCardsInHand().add(getDeckOfCards().get(j));
            deckOfCards.remove(getDeckOfCards().get(j));
            j = deckOfCards.size() - 1;
        }
    }
}