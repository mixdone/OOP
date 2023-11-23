import java.util.ArrayList;

/**
 * Класс зачетной книжки.
 */
public class CreditBook {
    ArrayList<Semester> semesters = new ArrayList<>();
    SubjectWithMark graduationWork;

    void addSemester(Semester semester) {
       this.semesters.add(semester);
    }
    void setGraduationWork(SubjectWithMark work) {
       this.graduationWork = work;
   }
    /**
     * Возращает среднее значение оценки.
     *
     * @return средняя арифметическая оценка.
     */
    public double getAverageMark() {
        double summ = semesters.stream().mapToDouble(x ->
                x.getSubjects().stream().mapToInt(y ->
                        y.getMark().value).sum()).sum();
        double count = semesters.stream().mapToDouble(x ->
                x.getSubjects().size()).sum();
        return summ / count;
    }

    /**
     * Возможность получения красного диплома.
     *
     * @return true/false.
     */
    public boolean honoursDegree() {
        boolean notBadMark = semesters.stream().allMatch(x ->
                x.getSubjects().stream().allMatch(y ->
                        y.getMark().value > 3));
        double fivesAmount = semesters.stream().mapToLong(x ->
                x.getSubjects().stream().filter(y ->
                        y.getMark().value == 5).count()).sum();
        double marksAmount = semesters.stream().mapToLong(x ->
                   x.getSubjects().size()).sum();

        return (fivesAmount / marksAmount > 0.75)
                && notBadMark
                && (graduationWork.getMark().value == 5);
    }

    /**
     * Будет ли стипендия повышенной.
     *
     * @return true/false.
     */
    public boolean increasedStipend() {
        return semesters.get(semesters.size() - 1).getSubjects().stream().allMatch(x ->
                x.getMark().value == 5);
    }

}
