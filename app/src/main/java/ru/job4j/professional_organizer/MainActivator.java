package ru.job4j.professional_organizer;

import androidx.fragment.app.Fragment;

public class MainActivator extends MainActivity {
    @Override
    public Fragment loadFrg() {
        return MainFragment.of(getIntent().getIntExtra(MainActivity.MAIN_FOR, 0));
    }
}
