package uk.ac.qub.eeecs.game.cardDemo;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetStore;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.cardDemo.Cards.Card;
import uk.ac.qub.eeecs.game.cardDemo.Cards.Deck;
import uk.ac.qub.eeecs.game.cardDemo.Cards.Hand;
import uk.ac.qub.eeecs.game.cardDemo.Cards.HeroCard;
import uk.ac.qub.eeecs.game.cardDemo.Cards.MinionCard;
import uk.ac.qub.eeecs.game.cardDemo.Cards.SpellCard;
import uk.ac.qub.eeecs.game.cardDemo.Cards.StrengthCard;

/**
 * Created by Edward Muldrew on 08/02/2018.
 * This class is used to help reduce code on the Card Demo Screen and make and easier place to instantiate and create variables
 */

public class Utilities {
    // Instance Variables
    private ArrayList<Card> defaultArrayOfCards = new ArrayList<Card>();  // Create Computer Science Array List of Cards
    private Card compSciMinionSpellCards[] = new Card[6];
    private HeroCard compSciHero;
    private HeroCard medicineHero;
    private Card medicineMinionSpellCards[] = new Card[6];
    private final int MAX_SPELL_CARDS = 3;
    private int numberOfInitialHandCards = 5;
    private int numberOfInitialDeckCards = 6;
    int aspectRatioX = 5;
    int aspectRatioY = 3;
    float frontYPosition = 230;
    float middleYPosition = 155;
    float backYPosition = 85;

    // Constructors

    /**
     * The constructor creates and instiantiates all the spell, minion and hero cards for the respective decks/hands
     *
     * @param gameScreen
     * @param mGame
     */
    public Utilities(GameScreen gameScreen, Game mGame) {
        this.loadAndAddAseests(mGame);
        compSciHero = new HeroCard(1, "Matthew Collins", Card.Subject.COMPUTER_SCIENCE, Card.Rarity.RARE, "I am your hero", "CompSciHero", gameScreen, StrengthCard.Row.All, 8);
        compSciMinionSpellCards[0] = new SpellCard(2, "Virus", Card.Subject.COMPUTER_SCIENCE, Card.Rarity.COMMON, "Damage enemy", "VirusSpell", gameScreen, SpellCard.Ability.DAMAGE);
        compSciMinionSpellCards[1] = new SpellCard(4, "Ram", Card.Subject.COMPUTER_SCIENCE, Card.Rarity.COMMON, "Boost strength", "RamSpell", gameScreen, SpellCard.Ability.INCREASE);
        compSciMinionSpellCards[2] = new SpellCard(5, "Processor", Card.Subject.COMPUTER_SCIENCE, Card.Rarity.RARE, "Double points", "ProcessorSpell", gameScreen, SpellCard.Ability.DOUBLE_POINTS);
        compSciMinionSpellCards[3] = new MinionCard(6, "Keyboard", Card.Subject.COMPUTER_SCIENCE, Card.Rarity.COMMON, "Back Row", "KeyboardMinion", gameScreen, StrengthCard.Row.Back, 2);
        compSciMinionSpellCards[4] = new MinionCard(7, "Printer", Card.Subject.COMPUTER_SCIENCE, Card.Rarity.COMMON, "Middle Row", "PrinterMinion", gameScreen, StrengthCard.Row.Middle, 3);
        compSciMinionSpellCards[5] = new MinionCard(8, "Mouse", Card.Subject.COMPUTER_SCIENCE, Card.Rarity.UNCOMMON, "Front Row", "MouseMinion", gameScreen, StrengthCard.Row.Front, 5);

        medicineHero = new HeroCard(12, "Dr Phil", Card.Subject.MEDICINE, Card.Rarity.RARE, "I am your hero", "MedicineHero", gameScreen, StrengthCard.Row.All, 8);
        medicineMinionSpellCards[0] = new SpellCard(13, "Transplant", Card.Subject.MEDICINE, Card.Rarity.COMMON, "Double points", "TransplantSpell", gameScreen, SpellCard.Ability.DOUBLE_POINTS);
        medicineMinionSpellCards[1] = new SpellCard(14, "Plaster", Card.Subject.MEDICINE, Card.Rarity.UNCOMMON, "Boost strength", "PlasterSpell", gameScreen, SpellCard.Ability.INCREASE);
        medicineMinionSpellCards[2] = new SpellCard(15, "MedKit", Card.Subject.MEDICINE, Card.Rarity.COMMON, "Damage enemy", "MedKitSpell", gameScreen, SpellCard.Ability.DAMAGE);
        medicineMinionSpellCards[3] = new MinionCard(16, "Syringe", Card.Subject.MEDICINE, Card.Rarity.RARE, "Back Row", "SyringeMinion", gameScreen, StrengthCard.Row.Back, 5);
        medicineMinionSpellCards[4] = new MinionCard(17, "Stethoscope", Card.Subject.MEDICINE, Card.Rarity.COMMON, "Front Row", "StethoscopeMinion", gameScreen, StrengthCard.Row.Front, 3);
        medicineMinionSpellCards[5] = new MinionCard(18, "Antibiotic", Card.Subject.MEDICINE, Card.Rarity.UNCOMMON, "Middle Row", "AntibioticMinion", gameScreen, StrengthCard.Row.Middle, 2);
    }

