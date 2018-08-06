package com.example.a.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.a.entity.Link;
import com.example.a.model.interactor.Interactor;

import java.util.Date;

public class IntentReceiver extends BroadcastReceiver {

    Interactor interactor;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("Log", "RECIVED");
        interactor = new Interactor(context);

        String action = intent.getStringExtra("FOR");
        long imageID = intent.getLongExtra("IMAGE_ID", -1);
        String imageURL = intent.getStringExtra("IMAGE_URL");
        int imageStatus = intent.getIntExtra("IMAGE_STATUS", 3);
        Date imageDate = new Date(intent.getLongExtra("IMAGE_DATE", 0));

        switch (action) {
            case "INSERT":
                Link link = new Link(imageURL, imageStatus, imageDate);
                interactor.getmRepository().insertRxJava(link);
                break;

            case "UPDATE":
                Link link2 = new Link(imageID, imageURL, 1, imageDate);
                interactor.getmRepository().updateRxJava(link2);
                break;

            case "DELETE":
                interactor.getmRepository().deleteByIdRxJava(imageID);
                break;

        }
    }
}