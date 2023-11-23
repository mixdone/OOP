/**
 * Оценка.
 */
public enum Mark {
    EXCELLENT(5),
    GOOD(4),
    SATISFACTORILY(3),
    UNSATISFACTORILY(2);

    public final Integer value;

    /**
     * Цисленное представление оценки.
     *
     * @param value - оценка в виде числа.
     */
    private Mark(int value) {
        this.value = value;
    }
}
