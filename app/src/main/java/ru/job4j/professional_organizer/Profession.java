package ru.job4j.professional_organizer;

public class Profession {
    private String title;
    private int code;

    public Profession(String title, int code) {
        this.title = title;
        this.code = code;
    }
    String getTitle() {
        return this.title;
    }
    int getCode() {
        return this.code;
    }
    @Override
    public int hashCode() {
        return code;
    }
    @Override
    public boolean equals(Object o) {
        return this == o;
    }
}
