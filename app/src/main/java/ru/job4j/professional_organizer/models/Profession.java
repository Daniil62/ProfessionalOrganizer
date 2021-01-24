package ru.job4j.professional_organizer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profession {
    @SerializedName("name")
    @Expose
    private final String name;
    @SerializedName("specialty_id")
    @Expose
    private final int code;
    public Profession(String title, int code) {
        this.name = title;
        this.code = code;
    }
    public String getName() {
        return this.name;
    }
    public int getCode() {
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
