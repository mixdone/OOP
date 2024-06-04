package task.checker;

/**
 * Auditor.
 */
public interface Auditor {
    /**
     * Check.
     *
     * @param context path and task.
     */
    public void check(Context context);
}
