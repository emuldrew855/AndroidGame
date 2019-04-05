package uk.ac.qub.eeecs.game;

import android.graphics.Bitmap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Random;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetStore;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.cardDemo.Cards.Card;
import uk.ac.qub.eeecs.game.cardDemo.Cards.Deck;
import uk.ac.qub.eeecs.game.cardDemo.Cards.Hand;
import uk.ac.qub.eeecs.game.cardDemo.Cards.HeroCard;
import uk.ac.qub.eeecs.game.cardDemo.Cards.MinionCard;
import uk.ac.qub.eeecs.game.cardDemo.Cards.SpellCard;
import uk.ac.qub.eeecs.game.cardDemo.Cards.StrengthCard;
import uk.ac.qub.eeecs.game.cardDemo.Utilities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by Edward Muldrew on 28/11/2017.
 * This class is used specifically to test the card class
 */



public class CardClassTest {

    @Mock
    GameScreen gameScreen = Mockito.mock(GameScreen.class);
    Game game = Mockito.mock(Game.class);

    @Mock
    private Bitmap cardBitmap;
    String expectedBitmap = "Turret";
    HeroCard testCard;
    SpellCard mockCard;
    private GameObject mBoardBG;
    MinionCard testMinion;
    Utilities helperUtilities;

    @Mock
    private AssetStore assetManager;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(gameScreen.getGame()).thenReturn(game);
        when(game.getAssetManager()).thenReturn(assetManager);
        when(assetManager.getBitmap(expectedBitmap)).thenReturn(cardBitmap);
        helperUtilities = new Utilities(gameScreen, game);
        helperUtilities.loadAndAddAseests(game);
        testMinion = new MinionCard(1,"TestMinion",Card.Subject.COMPUTER_SCIENCE,
                Card.Rarity.RARE,"This is a test card","PrinterMinion",gameScreen, StrengthCard.Row.Front,
                10);

    }


    // Testing if a Card object class has been constructed correctly and testing their respective
// Getters of the class
    @Test
    public void cardClassTestConstruction() {
        //Define expected properties
        int expectedCardNumber = 1;
        int expectedYPosition = 24;
        int expectedXPosition = 20;
        String expectedCardName = "Test Card";
        Card.Subject expectedCardSubject = Card.Subject.COMPUTER_SCIENCE;
        String expectedCardDescription = "This is a test card";
        SpellCard.Ability expectedCardAbility = SpellCard.Ability.INCREASE;
        Card.Rarity expectedCardRarity = SpellCard.Rarity.RARE;

        // Create Test Card
        mockCard = new SpellCard(expectedCardNumber,expectedCardName,expectedCardSubject,
                expectedCardRarity,expectedCardDescription, "HeroFront",this.gameScreen,expectedCardAbility);

        // Test that the constructed values are as expected
//        assertEquals(mockCard.getStartX() ,expectedXPosition,1.0);
//        assertEquals(mockCard.getStartY(), expectedYPosition, 1.0);
        assertTrue(mockCard.getCardName() == expectedCardName);
        assertTrue(mockCard.getCardSubject() == expectedCardSubject);
        assertTrue(mockCard.getCardAbility() == expectedCardAbility);
        assertTrue(mockCard.getCardRarity() == expectedCardRarity);
        assertTrue(mockCard.getCardID() == expectedCardNumber );

    }


    // Test to check if Card class setters are working correctly
    @Test
    public void checkCardSetters() {
        // Create Test Card
        testCard = new HeroCard(1,"TestCard",Card.Subject.COMPUTER_SCIENCE,
                Card.Rarity.RARE,"This is a test card","HeroFront",this.gameScreen,StrengthCard.Row.Front, 10);

        // Set a new value to Card Name
        String newCardName = "Setter Has Changed Name";
        testCard.setCardName(newCardName);

        assertTrue(testCard.getCardName() == newCardName);
    }


    // This test is to check when the cardStrength value is converted, it is converted correctly so it
// can be drawn on screen
    @Test
    public void checkCardStrengthConvert() {
        // Create Card
        testCard = new HeroCard(1,"TestCard",Card.Subject.COMPUTER_SCIENCE,
                Card.Rarity.RARE,"This is a test card","HeroFront",this.gameScreen, StrengthCard.Row.Front,
                10);

        String cardStrength = String.valueOf(testCard.getCardStrength());
        assertEquals(String.valueOf(testCard.getCardStrength()),cardStrength);
    }


    // This test is to check if the inheritance hierarchy is working correctly that a minion card
// is an instance of the Card Class
    @Test
    public void checkCardIsInstanceOfParentClass() {
        assertTrue(testMinion instanceof Card);

    }

    @Test
    public void checkIfGetStartXMethodWorks() {
        assertEquals(testMinion.getStartX(), 20.0f,2.0f);
        testMinion.setStartX(40);
        assertEquals(testMinion.getStartX(),40,2.0f);
    }

    @Test
    public void checkSetBitmapMethodWork() {
       // assertEquals(testMinion.getBitmap(),"PrinterMinion");
        testMinion.setBitmapName("MouseMinion",gameScreen);
        assertEquals(testMinion.getBitmap(),game.getAssetManager().getBitmap("MouseMinion"));
        assertEquals(testMinion.getBitmapName(),"MouseMinion");
    }







}

