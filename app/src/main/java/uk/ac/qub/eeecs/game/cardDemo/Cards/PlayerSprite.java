package uk.ac.qub.eeecs.game.cardDemo.Cards;

import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.Sprite;
import uk.ac.qub.eeecs.game.StudentsUnion;
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

public class PlayerSprite extends Sprite {
    GameScreen gameScreen;
    private static float spriteWidth = 25.0f;
    private static float spriteHeight = 25.0f;
    // Default values for startY & startX position
    private static float positionY = 0;
    private static float positionX = 0;
    private String bitmapName;

    StudentsUnion.Direction direction;

    public PlayerSprite(GameScreen gameScreen, String bitmapName) {
        super(positionX, positionY, spriteWidth, spriteHeight, gameScreen.getGame().getAssetManager().getBitmap(bitmapName), gameScreen);
        this.gameScreen = gameScreen;
        this.bitmapName = bitmapName;
        this.positionX = positionX;
        this.positionY = positionY;
        mBound.halfHeight = spriteHeight / 2;
        mBound.halfWidth = spriteWidth / 2;
    }

    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setBitmapName(String bitmapName) {
        this.bitmapName = bitmapName;
    }
}
