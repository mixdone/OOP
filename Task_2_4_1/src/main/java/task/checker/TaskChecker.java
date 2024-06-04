package task.checker;

import lombok.Getter;
import task.groovy.Task;
import task.groovy.TaskResult;

/**
 * Task checker.
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

        var context = new Context();
        context.setTask(task);
        context.setResult(result);
        context.setPath(path + "/OOP/");

        var auditors = new Auditor[] {new BuildCheck(), new StyleCheck(),
                new JavaDocCheck(), new TestCheck()};

        for (var auditor : auditors) {
            auditor.check(context);
        }

        if (result.getBuild() && result.getFailedTests() == 0) {
            var deadline = new DeadLineCheck();
            deadline.check(context);
        }
    }



}