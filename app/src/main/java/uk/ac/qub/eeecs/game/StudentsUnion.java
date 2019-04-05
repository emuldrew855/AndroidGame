package uk.ac.qub.eeecs.game;

import uk.ac.qub.eeecs.gage.world.GameScreen;
import android.graphics.Color;

import java.util.List;
import java.util.Random;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetStore;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.util.GraphicsHelper;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.game.cardDemo.CardDemoScreen;
import uk.ac.qub.eeecs.game.cardDemo.Cards.PlayerSprite;
import java.util.ArrayList;
/**
 * Created by Conor on 22/03/2018.
 */


public class StudentsUnion extends GameScreen {
    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////
    private final float LEVEL_WIDTH = 480.0f;
    private final float LEVEL_HEIGHT = 270.0f;
    /**
     * Define the buttons for playing the 'games'
     */
    private LayerViewport mLayerViewport;
    private ScreenViewport mScreenViewport;
    private PushButton upButton;
    private PushButton downButton;
    private PushButton leftButton;
    private PushButton rightButton;
    private PushButton interactionButton;
    private GameObject mStudentsUnionMap;
    private PlayerSprite playerSprite;
    private PlayerSprite AISprite;
    private double timeSpentWalking;
    ArrayList<PlayerSprite> AISprites;
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
    public enum LastStepTaken{
        LEFT, RIGHT
    }

    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Create a simple menu screen
     *
     * @param game Game to which this screen belongs
     */
    public StudentsUnion(Game game) {
        super("StudentsUnion", game);

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

        // Load in the bitmaps used on the main menu screen
        AssetStore assetManager = mGame.getAssetManager();


        assetManager.loadAndAddBitmap("LocationArrow", "img/LocationArrow.png");
        assetManager.loadAndAddBitmap("UpArrow", "img/UpArrow.png");
        assetManager.loadAndAddBitmap("DownArrow", "img/DownArrow.png");
        assetManager.loadAndAddBitmap("LeftArrow", "img/LeftArrow.png");
        assetManager.loadAndAddBitmap("RightArrow", "img/RightArrow.png");
        //Player Sprite walking images
        assetManager.loadAndAddBitmap("UpStill", "img/UpStill.png");
        assetManager.loadAndAddBitmap("UpLeft", "img/UpLeft.png");
        assetManager.loadAndAddBitmap("UpRight", "img/UpRight.png");

        assetManager.loadAndAddBitmap("DownStill", "img/DownStill.png");
        assetManager.loadAndAddBitmap("DownLeft", "img/DownLeft.png");
        assetManager.loadAndAddBitmap("DownRight", "img/DownRight.png");

        assetManager.loadAndAddBitmap("RightStill", "img/RightStill.png");
        assetManager.loadAndAddBitmap("RightLeft", "img/RightLeft.png");
        assetManager.loadAndAddBitmap("RightRight", "img/RightRight.png");

        assetManager.loadAndAddBitmap("LeftStill", "img/LeftStill.png");
        assetManager.loadAndAddBitmap("LeftLeft", "img/;LeftLeft.png");
        assetManager.loadAndAddBitmap("LeftRight", "img/LeftRight.png");

        // Loading the AI sprites
        assetManager.loadAndAddBitmap("UpStillAI", "img/UpStillAI.png");
        assetManager.loadAndAddBitmap("DownStillAI", "img/DownStillAI.png");
        assetManager.loadAndAddBitmap("RightStillAI", "img/RightStillAI.png");
        assetManager.loadAndAddBitmap("LeftStillAI", "img/LeftStillAI.png");

        assetManager.loadAndAddBitmap("InteractButton", "img/Interact.png");

        // Define the spacing that will be used to position the buttons
        int spacingX = game.getScreenWidth() / 5;
        int spacingY = game.getScreenHeight() / 3;

        mStudentsUnionMap = new GameObject(LEVEL_WIDTH / 2.0f,
                LEVEL_HEIGHT / 2.0f, LEVEL_WIDTH, LEVEL_HEIGHT, getGame()
                .getAssetManager().getBitmap("StudentsUnionMap"), this);

        // Create the trigger buttons
        upButton = new PushButton(
                spacingX * 1.5f, spacingY * 2.2f, spacingX*.56f, spacingY*0.4f, "UpArrow", this);
        downButton = new PushButton(
                spacingX * 1.5f, spacingY * 1.1f, spacingX*.56f, spacingY*0.4f, "DownArrow", this);
       leftButton = new PushButton(
                spacingX * 0.75f, spacingY * 1.6f, spacingX*.56f, spacingY*0.4f, "LeftArrow", this);
        rightButton = new PushButton(
                spacingX * 2.25f, spacingY * 1.6f, spacingX*.56f, spacingY*0.4f, "RightArrow", this);
        interactionButton = new PushButton(
                spacingX * 5.0f, spacingY * 1.6f, spacingX*.56f, spacingY*0.4f, "InteractButton", this);
        playerSprite = new PlayerSprite(this, "DownStill");
        AISprites = generateAISprites();


    }

