package task.groovy;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Class config groovy.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Config extends Groovy {
    private List<Group> groups;
    private List<Task> tasks;
    private String settings;
}