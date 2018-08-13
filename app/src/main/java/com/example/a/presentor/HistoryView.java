package com.example.a.presentor;

import android.arch.lifecycle.LiveData;

import com.example.a.entity.Link;

import java.util.List;

public interface HistoryView {

    void setLinksList(final LiveData<List<Link>> linkList);
    void showSortDialog();
    void exportChosenLink(Link link);

}
