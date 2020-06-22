package ru.job4j.professional_organizer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public abstract class SpecialistsActivity extends FragmentActivity {
    public static final String SPECIALISTS_FOR = "specialists_for";
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
