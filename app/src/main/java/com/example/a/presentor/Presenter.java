package com.example.a.presentor;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import com.example.a.entity.Link;
import com.example.a.model.interactor.Interactor;

import java.util.Collections;
import java.util.List;

import static com.example.a.utils.Constants.*;

public class Presenter extends AndroidViewModel {
    private HistoryView view;
    private Interactor interactor;

    public Presenter(Application application, HistoryView view) {
        super(application);
        this.view = view;

        interactor = new Interactor(application);
    }

    public void getImageList() {
        view.setLinksList(interactor.getmRepository().getmAllLinks());
    }

    public void sortLinksList(List<Link> linkList, String sortingMode) {
        switch (sortingMode) {
            case OLD_NEW:
                Collections.sort(linkList, (link1, link2) -> link1.getDate().compareTo(link2.getDate()));
                break;
            case NEW_OLD:
                Collections.sort(linkList, (link1, link2) -> link2.getDate().compareTo(link1.getDate()));
                break;
            case SUCCESS_ERROR:
                Collections.sort(linkList, (link1, link2) -> link1.getDate().compareTo(link2.getDate()));
                Collections.sort(linkList, (link1, link2) -> link1.getStatus() - link2.getStatus());
                break;
            case ERROR_SUCCESS:
                Collections.sort(linkList, (link1, link2) -> link1.getDate().compareTo(link2.getDate()));
                Collections.sort(linkList, (link1, link2) -> link2.getStatus() - link1.getStatus());
                break;
        }
    }

    public void getChosenLink(int position) {
        view.exportChosenLink(interactor.getmRepository().getmAllLinks().getValue().get(position));
    }

    @Override
    protected void onCleared() {
        interactor.getmRepository().unsubscribeRxJava();
        this.interactor = null;
        this.view = null;
        super.onCleared();
    }

}
