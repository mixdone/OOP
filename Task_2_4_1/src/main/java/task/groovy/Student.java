package task.groovy;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Class student groovy.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Student extends Groovy {
    private String name;
    private String username;
    private String repository;
    private List<Task> tasks;
}
