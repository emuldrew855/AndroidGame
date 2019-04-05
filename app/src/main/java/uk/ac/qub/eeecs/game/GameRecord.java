package uk.ac.qub.eeecs.game;

import java.util.ArrayList;

/**
 * Created by Edward Muldrew on 01/03/2018. This class is used to monitor the game being played between the AI and the User
 * It holds information on who is currently in the lead and will help to decide when a game is due to end.
 */

public class GameRecord {
    // Instance Variables
    private ArrayList<Round> roundRecord;
    private int userOverallScore;
    private int aiOVerallScore;

    // Constructor
    // The game record object is used to encapsualte each round & determine the overall winner.
    public GameRecord(ArrayList<Round> roundRecord, int userOverallScore, int aiOVerallScore) {
        this.roundRecord = roundRecord;
        this.userOverallScore = userOverallScore;
        this.aiOVerallScore = aiOVerallScore;
    }


    // Getters
    public ArrayList<Round> getRoundRecord() {
        return roundRecord;
    }

    public int getUserOverallScore() {
        return userOverallScore;
    }

    public int getAiOVerallScore() {
        return aiOVerallScore;
    }

    // Settters
    public void setUserOverallScore(int userOverallScore) {
        this.userOverallScore = userOverallScore; }

    public void setRoundRecord(ArrayList<Round> roundRecord) {
        this.roundRecord = roundRecord;
    }

    public void setAiOVerallScore(int aiOVerallScore) {
        this.aiOVerallScore = aiOVerallScore;
    }
}
