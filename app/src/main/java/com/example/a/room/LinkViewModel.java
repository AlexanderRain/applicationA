package com.example.a.room;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

public class LinkViewModel extends AndroidViewModel {

    private LinkRepository mRepository;

    public LinkRepository getmRepository() {
        return mRepository;
    }

    public LinkViewModel(Application application) {
        super(application);
        mRepository = new LinkRepository(application);
    }

    @Override
    protected void onCleared() {
        mRepository.unsubscribeRxJava();
        super.onCleared();
    }
}
