package ru.job4j.professional_organizer.store;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import ru.job4j.professional_organizer.models.Profession;
import ru.job4j.professional_organizer.models.Specialist;

public class ProfessionDbHelper extends SQLiteOpenHelper {
    private static final String DB = "profession_organizer.db";
    private static final int VERSION = 1;
    public ProfessionDbHelper(Context context) {
        super (context, DB, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ProfDbSchema.ProfessionTable.TAB_NAME + "("
                + "_id integer primary key autoincrement, "
                + ProfDbSchema.ProfessionTable.Cols.TITLE + " text, "
                + ProfDbSchema.ProfessionTable.Cols.CODE + " integer "
                + ")");
        db.execSQL("create table " + ProfDbSchema.SpecialistTable.TAB_NAME + "("
                + "_id integer primary key autoincrement, "
                + ProfDbSchema.SpecialistTable.Cols.NAME + " text, "
                + ProfDbSchema.SpecialistTable.Cols.SURNAME + " text, "
                + ProfDbSchema.SpecialistTable.Cols.BIRTH_DATE + " text, "
                + ProfDbSchema.SpecialistTable.Cols.PROF_CODE + " integer, "
                + ProfDbSchema.SpecialistTable.Cols.PHOTO_ID + " integer, "
                + ProfDbSchema.SpecialistTable.Cols.FOREIGN_KEY + " integer, "
                + "foreign key " + "(" + ProfDbSchema.SpecialistTable.Cols.FOREIGN_KEY + ")"
                + " references " + ProfDbSchema.ProfessionTable.TAB_NAME + "_id"
                + ")");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public void loadProfessions(List<Profession> store) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (Profession p : store) {
            values.put(ProfDbSchema.ProfessionTable.Cols.TITLE, p.getName());
            values.put(ProfDbSchema.ProfessionTable.Cols.CODE, p.getCode());
            db.insert(ProfDbSchema.ProfessionTable.TAB_NAME, null, values);
        }
    }
    public void loadSpecialists(List<Specialist> store) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (Specialist s : store) {
            if (!isSameSpecialist(s)) {
                values.put(ProfDbSchema.SpecialistTable.Cols.NAME, s.getName());
                values.put(ProfDbSchema.SpecialistTable.Cols.SURNAME, s.getSurname());
                values.put(ProfDbSchema.SpecialistTable.Cols.BIRTH_DATE, s.getBirthDate());
                values.put(ProfDbSchema.SpecialistTable.Cols.PHOTO_ID, s.getPhotoId());
                for (Profession p : s.getProfession()) {
                    values.put(ProfDbSchema.SpecialistTable.Cols.PROF_CODE, p.getCode());
                    db.insert(ProfDbSchema.SpecialistTable.TAB_NAME, null, values);
                }
            }
        }
    }
    private boolean isSameSpecialist(Specialist specialist) {
        boolean result = false;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(ProfDbSchema.SpecialistTable.TAB_NAME, null,
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String name = cursor.getString(
                    cursor.getColumnIndex(ProfDbSchema.SpecialistTable.Cols.NAME));
            String surname = cursor.getString(cursor.getColumnIndex(
                    ProfDbSchema.SpecialistTable.Cols.SURNAME));
            String birthDate = cursor.getString(cursor.getColumnIndex(
                    ProfDbSchema.SpecialistTable.Cols.BIRTH_DATE));
            int photo = cursor.getInt(cursor.getColumnIndex(
                    ProfDbSchema.SpecialistTable.Cols.PHOTO_ID));
            List<Profession> professions = new ArrayList<>();
            Specialist s = new Specialist(name, surname, birthDate, professions, photo);
            if (specialist.equals(s)) {
                result = true;
                break;
            } else {
                cursor.moveToNext();
            }
        }
        cursor.close();
        return result;
    }
    private List<Profession> getProfession(int code) {
        List<Profession> result = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(ProfDbSchema.ProfessionTable.TAB_NAME, null,
                ProfDbSchema.ProfessionTable.Cols.CODE + " = " + code, null,
                null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            String name = cursor.getString(
                    cursor.getColumnIndex(ProfDbSchema.ProfessionTable.Cols.TITLE));
            int id = cursor.getInt(cursor.getColumnIndex(ProfDbSchema.ProfessionTable.Cols.CODE));
            Profession p = new Profession(name, id);
            if (id == code) {
                result.add(p);
            }
            cursor.moveToNext();
        }
        cursor.close();
        return result;
    }
    public List<Profession> getProfessions() {
        List<Profession> professions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(ProfDbSchema.ProfessionTable.TAB_NAME, null,
                null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            professions.add(new Profession(cursor.getString(
                    cursor.getColumnIndex(ProfDbSchema.ProfessionTable.Cols.TITLE)),
                    cursor.getInt(cursor.getColumnIndex(ProfDbSchema.ProfessionTable.Cols.CODE))));
            cursor.moveToNext();
        }
        cursor.close();
        return professions;
    }
    public List<Specialist> getSpecialists(int code) {
        List<Specialist> specialists = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(ProfDbSchema.SpecialistTable.TAB_NAME, null,
                null,
                null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if (code == cursor.getInt(cursor.getColumnIndex(
                    ProfDbSchema.SpecialistTable.Cols.PROF_CODE))) {
                specialists.add(new Specialist(cursor.getString(
                        cursor.getColumnIndex(
                                ProfDbSchema.SpecialistTable.Cols.NAME)),
                        cursor.getString(cursor.getColumnIndex(
                                ProfDbSchema.SpecialistTable.Cols.SURNAME)),
                        cursor.getString(cursor.getColumnIndex(
                                ProfDbSchema.SpecialistTable.Cols.BIRTH_DATE)),
                        this.getProfession(code),
                        cursor.getInt(cursor.getColumnIndex(
                                ProfDbSchema.SpecialistTable.Cols.PHOTO_ID))));
            }
            cursor.moveToNext();
        }
        cursor.close();
        return specialists;
    }
    public void createTablesFromItem(List<Specialist> list) {
        Set<Profession> professions = new HashSet<>();
        List<Specialist> specialists = new ArrayList<>();
        Profession profession;
        for (Specialist specialist : list) {
            for (int i = 0; i < specialist.getProfession().size(); ++i) {
                profession = specialist.getProfession().get(i);
                professions.add(profession);
            }
            specialists.add(specialist);
        }
        List<Profession> profList = new ArrayList<>(professions);
        loadProfessions(profList);
        loadSpecialists(specialists);
    }
}
