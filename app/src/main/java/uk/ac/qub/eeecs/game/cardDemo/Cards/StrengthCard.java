package uk.ac.qub.eeecs.game.cardDemo.Cards;

import android.graphics.Color;
import android.graphics.Paint;

import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;

/**
 * Created by Edward Muldrew on 27/11/2017. ~ User Story 12: Develop & Implement Inheritance Hierarchy
 * The StrengthCard extends the card class and has the cardRow and cardStrength instance variable with getters and setters as well as the updateScore method
 */
public class StrengthCard extends Card {
    //Enums
    public enum Row {
        Front, Middle, Back, All
    }

    // Instance Variables
    private Row cardRow;
    private int cardStrength;

    // Constructor
    public StrengthCard(int cardID, String cardName, Subject cardSubject, Rarity cardRarity, String cardDescription,
                        String bitmapName, GameScreen gameScreen, Row cardRow, int cardStrength) {
        super(cardID, cardName, cardSubject, cardRarity, cardDescription, bitmapName, gameScreen);
        this.cardRow = cardRow;
        this.cardStrength = cardStrength;
    }
    // Copy Constructor
    public StrengthCard(StrengthCard copy, String bitmapName, GameScreen gameScreen) {
        super(copy, bitmapName, gameScreen);
        this.cardRow = copy.cardRow;
        this.cardStrength = copy.cardStrength;
    }

    // Getters
    public Row getCardRow() { return cardRow; }

    public int getCardStrength() { return cardStrength;}

    // Setters
    public void setCardStrength(int cardStrength) { this.cardStrength = cardStrength;}

    public void setCardRow(Row cardRow) { this.cardRow = cardRow; }

    // Methods
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D, LayerViewport layerViewport, ScreenViewport screenViewport) {
        super.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
        Paint paintVariable = new Paint();
        paintVariable.setColor(Color.WHITE);
        paintVariable.setFakeBoldText(true);
        paintVariable.setTextSize(20);
        String cardStrength = String.valueOf(this.cardStrength);  // Converts cardStrength int value to string
        if (this.getBitmapName().contains("Back")) {  // Only draws cards name if it is apart of the User Hand

        } else {
            graphics2D.drawText(cardStrength, drawScreenRect.right - 37 + (drawScreenRect.width() / 20),
                    drawScreenRect.top - 10 + ((drawScreenRect.height() * 3) / 3), paintVariable);
        }
    }
}