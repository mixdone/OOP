package task;

import java.io.File;
import java.io.IOException;

public class Loader {
    static public boolean clone(String url, String dest, String branch) throws IOException, InterruptedException {
        File directory = new File(dest);
        if (!directory.exists()){
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