import java.util.List;

/**
 * Последовательный поиск непростого чисел.
 */
public class SequentialSearch {

    /**
     * Последовательный поиск непростого чисел.
     *
     * @param array - список целых чисел.
     * @return boolean.
     */
    public static boolean notPrimeSearch(List<Integer> array) {
        for (Integer integer : array) {
            if (IsNotPrime.isNotPrime(integer)) {
                return true;
            }
        }

        return false;
    }
}
