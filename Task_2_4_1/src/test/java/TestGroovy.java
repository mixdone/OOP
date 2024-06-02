import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import task.groovy.Config;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public class TestGroovy {

    @Test
    public void groupTest() throws URISyntaxException {
        var info = new Config();
        URI configPath = Objects.requireNonNull(TestGroovy.class.getClassLoader()
                .getResource("config.groovy")).toURI();
        info.runFrom(configPath);
        info.postProcess();

        for (var group : info.getGroups()) {
            Assertions.assertEquals(group.getNumber(), 22214);
        }
    }

    @Test
    public void studentsTest() throws URISyntaxException {
        var info = new Config();
        URI configPath = Objects.requireNonNull(TestGroovy.class.getClassLoader()
                .getResource("config.groovy")).toURI();
        info.runFrom(configPath);
        info.postProcess();

        for (var group : info.getGroups()) {
            Assertions.assertEquals(group.getStudents().size(), 2);
        }
    }
}
