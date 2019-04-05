package uk.ac.qub.eeecs.game;

import android.graphics.Bitmap;
import android.test.UiThreadTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by Edward Muldrew on 28/11/2017.
 * This class is used to specifically test the deck class
 */



public class DeckClassTest {

    @Mock
    GameScreen gameScreen = Mockito.mock(GameScreen.class);
    Game game = Mockito.mock(Game.class);

    @Mock
    private Bitmap cardBitmap;
    String expectedBitmap = "Turret";
    HeroCard testCard;
    SpellCard mockCard;
    private GameObject mBoardBG;
    private ArrayList<Card> compSciCards = new ArrayList<Card>();  // Create Computer Science Array List of Cards
    private Deck userDeck = new Deck(1, "User Deck", compSciCards,gameScreen); // Create User Deck
    private Hand userHand;
    Utilities helperUtilities;

    @Mock
    private AssetStore assetManager;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(gameScreen.getGame()).thenReturn(game);
        when(game.getAssetManager()).thenReturn(assetManager);
        when(assetManager.getBitmap(expectedBitmap)).thenReturn(cardBitmap);

        userHand = new Hand("User Hand", Hand.UserType.USER,compSciCards,this.gameScreen, game);
        helperUtilities = new Utilities(gameScreen,game);
        userDeck = helperUtilities.generateCompSciDeck(gameScreen);
        userHand = helperUtilities.generateCompSciHand(gameScreen,userHand);
    }

    // Test checks to see if all the properties of the deck class have been insantiated properly using getters
    @Test
    public void checkDeckInstantiation(){
        assertEquals(userDeck.getDeckID(),1);
        assertEquals(userDeck.getDeckOfCards().size(),7);
        assertEquals(userDeck.getDeckName(),"User Deck");
    }

    // Check to see if setter methods are working on Deck class
    @Test
    public void checkDeckSetters() {
        userDeck.setDeckID(2);
        userDeck.setDeckName("TestDeck");
        ArrayList<Card> testArray = new ArrayList<>();
        userDeck.setDeckOfCards(testArray);

        assertEquals(userDeck.getDeckID(),2);
        assertEquals(userDeck.getDeckName(),"TestDeck");
        assertTrue(userDeck.getDeckOfCards().equals(testArray));
    }

    // Test checks to see if card is added sucesfully to the deck
    @Test
    public void testAddCardToDeckMethod() {
        assertEquals(userDeck.getDeckOfCards().size(), 7);

        userDeck.addCardToDeck(testCard);
        assertEquals(userDeck.getDeckOfCards().size(), 8);
    }

    // Test checks to see if card is removed from the deck and added in to the user hand
    @Test
    public void checkUserHandGetsCardFromDeck() {
        assertEquals(userHand.getCardsInHand().size(),7);
        assertEquals(userDeck.getDeckOfCards().size(),7);
        userDeck.removeCardFromDeck(userHand);
        assertEquals(userHand.getCardsInHand().size(),8);
        assertEquals(userDeck.getDeckOfCards().size(),6);
    }
}

