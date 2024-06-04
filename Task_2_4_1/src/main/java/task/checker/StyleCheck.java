package task.checker;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import com.puppycrawl.tools.checkstyle.Main;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Auditor.
 */
public class StyleCheck implements Auditor {

    /**
     * Check.
     *
     * @param context path and task.
     */
    @Override
    public void check(Context context) {
        int status = 0;
        try {
            status = SystemLambda.catchSystemExit(() -> {
                var config = context.getPath() + "/.github/google_checks.xml";
                Main.main("-c", config, "-o", context.getPath() + "result.txt",
                        context.getPath() + "/" + context.getTask().getName() + "/src/main/java");
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        var result = context.getResult();
        if (status != 0) {
            result.setCheckstyle(false);
            return;
        }

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(context.getPath() + "result.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int counter = 0;
        while (true) {
            try {
                if (!reader.ready()) {
                    break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                if (reader.readLine().contains("[WARN]")) {
                    counter++;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        result.setCheckstyle(counter <= 10);
    }
}
