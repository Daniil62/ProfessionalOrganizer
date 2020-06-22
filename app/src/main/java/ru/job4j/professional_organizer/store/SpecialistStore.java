package ru.job4j.professional_organizer.store;

import java.util.Arrays;
import java.util.List;

import ru.job4j.professional_organizer.R;
import ru.job4j.professional_organizer.Specialist;

public class SpecialistStore {
    private ProfessionStore profStore = new ProfessionStore();
    private List<Specialist> specStore = Arrays.asList(
            new Specialist("Сергей", "Маврин",
                    "28.02.1963", profStore.get(0), R.drawable.sergey_mavrin),
            new Specialist("Владимир", "Холстинин",
                    "12.05.1958", profStore.get(0), R.drawable.vladimir_holstinin),
            new Specialist("Александр", "Грановский",
                    "27.10.1959", profStore.get(1), R.drawable.alexander_granovsky),
            new Specialist("Виталий", "Дубинин",
                    "09.10.1958", profStore.get(1), R.drawable.vitaly_dubinin),
            new Specialist("Александр", "Манякин",
                    "14.03.1966", profStore.get(2), R.drawable.alexander_mayakin),
            new Specialist("Валерий", "Кипелов",
                    "12.07.1958", profStore.get(3), R.drawable.valery_kipelov),
            new Specialist("James", "Hatfield",
                    "03.08.1963", profStore.get(4), R.drawable.james_hetfield),
            new Specialist("Tom", "Araya",
                    "06.06.1961", profStore.get(5), R.drawable.tom_araya));
    public List<Specialist> getSpecStore() {
        return this.specStore;
    }
    public Specialist get(int index) {
        return specStore.get(index);
    }
}
