package task.groovy;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class Task extends Groovy {
    private String name;
    private String description;
    private LocalDate softDeadline;
    private LocalDate hardDeadline;
}