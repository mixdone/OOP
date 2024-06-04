package task.checker;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import org.gradle.tooling.GradleConnector;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;

/**
 * Auditor.
 */
public class TestCheck implements Auditor {

    /**
     * Check.
     *
     * @param context path and task.
     */
    @Override
    public void check(Context context) {
        var  path = context.getPath() + context.getTask().getName();
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
            var documentBuilderFactory = DocumentBuilderFactory.newInstance();
            var documentBuilder = documentBuilderFactory.newDocumentBuilder();
            var doc = documentBuilder.parse(new File(resultPath));

            doc.getDocumentElement().normalize();
            NamedNodeMap attrList = doc.getElementsByTagName("testsuite").item(0).getAttributes();
            int skipped = Integer.parseInt(attrList.getNamedItem("skipped").getNodeValue());
            int failed = Integer.parseInt(attrList.getNamedItem("failures").getNodeValue());
            int passed = Integer.parseInt(attrList.getNamedItem("tests").getNodeValue())
                    - skipped - failed;

            var result = context.getResult();
            result.setSkippedTests(skipped);
            result.setFailedTests(failed);
            result.setPassedTests(passed);
            context.setResult(result);

        } catch (IOException | NullPointerException
                 | ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }
    }
}
