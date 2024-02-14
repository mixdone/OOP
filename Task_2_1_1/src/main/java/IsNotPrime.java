/**
 * Класс непростых чисел.
 */
public class IsNotPrime {
    /**
     * Проверка непростоты.
     *
     * @param num - числою.
     * @return boolean.
     */
    public static boolean isNotPrime(int num) {
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
}
