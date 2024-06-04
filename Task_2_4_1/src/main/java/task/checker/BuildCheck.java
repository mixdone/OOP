package task.checker;

import org.gradle.tooling.GradleConnector;

import java.io.File;

public class BuildCheck implements Auditor{
    /**
     * Check.
     *
     * @param context path and task.
     */
    @Override
    public void check(Context context) {
        GradleConnector connector = GradleConnector.newConnector();
        connector.forProjectDirectory(new File(context.getPath() + context.getTask().getName()));
        try (var connection = connector.connect()) {
            connection.newBuild().forTasks("build").run();
        } catch (RuntimeException e) {
            System.out.println("Build exception: " + e);
            return;
        }

        var result = context.getResult();
        result.setBuild(true);
        context.setResult(result);
    }
}
