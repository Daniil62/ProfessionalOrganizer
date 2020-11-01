package ru.job4j.professional_organizer.models;

import ru.job4j.professional_organizer.R;

public class Specialist {
    private String name;
    private String surname;
    private String birthDate;
    private Profession profession;
    private int photoId;
    public Specialist(String name, String surname, String birthDate,
                      Profession profession, int photoId) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.profession = profession;
        this.photoId = photoId;
    }
    public Specialist(String name, String surname, String birthDate,
                      Profession profession) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.profession = profession;
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
    public Profession getProfession() {
        return this.profession;
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
                && this.birthDate.equals(s.birthDate)
                && this.profession.getCode() == s.profession.getCode()) {
            result = true;
        }
        return result;
    }
}
