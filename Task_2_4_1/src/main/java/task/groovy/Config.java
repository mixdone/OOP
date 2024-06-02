package task.groovy;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class Config extends Groovy {
    private List<Group> groups;
    private List<Task> tasks;
    private String settings;
}