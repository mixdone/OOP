package task.groovy;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Class group groovy.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Group extends Groovy {
    private int number;
    private List<Student> students;
}
