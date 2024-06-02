package task;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import com.puppycrawl.tools.checkstyle.Main;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import lombok.Getter;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.gradle.tooling.GradleConnector;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;
import task.groovy.Task;
import task.groovy.TaskResult;

/**
 * Auditor.
 */
@Getter
public class TaskChecker {

    private final TaskResult result;

    /**
     * Constructor.
     *
     * @param result task result.
     */
    public TaskChecker(TaskResult result) {
        this.result = result;
    }

    /**
     * Run checks.
     *
     * @param path path.
     * @param task task.
     */
    public void taskCheck(String path, Task task) {
        var taskPath = path + "/OOP/" + task.getName();
        try {
        checkBuild(taskPath);
        checkStyle(path + "/OOP/", task.getName());
        checkJavaDoc(taskPath);
        checkTests(taskPath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gradle build.
     *
     * @param path path.
     */
    private void checkBuild(String path) {
        GradleConnector connector = GradleConnector.newConnector();
        connector.forProjectDirectory(new File(path));
        try (var connection = connector.connect()) {
            connection.newBuild().forTasks("build").run();
        } catch (RuntimeException e) {
            System.out.println("Build exception: " + e);
            return;
        }

        result.setBuild(true);
    }

    /**
     * Check style.
     *
     * @param path path.
     * @param taskName task name.
     * @throws Exception exceprtion.
     */
    private void checkStyle(String path, String taskName) throws Exception {
        int status = SystemLambda.catchSystemExit(() -> {
            var config = path + "/.github/google_checks.xml";
            Main.main("-c", config, "-o", path + "result.txt",
                    path + "/" + taskName + "/src/main/java");
        });

        if (status != 0) result.setCheckstyle(false);

        BufferedReader reader = new BufferedReader(new FileReader(path + "result.txt"));
        int counter = 0;
        while(reader.ready()) {
            if (reader.readLine().contains("[WARN]")) {
                counter++;
            }
        }
        reader.close();
        result.setCheckstyle(counter <= 10);

    }

    /**
     * Generate javadoc.
     *
     * @param path path.
     */
    private void checkJavaDoc(String path) {
        GradleConnector connector = GradleConnector.newConnector();
        connector.forProjectDirectory(new File(path));
        try (var connection = connector.connect()) {
            connection.newBuild().forTasks("javadoc").run();
        } catch (RuntimeException e) {
            System.out.println("Javadoc exception: " + e);
            return;
        }

        result.setJavadoc(true);
    }

    /**
     * Run tests.
     *
     * @param path path.
     */
    private void checkTests(String path) {
        GradleConnector connector = GradleConnector.newConnector();
        connector.forProjectDirectory(new File(path));
        try (var connection = connector.connect()) {
            connection.newBuild().forTasks("test").run();
        } catch (RuntimeException e) {
            System.out.println("Build exception: " + e);
            return;
        }

        try {
            File testDir = new File(path + "/build/test-results/test/");
            String resultPath = "";
            for (var file : Objects.requireNonNull(testDir.listFiles())) {
                var filename = file.getName();
                if (filename.endsWith(".xml")) {
                    resultPath = path + "/build/test-results/test/" + filename;
                    break;
                }
            }
            var dbFactory = DocumentBuilderFactory.newInstance();
            var dBuilder = dbFactory.newDocumentBuilder();
            var doc = dBuilder.parse(new File(resultPath));

            doc.getDocumentElement().normalize();
            NamedNodeMap attrList = doc.getElementsByTagName("testsuite").item(0).getAttributes();
            int skipped = Integer.parseInt(attrList.getNamedItem("skipped").getNodeValue());
            int failed = Integer.parseInt(attrList.getNamedItem("failures").getNodeValue());
            int passed = Integer.parseInt(attrList.getNamedItem("tests").getNodeValue())
                    - skipped - failed;
            result.setSkippedTests(skipped);
            result.setFailedTests(failed);
            result.setPassedTests(passed);

        } catch (IOException | NullPointerException |
                 ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Check deadlines, get mark.
     *
     * @param path path.
     * @param task task.
     * @throws IOException exception.
     * @throws GitAPIException exception.
     */
    public void checkDeadlines(String path, Task task) throws IOException, GitAPIException {
        File repository = new File(path);
        var commits = Git.open(repository).log().addPath(task.getName()).call();

        AtomicReference<LocalDate> firstCommitDate = new AtomicReference<>(LocalDate.now());

        LocalDate finalFirstCommitDate = firstCommitDate.get();
        commits.forEach(revCommit -> {
            var date = LocalDate.ofInstant(Instant.ofEpochSecond(revCommit.getCommitTime()),
                    ZoneId.systemDefault());
            if (date.isBefore(finalFirstCommitDate)) {
                firstCommitDate.set(date);
            }
        });

        Git.shutdown();


        assert firstCommitDate.get() != null;
        if (firstCommitDate.get().isBefore(task.getSoftDeadline())) {
            result.setMark(1.0);
        } else if (firstCommitDate.get().isBefore(task.getHardDeadline())) {
            result.setMark(0.5);
        } else {
            result.setMark(0.0);
        }


    }
}