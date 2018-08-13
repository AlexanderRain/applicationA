package com.example.a.ui.fragment;

import android.app.AlertDialog;
import android.arch.lifecycle.LiveData;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.a.ui.adapter.OnClickListener.OnItemClickListener;

import java.util.List;
import java.util.Objects;

import static com.example.a.utils.Constants.*;

public class HistoryFragment extends Fragment implements HistoryView {
    View view;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    LinkAdapter linkAdapter;

    Presenter presenter;

    private SharedPreferences mSettings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        if(presenter == null) {
            presenter = new Presenter(this.getActivity().getApplication(), this);
        }

        mSettings = Objects.requireNonNull(getContext()).getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
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

        recyclerView = view.findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        linkAdapter = new LinkAdapter(onItemClickCallback);

        presenter.getImageList();
        recyclerView.setAdapter(linkAdapter);

    }

    OnItemClickListener.OnItemClickCallback onItemClickCallback = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            Log.e("Log", " CLICKED " + position);
            presenter.getChosenLink(position);
        }
    };

    @Override
    public void setLinksList(final LiveData<List<Link>> linkList) {
        linkList.observe(this, links -> {
            presenter.sortLinksList(links, mSettings.getString(APP_PREFERENCES_SORTING_MODE, OLD_NEW));
            linkAdapter.setData(links);
        });
        linkAdapter.notifyDataSetChanged();
    }

    @Override
    public void showSortDialog() {
        View root = getLayoutInflater().inflate(R.layout.sort_dialog, null);
        SharedPreferences.Editor editor = mSettings.edit();
        RadioGroup radioGroup = root.findViewById(R.id.radioGroup);

        radioGroup.check(mSettings.getInt(APP_PREFERENCES_RADIO_BUTTON, R.id.radio_old_new));

        new AlertDialog.Builder(getContext())
                .setView(root)
                .setTitle(R.string.sort_title)
                .setPositiveButton(R.string.button_sort, (dialogInterface, i) -> {
                    int radioButtonId = radioGroup.getCheckedRadioButtonId();
                    editor.putInt(APP_PREFERENCES_RADIO_BUTTON, radioButtonId);
                    switch (radioButtonId) {
                        case R.id.radio_old_new:
                            editor.putString(APP_PREFERENCES_SORTING_MODE, OLD_NEW);
                            break;
                        case R.id.radio_new_old:
                            editor.putString(APP_PREFERENCES_SORTING_MODE, NEW_OLD);
                            break;
                        case R.id.radio_success_error:
                            editor.putString(APP_PREFERENCES_SORTING_MODE, SUCCESS_ERROR);
                            break;
                        case R.id.radio_error_success:
                            editor.putString(APP_PREFERENCES_SORTING_MODE, ERROR_SUCCESS);
                            break;
                    }
                    editor.apply();
                    presenter.getImageList();
                })
                .setNegativeButton(R.string.button_cancel, (dialogInterface, i) -> dialogInterface.cancel())
                .show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort: showSortDialog();
                break;
        }
        return true;
    }

    @Override
    public void exportChosenLink(Link link) {
        Intent intent = new Intent(ACTION);

        intent.putExtra(IMAGE_URL, link.getImageLink());
        intent.putExtra(IMAGE_STATUS, link.getStatus());
        intent.putExtra(IMAGE_ID, link.getId());

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException exception) {
            Toast.makeText(getActivity(), "Приложение В не установлено", Toast.LENGTH_SHORT).show();
            Log.e("Log", "ActivityNotFoundException: " + exception);
        }
    }
}