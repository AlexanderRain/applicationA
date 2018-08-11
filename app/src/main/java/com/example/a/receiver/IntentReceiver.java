package com.example.a.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.a.entity.Link;
import com.example.a.model.interactor.Interactor;

import static com.example.a.utils.Constants.DELETE;
import static com.example.a.utils.Constants.IMAGE_ACTION;
import static com.example.a.utils.Constants.IMAGE_DATE;
import static com.example.a.utils.Constants.IMAGE_STATUS;
import static com.example.a.utils.Constants.IMAGE_URL;
import static com.example.a.utils.Constants.INSERT;
import static com.example.a.utils.Constants.UNDEFINED;
import static com.example.a.utils.Constants.UPDATE;

public class IntentReceiver extends BroadcastReceiver {

    Interactor interactor;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("Log", "RECIVED");
        interactor = new Interactor(context);

        String action = intent.getStringExtra(IMAGE_ACTION);
        String imageURL = intent.getStringExtra(IMAGE_URL);
        String imageDate = intent.getStringExtra(IMAGE_DATE);
        int imageStatus = intent.getIntExtra(IMAGE_STATUS, UNDEFINED);

        switch (action) {
            case INSERT:
                Link link = new Link(imageURL, imageStatus, imageDate);
                interactor.getmRepository().insertRxJava(link);
                break;

            case UPDATE:
                Link link2 = new Link(imageURL, imageStatus, imageDate);
                interactor.getmRepository().updateRxJava(link2);
                break;

            case DELETE:
                Link link3 = new Link(imageURL, imageStatus, imageDate);
                interactor.getmRepository().deleteRxJava(link3);
                break;

        }
    }
}
