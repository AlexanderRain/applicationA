package com.example.a.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.a.ui.fragment.HistoryFragment;
import com.example.a.entity.Link;
import com.example.a.model.room.LinkViewModel;

import java.util.Date;

public class MyReceiver extends BroadcastReceiver {
 LinkViewModel mLinkViewModel;
    @Override
    public void onReceive(Context context, Intent intent) {
       // mLinkViewModel = HistoryFragment.mLinkViewModel;
        String action = intent.getStringExtra("FOR");
        long imageID = intent.getLongExtra("IMAGE_ID", -1);
        String imageURL = intent.getStringExtra("IMAGE_URL");
        int imageStatus = intent.getIntExtra("IMAGE_STATUS", 3);
        Date imageDate = new Date(intent.getLongExtra("IMAGE_DATE", 0));

        /*switch (action) {
            case "INSERT" :
                Link link = new Link(imageURL, imageStatus, imageDate);

                mLinkViewModel.getmRepository().insertRxJava(link);
                HistoryFragment.getAll().add(0, link);
                HistoryFragment.getMa().notifyDataSetChanged();
                break;
            case "UPDATE" :
                Link link2 = new Link(imageID, imageURL, 1, imageDate);

                mLinkViewModel.getmRepository().updateRxJava(link2);
                updateFromAllById(imageID);
                HistoryFragment.getMa().notifyDataSetChanged();
                break;
            case "DELETE" :
                mLinkViewModel.getmRepository().deleteByIdRxJava(imageID);
                deleteFromAllById(imageID);
                HistoryFragment.getOpenedLinks().remove(imageURL);
                HistoryFragment.getMa().notifyDataSetChanged();
                break;
        }*/
    }

    /*private void deleteFromAllById(long id) {
        for (Link temp : HistoryFragment.getAll()) {
            if (temp.getId() == id) {
                HistoryFragment.getAll().remove(temp);
                return;
            }
        }
    }

    private void updateFromAllById(long id) {
        for (Link temp : HistoryFragment.getAll()) {
            if (temp.getId() == id) {
                temp.setStatus(1);
                return;
            }
        }
    }*/
}