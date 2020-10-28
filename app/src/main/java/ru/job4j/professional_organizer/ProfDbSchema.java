package ru.job4j.professional_organizer;

class ProfDbSchema {
    final static class ProfessionTable {
        final static String TAB_NAME = "profession_table";
        final static class Cols {
            static final String TITLE = "profession_title";
            static final String CODE = "profession_code";
        }
    }
    final static class SpecialistTable {
        final static String TAB_NAME = "specialist_table";
        final static class Cols {
            static final String NAME = "specialist_name";
            static final String SURNAME = "specialist_surname";
            static final String BIRTH_DATE = "specialist_birth_date";
            static final String PROF_CODE = "spec_prof_code";
            static final String PHOTO_ID = "photo_id";
            static final String FOREIGN_KEY = "specialist_foreign_key";
        }
    }
}
