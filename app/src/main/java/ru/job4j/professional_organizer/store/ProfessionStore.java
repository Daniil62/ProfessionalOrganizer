package ru.job4j.professional_organizer.store;

import java.util.Arrays;
import java.util.List;
import ru.job4j.professional_organizer.models.Profession;

public class ProfessionStore {
    private List<Profession> profStore = Arrays.asList(
            new Profession("Гитарист", 1),
            new Profession("Басист", 2),
            new Profession("Ударник", 3),
            new Profession("Вокалист", 4),
            new Profession("Гитарист-вокалист", 5),
            new Profession("Басист-вокалист", 6));
    public List<Profession> getProfStore() {
        return profStore;
    }
    Profession get(int index) {
        return profStore.get(index);
    }
    public int size() {
        return profStore.size();
    }
}
