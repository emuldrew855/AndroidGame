package uk.ac.qub.eeecs.game.cardDemo;

import android.graphics.Color;
import android.graphics.Paint;


import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetStore;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.audio.Sound;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.game.MenuScreen;



/**
 * Created by Jack McNaughton on 27/11/2017.
 */

public class TutorialScreen extends GameScreen {
    private final float LEVEL_WIDTH = 1000.0f;
    private final float LEVEL_HEIGHT = 1000.0f;
    private ScreenViewport mScreenViewport;
    private LayerViewport mLayerViewport;

    //Game Object used for background
    private GameObject mMenuBG;

    //Game Object used for images of the main board
    private GameObject mBoardBG1;
    private GameObject mBoardBG2;

    //Game Object used for images of Hero cards
    private GameObject mHeroCardFront1;
    private GameObject mHeroCardFront2;
    private GameObject mHeroCardFront3;

    //Game Object used for images of Minion Cards
    private GameObject mMinionCardFront1;
    private GameObject mMinionCardFront2;
    private GameObject mMinionCardFront3;

    //Game Object used for images of Spell cards
    private GameObject mSpellCardFront1;
    private GameObject mSpellCardFront2;
    private GameObject mSpellCardFront3;

    //Push buttons used to transition between different parts of tutorial
    private PushButton mBack;
    private PushButton mForward;

    //Sound to go off when button is clicked
    private Sound buttonClick;

    //interger used to keep track of what part of the tutorial it is on
    private int currentPage;




    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    /**
     *
     * //@param game Game to which this screen belongs
     */
    public TutorialScreen(Game mGame) {
        super("tutorialScreen", mGame);
        currentPage = 0;
        //Gets all needed images from asset store
        AssetStore assetManager = mGame.getAssetManager();
        assetManager.loadAndAddBitmap("HeroFront", "img/HeroCard.jpg");
        assetManager.loadAndAddBitmap("MinionFront", "img/KeyboardMinion.jpg");
        assetManager.loadAndAddBitmap("SpellFront", "img/BugSpell.jpg");
        assetManager.loadAndAddBitmap("Board", "img/board.png");
        assetManager.loadAndAddBitmap("Back", "img/buttons/button_back.png");
        assetManager.loadAndAddBitmap("Forward", "img/buttons/button_forward.png");
        assetManager.loadAndAddBitmap("MenuBackground", "img/SplashScreen.png");
        //Gets all needed sounds from asset store
        assetManager.loadAndAddSound("buttonClick", "music/buttonclick.mp3");
        buttonClick = mGame.getAssetManager().getSound("buttonClick");

        mScreenViewport = new ScreenViewport(0, 0, mGame.getScreenWidth(),
                mGame.getScreenHeight());

        if (mScreenViewport.width > mScreenViewport.height)
            mLayerViewport = new LayerViewport(240.0f, 240.0f
                    * mScreenViewport.height / mScreenViewport.width, 240,
                    240.0f * mScreenViewport.height / mScreenViewport.width);
        else
            mLayerViewport = new LayerViewport(240.0f * mScreenViewport.height
                    / mScreenViewport.width, 240.0f, 240.0f
                    * mScreenViewport.height / mScreenViewport.width, 240);

        mMinionCardFront1 = new GameObject(580.0f, 260.0f, 80.0f, 140.0f,
                getGame().getAssetManager().getBitmap("MinionFront"), this);
        mMinionCardFront2 = new GameObject(100.0f, 260.0f, 80.0f, 140.0f,
                getGame().getAssetManager().getBitmap("MinionFront"), this);
        mMinionCardFront3 = new GameObject(100.0f, 140.0f, 80.0f, 140.0f,
                getGame().getAssetManager().getBitmap("MinionFront"), this);

        mHeroCardFront1 = new GameObject(490.0f, 260.0f, 80.0f, 140.0f,
                getGame().getAssetManager().getBitmap("HeroFront"), this);
        mHeroCardFront2 = new GameObject(300.0f, 260.0f, 80.0f, 140.0f,
                getGame().getAssetManager().getBitmap("HeroFront"), this);
        mHeroCardFront3 = new GameObject(100.0f, 630.0f, 80.0f, 140.0f,
                getGame().getAssetManager().getBitmap("HeroFront"), this);

        mSpellCardFront1 = new GameObject(400.0f, 260.0f, 80.0f, 140.0f,
                getGame().getAssetManager().getBitmap("SpellFront"), this);
        mSpellCardFront2 = new GameObject(200.0f, 260.0f, 80.0f, 140.0f,
                getGame().getAssetManager().getBitmap("SpellFront"), this);
        mSpellCardFront3 = new GameObject(100.0f, 400.0f, 80.0f, 140.0f,
                getGame().getAssetManager().getBitmap("SpellFront"), this);

        mBoardBG1 = new GameObject(150.0f, 270.0f, 240.0f, 160.0f,
                getGame().getAssetManager().getBitmap("Board"), this);
        mBoardBG2 = new GameObject(150.0f, 270.0f, 240.0f, 160.0f,
                getGame().getAssetManager().getBitmap("Board"), this);

        mBack = new PushButton(750.f,900.0f,350.0f,250.0f,"Back",this);
        mForward = new PushButton(1250.f,900.0f,350.0f,250.0f,"Forward",this);

        mMenuBG = new GameObject(LEVEL_WIDTH / 2.0f,
                LEVEL_HEIGHT / 2.0f, LEVEL_WIDTH, LEVEL_HEIGHT, getGame().getAssetManager().getBitmap("MenuBackground"), this);


    }

