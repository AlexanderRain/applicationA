package com.example.a.presentor;

import android.arch.lifecycle.LiveData;

import com.example.a.entity.Link;

import java.util.List;

// Этим интерфейсом описываются основные методы фрагмента
public interface HistoryView {

    void setLinksList(final LiveData<List<Link>> allList);
    void exportToAppB();

}
