package uk.ac.qub.eeecs.gage.util;

import android.app.Fragment;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 *Worked on by Grace 40172213
 *Reference/s: Code adapted from Unimon54
 */
public class ToastMessages extends Fragment{

    private Handler mHandler;
    private Context context;

    public ToastMessages(Context context) {
        this.context = context;
    }

    @Override
    public Context getContext() {
        return context;
    }

    //Method called to display toast messages when a button is clicked
    public void showMessage(final String msg) {
        mHandler = new Handler(Looper.getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }}
