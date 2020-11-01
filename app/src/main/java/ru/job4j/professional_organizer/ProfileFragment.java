package ru.job4j.professional_organizer;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import ru.job4j.professional_organizer.models.Specialist;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileFragment extends Fragment {
    static ProfileFragment of(int index) {
        ProfileFragment pf = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ProfileActivity.PROFILE_FOR, index);
        pf.setArguments(bundle);
        return pf;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.specialist_profile, container, false);
        ImageView photo = view.findViewById(R.id.imageView);
        TextView nameText = view.findViewById(R.id.name_text);
        TextView surnameText = view.findViewById(R.id.surname_text);
        TextView dateText = view.findViewById(R.id.birth_date_text);
        TextView professionText = view.findViewById(R.id.profession_text);
        Specialist specialist = SpecialistsFragment.getList().get(SpecialAdapter.getId());
        if (specialist != null) {
            photo.setImageResource(specialist.getPhotoId());
            nameText.setText(specialist.getName());
            surnameText.setText(specialist.getSurname());
            dateText.setText(specialist.getBirthDate());
            professionText.setText(specialist.getProfession().getTitle());
        }
        return view;
    }
}
