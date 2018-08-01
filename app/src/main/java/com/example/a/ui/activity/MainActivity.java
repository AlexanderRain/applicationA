package com.example.a.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.a.R;
import com.example.a.ui.adapter.ViewPagerAdapter;
import com.example.a.ui.fragment.HistoryFragment;
import com.example.a.ui.fragment.TestFragment;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.myToolbar));

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new TestFragment(), "Тест");
        viewPagerAdapter.addFragment(new HistoryFragment(), "История");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

/*
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "roomDatabase")
                .allowMainThreadQueries()
                .build();
        linkDao = db.linkDao();
*/
    }
}