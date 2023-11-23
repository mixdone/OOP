import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestSubjectWithMark {
    @Test
    void testGetName() {
        SubjectWithMark subj = new SubjectWithMark("smth", Mark.GOOD);

        Assertions.assertEquals("smth", subj.getName());
    }

    @Test
    void testGetMark() {
        SubjectWithMark subj = new SubjectWithMark("smth", Mark.UNSATISFACTORILY);

        Assertions.assertEquals(2, subj.getMark().value);
    }
}
