package task;

import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.SneakyThrows;
import task.groovy.Student;
import task.groovy.Task;
import task.groovy.TaskResult;

/**
 * Class render.
 */
public class Render {

    /**
     * Render.
     *
     * @param tasks list of task.
     * @param tasksResults results.
     */
    @SneakyThrows
    public static void render(List<Task> tasks,
                              HashMap<Student, HashMap<Task, TaskResult>> tasksResults) {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);
        cfg.setClassForTemplateLoading(Render.class, "/");
        cfg.setDefaultEncoding("UTF-8");
        Map<String, Object> root = new HashMap<>();
        root.put("tasks", tasks);
        root.put("tasksResults", tasksResults);

        Template temp = cfg.getTemplate("tmp.ftl");
        Writer out = new OutputStreamWriter(new
                FileOutputStream("src/main/resources/index.html"));
        temp.process(root, out);
    }
}