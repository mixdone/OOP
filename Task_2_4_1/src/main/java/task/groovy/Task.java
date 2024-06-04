package task.groovy;

import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Class task groovy.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Task extends Groovy {
    private String name;
    private String description;
    private LocalDate softDeadline;
    private LocalDate hardDeadline;
}