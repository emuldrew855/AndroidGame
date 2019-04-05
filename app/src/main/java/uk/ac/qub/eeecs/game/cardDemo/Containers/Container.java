package uk.ac.qub.eeecs.game.cardDemo.Containers;

import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.cardDemo.Cards.Card;
import uk.ac.qub.eeecs.game.cardDemo.Cards.MinionCard;

/**
 * Created by 40178070(Brandon) on 10/12/2017.
 * NOTE: This class and all classes derived from it are based on the classes within the Unimon54 project
 * NOTE: Containers were not implemented into the final project, but I(Brandon) had spent too much time on them to leave them out
 */

public class Container extends GameObject {
    /**The area of the container*/
    private Rect containerRectangle;

    /**The paint for the container*/
    private Paint containerPaint;

    /**The max number of cards in a container*/
    private int maxNoOfCards;

    /*The type of the container*/
    private ContainerType containerType;

    /**Contains the actual cards within the container**/
    private ArrayList<Card> cardsInContainer = new ArrayList<>();

    /*The spacing between the cards within the container*/
    private float spacerX =  cardDimensions.x;
    private float spacerY =  cardDimensions.y;

    //Constructor
    public Container(float x, float y, float width, float height, int maxNoOfCards, GameScreen gameScreen) {
        super(x, y, width, height, gameScreen);
        containerType = ContainerType.DEFAULT;
        this.containerRectangle = containerRectangle;
        this.containerPaint = containerPaint;
        this.maxNoOfCards = maxNoOfCards;
    }

    //Getters
    public Paint getContainerPaint() {
        return containerPaint;
    }

    public ContainerType getContainerType() {
        return containerType;
    }

    public int getMaxNoOfCards() {
        return maxNoOfCards;
    }

    public int getNumOfCardsInContainer(){return cardsInContainer.size();}

    public ArrayList<Card> getCardsInContainer() {
        return cardsInContainer;
    }

    public Rect getContainerRectangle() {return containerRectangle;}

    public Card getCardAt(int pos) {
        ArrayList<Card> cardList = getCardsInContainer();
        for (int i = 0; i < cardList.size(); i++) {
            if (i == pos) {
                return cardList.get(i);
            }
        }
        return null;
    }



    //Setters
    public void setMaxNoOfCards(int maxNoOfCards) {this.maxNoOfCards = maxNoOfCards;}

    public void setCardsInContainer(ArrayList<Card> cardsInContainer) {this.cardsInContainer = cardsInContainer;}

    public void setContainerPaint(Paint containerPaint) {
        this.containerPaint = containerPaint;
    }

    public void setContainerRectangle(Rect containerRectangle) {this.containerRectangle = containerRectangle;}

    public void setContainerType(ContainerType containerType) {this.containerType = containerType;}

    //Update method
    public void update(Card card, int posOfCard){
       // card.setContainerType(getContainerType());
        update(card, cardsInContainer.size());
      //  card.setDoneMovement(false);
        this.cardsInContainer.add(posOfCard, card);
    }

    //Alternative update armour
    public void update(ArrayList<Card> activeCards){
        ArrayList<Card> temp = new ArrayList<>();

        for(int i=0;i<cardsInContainer.size();i++){
            if(isDetected(activeCards.get(i))){
                temp.add(cardsInContainer.get(i));
            }
        }
        cardsInContainer.clear();

    }

    //Checks if the container is empty
    public boolean isContainerEmpty() {
        if(cardsInContainer.size() == 0) {
            return true;
        }
        return false;
    }

    //Checks if the container is full
    public boolean isContainerFull() {
        if(cardsInContainer.size() == maxNoOfCards) {
            return true;
        }
        return false;
    }

    //Checks if the container has any space available
    public boolean isContainerFree() {
        if(cardsInContainer.size() < maxNoOfCards) {
            return true;
        }
        return false;
    }

    //Checks for the presence of a card
    public boolean isDetected(Card Card) {
        if (Card.getPositionX() >= containerRectangle.left
                && Card.getPositionX() <= containerRectangle.right
                && screenDimensions.y - Card.getPositionY() <= containerRectangle.bottom
                && screenDimensions.y - Card.getPositionY() >= containerRectangle.top) {
            return true;
        }
        return false;
    }

    //Update a card's position
    public void updateCardPos(Card card, int cardNumberAcross){
       card.setPosition(position.x+card.getPositionX() +(cardNumberAcross*spacerX),
                screenDimensions.y-position.y - card.getPositionY());
    }

    //Updates the last card's position
    public void updateLastCardPos(Card card, int cardNumberAcross){
       card.setPositionX(position.x+card.getPositionX() +(cardNumberAcross*spacerX));
       card.setPositionY(position.y+card.getPositionY() +(cardNumberAcross*spacerY));
    }

    //Calls the two above methods
    public void updateCardPosAndLast(Card card, int cardNumberAcross){
        updateCardPos(card, cardNumberAcross);
        updateLastCardPos(card,cardNumberAcross);
    }

    //Adds a card to the container
    public void addCard(Card card, ArrayList<Card> activeCards, int posOfCard){
        //activeCards.get(posOfCard).setContainerType(getContainerType());
        updateCardPosAndLast(activeCards.get(posOfCard), cardsInContainer.size());
      //  activeCards.get(posOfCard).setDoneMovement(false);
        cardsInContainer.add(posOfCard, card);
    }

    //Adds a card to the container
    public void removeCard(ArrayList<Card> activeCards, int posOfCard){
        //activeCards.get(posOfCard).setContainerType(getContainerType());
        updateCardPosAndLast(activeCards.get(posOfCard), cardsInContainer.size());
        //  activeCards.get(posOfCard).setDoneMovement(false);
        Card card = cardsInContainer.get(posOfCard);
        cardsInContainer.remove(card);
    }

}
