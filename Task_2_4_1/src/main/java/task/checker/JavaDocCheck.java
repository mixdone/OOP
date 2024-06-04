package task.checker;

import org.gradle.tooling.GradleConnector;

import java.io.File;

public class JavaDocCheck implements Auditor {

    /**
     * Check.
     *
     * @param context path and task.
     */
    @Override
    public void check(Context context) {
        GradleConnector connector = GradleConnector.newConnector();
        connector.forProjectDirectory(new File(context.getPath()));
        try (var connection = connector.connect()) {
            connection.newBuild().forTasks("javadoc").run();
        } catch (RuntimeException e) {
            System.out.println("Javadoc exception: " + e);
            return;
        }

        var result = context.getResult();
        result.setJavadoc(true);
        context.setResult(result);
    }
}
