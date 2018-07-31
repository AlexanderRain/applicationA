package com.example.a.ui.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a.R;
import com.example.a.entity.Link;

public class TestFragment extends Fragment {
    private Button btnOk;
    private EditText link;
    private Toast toast;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.test_fragment, container, false);

        link = rootView.findViewById(R.id.link);

        btnOk = rootView.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(e -> {
            String url = link.getText().toString();
            if (url.length() == 0) {
                showToast("Заполните поле");
            } /*else if (isInAllByLink(url)) {
                showToast("Ссылка " + url + " уже есть в базе");
            }*/ else {
                Intent i = new Intent("com.example.b.MainActivity");
                i.putExtra("FROM", "OK");
                i.putExtra("IMAGE_LINK", url);
                link.setText("");
                try {
                    startActivity(i);
                } catch (ActivityNotFoundException ex) {
                    showToast("Приложение B не установлено");
                }
            }
        });

        return rootView;
    }

    public void showToast(String str) {
        if (toast == null) {
            toast = Toast.makeText(getContext(), null, Toast.LENGTH_SHORT);
        }
        toast.setText(str);
        toast.show();
    }

    /*private boolean isInAllByLink(String url) {
        for (Link temp : HistoryFragment.getAll()) {
            if (temp.getImageLink().equals(url)) {
                return true;
            }
        }
        return false;
    }*/
}
