package task.checker;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.concurrent.atomic.AtomicReference;

public class DeadLineCheck implements Auditor {
    /**
     * Check.
     *
     * @param context path and task.
     */
    @Override
    public void check(Context context) {
        File repository = new File(context.getPath());
        Iterable<RevCommit> commits = null;
        try {
            commits = Git.open(repository).log().addPath(context.getTask().getName()).call();
        } catch (GitAPIException | IOException e) {
            throw new RuntimeException(e);
        }

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

        var result = context.getResult();
        assert firstCommitDate.get() != null;
        if (firstCommitDate.get().isBefore(context.getTask().getSoftDeadline())) {
            result.setMark(1.0);
        } else if (firstCommitDate.get().isBefore(context.getTask().getHardDeadline())) {
            result.setMark(0.5);
        } else {
            result.setMark(0.0);
        }

        context.setResult(result);

    }
}
