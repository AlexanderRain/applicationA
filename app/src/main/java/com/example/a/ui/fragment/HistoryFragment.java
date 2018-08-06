package com.example.a.ui.fragment;

import android.app.AlertDialog;
import android.arch.lifecycle.LiveData;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.a.R;
import com.example.a.entity.Link;
import com.example.a.presentor.HistoryView;
import com.example.a.presentor.Presenter;
import com.example.a.ui.adapter.LinkAdapter;
import com.example.a.ui.adapter.utils.OnItemClickListener;


import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.example.a.ui.adapter.utils.Constants.*;

public class HistoryFragment extends Fragment implements HistoryView {
    View view;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    LinkAdapter linkAdapter;

    Presenter presenter;

    String currentSortingMode = OLD_NEW;   //Eugene: Значение текущего режима сортировки (Constants.java)
    int currentRadioButton;   //Eugene: ID текущей выбраной кнопки сортировки (для сохранения выбора)

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Фрагмент не сдохнет при повороте екрана,
        //поля не инициализируются заново
        setRetainInstance(true);
        //Добавляет опц менюху
        setHasOptionsMenu(true);

        if(presenter == null){
            // я блять в душе не ебу как так работает )0)
            presenter = new Presenter(this.getActivity().getApplication(), this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.history_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // инициализация recyclerView и LayoutManager
        recyclerView = view.findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        linkAdapter = new LinkAdapter(onItemClickCallback);

        // добавление ссылки в бд // ВРЕМЕННО
        Link exampleLink = new Link("example", 1, new Date(System.currentTimeMillis()));
        Link exampleLink2 = new Link("example2", 1, new Date(System.currentTimeMillis()+10000));
        Link exampleLink3 = new Link("example3", 2, new Date(System.currentTimeMillis()+20000));
        presenter.insertLink(exampleLink);
        presenter.insertLink(exampleLink2);
        presenter.insertLink(exampleLink3);

        //по смыслу должно быть все понятно
        presenter.getImageList();
        recyclerView.setAdapter(linkAdapter);

    }

    // Инициализация лисенера
    OnItemClickListener.OnItemClickCallback onItemClickCallback = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            Log.e("Log", " CLICKED " + position);
            presenter.exportChosenLink(position);
        }
    };

    @Override
    public void setLinksList(final LiveData<List<Link>> linkList) {
        // Красиво, правда ?) подписываемся на лайв дату и пхаем список ссылок в сет
        linkList.observe(this, links -> {
            sortLinksList(links, currentSortingMode);
            linkAdapter.setData(links);
        });
        linkAdapter.notifyDataSetChanged();
    }

    @Override
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

    //Eugene: Создание диалогового окна для выбора режима сортировки
    @Override
    public void showSortDialog() {
        View root = getLayoutInflater().inflate(R.layout.sort_dialog, null);
        RadioGroup radioGroup = root.findViewById(R.id.radioGroup);
        if(currentRadioButton != 0) radioGroup.check(currentRadioButton);

        new AlertDialog.Builder(getContext())
                .setView(root)
                .setTitle(R.string.sort_title)
                .setPositiveButton(R.string.button_sort, (dialogInterface, i) -> {
                    switch (currentRadioButton = radioGroup.getCheckedRadioButtonId()) {
                        case R.id.radio_old_new:
                            currentSortingMode = OLD_NEW;
                            break;
                        case R.id.radio_new_old:
                            currentSortingMode = NEW_OLD;
                            break;
                        case R.id.radio_success_error:
                            currentSortingMode = SUCCESS_ERROR;
                            break;
                        case R.id.radio_error_success:
                            currentSortingMode = ERROR_SUCCESS;
                            break;
                    }
                    presenter.getImageList();
                })
                .setNegativeButton(R.string.button_cancel, (dialogInterface, i) -> dialogInterface.cancel())
                .show();
    }

    //Кнопочки сортировки
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /*Eugene: Не надо нам этого, сделаем иначе (и зачем здесь что-то кидать в лог?)
            case R.id.sort_by_date:
                Log.e("Log", "sort_by_date");
                break;
            case R.id.sort_by_status:
                Log.e("Log", "sort_by_status");
                break;
             */
            case R.id.action_sort: showSortDialog();
                break;
        }
        return true;
    }

    @Override
    public void exportChosenLink(final LiveData<List<Link>> linkList, int position) {
        Intent intent = new Intent("com.example.b.MainActivity");

        linkList.observe(this, links -> {
            intent.putExtra("FROM", "HISTORY");
            intent.putExtra("IMAGE_LINK",  links.get(position).getImageLink());
            intent.putExtra("IMAGE_STATUS", links.get(position).getStatus());
            intent.putExtra("IMAGE_ID", links.get(position).getId());
        });

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException exception) {
            Toast.makeText(getActivity(),"Приложение В не установлено", Toast.LENGTH_SHORT).show();
            Log.e("Log", "ActivityNotFoundException: " + exception);
        }
    }
}