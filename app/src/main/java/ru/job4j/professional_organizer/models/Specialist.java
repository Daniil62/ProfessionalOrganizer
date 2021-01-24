package ru.job4j.professional_organizer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import ru.job4j.professional_organizer.R;

public class Specialist {
    @SerializedName("f_name")
    @Expose
    private final String name;
    @SerializedName("l_name")
    @Expose
    private final String surname;
    @SerializedName("birthday")
    @Expose
    private final String birthDate;
    @SerializedName("specialty")
    @Expose
    private final List<Profession> professions;
    @SerializedName("avatr_url")
    @Expose
    private final int photoId;
    public Specialist(String name, String surname, String birthDate,
                      List<Profession> professions, int photoId) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.professions = professions;
        this.photoId = photoId;
    }
    public Specialist(String name, String surname, String birthDate,
                      List<Profession> professions) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.professions = professions;
        this.photoId = R.drawable.default_photo;
    }
    public String getName() {
        return this.name;
    }
    public String getSurname() {
        return this.surname;
    }
    public String getBirthDate() {
        return this.birthDate;
    }
    public List<Profession> getProfession() {
        return this.professions;
    }
    public int getPhotoId() {
        return this.photoId;
    }
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    @Override
    public boolean equals(Object o) {
        boolean result = false;
        Specialist s = (Specialist) o;
        if (o != null && this.name.equals(s.name) && this.surname.equals(s.surname)
                && this.birthDate.equals(s.birthDate)) {
            result = true;
        }
        return result;
    }
}
