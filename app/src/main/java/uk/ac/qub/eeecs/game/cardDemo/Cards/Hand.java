package uk.ac.qub.eeecs.game.cardDemo.Cards;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.gage.world.Sprite;
import uk.ac.qub.eeecs.game.Round;
import uk.ac.qub.eeecs.game.cardDemo.CardDemoScreen;
import uk.ac.qub.eeecs.game.cardDemo.PlayerScore;
import uk.ac.qub.eeecs.game.cardDemo.Turn;
import uk.ac.qub.eeecs.game.cardDemo.Utilities;

/**
 * Created by Edward Muldrew on 18/01/2018. ~ Sprint 4 User Story 3
 * This class represents the selection of cards made available
 * for the User to select at any point during a round
 */

public class Hand extends Sprite {
    // Instance Variables
    private ArrayList<Card> cardsInHand;
    private String handName;
    UserType typeOfUser;

    // Class Memebers
    int choosenCardID = -1;
    int previousUserScore = 0;
    int previousAIScore = 0;
    int screenSize = 270;
    int maxCardSelectionTouchInputHeight = 50;
    boolean cardSelected = false;
    Utilities helperUtilities;

    // Enum to identify which object belongs to who the AI or the User
    public enum UserType {
        USER, AI
    }

    public Hand(GameScreen gameScreen, Game game) {
        super(gameScreen);
    }

    /*
     *
     * @param handName - text describing the hand
     * @param typeOfHand - enum identifying whos hand is which
     * @param cardsInHand - an array list of all the cards in the current hand
     * @param gameScreen
     * @param game
     */
    public Hand(String handName, UserType typeOfUser, ArrayList<Card> cardsInHand, GameScreen gameScreen, Game game) {
        super(gameScreen);
        this.cardsInHand = new ArrayList<Card>();
        this.handName = handName;
        this.typeOfUser = typeOfUser;
        helperUtilities = new Utilities(gameScreen, game);
    }


    // Getters
    public ArrayList<Card> getCardsInHand() {
        return cardsInHand;
    }

    public String getHandName() {
        return handName;
    }

    public UserType getTypeOfHand() {
        return typeOfUser;
    }

    // Setters
    public void setCardsInHand(ArrayList<Card> cardsInHand) {
        this.cardsInHand = cardsInHand;
    }

    public void setHandName(String handName) {
        this.handName = handName;
    }

    public void setTypeOfHand(UserType typeOfHand) {
        this.typeOfUser = typeOfHand;
    }

    // Methods
    public void addCardToHand(Card addCard) {
        this.cardsInHand.add(addCard);
    }

    /**
     * This method "pops" a card up when a user clicks on a specified card. It acts as a "selection" process.
     */
    public void popUpCard() {
        Input input = mGameScreen.getGame().getInput();
        List<TouchEvent> touchEvents = input.getTouchEvents();
        for (TouchEvent te : touchEvents) {
            float x = te.x / 4;
            float y = screenSize - (te.y / 4);
            for (int i = 0; i < getCardsInHand().size(); i++) {
                if (getCardsInHand().get(i).getBound().contains(x, y) && cardSelected == false && y < maxCardSelectionTouchInputHeight) {
                    getCardsInHand().get(i).setPosition(getCardsInHand().get(i).getPositionX(), 30);
                    choosenCardID = i;
                    cardSelected = true;
                }
            }
        }

    }

