package uk.ac.qub.eeecs.game.cardDemo.AI;

import java.util.ArrayList;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.cardDemo.Cards.Card;
import uk.ac.qub.eeecs.game.cardDemo.Cards.Deck;
import uk.ac.qub.eeecs.game.cardDemo.Containers.ContainerManager;

/**
 * Created by Brandon on 05/03/2018.
 */

public class AI {

    private ArrayList<Card> activeCards = new ArrayList<>();

    private Deck aiDeck;

    private Difficulty difficulty;

    private ContainerManager playerContainers;

    private GameScreen currentGamescreen;

    private Game game;

    private boolean endTurn;

    private boolean cardPlayed;

    private boolean playerTurn;

    private boolean hideCards;

    public AI(Difficulty difficulty, GameScreen gameScreen, Game game) {
        this.currentGamescreen = gameScreen;
        this.game = game;
        this.hideCards = true;
        this.difficulty = difficulty;
    }

    public ArrayList<Card> getActiveCards() {return activeCards;}

    public void setActiveCards(ArrayList<Card> activeCards) {
        this.activeCards = activeCards;
    }

    public Deck getAiDeck() {
        return aiDeck;
    }

    public void setAiDeck(Deck aiDeck) {
        this.aiDeck = aiDeck;
    }

    public boolean isEndTurn() {
        return endTurn;
    }

    public void setEndTurn(boolean endTurn) {
        this.endTurn = endTurn;
    }

    public boolean isCardPlayed() {return cardPlayed;}

    public void setCardPlayed(boolean cardPlayed) {
        this.cardPlayed = cardPlayed;
    }

    public boolean isPlayerTurn() {return playerTurn;}

    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }

    public boolean isHideCards() {
        return hideCards;
    }

    public void setHideCards(boolean hideCards) {
        this.hideCards = hideCards;
    }

    public Difficulty getDifficulty() {return difficulty;}

    public void setDifficulty(Difficulty difficulty) {this.difficulty = difficulty;}
}
