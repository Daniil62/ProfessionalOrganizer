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
import ru.job4j.professional_organizer.models.Specialist;
import ru.job4j.professional_organizer.store.ProfessionDbHelper;
import ru.job4j.professional_organizer.store.SpecialistStore;

public class SpecialistsFragment extends Fragment {
    private static List<Specialist> list;
    static List<Specialist> getList() {
        return list;
    }
    public interface SpecSelect {
        void selected(int index);
    }
    private SpecSelect select;
    @Override
    public void onAttach(@NonNull Context context) {
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
        int code = Objects.requireNonNull(
                getActivity()).getIntent().getIntExtra("code", 0);
        ProfessionDbHelper helper = new ProfessionDbHelper(getContext());
        SpecialistStore specialistStore = new SpecialistStore();
        RecyclerView recycler = view.findViewById(R.id.specialistsRecycler);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        SpecialAdapter adapter = new SpecialAdapter(select);
        recycler.setAdapter(adapter);
        list = new ArrayList<>();
        helper.loadSpecialists(specialistStore.getSpecStore());
        list = helper.getSpecialists(code);
        return view;
    }
}