    /**
     *  This method generates a computer science hand
     *
     * @param gameScreen
     * @param compSciHand
     * @return returns a newly generated hand with computer science spell , minion and hero cards
     */
    public Hand generateCompSciHand(GameScreen gameScreen, Hand compSciHand) {
        if (compSciHand.getTypeOfHand() == Hand.UserType.AI) {
            this.setAICardsBlank(compSciMinionSpellCards, gameScreen);
        }
        compSciHand.getCardsInHand().add(compSciHero);      // Originally add computer science hero to user Hand
        int spellCards = 0;
        // Randomly generate spell and minion cards and populate User Hand
        for (int i = 0; i <= numberOfInitialHandCards; ) {
            Random rand = new Random();
            int index = rand.nextInt(compSciMinionSpellCards.length);
            if (compSciMinionSpellCards[index].getClass() == MinionCard.class) {
                compSciHand.getCardsInHand().add(i + 1, new MinionCard((MinionCard) compSciMinionSpellCards[index], compSciMinionSpellCards[index].getBitmapName(), gameScreen));
                i++;
            } else if (spellCards < MAX_SPELL_CARDS) {
                compSciHand.getCardsInHand().add(i + 1, new SpellCard((SpellCard) compSciMinionSpellCards[index], compSciMinionSpellCards[index].getBitmapName(), gameScreen));
                i++;
                spellCards++;
            }
        }
        setCardXPosition(compSciHand);
        return compSciHand;
    }

    /**
     * This method generates a computer science deck
     *
     * @param gameScreen - a gamescreen is passed in to be used by a function
     * @returns the newly populated deck populated with computer science cards
     */
    public Deck generateCompSciDeck(GameScreen gameScreen) { // doesn't include
        Deck compSciDeck = new Deck(1, "User Deck", defaultArrayOfCards, gameScreen); // Create User Deck
        // Add User Deck - The remaining cards are drawn to the deck for the user to pick from throughout rounds
        int spellCards = 0;
        for (int i = 0; i <= numberOfInitialDeckCards; ) {
            Random rand = new Random();
            int index = rand.nextInt(compSciMinionSpellCards.length);
            if (compSciMinionSpellCards[index].getClass() == MinionCard.class) {
                compSciDeck.getDeckOfCards().add(i, new MinionCard((MinionCard) compSciMinionSpellCards[index], compSciMinionSpellCards[index].getBitmapName(), gameScreen));
                i++;
            } else if (spellCards < MAX_SPELL_CARDS) {
                compSciDeck.getDeckOfCards().add(i, new SpellCard((SpellCard) compSciMinionSpellCards[index], compSciMinionSpellCards[index].getBitmapName(), gameScreen));
                i++;
                spellCards++;
            }
        }
        return compSciDeck;
    }

