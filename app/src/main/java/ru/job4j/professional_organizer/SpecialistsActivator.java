package ru.job4j.professional_organizer;

import android.content.Intent;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class SpecialistsActivator extends SpecialistsActivity implements SpecialistsFragment.SpecSelect {
    @Override
    public Fragment loadFrg() {
        return new SpecialistsFragment();
    }
    @Override
    public void selected(int index) {
        if (findViewById(R.id.detail) == null) {
            Intent intent = new Intent(this, ProfileActivator.class);
            intent.putExtra("index", index);
            startActivity(intent);
        } else {
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction()
                    .replace(R.id.detail, ProfileFragment.of(index))
                    .commit();
        }
    }
}
