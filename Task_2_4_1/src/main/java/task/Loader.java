package task;

import java.io.File;
import java.io.IOException;

/**
 * Class loader.
 */
public class Loader {

    /**
     * Clone repository.
     *
     * @param url url.
     * @param path path.
     * @param branch branch.
     *
     * @return boolean.
     *
     * @throws IOException exception.
     * @throws InterruptedException exception.
     */
    public static boolean clone(String url, String path, String branch)
            throws IOException, InterruptedException {
        File directory = new File(path);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                return false;
            }
        }

        ProcessBuilder pb = new ProcessBuilder()
                .command("git", "clone", "-b", branch, url)
                .directory(directory);
        var process = pb.start();
        int exitCode = process.waitFor();
        return exitCode == 0;
    }
}