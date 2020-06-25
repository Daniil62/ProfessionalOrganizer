package ru.job4j.professional_organizer;

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
    String getName() {
        return this.name;
    }
    String getSurname() {
        return this.surname;
    }
    String getBirthDate() {
        return this.birthDate;
    }
    Profession getProfession() {
        return this.profession;
    }
    int getPhotoId() {
        return this.photoId;
    }
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
