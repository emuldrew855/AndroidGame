package uk.ac.qub.eeecs.game;

import uk.ac.qub.eeecs.game.cardDemo.PlayerScore;
import uk.ac.qub.eeecs.game.cardDemo.Turn;

/**
 * Created by Edward Muldrew on 21/02/2018.
 * This class is used to represent each round that is played and should record the amount of turns and each Users Score
 */

public class Round {
    // Instance Variables
    String roundName;
    private Turn turnRecord;
    private PlayerScore userScore;
    private PlayerScore aiScore;

    // Constructor
    // The round object is used encapsulate each round a user & the ai play
    public Round(String name, Turn turnRecord, PlayerScore userScore, PlayerScore aiScore) {
        this.roundName = name;
        this.turnRecord = turnRecord;
        this.userScore = userScore;
        this.aiScore = aiScore;
    }

    // Getters
    public String getRoundName() {
        return roundName;
    }

    public Turn getTurnRecord() {
        return turnRecord;
    }

    public PlayerScore getUserScore() {
        return userScore;
    }

    public PlayerScore getAiScore() {
        return aiScore;
    }

    // Setters
    public void setAiScore(PlayerScore aiScore) {
        this.aiScore = aiScore;
    }

    public void setTurnRecord(Turn turnRecord) {
        this.turnRecord = turnRecord;
    }

    public void setUserScore(PlayerScore userScore) {
        this.userScore = userScore;
    }

    public void setRoundName(String roundName) {
        this.roundName = roundName;
    }

}
