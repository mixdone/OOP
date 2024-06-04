package task.checker;

import lombok.Data;
import task.groovy.Task;
import task.groovy.TaskResult;

/**
 * Context for auditors.
 */
@Data
public class Context {
    private TaskResult result;
    private String path;
    private Task task;
}
