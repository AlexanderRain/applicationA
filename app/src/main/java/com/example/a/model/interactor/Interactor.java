package com.example.a.model.interactor;

import android.content.Context;

import com.example.a.model.repository.LinkRepository;

// ЭТО БЫВШИЙ LinkViewModel,
public class Interactor {

    private LinkRepository linkRepository;

    public Interactor(Context context) {
        linkRepository = new LinkRepository(context);
    }

    public LinkRepository getmRepository() {
        return linkRepository;
    }

}
