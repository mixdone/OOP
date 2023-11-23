import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TestCreditBook {
    private static final CreditBook goodStudent         = new CreditBook();
    private static final CreditBook satisfactoryStudent  = new CreditBook();

    @BeforeAll
    static void setUp() {
        Semester goodSemester   = new Semester();
        SubjectWithMark eng     = new SubjectWithMark("English", Mark.GOOD);
        SubjectWithMark c       = new SubjectWithMark("imperative programming", Mark.EXCELLENT);
        SubjectWithMark haskell = new SubjectWithMark("declarative programming", Mark.EXCELLENT);

        goodSemester.addSubject(eng);
        goodSemester.addSubject(c);
        goodSemester.addSubject(haskell);

        Semester excelentSemester   = new Semester();
        SubjectWithMark pe          = new SubjectWithMark("PE", Mark.EXCELLENT);
        SubjectWithMark math        = new SubjectWithMark("math", Mark.EXCELLENT);

        excelentSemester.addSubject(pe);
        excelentSemester.addSubject(math);

        Semester satisfactorySemester   = new Semester();
        SubjectWithMark rus             = new SubjectWithMark("Russian", Mark.SATISFACTORILY);
        SubjectWithMark dp
                = new SubjectWithMark("Digital Platforms", Mark.SATISFACTORILY);

        satisfactorySemester.addSubject(rus);
        satisfactorySemester.addSubject(dp);

        satisfactoryStudent.addSemester(goodSemester);
        satisfactoryStudent.addSemester(satisfactorySemester);

        satisfactoryStudent.setGraduationWork(new SubjectWithMark("GraduationWork", Mark.GOOD));

        goodStudent.addSemester(goodSemester);
        goodStudent.addSemester(excelentSemester);

        goodStudent.setGraduationWork(new SubjectWithMark("GraduationWork", Mark.EXCELLENT));
    }

    @ParameterizedTest
    @MethodSource("getForAverage")
    void testAverage(double expected, CreditBook student) {
        Assertions.assertEquals(expected, student.getAverageMark());
    }

    @ParameterizedTest
    @MethodSource("getForStipend")
    void testStipend(boolean expected, CreditBook student) {
        Assertions.assertEquals(expected, student.increasedStipend());
    }

    @ParameterizedTest
    @MethodSource("getForHonoursDegree")
    void testHonoursDegree(boolean expected, CreditBook student) {
        Assertions.assertEquals(expected, student.honoursDegree());
    }

    static Stream<Arguments> getForAverage() {
        return Stream.of(
          Arguments.of(4.8, goodStudent),
          Arguments.of(4.0, satisfactoryStudent));
    }

    static Stream<Arguments> getForStipend() {
        return Stream.of(
                Arguments.of(true, goodStudent),
                Arguments.of(false, satisfactoryStudent));
    }

    static Stream<Arguments> getForHonoursDegree() {
        return Stream.of(
                Arguments.of(true, goodStudent),
                Arguments.of(false, satisfactoryStudent));
    }
}