    /**
     * Update the menu screen
     *
     * @param elapsedTime Elapsed time information
     */
    @Override
    public void update(ElapsedTime elapsedTime) {

        // Process any touch events occurring since the update
        Input input = mGame.getInput();
        Direction direction;
        LastStepTaken lastStep = LastStepTaken.LEFT;
        List<TouchEvent> touchEvents = input.getTouchEvents();
        if (touchEvents.size() > 0) {

            // Just check the first touch event that occurred in the frame.
            // It means pressing the screen with several fingers may not
            // trigger a 'button', but, hey, it's an exceedingly basic menu.
            TouchEvent touchEvent = touchEvents.get(0);

            // Update each button and transition if needed
            upButton.update(elapsedTime);
            downButton.update(elapsedTime);
            leftButton.update(elapsedTime);
            rightButton.update(elapsedTime);
            interactionButton.update(elapsedTime);


            playerSprite.update(elapsedTime);


            if (upButton.isPushTriggered()) {
                direction = Direction.UP;
                walk(direction, playerSprite, elapsedTime, lastStep);
                changeStep(lastStep);
            }
            else if (downButton.isPushTriggered()) {
                direction = Direction.DOWN;
                walk(direction, playerSprite, elapsedTime, lastStep);
                changeStep(lastStep);
            }
            else if (leftButton.isPushTriggered()) {
                direction = Direction.LEFT;
                walk(direction, playerSprite, elapsedTime, lastStep);
                changeStep(lastStep);
            }
            else if (rightButton.isPushTriggered()) {
                direction = Direction.RIGHT;
                walk(direction, playerSprite, elapsedTime, lastStep);
                changeStep(lastStep);
            }
            //else if (interactionButton.isPushTriggered())
              //interact(direction, playerSprite);
        }

    }


