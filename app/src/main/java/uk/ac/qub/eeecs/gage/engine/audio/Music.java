package uk.ac.qub.eeecs.gage.engine.audio;

import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;

import java.io.IOException;

/**
 *Music clip
 *Worked on by Grace 40172213
 *Reference/s: Code adapted from Unimon54
 *
 * @version 1.1
 */
public class Music implements OnCompletionListener {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    //Media player that will be used to playback this music clip
    private MediaPlayer mMediaPlayer;

    //Flag indicating if playback can commence
    private boolean mIsPrepared = false;

    //Asset filename
    private String mAssetFile;

    private SharedPreferences.Editor edit;
    private SharedPreferences pref;
    private boolean continuePlaying;

    private int maxVolume, currVolume;

    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Create a new music clip
     *
     * @param assetDescriptor Asset descriptor linked to this audio file
     */
    public Music(AssetFileDescriptor assetDescriptor) {
        mAssetFile = assetDescriptor.getFileDescriptor().toString();
        maxVolume = 50;
        currVolume = 25;
        continuePlaying = true;

        //Create a new play player linked to the specified music asset
        mMediaPlayer = new MediaPlayer();
        try {
            //Link the data source
            mMediaPlayer.setDataSource(assetDescriptor.getFileDescriptor(),
                    assetDescriptor.getStartOffset(),
                    assetDescriptor.getLength());

            //Prep the audio for playback
            mMediaPlayer.prepare();
            mIsPrepared = true;

            //Add an on completion listener for the clip
            mMediaPlayer.setOnCompletionListener(this);
        } catch (IOException e) {
            String errorTag = "Gage Error:";
            String errorMessage = "Music clip " + mAssetFile
                    + " cannot be loaded.";
            Log.w(errorTag, errorMessage);
        }
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Play the music clip.
     *
     * Note: If the music clip is already playing the play request is ignored.
     */
    public void play() {
        if(continuePlaying) {
            if (mMediaPlayer.isPlaying())
                return;
            try {
                synchronized (this) {
                    //Start the clip, preparing it if needed
                    if (!mIsPrepared)
                        mMediaPlayer.prepare();
                    mMediaPlayer.start();
                    mMediaPlayer.isPlaying();
                }
            } catch (Exception e) { //Either IllegalStateException or IOException
                String errorTag = "Error:";
                String errorMessage = "Music clip " + mAssetFile
                        + " cannot be played.";
                Log.w(errorTag, errorMessage);
            }
        }
    }

    //Stop the music clip
    public void stop() {
        mMediaPlayer.stop();
        mMediaPlayer.release();
        synchronized (this) {
            mIsPrepared = false;
        }
    }

    //Pause the music clip
    public void pause() {
        if (mMediaPlayer.isPlaying())
            mMediaPlayer.pause();
        continuePlaying = false;
    }

    protected void onPause(){

    }

    protected void onResume(){

    }

    public void setContinuePlaying(boolean continuePlaying1){
        continuePlaying = continuePlaying1;
    }

    /**
     * Determine if the music clip will loop
     *
     * @param looping Boolean true to loop, false for play once.
     */
    public void setLopping(boolean looping) {
        mMediaPlayer.setLooping(looping);
    }

    /**
     * Determine if the music clip is currently playing
     *
     * @return Boolean true if the music clip is currently playing, otherwise
     * false
     */
    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    /**
     * Determine if the music clip is set to loop
     *
     * @return Boolean true if the clip is looping, otherwise false
     */
    public boolean isLooping() {
        return mMediaPlayer.isLooping();
    }

    /**
     * Dispose of the music clip
     */
    public void dispose() {
        if (mMediaPlayer.isPlaying())
            mMediaPlayer.stop();
        mMediaPlayer.release();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.media.MediaPlayer.OnCompletionListener#onCompletion(android.media
     * .MediaPlayer)
     */
    public void onCompletion(MediaPlayer player) {
        synchronized (this) {
            mIsPrepared = false;
        }
    }

    public void unPause(){
        if(!mMediaPlayer.isPlaying()){
            mMediaPlayer.start();
        }
    }
}


