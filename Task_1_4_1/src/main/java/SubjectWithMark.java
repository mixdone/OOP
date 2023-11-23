/**
 * Класс предмета с оценкой.
 */
public class SubjectWithMark {
    String name;
    Mark mark;

    /**
     * Конструктор класса предмет с зачетом.
     */
    public SubjectWithMark(String title, Mark mark) {
        this.name = title;
        this.mark = mark;
    }

    /**
     * Возвращает название предмета.
     * @return название предмета.
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает оценку.
     * @return оценку.
     */
    public Mark getMark() {
        return mark;
    }
}
