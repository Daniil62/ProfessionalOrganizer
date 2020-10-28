package ru.job4j.professional_organizer;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import ru.job4j.professional_organizer.store.ProfessionStore;

public class MainFragment extends Fragment {
    private RecyclerView recycler;
    private ProfessionStore ps;
    private ProfessionDbHelper helper;
    static MainFragment of(int index) {
        MainFragment mf = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(MainActivity.MAIN_FOR, index);
        mf.setArguments(bundle);
        return mf;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        recycler = view.findViewById(R.id.professionsRecycler);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        this.ps = new ProfessionStore();
        this.helper = new ProfessionDbHelper(getContext());
        helper.loadProfessions(ps.getProfStore());
        updateUI();
        return view;
    }
    class ProfessionHolder extends RecyclerView.ViewHolder {
        private View view;
        ProfessionHolder(@NonNull View view) {
            super(view);
            this.view = itemView;
        }
    }
    private void updateUI() {
        this.recycler.setAdapter(new ProfessionAdapter(helper.getProfessions()));
    }
    public class ProfessionAdapter extends RecyclerView.Adapter<ProfessionHolder> {
        private final List<Profession> professions;
        ProfessionAdapter(List<Profession> professions) {
            this.professions = professions;
        }
        @NonNull
        @Override
        public ProfessionHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.profession_module, parent, false);
            return new ProfessionHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull ProfessionHolder holder, int i) {
            final Profession p = professions.get(i);
            TextView profession = holder.view.findViewById(R.id.profession);
            profession.setText(p.getTitle());
            profession.setId(p.getCode());
            holder.view.setId(p.getCode());
            holder.view.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), SpecialistsActivator.class);
                intent.putExtra("code", v.getId());
                startActivity(intent);
            });
        }
        @Override
        public int getItemCount() {
            return ps.size();
        }
    }
}
