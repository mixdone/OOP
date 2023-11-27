import java.util.ArrayList;

/**
 * Класс семестра.
 */
public class Semester {
    private final ArrayList<SubjectWithMark> subjects = new ArrayList<>();

    /**
     * Добавить предмет.
     *
     * @param subject - Добавляемый предмет.
     */
    public void addSubject(SubjectWithMark subject) {
        subjects.add(subject);
    }

    /**
     * Возвращает список предметов.
     *
     * @return список предметов.
     */
    public ArrayList<SubjectWithMark> getSubjects() {
        return subjects;
    }

    /**
     * Проверяет возможность получения стипендии в этом семестре.
     *
     * @return true/false.
     */
    public boolean isStipend() {
        return subjects.stream().allMatch(x -> x.getMark().value > 3);
    }

}