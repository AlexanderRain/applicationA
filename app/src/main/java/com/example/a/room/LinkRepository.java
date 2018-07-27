package com.example.a.room;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.util.Log;

import com.example.a.model.Link;
import com.example.a.room.core.LinkDao;
import com.example.a.room.core.LinkRoomDatabase;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LinkRepository {

    private LinkDao mLinkDao;

    private LiveData<List<Link>> mAllLinks;

    public LiveData<List<Link>> getmAllLinks() {
        return mAllLinks;
    }

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    public LinkRepository(Context application) {
        LinkRoomDatabase database = LinkRoomDatabase.getDatabase(application);
        mLinkDao = database.linkDao();
        mAllLinks = mLinkDao.getAllLinks();
    }

    private Completable insertLink(Link link) {
        return Completable.fromAction(() -> mLinkDao.insert(link));
    }

    private Completable updateLink(Link link) {
        return Completable.fromAction(() -> mLinkDao.update(link));
    }

    private Completable deleteLink(Link link) {
        return Completable.fromAction(() -> mLinkDao.delete(link));
    }

    private Completable deleteLinkById(long id) {
        return Completable.fromAction(() -> mLinkDao.deleteById(id));
    }

    public void insertRxJava(Link link) {
        mDisposable.add(insertLink(link)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Log.e("Log", "Insert"), throwable -> Log.e("Log", "Unable to insert", throwable))
        );
    }

    public void updateRxJava(Link link) {
        mDisposable.add(updateLink(link)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Log.e("Log", "Update"), throwable -> Log.e("Log", "Unable to update", throwable))
        );
    }

    public void deleteRxJava(Link link) {
        mDisposable.add(deleteLink(link)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Log.e("Log", "Delete"), throwable -> Log.e("Log", "Unable to delete", throwable))
        );
    }

    public void deleteByIdRxJava(Long id) {
        mDisposable.add(deleteLinkById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Log.e("Log", "Delete"), throwable -> Log.e("Log", "Unable to delete", throwable))
        );
    }

    public void unsubscribeRxJava() {
        if(!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}