package ru.job4j.professional_organizer;

import android.support.v4.app.Fragment;

public class SpecialistsActivator extends SpecialistsActivity {
    @Override
    public Fragment loadFrg() {
        return SpecialistsFragment.of(getIntent().getIntExtra(SpecialistsActivity.SPECIALISTS_FOR, 0));
    }
}
