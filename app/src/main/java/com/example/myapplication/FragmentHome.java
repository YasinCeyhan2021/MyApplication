package com.example.myapplication;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentHome extends Fragment {

    public FragmentHome() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // fragment_home.xml layout dosyasını inflate ediyoruz
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}
