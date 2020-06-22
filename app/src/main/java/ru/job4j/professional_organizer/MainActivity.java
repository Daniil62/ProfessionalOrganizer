package ru.job4j.professional_organizer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

public abstract class MainActivity extends FragmentActivity {
    public static final String MAIN_FOR = "main_for";
    public abstract Fragment loadFrg();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.host_fragment);
        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentById(R.id.content) == null) {
            fm.beginTransaction()
                    .add(R.id.content, loadFrg())
                    .commit();
        }
    }
}
