import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import workers.Baker;
import workers.Delivery;

/**
 * Test read json.
 */
public class TestReadJson {

    @Test
    public void testWorkDay() {
        try {
            Reader reader = Files.newBufferedReader(Path.of("src/pizzeria.json"), StandardCharsets.UTF_8);
            ReadJson readJson = new ReadJson(reader);

            Assertions.assertEquals(100000, readJson.getWorkingDay());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest
    @MethodSource("getBakers")
    public void testBakers(String expected, Baker tested) {
        Assertions.assertEquals(expected, tested.getName());
    }

    @ParameterizedTest
    @MethodSource("getDelivery")
    public void testDelivery(String expected, Delivery tested) {
        Assertions.assertEquals(expected, tested.getName());
    }

    static Stream<Arguments> getBakers() {
        try {
            Reader reader = Files.newBufferedReader(Path.of("src/pizzeria.json"), StandardCharsets.UTF_8);
            ReadJson readJson = new ReadJson(reader);

            var bakers = readJson.getBakers();

            return Stream.of(
                    Arguments.of("Petya Vasichkin", bakers.get(0)),
                    Arguments.of("Ivan Frolov", bakers.get(1)),
                    Arguments.of("Artem Kustov", bakers.get(2)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static Stream<Arguments> getDelivery() {
        try {
            Reader reader = Files.newBufferedReader(Path.of("src/pizzeria.json"), StandardCharsets.UTF_8);
            ReadJson readJson = new ReadJson(reader);

            var delivery = readJson.getDelivery();

            return Stream.of(
                    Arguments.of("Ivan Korsov", delivery.get(0)),
                    Arguments.of("Sergey Suslov", delivery.get(1)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
