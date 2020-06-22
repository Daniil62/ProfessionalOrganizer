package ru.job4j.professional_organizer;

import android.support.v4.app.Fragment;

public class ProfileActivator extends ProfileActivity {
    @Override
    public Fragment loadFrg() {
        return ProfileFragment.of(getIntent().getIntExtra(ProfileActivity.PROFILE_FOR, 0));
    }
}
