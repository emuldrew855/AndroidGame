package uk.ac.qub.eeecs.game.cardDemo;

import java.util.HashMap;

import uk.ac.qub.eeecs.game.cardDemo.Cards.Hand;

/**
 * Created by Edward Muldrew on 17/02/2018. This class is used to represent the different turns each User takes
 */

public class Turn {
    // Instance Variables
    private HashMap<Integer,String> userTurnRecord = new HashMap<Integer,String>();
    private HashMap<Integer,String> aiTurnRecord = new HashMap<Integer,String>();

    // Constructor
    // One Turn object takes in both the users & the ai's turn record
    public Turn(HashMap<Integer, String> userTurnRecord, HashMap<Integer, String> aiTurnRecord) {
        this.userTurnRecord = userTurnRecord;
        this.aiTurnRecord = aiTurnRecord;
        this.aiTurnRecord.put(0,"null"); // sets ai's first default value to avoid bugs
        this.userTurnRecord.put(0,"null"); // sets ai's first default value to avoid bugs
    }

    // Getters
    public HashMap<Integer, String> getUserTurnRecord() {
        return userTurnRecord;
    }

    public HashMap<Integer, String> getAiTurnRecord() {
        return aiTurnRecord;
    }

    // Setters
    public void setUserTurnRecord(HashMap<Integer, String> userTurnRecord) {
        this.userTurnRecord = userTurnRecord;
    }

    public void setAiTurnRecord(HashMap<Integer, String> aiTurnRecord) {
        this.aiTurnRecord = aiTurnRecord;
    }
}