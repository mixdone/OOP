import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Тесты.
 */
public class TestExpression {

    @ParameterizedTest
    @MethodSource("getTests")
    public void test(double expected, String expr) {
        try {
            Assertions.assertEquals(expected, new Expression(expr).calculate());
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    static Stream<Arguments> getTests() {
        return Stream.of(
                Arguments.of(512, "pow 2 + sin - 7 7 9"),
                Arguments.of(5, "+ 7 - sqrt 4"),
                Arguments.of(1, " + pow sin 2 2 pow cos 2 2"),
                Arguments.of(1, "cos sin * 2 0"),
                Arguments.of( 65000, " -  pow 2  pow 2  pow 2 2  536   ")
        );
    }

    @Test
    public void testWrongPolishNotationException(){
        String s = "sin 4 8";
        try {
            new Expression(s).calculate();
        } catch (WrongPolishNotationException e1) {
            return;
        } catch (IllegalOperationException e2) {
            Assertions.fail();
        }
        Assertions.fail();
    }

    @ParameterizedTest
    @MethodSource("getTestsIllegalOperationException")
    public void testIllegalOperationException(String expr) {
        try {
            new Expression(expr).calculate();
        } catch (WrongPolishNotationException e1) {
            Assertions.fail();
        } catch (IllegalOperationException e2) {
            return;
        }
        Assertions.fail();
    }

    static Stream<Arguments> getTestsIllegalOperationException() {
        return Stream.of(
            Arguments.of("/ 1 0"),
            Arguments.of("log -1"),
            Arguments.of("sqrt -5")
        );
    }
}