    /**
     * This method generates the medicine hand which gives the user 1 hero, 3 max spell cards and the rest minion cards
     *
     * @param gameScreen   - the game screen is used for some of the methods
     * @param medicineHand - takes in a current medicine hand
     * @returns the newly populated hand with medicine spell cards
     */
    public Hand generateMedicineHand(GameScreen gameScreen, Hand medicineHand) {
        if (medicineHand.getTypeOfHand() == Hand.UserType.AI) {
            this.setAICardsBlank(medicineMinionSpellCards, gameScreen);
        }
        medicineHand.getCardsInHand().add(medicineHero);    // Originally add medicine hero to ai Hand
        int spellCards = 0;
        // Randomly generate spell and minion cards and populate AI Hand
        for (int i = 0; i <= numberOfInitialHandCards; ) {
            Random rand = new Random();
            int index = rand.nextInt(medicineMinionSpellCards.length);
            if (medicineMinionSpellCards[index].getClass() == MinionCard.class) {
                medicineHand.getCardsInHand().add(i + 1, new MinionCard((MinionCard) medicineMinionSpellCards[index], medicineMinionSpellCards[index].getBitmapName(), gameScreen));
                i++;
            } else if (spellCards < MAX_SPELL_CARDS) {
                medicineHand.getCardsInHand().add(i + 1, new SpellCard((SpellCard) medicineMinionSpellCards[index], medicineMinionSpellCards[index].getBitmapName(), gameScreen));
                i++;
                spellCards++;

            }
        }
        setCardXPosition(medicineHand);
        return medicineHand;
    }

    /**
     * This method generates a medicine deck which creates a deck of size 7 & populates the deck with minion & spell cards
     *
     * @param gameScreen - takes the gamescreen
     * @returns the newly populated deck with medicine minion & spell cards
     */
    public Deck generateMedicineDeck(GameScreen gameScreen) {
        Deck medicineDeck = new Deck(2, "AI Deck", defaultArrayOfCards, gameScreen); // Create AI Deck
        // Add AI Deck - The remaining cards are drawn to the deck for the AI to pick from throughout rounds
        int spellCards = 0;
        for (int i = 0; i <= numberOfInitialDeckCards; ) {
            Random rand = new Random();
            int index = rand.nextInt(medicineMinionSpellCards.length);
            if (medicineMinionSpellCards[index].getClass() == MinionCard.class) {
                medicineDeck.getDeckOfCards().add(i, new MinionCard((MinionCard) medicineMinionSpellCards[index], medicineMinionSpellCards[index].getBitmapName(), gameScreen));
                i++;
            } else if (spellCards < MAX_SPELL_CARDS) {
                medicineDeck.getDeckOfCards().add(i, new SpellCard((SpellCard) medicineMinionSpellCards[index], medicineMinionSpellCards[index].getBitmapName(), gameScreen));
                spellCards++;
                i++;
            }
        }
        return medicineDeck;
    }

