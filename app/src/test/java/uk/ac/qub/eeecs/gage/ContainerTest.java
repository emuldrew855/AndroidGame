package uk.ac.qub.eeecs.gage;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import uk.ac.qub.eeecs.gage.engine.AssetStore;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.cardDemo.Cards.Card;
import uk.ac.qub.eeecs.game.cardDemo.Cards.MinionCard;
import uk.ac.qub.eeecs.game.cardDemo.Cards.SpellCard;
import uk.ac.qub.eeecs.game.cardDemo.Containers.Container;
import uk.ac.qub.eeecs.game.cardDemo.Cards.HeroCard;
import uk.ac.qub.eeecs.game.cardDemo.Cards.StrengthCard;
import uk.ac.qub.eeecs.game.cardDemo.Containers.ContainerType;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static uk.ac.qub.eeecs.game.cardDemo.Containers.ContainerType.HAND;

/**
 * Created by 40178070(Brandon) on 10/12/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class ContainerTest {

    @Mock
    GameScreen gameScreen = Mockito.mock(GameScreen.class);
    Game game = Mockito.mock(Game.class);
    Container mockContainer;

    @Mock
    private Bitmap temp;
    String expectedBitmap = "Turret";
    ArrayList<Integer> activeCards = new ArrayList<>();
    ArrayList<Card> activeCards2 = new ArrayList<>();
    HeroCard mockCard;
    MinionCard testMinion;
    MinionCard testMinion2;
    MinionCard testMinion3;
    MinionCard testMinion4;
    MinionCard testMinion5;
    MinionCard testMinion6;
    MinionCard testMinion7;

    @Mock
    private AssetStore assetManager;

    @Before
    public void setUp(){
        when(gameScreen.getGame()).thenReturn(game);

      mockContainer = new Container(100, 100, 20,20, 4,  gameScreen);

      MockitoAnnotations.initMocks(this);
      when(gameScreen.getGame()).thenReturn(game);
      when(game.getAssetManager()).thenReturn(assetManager);
      when(assetManager.getBitmap(expectedBitmap)).thenReturn(temp);

      testMinion = new MinionCard(1,"TestMinion",Card.Subject.COMPUTER_SCIENCE,
                Card.Rarity.RARE,"This is a test card",temp.toString(),gameScreen, StrengthCard.Row.Front,
              10);

      testMinion2 = new MinionCard(2,"TestMinion2",Card.Subject.COMPUTER_SCIENCE,
                Card.Rarity.RARE,"This is a test card",temp.toString(),gameScreen, StrengthCard.Row.Front,
                10);

      testMinion3 = new MinionCard(3,"TestMinion3",Card.Subject.COMPUTER_SCIENCE,
                Card.Rarity.RARE,"This is a test card",temp.toString(),gameScreen, StrengthCard.Row.Front,
                10);

      testMinion4 = new MinionCard(4,"TestMinion4",Card.Subject.COMPUTER_SCIENCE,
                Card.Rarity.RARE,"This is a test card",temp.toString(),gameScreen, StrengthCard.Row.Front,
                10);

      testMinion5 = new MinionCard(5,"TestMinion5",Card.Subject.COMPUTER_SCIENCE,
                Card.Rarity.RARE,"This is a test card",temp.toString(),gameScreen, StrengthCard.Row.Front,
                10);

      testMinion6 = new MinionCard(6,"TestMinion6",Card.Subject.COMPUTER_SCIENCE,
                Card.Rarity.RARE,"This is a test card",temp.toString(),gameScreen, StrengthCard.Row.Front,
                10);

      testMinion7 = new MinionCard(7,"TestMinion7",Card.Subject.COMPUTER_SCIENCE,
                Card.Rarity.RARE,"This is a test card",temp.toString(),gameScreen, StrengthCard.Row.Front,
                10);
    }

    @Test
    public void containerClassTestConstruction() {
        //Define expected properties
        ArrayList<Card> expectedCards = new ArrayList<>(); expectedCards.add(testMinion4);
        Paint expectedPaint = new Paint();
        Rect expectedRect = new Rect();
        ContainerType expectedType = HAND;
        int expectedMax = 6;

        //Create test container
        mockContainer.setCardsInContainer(expectedCards);
        mockContainer.setContainerPaint(expectedPaint);
        mockContainer.setContainerRectangle(expectedRect);
        mockContainer.setContainerType(expectedType);
        mockContainer.setMaxNoOfCards(expectedMax);

        // Test that the constructed values are as expected
        assertTrue(mockContainer.getCardsInContainer() == expectedCards);
        assertTrue(mockContainer.getContainerPaint() == expectedPaint);
        assertTrue(mockContainer.getContainerRectangle() == expectedRect);
        assertTrue(mockContainer.getContainerType() == expectedType);
        assertTrue(mockContainer.getMaxNoOfCards() == expectedMax);
    }

    @Test
    public void checkContainerSetters() {
        mockContainer = new Container(1,1,1,1,1,gameScreen);

        // Set a new value to Card Name
        int newContainerMax = 5;
        mockContainer.setMaxNoOfCards(newContainerMax);

        assertTrue(mockContainer.getMaxNoOfCards() == newContainerMax);
    }


    @Test
    public void isContainerEmpty_Valid() {
        boolean success = mockContainer.isContainerEmpty();
        assertTrue(success);
    }


    @Test
    public void isContainerEmpty_Invalid(){
        activeCards2.add(testMinion);
        mockContainer.setCardsInContainer(activeCards2);
        boolean success = mockContainer.isContainerEmpty();
        assertFalse(success);
    }

    @Test
    public void isContainerFull_Valid(){
        activeCards.add(1);
        activeCards.add(2);
        activeCards.add(7);
        activeCards.add(3);
        mockContainer.setMaxNoOfCards(4);
        mockContainer.setCardsInContainer(activeCards2);
        boolean success = mockContainer.isContainerFull();
        assertTrue(success);
    }

    @Test
    public void isContainerFull_Invalid(){
        activeCards.add(1);
        mockContainer.setMaxNoOfCards(4);
        mockContainer.setCardsInContainer(activeCards2);
        boolean success = mockContainer.isContainerFull();
        assertFalse(success);
    }

    @Test
    public void isContainerFree_Valid(){
        activeCards.add(1);
        mockContainer.setMaxNoOfCards(4);
        mockContainer.setCardsInContainer(activeCards2);
        boolean success = mockContainer.isContainerFree();
        assertTrue(success);
    }

    @Test
    public void isContainerFree_Invalid(){
        activeCards.add(4);
        activeCards.add(3);
        activeCards.add(2);
        activeCards.add(6);
        mockContainer.setMaxNoOfCards(4);
        mockContainer.setCardsInContainer(activeCards2);
        boolean success = mockContainer.isContainerFree();
        assertFalse(success);
    }

    @Test
    public void isDetected_Valid() {
        mockContainer.setPosition(3.0f,3.0f);
        testMinion.position.set(3.0f,3.0f);
        boolean success = mockContainer.isDetected(mockCard);
        assertTrue(success);
    }

    @Test
    public void isDetected_Invalid() {
        mockContainer.setPosition(99.0f,99.0f);
        testMinion.setPositionX(3.0f);
        testMinion.setPositionY(3.0f);
        boolean success = mockContainer.isDetected(testMinion);
        assertFalse(success);
    }

    @Test
    public void addCard_Valid() {
       activeCards2.add(testMinion);
       mockContainer.addCard(testMinion, activeCards2, 0);
       boolean success = mockContainer.getCardAt(0) == testMinion;
       assertTrue(success);
    }

    @Test
    public void addCardPosition_Valid() {
        activeCards2.add(testMinion);
        activeCards2.add(testMinion2);
        activeCards2.add(testMinion3);
        mockContainer.addCard(testMinion, activeCards2, 0);
        mockContainer.addCard(testMinion2, activeCards2, 1);
        mockContainer.addCard(testMinion3, activeCards2, 2);
        boolean success = mockContainer.getCardAt(1) == testMinion2;
        assertTrue(success);
    }

    @Test
    public void addCard_Invalid() {
        activeCards2.add(testMinion);
        mockContainer.addCard(testMinion, activeCards2, 0);
        boolean success = mockContainer.getCardAt(5) == testMinion;
        assertFalse(success);
    }

    @Test
    public void addCardPosition_Invalid() {
        activeCards2.add(testMinion);
        activeCards2.add(testMinion2);
        activeCards2.add(testMinion3);
        mockContainer.addCard(testMinion, activeCards2, 0);
        mockContainer.addCard(testMinion2, activeCards2, 1);
        mockContainer.addCard(testMinion3, activeCards2, 2);
        boolean success = mockContainer.getCardAt(1) == testMinion3;
        assertFalse(success);
    }

    @Test
    public void removeCard_Valid() {
        activeCards2.add(testMinion);
        activeCards2.add(testMinion2);
        activeCards2.add(testMinion3);
        mockContainer.addCard(testMinion, activeCards2, 0);
        mockContainer.addCard(testMinion2, activeCards2, 1);
        mockContainer.addCard(testMinion3, activeCards2, 2);
        mockContainer.removeCard(activeCards2,0);
        boolean success = mockContainer.getCardAt(0) == null;
        assertFalse(success);
    }

    @Test
    public void removeCard_Invalid() {
        activeCards2.add(testMinion);
        activeCards2.add(testMinion2);
        activeCards2.add(testMinion3);
        mockContainer.addCard(testMinion, activeCards2, 0);
        mockContainer.addCard(testMinion2, activeCards2, 1);
        mockContainer.addCard(testMinion3, activeCards2, 2);
        mockContainer.removeCard(activeCards2,2);
        boolean success = mockContainer.getCardAt(0) == null;
        assertFalse(success);
    }

    @Test
    public void updateCardPos_Valid() {
        activeCards2.add(testMinion);
        activeCards2.add(testMinion2);
        activeCards2.add(testMinion3);
        mockContainer.addCard(testMinion, activeCards2, 0);
        mockContainer.addCard(testMinion2, activeCards2, 1);
        mockContainer.addCard(testMinion3, activeCards2, 2);
        mockContainer.removeCard(activeCards2,0);
        mockContainer.updateCardPos(testMinion2,2);
        boolean success = mockContainer.getCardAt(0) == testMinion2;
        assertTrue(success);
    }

    @Test
    public void updateCardPos_Invalid() {
        activeCards2.add(testMinion);
        activeCards2.add(testMinion2);
        activeCards2.add(testMinion3);
        mockContainer.addCard(testMinion, activeCards2, 0);
        mockContainer.addCard(testMinion2, activeCards2, 1);
        mockContainer.addCard(testMinion3, activeCards2, 2);
        mockContainer.removeCard(activeCards2,0);
        mockContainer.updateCardPos(testMinion2,2);
        boolean success = mockContainer.getCardAt(1) == testMinion2;
        assertFalse(success);
    }

    @Test
    public void updateLastCardPos_Valid() {
        activeCards2.add(testMinion);
        activeCards2.add(testMinion2);
        activeCards2.add(testMinion3);
        mockContainer.addCard(testMinion, activeCards2, 0);
        mockContainer.addCard(testMinion2, activeCards2, 1);
        mockContainer.addCard(testMinion3, activeCards2, 2);
        mockContainer.removeCard(activeCards2,0);
        mockContainer.updateCardPos(testMinion2,1);
        mockContainer.updateLastCardPos(testMinion3,2);
        boolean success = mockContainer.getCardAt(1) == testMinion3;
        assertTrue(success);
    }

    @Test
    public void updateLastCardPos_Invalid() {
        activeCards2.add(testMinion);
        activeCards2.add(testMinion2);
        activeCards2.add(testMinion3);
        mockContainer.addCard(testMinion, activeCards2, 0);
        mockContainer.addCard(testMinion2, activeCards2, 1);
        mockContainer.addCard(testMinion3, activeCards2, 2);
        mockContainer.removeCard(activeCards2,0);
        mockContainer.updateCardPos(testMinion2,1);
        mockContainer.updateLastCardPos(testMinion3,2);
        boolean success = mockContainer.getCardAt(0) == testMinion3;
        assertFalse(success);
    }

    @Test
    public void updateCardPosAndLast_Valid() {
        activeCards2.add(testMinion);
        activeCards2.add(testMinion2);
        activeCards2.add(testMinion3);
        mockContainer.addCard(testMinion, activeCards2, 0);
        mockContainer.addCard(testMinion2, activeCards2, 1);
        mockContainer.addCard(testMinion3, activeCards2, 2);
        mockContainer.removeCard(activeCards2,0);
        mockContainer.updateCardPosAndLast(testMinion2,1);
        boolean success = mockContainer.getCardAt(1) == testMinion3;
        assertTrue(success);
    }

    @Test
    public void updateCardPosAndLast_Invalid() {
        activeCards2.add(testMinion);
        activeCards2.add(testMinion2);
        activeCards2.add(testMinion3);
        mockContainer.addCard(testMinion, activeCards2, 0);
        mockContainer.addCard(testMinion2, activeCards2, 1);
        mockContainer.addCard(testMinion3, activeCards2, 2);
        mockContainer.removeCard(activeCards2,0);
        mockContainer.updateCardPosAndLast(testMinion3,1);
        boolean success = mockContainer.getCardAt(0) == testMinion3;
        assertFalse(success);
    }

}
