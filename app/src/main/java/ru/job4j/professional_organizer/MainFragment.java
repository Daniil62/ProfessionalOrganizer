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
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import java.util.Objects;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import okhttp3.OkHttpClient;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.job4j.professional_organizer.models.Profession;
import ru.job4j.professional_organizer.models.Specialist;
import ru.job4j.professional_organizer.store.ProfessionDbHelper;
import ru.job4j.professional_organizer.store.ProfessionStore;

public class MainFragment extends Fragment {
    private RecyclerView recycler;
    private ProfessionStore ps;
    private ProfessionDbHelper helper;
    private Call<List<Specialist>> getItems;
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
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        levelSelector(interceptor);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient.Builder client = new OkHttpClient.Builder().addInterceptor(interceptor);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://github.com/test-tasks/task-json/blob/master/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client.build())
                .client(createClientErrorIntercept())
                .build();
        GitHubPlaceHolderApi placeHolderApi = retrofit.create(GitHubPlaceHolderApi.class);
        this.getItems = placeHolderApi.getItems();
        loadProfessions();
        updateUI();
        return view;
    }
    static class ProfessionHolder extends RecyclerView.ViewHolder {
        private final View view;
        ProfessionHolder(@NonNull View view) {
            super(view);
            this.view = itemView;
        }
    }
    private void levelSelector(HttpLoggingInterceptor interceptor) {
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        }
    }
    private OkHttpClient createClientErrorIntercept() {
        return new OkHttpClient.Builder().addInterceptor(chain -> {
            Request request = chain.request();
            okhttp3.Response response = chain.proceed(request);
            int code = response.code();
            if (code >= 400 && code <= 599) {
                String report = "Сетевая ошибка.\nКод: " + code;
                Objects.requireNonNull(getActivity()).runOnUiThread(() ->
                        Toast.makeText(getActivity(), report, Toast.LENGTH_LONG).show());
                return response;
            }
            return response;
        }).build();
    }
    private void updateUI() {
        this.recycler.setAdapter(new ProfessionAdapter(helper.getProfessions()));
    }
    public class ProfessionAdapter extends RecyclerView.Adapter<ProfessionHolder> {
        private final List<Profession> items;
        ProfessionAdapter(List<Profession> professions) {
            this.items = professions;
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
            final Profession p = items.get(i);
            TextView profession = holder.view.findViewById(R.id.profession);
            profession.setText(p.getName());
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
    private void loadProfessions() {
        getItems.enqueue(new Callback<List<Specialist>>() {
            @Override
            public void onResponse(@NonNull Call<List<Specialist>> call,
                                   @NonNull Response<List<Specialist>> response) {
                if (response.isSuccessful()) {
                    helper.createTablesFromItem(Objects.requireNonNull(response.body()));
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Specialist>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
