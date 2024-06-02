package task.groovy;

import lombok.Data;

@Data
public class TaskResult {
    Boolean build = false;
    Boolean javadoc = false;
    Boolean checkstyle = false;
    Integer skippedTests = 0;
    Integer passedTests = 0;
    Integer failedTests = 0;
    Double mark = - 0.5;
}