    /**
     * This method is used to load and add all the assets which will be used by the game
     *
     * @param mGame - takes a game object as paramter to be used to get the asset manager
     */
    public void loadAndAddAseests(Game mGame) {
        // Load in the assets used by this layer
        AssetStore assetManager = mGame.getAssetManager();
        // Back of Cards
        assetManager.loadAndAddBitmap("MinionBack", "img/MinionCardBack.png");
        assetManager.loadAndAddBitmap("HeroBack", "img/HeroCardBack.png");
        assetManager.loadAndAddBitmap("SpellBack", "img/SpellCardBack.png");
        assetManager.loadAndAddBitmap("PassCoin", "img/PassCoin.png");
        assetManager.loadAndAddBitmap("UndoButton", "img/undoButton.png");
        assetManager.loadAndAddBitmap("PassCheck", "img/passPrompt.png");
        assetManager.loadAndAddBitmap("UserTurn", "img/userTurn.png");
        assetManager.loadAndAddBitmap("RoundEnd", "img/RoundEnd.jpg");
        assetManager.loadAndAddBitmap("NewRound", "img/NewRound.png");
        assetManager.loadAndAddBitmap("GameEndScreen", "img/GameEndScreen.jpg");
        assetManager.loadAndAddBitmap("Sign", "img/Sign.png");
        assetManager.loadAndAddBitmap("Back", "img/button/button_back.png");
        assetManager.loadAndAddBitmap("ExitPrompt", "img/exitPrompt.png");
        assetManager.loadAndAddBitmap("CardPlacePrompt", "img/cardPlacePrompt.png");
        // Compsci Hero bitmaps
        assetManager.loadAndAddBitmap("CompSciHero", "img/HeroCard.jpg");
        // Compsci minion bitmaps
        assetManager.loadAndAddBitmap("KeyboardMinion", "img/KeyboardMinion.jpg");
        assetManager.loadAndAddBitmap("MouseMinion", "img/MouseMinion.jpg");
        assetManager.loadAndAddBitmap("PrinterMinion", "img/PrinterMinion.jpg");
        //Compsci Spell card bitmaps
        assetManager.loadAndAddBitmap("BugSpell", "img/BugSpell.jpg");
        assetManager.loadAndAddBitmap("ProcessorSpell", "img/ProcessorSpell.jpg");
        assetManager.loadAndAddBitmap("RamSpell", "img/RamSpell.jpg");
        assetManager.loadAndAddBitmap("VirusSpell", "img/VirusSpell.jpg");

        // MedicineDeck
        assetManager.loadAndAddBitmap("MedicineHero", "img/HeroCard2.jpg");
        // Medicine minion bitmaps
        assetManager.loadAndAddBitmap("SyringeMinion", "img/SyringeMinion.jpg");
        assetManager.loadAndAddBitmap("StethoscopeMinion", "img/StethoscopeMinion.jpg");
        assetManager.loadAndAddBitmap("AntibioticMinion", "img/AntibioticMinion.jpg");
        // Medicine Spell Card Bitmaps
        assetManager.loadAndAddBitmap("TransplantSpell", "img/TransplantSpell.jpg");
        assetManager.loadAndAddBitmap("PlasterSpell", "img/PlasterSpell.jpg");
        assetManager.loadAndAddBitmap("MedKitSpell", "img/MedKitSpell.jpg");
    }

    /**
     * This method sets the initial hand position to be equally aligned. Sets both the start position and current position value
     *
     * @param hand
     * @return a hand with all cards position x equally aligned
     */
    public Hand setCardXPosition(Hand hand) {
        for (int i = 1; i < hand.getCardsInHand().size(); i++) {
            //   hand.getCardsInHand().get(i).setStartX(hand.getCardsInHand().get(i - 1).getStartX() + 35);
            hand.getCardsInHand().get(0).setStarterX(20);
            hand.getCardsInHand().get(i).setPositionX(hand.getCardsInHand().get(i - 1).getPositionX() + 38);
            hand.getCardsInHand().get(i).setStarterX(hand.getCardsInHand().get(i - 1).getPositionX() + 38);
        }
        return hand;
    }

    /**
     * Method to change the current game screen
     *
     * @param screen- takes in current game screen to swtich to a new game screen
     */
    public void changeToScreen(GameScreen screen, GameScreen currentGameScreen, Game mGame) {
        mGame.getScreenManager().removeScreen(currentGameScreen.getName());
        mGame.getScreenManager().addScreen(screen);
        mGame.getScreenManager().setAsCurrentScreen(screen.getName());
    }


    /**
     * This method ensures all the ai cards are set to be blank so that user can't gain a competitive advantage
     *
     * @param cards      - takes the respective cards for the ai and changes the bitmap
     * @param gameScreen - used as part of the set bitmap name method
     */
    public void setAICardsBlank(Card cards[], GameScreen gameScreen) {
        for (Card selectedCard : cards) {
            if (selectedCard.getClass() == MinionCard.class) {
                selectedCard.setBitmapName("MinionBack", gameScreen);
            } else {
                selectedCard.setBitmapName("SpellBack", gameScreen);
            }
        }
    }

