package com.example.a.model.interactor;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import com.example.a.model.repository.LinkRepository;

// ЭТО БЫВШИЙ LinkViewModel,
public class Interactor extends AndroidViewModel {

    private LinkRepository linkRepository;

    public Interactor(Application application) {
        super(application);
        linkRepository = new LinkRepository(application);
    }

    public LinkRepository getmRepository() {
        return linkRepository;
    }

    @Override
    protected void onCleared() {
        linkRepository.unsubscribeRxJava();
        super.onCleared();
    }

    //TO DO: методы для ВЫЗОВА сортировки
    //(саму сортировку описать в репозитории, сюда только метод подтягивать),
    // предлагаю полный кастом,
    //типо давать выбор, красные/серые/зеленые/новые/старые
    //а там хотябы просто по статусу(з -> к) и по времени(нов -> стр)

}
