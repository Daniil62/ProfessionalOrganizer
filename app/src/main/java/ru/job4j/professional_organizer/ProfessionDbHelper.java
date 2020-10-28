package ru.job4j.professional_organizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class ProfessionDbHelper extends SQLiteOpenHelper {
    private static final String DB = "profession_organizer.db";
    private static final int VERSION = 1;
    ProfessionDbHelper(Context context) {
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
    void loadProfessions(List<Profession> store) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (Profession p : store) {
            values.put(ProfDbSchema.ProfessionTable.Cols.TITLE, p.getTitle());
            values.put(ProfDbSchema.ProfessionTable.Cols.CODE, p.getCode());
            db.insert(ProfDbSchema.ProfessionTable.TAB_NAME, null, values);
        }
    }
    void loadSpecialists(List<Specialist> store) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (Specialist s : store) {
            if (!isSameSpecialist(s)) {
                values.put(ProfDbSchema.SpecialistTable.Cols.NAME, s.getName());
                values.put(ProfDbSchema.SpecialistTable.Cols.SURNAME, s.getSurname());
                values.put(ProfDbSchema.SpecialistTable.Cols.BIRTH_DATE, s.getBirthDate());
                values.put(ProfDbSchema.SpecialistTable.Cols.PROF_CODE, s.getProfession().getCode());
                values.put(ProfDbSchema.SpecialistTable.Cols.PHOTO_ID, s.getPhotoId());
                db.insert(ProfDbSchema.SpecialistTable.TAB_NAME, null, values);
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
            Profession profession = this.getProfession(specialist.getProfession().getCode());
            int photo = cursor.getInt(cursor.getColumnIndex(
                    ProfDbSchema.SpecialistTable.Cols.PHOTO_ID));
            Specialist s = new Specialist(name, surname, birthDate, profession, photo);
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
    private Profession getProfession(int id) {
        Profession result = new Profession("", 0);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(ProfDbSchema.ProfessionTable.TAB_NAME, null,
                ProfDbSchema.ProfessionTable.Cols.CODE+ " = " + id, null,
                null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            result = new Profession(cursor.getString(
                    cursor.getColumnIndex(ProfDbSchema.ProfessionTable.Cols.TITLE)), cursor.getInt(
                            cursor.getColumnIndex(ProfDbSchema.ProfessionTable.Cols.CODE)
            ));
            if (cursor.getInt(cursor.getColumnIndex(ProfDbSchema.ProfessionTable.Cols.CODE)) == id) {
                break;
            } else {
                cursor.moveToNext();
            }
        }
        cursor.close();
        return result;
    }
    List<Profession> getProfessions() {
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
    List<Specialist> getSpecialists(int id) {
        List<Specialist> specialists = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(ProfDbSchema.SpecialistTable.TAB_NAME, null,
                ProfDbSchema.SpecialistTable.Cols.PROF_CODE + " = " + id,
                null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if (id == cursor.getInt(cursor.getColumnIndex(
                    ProfDbSchema.SpecialistTable.Cols.PROF_CODE))) {
                specialists.add(new Specialist(cursor.getString(
                        cursor.getColumnIndex(
                                ProfDbSchema.SpecialistTable.Cols.NAME)),
                        cursor.getString(cursor.getColumnIndex(
                                ProfDbSchema.SpecialistTable.Cols.SURNAME)),
                        cursor.getString(cursor.getColumnIndex(
                                ProfDbSchema.SpecialistTable.Cols.BIRTH_DATE)),
                        this.getProfession(id),
                        cursor.getInt(cursor.getColumnIndex(
                                ProfDbSchema.SpecialistTable.Cols.PHOTO_ID))));
            }
            cursor.moveToNext();
        }
        cursor.close();
        return specialists;
    }
}
