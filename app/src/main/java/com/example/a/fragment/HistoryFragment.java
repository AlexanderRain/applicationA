package com.example.a.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a.adapter.CustomLinkAdapter;
import com.example.a.R;
import com.example.a.activity.MainActivity;
import com.example.a.model.Link;
import com.example.a.room.LinkViewModel;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {
    public static LinkViewModel mLinkViewModel;
    private static ArrayList<Link> all;
    private static CustomLinkAdapter ma;
    private static ListView lv;
    private static ArrayList<String> openedLinks;
    private Toast toast;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.history_fragment, container, false);

        all = new ArrayList<>();
        lv = rootView.findViewById(R.id.listView);
        ma = new CustomLinkAdapter(getActivity(), R.layout.adapter_view_layout, all);
        lv.setAdapter(ma);
        openedLinks = new ArrayList<>();

        syncDatabaseWithListView();

        lv.setOnItemClickListener((adapterView, view, i, l) -> {
            if (isOnline(getContext())) {
                if (!openedLinks.contains(all.get(i).getImageLink())) {
                    Intent intent = new Intent("com.example.b.MainActivity");
                    intent.putExtra("FROM", "HISTORY");
                    intent.putExtra("IMAGE_ID", all.get(i).getId());
                    intent.putExtra("IMAGE_LINK", all.get(i).getImageLink());
                    intent.putExtra("IMAGE_STATUS", all.get(i).getStatus());
                    intent.putExtra("IMAGE_DATE", all.get(i).getDate());
                    openedLinks.add(all.get(i).getImageLink());
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        showToast("Приложение B не установлено");
                    }
                }
            } else {
                showToast("Отсутствует подключение к интернету");
            }
        });

        return rootView;
    }

    private void syncDatabaseWithListView() {
    /*
        for(Link temp : MainActivity.getLinkDao().getAll()) {
            all.add(temp);
        }
    */
        mLinkViewModel = ViewModelProviders.of(this).get(LinkViewModel.class);
        mLinkViewModel.getmRepository().getmAllLinks().observe(this, links -> {
            all.clear();
            all.addAll(links);
        });
        ma.notifyDataSetChanged();
    }

    public static ArrayList<Link> getAll() {
        return all;
    }

    public static CustomLinkAdapter getMa() {
        return ma;
    }

    public static ArrayList<String> getOpenedLinks() {
        return openedLinks;
    }

    private static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public void showToast(String str) {
        if (toast == null) {
            toast = Toast.makeText(getContext(), null, Toast.LENGTH_SHORT);
        }
        toast.setText(str);
        toast.show();
    }
}