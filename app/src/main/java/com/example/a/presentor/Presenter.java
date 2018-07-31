package com.example.a.presentor;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;

import com.example.a.entity.Link;
import com.example.a.model.interactor.Interactor;

public class Presenter {
    HistoryView view;
    Interactor interactor;

    public Presenter(HistoryView view, Fragment historyFragment) {
        this.view = view;
        //historyFragment нужен для єтого метода, а там хз возможно можно проще
        //єто из-за бд, так шо Лужецкий разберись xD)
        interactor = ViewModelProviders.of(historyFragment).get(Interactor.class);
    }

    public void getImageList() {
        view.setLinksList(interactor.getmRepository().getmAllLinks());
    }

    // ВРЕМЕННО !!!
    public void insertLink(Link exampleLink) {
        interactor.getmRepository().insertRxJava(exampleLink);
    }
}
