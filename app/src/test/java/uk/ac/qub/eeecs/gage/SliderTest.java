package uk.ac.qub.eeecs.gage;

import android.graphics.Color;
import android.graphics.Paint;

import org.junit.Test;
import org.mockito.Mock;

import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.cardDemo.Options.Slider;

//Created by Grace 40172213

public class SliderTest {

    @Mock
    int min, max, val;
    Paint textStyle;
    float x, y, width, height;
    String defaultBitmap, sliderFillBitmap, triggerSound;
    GameScreen gameScreen;
    Boolean processInLayerSpace;

    Slider testSlider;

    //Test creating a slider object
    @Test
    public void testCreatingSlider() {
        Paint sliderPainter = new Paint();
        sliderPainter.setTextSize(60);
        sliderPainter.setColor(Color.BLACK);
        sliderPainter.setTextAlign(Paint.Align.CENTER);

        testSlider = new Slider(min, max, val, textStyle, x, y, width, height, defaultBitmap, sliderFillBitmap, gameScreen, processInLayerSpace);
    }
}
