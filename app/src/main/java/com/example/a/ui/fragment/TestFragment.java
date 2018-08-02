package com.example.a.ui.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a.R;


public class TestFragment extends Fragment {
    private View view;
    private Button button;
    private EditText textField;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Фрагмент не сдохнет при повороте екрана,
        //поля не инициализируются заново
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.test_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        textField = view.findViewById(R.id.link);
        button = view.findViewById(R.id.button);

        button.setOnClickListener( onClickListener  -> {
            // текст в строку без пробелов
            exportLink(textField.getText().toString().trim());
        });
    }

    public void exportLink(String url) {
        if (url.length() == 0) {
            Toast.makeText(getActivity(), "Заполните поле", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent("com.example.b.MainActivity");
            intent.putExtra("IMAGE_LINK", url);
            Log.e("Log", "URL: " + url);

            try {
                startActivity(intent);
                Log.e("Log", "STARTED");
            } catch (ActivityNotFoundException exception) {
                Toast.makeText(getActivity(), "Приложение В не установлено", Toast.LENGTH_SHORT).show();
                Log.e("Log", "ActivityNotFoundException: " + exception);
            }
        }
    }

}
