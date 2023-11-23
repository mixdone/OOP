import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TestSemester {
    @Test
    void testIsStipend() {
        Semester semester = new Semester();
        semester.addSubject(new SubjectWithMark("math", Mark.EXCELLENT));
        semester.addSubject(new SubjectWithMark("eng", Mark.GOOD));

        Assertions.assertTrue(semester.isStipend());
    }

    @Test
    void testAddGetMark() {
        Semester semester       = new Semester();
        SubjectWithMark eng     = new SubjectWithMark("English", Mark.GOOD);
        SubjectWithMark c       = new SubjectWithMark("imperative programming", Mark.EXCELLENT);
        SubjectWithMark haskell = new SubjectWithMark("declarative programming", Mark.EXCELLENT);

        semester.addSubject(eng);
        semester.addSubject(c);
        semester.addSubject(haskell);

        ArrayList<SubjectWithMark> list = new ArrayList<>();
        list.add(eng);
        list.add(c);
        list.add(haskell);

        Assertions.assertArrayEquals(list.toArray(), semester.getSubjects().toArray());
    }
}