    /**
     * This method acts as an undo function so that a user can choose to select another card to place if they choose to. The original selected &
     * placed card will return to its original position on the board
     *
     * @param round       - used as a parameter so that the cards effects are removed from the active scores, etc
     * @param activeCards - used as a parameter so the placed card is removed from the active cards on the screen
     */
    public void popDownCard(Round round, ArrayList<Card> activeCards) {
        int total = 0;
        if (choosenCardID == -1) {
        } else {
            getCardsInHand().get(choosenCardID).setPosition(getCardsInHand().get(choosenCardID).getStarterX(), getCardsInHand().get(choosenCardID).getStartY());
            round.getUserScore().setScore(previousUserScore);
            round.getAiScore().setScore(previousAIScore);
            if (getCardsInHand().get(choosenCardID).getClass() == SpellCard.class &&
                    ((SpellCard) getCardsInHand().get(choosenCardID)).getCardAbility() == SpellCard.Ability.INCREASE) {
                for (Card selectedCard : activeCards) {
                    if (selectedCard.getClass() == SpellCard.class) {

                    } else {
                        ((StrengthCard) selectedCard).setCardStrength(((StrengthCard) selectedCard).getCardStrength() / 2);
                        total += ((StrengthCard) selectedCard).getCardStrength();
                        round.getUserScore().setScore(total);
                    }

                }
            }
            getCardsInHand().get(choosenCardID).setCardPlaced(false);
            cardSelected = false;
            choosenCardID = -1;
        }
    }


    /**
     * This method conducts the placement of a card. Contains function calls to validating methods as well as setting the cards position
     * on the board, adjusting the score and removing a card from the hand to an active card.
     *
     * @param round - parameter is used in updating the current score
     * @param turnRecordIndex - the current turn record of the user
     * @param elapsedTime
     * @param activeCards - used to move a card from the users hand to the active cards
     */
    public void moveCard(Round round, int turnRecordIndex, ElapsedTime elapsedTime, ArrayList<Card> activeCards) {
        Input input = mGameScreen.getGame().getInput();
        List<TouchEvent> touchEvents = input.getTouchEvents();
        for (TouchEvent te : touchEvents) {
            float x = te.x / 4;
            float y = screenSize - (te.y / 4);
            // if there has been a choosen card & it is not currently placed & it is touch position is on the board of play
            if (choosenCardID != -1 && getCardsInHand().get(choosenCardID).isCardPlaced() == false && y > 50) {
                Card cardToPlace = getCardsInHand().get(choosenCardID);
                cardToPlace = helperUtilities.checkIfCardCollide(activeCards, x, y, getCardsInHand().get(choosenCardID));
                getCardsInHand().get(choosenCardID).setCardPlaced(true);
                previousUserScore = round.getUserScore().getScore();
                previousAIScore = round.getAiScore().getScore();
                round.getTurnRecord().getUserTurnRecord().put(turnRecordIndex, "true");
                cardSelected = false;
                // Apply card effects to active game
                if (getCardsInHand().get(choosenCardID).getClass() != SpellCard.class) {
                    round.getUserScore().updateScore(((StrengthCard) getCardsInHand().get(choosenCardID)).getCardStrength()); // update user score
                } else if (getCardsInHand().get(choosenCardID).getClass() == SpellCard.class) {
                    ((SpellCard) getCardsInHand().get(choosenCardID)).userSpellCard(elapsedTime, round, activeCards);
                }
            }
        }
    }

    /**
     * This method removes the the selected & placed card from the user hand to the active cards
     *
     * @param activeCards - active cards on board
     */
    public void removeCard(ArrayList<Card> activeCards) {
        if (choosenCardID != -1) {
            activeCards.add(getCardsInHand().get(choosenCardID));
            getCardsInHand().remove(getCardsInHand().get(choosenCardID));
            choosenCardID = -1;
        }
}

    /**
     * This method allows the user to select & move a card
     *
     * @param elapsedTime
     * @param round
     * @param turnRecordIndex
     * @param activeCards
     */
    public void update(ElapsedTime elapsedTime, Round round, int turnRecordIndex, ArrayList<Card> activeCards) {
        super.update(elapsedTime);
        if (getCardsInHand().size() == 0) { // if the user has no cards left to play, set their turn to not played
            round.getTurnRecord().getUserTurnRecord().put(turnRecordIndex, "false");
        } else {
            if (!cardSelected) { // if user has not selected a card allow them to choose a card
                popUpCard();
            } else if (cardSelected) { // if as card has been selected allow a user to move the card
                moveCard(round, turnRecordIndex, elapsedTime, activeCards);
            }
        }
    }
}
