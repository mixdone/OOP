package task.checker;

import lombok.Getter;
import task.groovy.Task;
import task.groovy.TaskResult;

/**
 * Auditor.
 */
@Getter
public class TaskChecker {

    private final TaskResult result;

    /**
     * Constructor.
     *
     * @param result task result.
     */
    public TaskChecker(TaskResult result) {
        this.result = result;
    }

    /**
     * Run checks.
     *
     * @param path path.
     * @param task task.
     */
    public void taskCheck(String path, Task task) {
        var taskPath = path + "/OOP/" + task.getName();

        var context = new Context();
        context.setTask(task);
        context.setResult(result);
        context.setPath(taskPath);

        var builder = new BuildCheck();
        builder.check(context);

        var javadoc = new JavaDocCheck();
        javadoc.check(context);

        var tester = new TestCheck();
        tester.check(context);

        context.setPath(path + "/OOP/");

        var styler = new StyleCheck();
        styler.check(context);

        if (result.getBuild() &&
                result.getFailedTests() == 0) {
            var deadliner = new DeadLineCheck();
            deadliner.check(context);
        }
    }



}