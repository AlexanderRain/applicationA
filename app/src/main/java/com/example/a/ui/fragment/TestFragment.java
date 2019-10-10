package com.example.a.ui.fragment;

import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a.R;

import java.util.regex.Matcher;

import static com.example.a.utils.Constants.ACTION;
import static com.example.a.utils.Constants.IMAGE_URL;

public class TestFragment extends Fragment {

    private ClipboardManager clipboardManager;
    private static final String TAG = "TestFragment";

    private EditText textField;
    private Button buttonClear;
    private Button buttonOk;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_fragment, container, false);
        textField = view.findViewById(R.id.link);
        buttonOk = view.findViewById(R.id.buttonOk);
        buttonClear = view.findViewById(R.id.buttonClear);
        buttonOk.setOnClickListener(onClickListener -> validate(textField.getText().toString().trim()));
        buttonClear.setOnClickListener(onClickListener -> textField.setText(""));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager != null) {
            textField.setText(clipboardManager.getPrimaryClip().getItemAt(0).getText().toString());
        }
    }

    public void validate(String imageUrl) {
        Matcher pattern = Patterns.WEB_URL.matcher(imageUrl);

        if (imageUrl.length() == 0) {
            Toast.makeText(getActivity(), "Fill teh field", Toast.LENGTH_SHORT).show();

        } else if (!pattern.matches()) {
            Toast.makeText(getActivity(), "Enter a link", Toast.LENGTH_SHORT).show();

        } else if (pattern.matches()) {
            exportLink(imageUrl);
        }
    }

    public void exportLink(String url) {
        Intent intent = new Intent(ACTION);
        intent.putExtra(IMAGE_URL, url);

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException exception) {
            Toast.makeText(getActivity(), "Image viewer is not installed!", Toast.LENGTH_SHORT).show();
            Log.e(TAG,"ActivityNotFoundException: " + exception);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.textField = null;
        this.buttonOk = null;
        this.buttonClear = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.clipboardManager = null;
    }
}

