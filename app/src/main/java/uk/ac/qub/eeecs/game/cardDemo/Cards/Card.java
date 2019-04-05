// User story 1//

package uk.ac.qub.eeecs.game.cardDemo.Cards;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.List;

import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.engine.input.TouchHandler;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.gage.world.Sprite;
import uk.ac.qub.eeecs.gage.util.Vector2;
import uk.ac.qub.eeecs.game.cardDemo.Containers.ContainerType;

/**
 * Created by Jack McNaughton on 05/11/2017.
 * Changed by Edward Muldrew on 27/11/2017 ~ User Story 12: Develop & Implement Inheritance Hierarchy
 */

public class Card extends Sprite {
    // Enums
    public enum Rarity {
        COMMON, UNCOMMON, RARE
    }
    public enum Subject {
        COMPUTER_SCIENCE, MEDICINE
    }
    // Instance Variables
    float starterX = 0;
    GameScreen gameScreen;
    private int cardID;
    private String cardName;
    private Subject cardSubject;
    private String cardDescription;
    private Rarity cardRarity;
    private String bitmapName;
    private Vector2 screenCentre = new Vector2();
    private boolean cardPlaced;
    // Default card width and height sizes
    private static float cardWidth = 38.0f;
    private static float cardHeight = 48.0f;
    // Default values for startY & startX position
    private static float startY = 24;
    private static float startX = 20;

    // Constructor
    // Every card is orginally set with set start x & y position as well as having the same card width & height
    public Card(int cardID, String cardName, Subject cardSubject, Rarity cardRarity, String cardDescription, String bitmapName, GameScreen gameScreen) {
        super(startX, startY, cardWidth, cardHeight, gameScreen.getGame().getAssetManager().getBitmap(bitmapName), gameScreen);
        this.gameScreen = gameScreen;
        this.cardID = cardID;
        this.cardName = cardName;
        this.cardSubject = cardSubject;
        this.cardRarity = cardRarity;
        this.cardDescription = cardDescription;
        this.bitmapName = bitmapName;
        this.startX = startX;
        this.startY = startY;
        screenCentre.x = mGameScreen.getGame().getScreenWidth() / 2;
        screenCentre.y = mGameScreen.getGame().getScreenHeight() / 2;
        this.cardPlaced = false;
    }


    //   Copy constructor
    public Card(Card copy, String bitmapName, GameScreen gameScreen) {
        super(startX,startY, cardWidth, cardHeight, gameScreen.getGame().getAssetManager().getBitmap(bitmapName), gameScreen);
        this.cardID = copy.cardID;
        this.cardName = copy.cardName;
        this.cardSubject = copy.cardSubject;
        this.cardRarity = copy.cardRarity;
        this.cardDescription = copy.cardDescription;
        this.bitmapName = copy.bitmapName;
        this.startX = copy.startX;
        this.startY = copy.startY;
        screenCentre.x = mGameScreen.getGame().getScreenWidth() / 2;
        screenCentre.y = mGameScreen.getGame().getScreenHeight() / 2;
        this.cardPlaced = false;
    }


    // Getters
    public int getCardID() {
        return cardID;
    }

    public String getCardName() {
        return cardName;
    }

    public Rarity getCardRarity() {
        return cardRarity;
    }

    public Subject getCardSubject() {
        return cardSubject;
    }

    public String getCardDescription() {
        return cardDescription;
    }

    public String getBitmapName() {
        return bitmapName;
    }

    public float getStartX() {
        return this.startX;
    }

    public float getStartY() {
        return this.startY;
    }

    public float getPositionX() {
        return position.x;
    }

    public float getPositionY() {
        return position.y;
    }


    public boolean isCardPlaced() {
        return cardPlaced;
    }

    public Vector2 getScreenCentre() {return screenCentre;}

    public void setScreenCentre(Vector2 screenCentre) {this.screenCentre = screenCentre;}

    // Setters
    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public void setCardRarity(Rarity cardRarity) {
        this.cardRarity = cardRarity;
    }

    public void setCardSubject(Subject cardSubject) {
        this.cardSubject = cardSubject;
    }

    public void setCardDescription(String cardDescription) { this.cardDescription = cardDescription;}

    public void setBitmapName(String bitmapName, GameScreen gameScreen) {this.bitmapName = bitmapName;
        this.setmBitmap(gameScreen.getGame().getAssetManager().getBitmap(bitmapName));}

    public void setStartX(float startX) {
        this.startX = startX;
    }

    public void setStartY(float startY) {
        this.startY = startY;
    }

    public void setPositionX(float x) {
        super.setPosition(x, getPositionY());
    }

    public void setPositionY(float y) {
        super.setPosition(getPositionX(), y);
    }

    public float getStarterX() {
        return starterX;
    }

    public void setStarterX(float starterX) {
        this.starterX = starterX;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
    }

    public void setCardPlaced(boolean cardPlaced) { this.cardPlaced = cardPlaced; }

    // Methods
    public void update(ElapsedTime elapsedTime, LayerViewport layerViewport, ScreenViewport screenViewport) {
        Input input = mGameScreen.getGame().getInput();
        List<TouchEvent> touchEvents = input.getTouchEvents();
    }

    // Add Card Name and Card Description to be drawn on card
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D, LayerViewport layerViewport, ScreenViewport screenViewport) {
        super.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
        Paint paintVariableDescription = new Paint();
        paintVariableDescription.setColor(Color.WHITE);
        paintVariableDescription.setFakeBoldText(true);
        paintVariableDescription.setTextSize(16);
        Paint paintVariableName = new Paint();
        paintVariableName.setColor(Color.BLACK);
        paintVariableName.setFakeBoldText(true);
        paintVariableName.setTextSize(16);
        if (this.getBitmapName().contains("Back")) {  // Only draws cards name if it is apart of the User Hand

        } else { // Draw card name & description
            graphics2D.drawText(this.getCardName(), drawScreenRect.left + (drawScreenRect.width() / 6), drawScreenRect.top + ((drawScreenRect.height() * 2) / 18), paintVariableName);
            graphics2D.drawText(this.getCardDescription(), drawScreenRect.left + (drawScreenRect.width() / 7), drawScreenRect.top + ((drawScreenRect.height() * 3) / 4), paintVariableDescription);
        }
    }
}