    /**
     * Draw the menu screen
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {


        // Clear the screen and draw the buttons
        graphics2D.clear(Color.WHITE);
        mStudentsUnionMap.draw(elapsedTime, graphics2D, mLayerViewport, mScreenViewport);
        upButton.draw(elapsedTime, graphics2D, null, null);
        downButton.draw(elapsedTime, graphics2D, null, null);
        leftButton.draw(elapsedTime, graphics2D, null, null);
        rightButton.draw(elapsedTime, graphics2D, null, null);
        interactionButton.draw(elapsedTime, graphics2D, null, null);
        playerSprite.draw(elapsedTime, graphics2D, null, null);
        for(int i = 0; i < AISprites.size(); i++){
            AISprites.get(i).draw(elapsedTime, graphics2D, null, null);
        }

    }

    void walk(Direction direction, PlayerSprite playerSprite, ElapsedTime elapsedTime, LastStepTaken lastStep)
    {
        timeSpentWalking = elapsedTime.totalTime;

        if(direction == Direction.UP)
        {
            playerSprite.setPosition(playerSprite.getPositionX(), playerSprite.getPositionY()+12.5f);
                    if(timeSpentWalking+ 0.5 < elapsedTime.totalTime && lastStep == LastStepTaken.LEFT){
                    playerSprite.setmBitmap(this.getGame().getAssetManager().getBitmap("UpLeft"));

                    }
                    else if(timeSpentWalking+ 0.5 < elapsedTime.totalTime && lastStep == LastStepTaken.RIGHT){
                        playerSprite.setmBitmap(this.getGame().getAssetManager().getBitmap("UpRight"));
                    }
        }
        else if(direction == Direction.DOWN)
        {
            playerSprite.setPosition(playerSprite.getPositionX(), playerSprite.getPositionY()-12.5f);
            if(timeSpentWalking+ 0.5 < elapsedTime.totalTime && lastStep == LastStepTaken.LEFT){
                playerSprite.setmBitmap(this.getGame().getAssetManager().getBitmap("DownLeft"));

            }
            else if(timeSpentWalking+ 0.5 < elapsedTime.totalTime && lastStep == LastStepTaken.RIGHT){
                playerSprite.setmBitmap(this.getGame().getAssetManager().getBitmap("DownRight"));
            }
        }
        else if(direction == Direction.LEFT)
        {
            playerSprite.setPosition(playerSprite.getPositionX()-12.5f, playerSprite.getPositionY());
            if(timeSpentWalking+ 0.5 < elapsedTime.totalTime && lastStep == LastStepTaken.LEFT){
                playerSprite.setmBitmap(this.getGame().getAssetManager().getBitmap("LeftLeft"));

            }
            else if(timeSpentWalking+ 0.5 < elapsedTime.totalTime && lastStep == LastStepTaken.RIGHT){
                playerSprite.setmBitmap(this.getGame().getAssetManager().getBitmap("LeftRight"));
            }
        }
        else if(direction == Direction.RIGHT)
        {
            playerSprite.setPosition(playerSprite.getPositionX()+12.5f, playerSprite.getPositionY());
            if(timeSpentWalking+ 0.5 < elapsedTime.totalTime && lastStep == LastStepTaken.LEFT){
                playerSprite.setmBitmap(this.getGame().getAssetManager().getBitmap("RightLeft"));

            }
            else if(timeSpentWalking+ 0.5 < elapsedTime.totalTime && lastStep == LastStepTaken.RIGHT){
                playerSprite.setmBitmap(this.getGame().getAssetManager().getBitmap("RightRight"));
            }
        }
    }
    /*
    void interact(Direction direction, PlayerSprite playerSprite){

        if(direction == direction.UP){
        if((playerSprite.getPositionX() && playerSprite.getPositionY()) = ())
        }
        if(direction == direction.DOWN){

        }
        if(direction == direction.LEFT){

        }
        if(direction == direction.RIGHT){

        }
    }*/

    public ArrayList<PlayerSprite> generateAISprites(){
        PlayerSprite AISprite;
        Random rand = new Random();
       ArrayList<PlayerSprite> AISprites = new ArrayList<>();
        for(int i = 0; i < rand.nextInt(15); i++){

            AISprite  = new PlayerSprite(this, randomImageDirection());
            AISprite.setPosition(rand.nextFloat(), rand.nextFloat());
            for(int y = 0; y < AISprites.size(); y++){
                if(((AISprites.get(i).getPositionY() > AISprites.get(i-1).getPositionY() +25.f) ||(AISprites.get(i).getPositionY() < AISprites.get(i-1).getPositionY() -25.f))
                        && ((AISprites.get(i).getPositionX() > AISprites.get(i-1).getPositionX() +25.f) || (AISprites.get(i).getPositionX() < AISprites.get(i-1).getPositionX() -25.f)) ){
                    AISprites.add(AISprite);
                }
            }
        }
        return AISprites;
    }
    String randomImageDirection() {
        Random rand = new Random();
        String bitmapName = null;
        int randomImageDirection = rand.nextInt(4);
        if(randomImageDirection == 0)
        {
            bitmapName = "UpStillAI";
        }
        if(randomImageDirection == 1)
        {
            bitmapName = "DownStillAI";
        }
        if(randomImageDirection == 2)
        {
            bitmapName = "LeftStillAI";
        }
        if(randomImageDirection == 3)
        {
            bitmapName = "RightStillAI";
        }
        return bitmapName;
    }
    LastStepTaken changeStep(LastStepTaken lastStep) {
        if(lastStep == LastStepTaken.LEFT){
             lastStep = LastStepTaken.RIGHT;

        }
        else if(lastStep == LastStepTaken.RIGHT){
            lastStep = LastStepTaken.LEFT;

        }
        return lastStep;
    }
}
