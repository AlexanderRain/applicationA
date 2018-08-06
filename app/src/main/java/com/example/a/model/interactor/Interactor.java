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


    //TO DO: методы для ВЫЗОВА сортировки
    //(саму сортировку описать в репозитории, сюда только метод подтягивать),
    // предлагаю полный кастом,
    //типо давать выбор, красные/серые/зеленые/новые/старые
    //а там хотябы просто по статусу(з -> к) и по времени(нов -> стр)

}