    /**
     * This method changes an ai choosen card bitmap to the front of the card when the card is about to become active
     *
     * @param card       - takes in a card to change bitmap
     * @param gameScreen - gamescreen is used in the set bitmap method
     * @return - returns a card with the changed bitmap
     */
    public Card setAICardsNotBlank(Card card, GameScreen gameScreen) {
        switch (card.getCardName()) {
            case "Transplant":
                card.setBitmapName("TransplantSpell", gameScreen);
                break;

            case "Plaster":
                card.setBitmapName("PlasterSpell", gameScreen);
                break;

            case "MedKit":
                card.setBitmapName("MedKitSpell", gameScreen);
                break;

            case "Syringe":
                card.setBitmapName("SyringeMinion", gameScreen);
                break;

            case "Stethoscope":
                card.setBitmapName("StethoscopeMinion", gameScreen);
                break;

            case "Antibiotic":
                card.setBitmapName("AntibioticMinion", gameScreen);
                break;

            default:
                break;
        }
        return card;
    }

    /**
     * This method is used to check if the round has finished on the ai's behalf
     *
     * @param turnRecord         - takes in the turn record to check each users' turn
     * @param turnRecordPosition - position of turn number
     * @param mGame              - game object used
     * @return - method returns a true or false value which will then be used to change the respective game screen
     */
    public boolean roundAIFinish(Turn turnRecord, int turnRecordPosition, Game mGame) {
        if (turnRecord.getUserTurnRecord().get(turnRecordPosition).equals("false") && turnRecord.getAiTurnRecord().get(turnRecordPosition).equals("false")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method is used to check if the round has finished on the behalf of the user
     *
     * @param turnRecord         - takes in the turn record to check each users' turn
     * @param turnRecordPosition - position of turn number
     * @param mGame              - game object used
     */
    public void roundUserFinish(Turn turnRecord, int turnRecordPosition, Game mGame) {
        if (turnRecord.getAiTurnRecord().get(0).equals("null")) {
            mGame.getScreenManager().setAsCurrentScreen("aiDemoScreen");
        } else {
            if (turnRecord.getUserTurnRecord().get(turnRecordPosition).equals("false") && turnRecord.getAiTurnRecord().get(turnRecordPosition - 1).equals("false")) {
                mGame.getScreenManager().setAsCurrentScreen("roundEndScreen");
            } else {
                mGame.getScreenManager().setAsCurrentScreen("aiDemoScreen");
            }
        }
    }

    /**
     * This method is a piece of validation which will alter a cards y position based of their card row value
     *
     * @param choosenCard - the card object to be evaluated
     * @param y           - takes in the current y position
     * @returns the readjusted y position
     */
    public float checkCardRow(Card choosenCard, float y) {
        if (choosenCard.getClass() == MinionCard.class) {
            switch (((MinionCard) choosenCard).getCardRow()) {
                case Front:
                    y = frontYPosition;
                    break;

                case Middle:
                    y = middleYPosition;
                    break;

                case Back:
                    y = backYPosition;
                    break;
            }
        }
        return y;
    }

    /**
     * This method is a piece of validation to check if a choosen x & y values are within the range of the board
     *
     * @param x - x value
     * @return - returns the readjusted x value
     */
    public float checkIfCardOutOfRange(float x, float y) {
        if (x < 80) {
            x = 100;
        }
        if (x > 450) {
            x = 450;
        }
        if (x > 405 && y == frontYPosition) {
            x = 405;
        }
        return x;
    }


    /**
     * This method is used to ensure cards do not collide with each other
     *
     * @param activeCards - takes in the active cards on the board so the current card is not placed on top
     * @param x           - takes the x position
     * @param y           - takes the y position
     * @param choosenCard - takes the current card
     * @return - returns a card with the readjusted x and y values
     */
    public Card checkIfCardCollide(ArrayList<Card> activeCards, float x, float y, Card choosenCard) {
        y = checkCardRow(choosenCard, y);
        x = checkIfCardOutOfRange(x, y);
        if (activeCards.size() == 0) {
            choosenCard.setPosition(x, y);
        } else {
            for (Card selectedCard : activeCards) {
                if (y == selectedCard.getPositionY()) {
                    if (x < selectedCard.getPositionX()) {
                        x = x - 50;
                        checkIfCardOutOfRange(x, y);
                    } else if (x + 20 > selectedCard.getPositionX()) {
                        x = x + 50;
                        checkIfCardOutOfRange(x, y);
                    }
                }
            }
            choosenCard.setPosition(x, y);
        }
        return choosenCard;
    }
}
