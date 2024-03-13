import java.util.List;

public class NotPrimeSearch {

    /**
     * Проверка непростоты.
     *
     * @param num число.
     * @return boolean.
     */
    public boolean isNotPrime(int num) {
        if (num == 0 || num == 1) {
            return true;
        } else if (num == 2) {
            return false;
        } else if (num % 2 == 0) {
            return true;
        }

        for (int i = 3; i <= Math.sqrt(num); i += 2) {
            if (num % i == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Последовательный поиск непростого чисел.
     *
     * @param array список целых чисел.
     * @return boolean.
     */
    public boolean notPrimeSearch(List<Integer> array) {
        for (Integer integer : array) {
            if (isNotPrime(integer)) {
                return true;
            }
        }

        return false;
    }
}
