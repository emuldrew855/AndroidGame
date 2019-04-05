package uk.ac.qub.eeecs.game.cardDemo.Containers;

import android.graphics.Bitmap;
import android.graphics.Rect;

import java.util.ArrayList;

import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.cardDemo.Cards.Card;

import static uk.ac.qub.eeecs.gage.world.GameObject.cardDimensions;
import static uk.ac.qub.eeecs.gage.world.GameObject.screenDimensions;

/**
 * Created by 40178070(Brandon) on 16/01/2018.
 */

public class ContainerManager {

    //containers for the relevant parts of the board
    private HandContainer mHandContainer;
    private DeckContainer mDeckContainer;
    private GraveContainer mGraveyardContainer;
    private HeroContainer mHeroContainer;
    private MeleeContainer mMeleeContainer;
    private RangedContainer mRangedContainer;
    private SupportContainer mSupportContainer;

    //Holds the card back image
    private Bitmap cardBack;

    //Sets up the position of the containers
    private void setUpContainerLayout(GameScreen gameScreen, int sizeOfDeck) {

        float cardX = cardDimensions.x;
        float cardY = cardDimensions.y;
        float playerScreenDimensionsWidth = screenDimensions.x;
        float playerScreenDimensionsHeight = screenDimensions.y;
        float spacerX = (playerScreenDimensionsWidth - (10 * cardX)) / 4;
        float spacerY = ((playerScreenDimensionsHeight / 2) % cardY) / 3;
        float spacerYHalf = (float) (spacerY * 0.5);

        mHandContainer = new HandContainer(spacerX, playerScreenDimensionsHeight - cardY - (2 * spacerY), cardX, cardY, 9, gameScreen);
        mDeckContainer = new DeckContainer((7 * cardX) + (spacerX * 2), playerScreenDimensionsHeight - cardY - (2 * spacerY), cardX, cardY, sizeOfDeck, gameScreen);
        mGraveyardContainer = new GraveContainer((7 * cardX) + (spacerX * 2), playerScreenDimensionsHeight - (2 * cardY) - (spacerY * 2) - spacerYHalf, cardX, cardY, 30, gameScreen);
        /*mHeroContainer = new HeroContainer();
        mMeleeContainer = new MeleeContainer();
        mRangedContainer = new RangedContainer();
        mSupportContainer = new SupportContainer();*/
    }

    //Draws the containers to the screen
    public void drawContainers(IGraphics2D graphics2D) {
        //drawing each container
        graphics2D.drawRect(mHandContainer.getContainerRectangle(), mHandContainer.getContainerPaint());
        graphics2D.drawRect(mGraveyardContainer.getContainerRectangle(), mGraveyardContainer.getContainerPaint());
        graphics2D.drawRect(mDeckContainer.getContainerRectangle(), mDeckContainer.getContainerPaint());
       /* graphics2D.drawRect(mHeroContainer.getContainerRectangle(), mHeroContainer.getContainerPaint());
        graphics2D.drawRect(mMeleeContainer.getContainerRectangle(), mMeleeContainer.getContainerPaint());
        graphics2D.drawRect(mRangedContainer.getContainerRectangle(), mRangedContainer.getContainerPaint());
        graphics2D.drawRect(mSupportContainer.getContainerRectangle(), mSupportContainer.getContainerPaint());
   */ }

   //Draws the graveyard cards
    public void drawGraveYardCards(IGraphics2D graphics2D) {
        //if not empty, draw back of card on top of graveyard
        if (!mGraveyardContainer.isContainerEmpty()) {
            graphics2D.drawBitmap(cardBack, null, mGraveyardContainer.getContainerRectangle(), null);
        }
    }

    //Draws the deck cards
    public void drawDeckCard(IGraphics2D graphics2D) {
        if (!mDeckContainer.isContainerEmpty()) {
            graphics2D.drawBitmap(cardBack, null, mDeckContainer.getContainerRectangle(), null);
        }
    }

    //Removes a card from a container
    private void findCardToRemove(ArrayList<Integer> cardInContainer, int pos){
        for(int i=0;i<cardInContainer.size();i++){
            if(cardInContainer.get(i)==pos){
                cardInContainer.remove(i);
            }
        }
    }
}