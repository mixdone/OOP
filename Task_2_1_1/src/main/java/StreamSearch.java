import java.util.List;

/**
 * Поиск при помощи parallelStream.
 */
public class StreamSearch {
    /**
     * Поиск при помощи parallelStream.
     *
     * @param list - список целых чисел.
     * @return boolean.
     */
    public static boolean notPrimeSearch(List<Integer> list) {
        return list.parallelStream().anyMatch(IsNotPrime::isNotPrime);
    }
}
