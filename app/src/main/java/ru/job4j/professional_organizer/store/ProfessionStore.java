package ru.job4j.professional_organizer.store;
import java.util.Arrays;
import java.util.List;
import ru.job4j.professional_organizer.Profession;

public class ProfessionStore {
    private List<Profession> profStore = Arrays.asList(
            new Profession("Гитарист", 0),
            new Profession("Басист", 1),
            new Profession("Ударник", 2),
            new Profession("Вокалист", 3),
            new Profession("Гитарист-вокалист", 4),
            new Profession("Басист-вокалист", 5));

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
