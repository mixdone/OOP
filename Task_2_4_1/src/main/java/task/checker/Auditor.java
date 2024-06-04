package task.checker;

public interface Auditor {
    /**
     * Check.
     *
     * @param context path and task.
     */
    public void check(Context context);
}
