package ru.job4j.professional_organizer;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;

public abstract class MainActivity extends FragmentActivity {
    public static final String MAIN_FOR = "main_for";
    public abstract Fragment loadFrg();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_host);
        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentById(R.id.main_host) == null) {
            fm.beginTransaction()
                    .add(R.id.main_host, loadFrg())
                    .commit();
        }
    }
}