    private void changeToScreen(GameScreen screen) {
        mGame.getScreenManager().removeScreen(this.getName());
        mGame.getScreenManager().addScreen(screen);
        mGame.getScreenManager().setAsCurrentScreen(screen.getName());
    }

    /**
     * Update the card demo screen
     *
     * @param elapsedTime Elapsed time information
     */
    @Override
    public void update(ElapsedTime elapsedTime) {
        // Process any touch events occurring since the last update


        Input input = mGame.getInput();
        List<TouchEvent> touchEvents = input.getTouchEvents();
        if (mBack.isPushTriggered()) {
            currentPage = currentPage - 1;
            buttonClick.play();
        }
        if (mForward.isPushTriggered()) {
            currentPage = currentPage + 1;
            buttonClick.play();
        }

        mBack.update(elapsedTime);
        mForward.update(elapsedTime);

    }

    /**
     * Draw the card demo screen
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {


        Input input = mGame.getInput();
        List<TouchEvent> touchEvents = input.getTouchEvents();

        if (currentPage < 0){
            changeToScreen(new MenuScreen(mGame));
        }

        if (currentPage == 0){
            graphics2D.clear(Color.WHITE);
            mMenuBG.draw(elapsedTime, graphics2D, mLayerViewport, mScreenViewport);
            mBack.draw(elapsedTime,graphics2D);
            mForward.draw(elapsedTime,graphics2D);
            drawIntro(elapsedTime,graphics2D);
        }
        if (currentPage == 1){
            graphics2D.clear(Color.WHITE);
            mMenuBG.draw(elapsedTime, graphics2D, mLayerViewport, mScreenViewport);
            mBack.draw(elapsedTime,graphics2D);
            mForward.draw(elapsedTime,graphics2D);
            drawRules1(elapsedTime,graphics2D);
        }
        if (currentPage == 2){
            graphics2D.clear(Color.WHITE);
            mMenuBG.draw(elapsedTime, graphics2D, mLayerViewport, mScreenViewport);
            mBack.draw(elapsedTime,graphics2D);
            mForward.draw(elapsedTime,graphics2D);
            drawRules2(elapsedTime,graphics2D);
        }
        if (currentPage == 3){
            graphics2D.clear(Color.WHITE);
            mMenuBG.draw(elapsedTime, graphics2D, mLayerViewport, mScreenViewport);
            mBack.draw(elapsedTime,graphics2D);
            mForward.draw(elapsedTime,graphics2D);
            drawCards1(elapsedTime,graphics2D);
        }
        if (currentPage == 4){
            graphics2D.clear(Color.WHITE);
            mMenuBG.draw(elapsedTime, graphics2D, mLayerViewport, mScreenViewport);
            mBack.draw(elapsedTime,graphics2D);
            mForward.draw(elapsedTime,graphics2D);
            drawCards2(elapsedTime,graphics2D);
        }
        if (currentPage == 5){
            graphics2D.clear(Color.WHITE);
            mMenuBG.draw(elapsedTime, graphics2D, mLayerViewport, mScreenViewport);
            mBack.draw(elapsedTime,graphics2D);
            mForward.draw(elapsedTime,graphics2D);
            drawCards3(elapsedTime,graphics2D);
        }
        if (currentPage == 6){
            graphics2D.clear(Color.WHITE);
            mMenuBG.draw(elapsedTime, graphics2D, mLayerViewport, mScreenViewport);
            mBack.draw(elapsedTime,graphics2D);
            mForward.draw(elapsedTime,graphics2D);
            drawRows(elapsedTime,graphics2D);
        }
        if (currentPage == 7){
            graphics2D.clear(Color.WHITE);
            mMenuBG.draw(elapsedTime, graphics2D, mLayerViewport, mScreenViewport);
            mBack.draw(elapsedTime,graphics2D);
            mForward.draw(elapsedTime,graphics2D);
            drawHTP(elapsedTime,graphics2D);
        }
        if (currentPage == -1 || currentPage == 8){
            changeToScreen(new MenuScreen(mGame));
        }
    }

    public void drawIntro(ElapsedTime elapsedTime, IGraphics2D graphics2D){



        Paint textColor = new Paint();
        textColor.setColor(Color.BLACK);
        textColor.setTextSize(30.0f);
        graphics2D.drawText("Introduction",0,30,textColor);
        graphics2D.drawText("Greetings and welcome to Quent, a Card game based upon the popular game Gwent with the twist of instead of using myth and monsters,",0,90,textColor);
        graphics2D.drawText("it uses school and subjects.",0,120,textColor);



    }
    public void drawRules1(ElapsedTime elapsedTime, IGraphics2D graphics2D){


        Paint textColor = new Paint();
        textColor.setColor(Color.BLACK);
        textColor.setTextSize(30.0f);
        graphics2D.drawText("Rules",0,30,textColor);
        graphics2D.drawText("Quent is a card game based upon the main rules of Gwent so those who have played will recognise these rules but if you haven't here's a quick",0,90,textColor);
        graphics2D.drawText("summery.",0,120,textColor);

        graphics2D.drawText("In the game you and your opponent will have 2 decks of 15 spell and/or minion cards and 1 hero card each which are placed on a board.\n",0,180,textColor);
        mBoardBG1.draw(elapsedTime, graphics2D);
        mMinionCardFront1.draw(elapsedTime, graphics2D);
        mHeroCardFront1.draw(elapsedTime, graphics2D);
        mSpellCardFront1.draw(elapsedTime, graphics2D);
    }

    public void drawRules2(ElapsedTime elapsedTime, IGraphics2D graphics2D){

        Paint textColor = new Paint();
        textColor.setColor(Color.BLACK);
        textColor.setTextSize(30.0f);


        graphics2D.drawText("Each player will start out with 5 cards in their hand although they can draw a card.",0,30,textColor);

        graphics2D.drawText("There are 3 rounds in a game of quent and to win the game, you must win 2 rounds.",0,90,textColor);

        graphics2D.drawText("In order to win a round you must have a higher total score.",0,150,textColor);

        graphics2D.drawText("In order to get a higher score, you will have to make sure that the sum total of the scores in each of your rows are greater than your opponents..",0,210,textColor);



    }
    public void drawCards1(ElapsedTime elapsedTime, IGraphics2D graphics2D){


        Paint textColor = new Paint();
        textColor.setColor(Color.BLACK);
        textColor.setTextSize(30.0f);



        graphics2D.drawText("Cards",0,30,textColor);
        graphics2D.drawText("The cards are the main way that you win at Quent.",0,90,textColor);
        graphics2D.drawText("There are 3 main types of cards: Creatures, Spells and Heroes.",0,150,textColor);
        mMinionCardFront2.draw(elapsedTime, graphics2D);
        mHeroCardFront2.draw(elapsedTime, graphics2D);
        mSpellCardFront2.draw(elapsedTime, graphics2D);
    }
    public void drawCards2(ElapsedTime elapsedTime, IGraphics2D graphics2D){

        Paint textColor = new Paint();
        textColor.setColor(Color.BLACK);
        textColor.setTextSize(30.0f);



        graphics2D.drawText("Creature cards are cards that you place on the field and whose total strength will equal your total score enabling you to win.",0,30,textColor);
        mMinionCardFront3.draw(elapsedTime, graphics2D);//

        graphics2D.drawText("Spell cards are cards that you can only activate once, they will cause some kind of effect to occur and then they will be sent to the graveyard.",0,270,textColor);
        graphics2D.drawText("However spell cards have no strength so placing to many in your deck could result in you losing a round.",0,300,textColor);
        mSpellCardFront3.draw(elapsedTime, graphics2D);//

        graphics2D.drawText("Hero cards are cards that do not start in your deck and instead are placed at the side of the field and can be summoned to the field similar to a",0,500,textColor);
        graphics2D.drawText("creature card while also having an ability similar to a spell card. Warning you can only use the hero card once so choose wisely when to use it.",0,530,textColor);
        mHeroCardFront3.draw(elapsedTime, graphics2D);//
    }
    public void drawCards3(ElapsedTime elapsedTime, IGraphics2D graphics2D){

        Paint textColor = new Paint();
        textColor.setColor(Color.BLACK);
        textColor.setTextSize(30.0f);

        graphics2D.drawText("The cards strength will be displayed in the bottom right hand corner of the card. When the cards is placed on a specific row, their strength",0,30,textColor);
        graphics2D.drawText("will be added to the row's overall score. At the end of the round, if the total score between all three rows is greater that your opponent's",0,60,textColor);
        graphics2D.drawText("you will win that round.",0,90,textColor);

        graphics2D.drawText("Certain cards will have specific abilities however these usually will come with drawbacks. All spell cards will have an ability of some kind,",0,150,textColor);
        graphics2D.drawText("however they have no strength and can only be used once and can be useless depending on the situation on the board. Some minion cards can",0,180,textColor);
        graphics2D.drawText("have effects, however these cards will usually have relatively low strength values compared to minions with no effect. Lastly Hero cards will usually\n",0,210,textColor);
        graphics2D.drawText("have a good ability however they can only be used once during the whole game and unlike other cards you can only have one copy of them in",0,240,textColor);
        graphics2D.drawText("your deck.",0,270,textColor);
    }

    public void drawRows(ElapsedTime elapsedTime, IGraphics2D graphics2D){
        Paint textColor = new Paint();
        textColor.setColor(Color.BLACK);
        textColor.setTextSize(30.0f);

        graphics2D.drawText("Rows",0,30,textColor);

        graphics2D.drawText("The last thing to mention is the rows mechanic as this plays a significant role in Quent. There are 3 different row: Front, Middle and Back.\n",0,90,textColor);
        graphics2D.drawText("Each seperate row has a score which increases based on the total strength of the cards within that row. Each card will have a symbol to show \n",0,120,textColor);
        graphics2D.drawText("what row it can be placed on as only certain cards can be placed on all three rows.\n",0,150,textColor);
        mBoardBG2.draw(elapsedTime, graphics2D);

    }

    public void drawHTP(ElapsedTime elapsedTime, IGraphics2D graphics2D){
        Paint textColor = new Paint();
        textColor.setColor(Color.BLACK);
        textColor.setTextSize(30.0f);

        graphics2D.drawText("How to Play",0,30,textColor);

        graphics2D.drawText("Playing Qwent is not difficult in the slightest and should be very easy to pick up",0,90,textColor)
        ;
        graphics2D.drawText("At the beginning, you will be provided with 5 random cards from your deck as well as your hero card",0,150,textColor);
        graphics2D.drawText("Each turn choose one card from your hand and place on the field by sliding the chosen card up and clicking yes",0,180,textColor);
        graphics2D.drawText("After this the opposing player/AI will take their turn",0,210,textColor);
        graphics2D.drawText("Once you feel as though you've done enough in the round, click on the pass button in the corner",0,240,textColor);
        graphics2D.drawText("After this you will no longer be able to place anymore cards on the board for the remainder of the round.",0,270,textColor);
        graphics2D.drawText("Once the other player/AI passes your total scores will be compared, the player with the highest score wins the round ",0,300,textColor);
        graphics2D.drawText("At the start of any following rounds you will get one add one addional card",0,330,textColor);
        graphics2D.drawText("After a player has won 2 rounds, they win the game",0,360,textColor);
    }
}
