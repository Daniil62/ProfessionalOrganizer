package ru.job4j.professional_organizer;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import ru.job4j.professional_organizer.store.SpecialistStore;

public class SpecialistsFragment extends Fragment {
    private int code;
    private static List<Specialist> list;
    static List<Specialist> getList() {
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
    }
    public interface SpecSelect {
        void selected(int index);
    }
    private SpecSelect select;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.select = (SpecSelect) context;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        this.select = null;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_specialist, container, false);
        this.code = Objects.requireNonNull(getActivity()).getIntent().getIntExtra("code", 0);
        RecyclerView recycler = view.findViewById(R.id.specialistsRecycler);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.Adapter adapter = new SpecialAdapter(select);
        recycler.setAdapter(adapter);
        list = new ArrayList<>();
        updateUI();
        return view;
    }
}
