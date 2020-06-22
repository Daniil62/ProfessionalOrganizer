package ru.job4j.professional_organizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import ru.job4j.professional_organizer.store.SpecialistStore;

public class SpecialistsFragment extends Fragment {
    private RecyclerView recycler;
    private int code;
    private static List<Specialist> list;
    public static List<Specialist> getList() {
        return list;
    }
    public static SpecialistsFragment of(int index) {
        SpecialistsFragment sf = new SpecialistsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(SpecialistsActivity.SPECIALISTS_FOR, index);
        sf.setArguments(bundle);
        return sf;
    }
    private void updateUI() {
        SpecialistStore sStore = new SpecialistStore();
        for (Specialist s : sStore.getSpecStore()) {
            if (s.getProfession().getCode() == this.code) {
                list.add(s);
            }
        }
        this.recycler.setAdapter(new SpecialAdapter(list));
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_specialist, container, false);
        getActivity();
        setRetainInstance(true);
        this.code = Objects.requireNonNull(getActivity()).getIntent().getIntExtra("code", 0);
        recycler = view.findViewById(R.id.specialistsRecycler);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<>();
        updateUI();
        return view;
    }
    public class SpecialAdapter extends RecyclerView.Adapter<SpecialHolder> {
        private final List<Specialist> specialists;
        SpecialAdapter(List<Specialist> spc) {
            this.specialists = spc;
        }
        @NonNull
        @Override
        public SpecialHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View view = inflater.inflate(R.layout.specialist_module, viewGroup, false);
            return new SpecialHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull SpecialHolder holder, int i) {
            final Specialist specialist = this.specialists.get(i);
            TextView nameText = holder.view.findViewById(R.id.previewName);
            TextView surnameText = holder.view.findViewById(R.id.previewSurname);
            ImageView photo = holder.view.findViewById(R.id.previewPhoto);
            nameText.setId(i);
            surnameText.setId(i);
            photo.setId(i);
            nameText.setText(specialist.getName());
            surnameText.setText(specialist.getSurname());
            photo.setImageResource(specialist.getPhotoId());
            nameText.setOnClickListener(this::click);
            surnameText.setOnClickListener(this::click);
            photo.setOnClickListener(this::click);
        }
        private void click(View view) {
            Intent intent = new Intent(getActivity(), ProfileActivator.class);
            intent.putExtra("id", view.getId());
            startActivity(intent);
        }
        @Override
        public int getItemCount() {
            return specialists.size();
        }
    }
    class SpecialHolder extends RecyclerView.ViewHolder {
        View view;
        SpecialHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
        }
    }
}
