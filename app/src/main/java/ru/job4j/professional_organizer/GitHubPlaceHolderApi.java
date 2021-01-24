package ru.job4j.professional_organizer;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import ru.job4j.professional_organizer.models.Specialist;

public interface GitHubPlaceHolderApi {
    @GET("task.json")
    Call<List<Specialist>> getItems();
}
