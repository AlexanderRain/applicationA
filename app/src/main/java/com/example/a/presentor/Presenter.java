package com.example.a.presentor;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import com.example.a.entity.Link;
import com.example.a.model.interactor.Interactor;

public class Presenter extends AndroidViewModel {
    HistoryView view;
    Interactor interactor;

    public Presenter(Application application, HistoryView view) {
        super(application);
        this.view = view;
        //historyFragment нужен для єтого метода, а там хз возможно можно проще
        //єто из-за бд, так шо Лужецкий разберись xD)

        interactor = new Interactor(application);
    }

    public void getImageList() {
        view.setLinksList(interactor.getmRepository().getmAllLinks());
    }

    @Override
    protected void onCleared() {
        interactor.getmRepository().unsubscribeRxJava();
        super.onCleared();
    }

    // ВРЕМЕННО !!!
    public void insertLink(Link exampleLink) {
        interactor.getmRepository().insertRxJava(exampleLink);
    }

    public void exportChosenLink(int position) {
        view.exportChosenLink(interactor.getmRepository().getmAllLinks(), position);
    }
}
