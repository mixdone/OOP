import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Многопоточный поиск.
 */
public class ThreadSearch{
    private static AtomicBoolean hasNotPrime = new AtomicBoolean();

    static class OneThreadSearch extends Thread {
        List<Integer> list;

        /**
         * Конструктор.
         *
         * @param numbers - список целых чисел.
         */
        public OneThreadSearch(List<Integer> numbers) {
            list = numbers;
        }

        /**
         * Run.
         */
        @Override
        public void run() {
            if(SequentialSearch.notPrimeSearch(list)) {
                hasNotPrime.set(true);
            }
        }
    }

    /**
     * Многопоточный поиск.
     *
     * @param list - список целых чисел.
     * @param threadAmount - желаемое количество потоков.
     * @return boolean.
     * @throws InterruptedException
     */
    public static boolean notPrimeSearch(List<Integer> list, int threadAmount) throws InterruptedException {
        hasNotPrime.set(false);

        if (threadAmount > list.size()) {
            threadAmount = list.size();
        }

        int part = list.size() / threadAmount;
        OneThreadSearch[] threads = new OneThreadSearch[threadAmount];

        for (int i = 0; i < threadAmount - 1; ++i) {
            threads[i] = new OneThreadSearch(list.subList(i * part, (i + 1) * part));
            threads[i].start();
        }
        threads[threadAmount - 1] = new OneThreadSearch(list.subList((threadAmount - 1) * part, list.size()));
        threads[threadAmount - 1].start();

        for (int i = 0; i < threadAmount; ++i) {
            threads[i].join();
        }

        return hasNotPrime.get();
    }

}
