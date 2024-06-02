package task;

import task.groovy.*;
import lombok.SneakyThrows;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Objects;

public class Application {

    @SneakyThrows
    public static void main(String[] args) throws URISyntaxException {
        var results = new HashMap<Student, HashMap<Task, TaskResult>>();
        var info = new Config();
        URI configPath = Objects.requireNonNull(Application.class.getClassLoader()
                .getResource("config.groovy")).toURI();
        info.runFrom(configPath);
        info.postProcess();

        for (var group : info.getGroups()) {
            System.out.println(group.getNumber());
            for (var student : group.getStudents()) {
                results.put(student, new HashMap<>());
                String path = "Repositories/" + group.getNumber() + "/" + student.getUsername();
                var status = Loader.clone(student.getRepository(), path, info.getSettings());
                if (!status) {
                    System.out.println("something went wrong");
                    continue;
                }
                for (var task : info.getTasks()) {
                    var taskResult = new TaskResult();
                    var auditor = new TaskChecker(taskResult);
                    auditor.taskCheck(path, task);
                    if (auditor.getResult().getBuild() && auditor.getResult().getFailedTests() == 0) {
                        auditor.checkDeadlines(path + "/OOP", task);
                    }
                    System.out.println(auditor.getResult());
                    results.get(student).put(task, auditor.getResult());
                }
            }
        }
        Render.render(info.getTasks(), results);
    }
}
