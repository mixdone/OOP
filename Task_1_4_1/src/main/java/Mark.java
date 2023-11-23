public enum Mark {
    EXCELLENT(5),
    GOOD(4),
    SATISFACTORILY(3),
    UNSATISFACTORILY(2);

    public final Integer value;

    private Mark(int value) {
        this.value = value;
    }
}
