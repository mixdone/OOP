import java.util.Arrays;
import java.util.List;

/**
 * Main.
 */
public class Main {
    /**
     * Main.
     *
     * @param args - args.
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        List<Integer> list = Arrays.asList(20319251, 6997967, 6998009, 6998029, 27644437,
                181, 191, 193, 197, 199, 211, 223, 227, 2, 3, 5, 7, 11, 13, 17,
                6997967, 6998009, 6998029, 27644437, 6997967, 6998009, 6998029, 27644437,
                20319251, 181, 191, 193, 197, 199, 211, 229, 233, 239, 241, 251, 1597, 28657,
                514229, 433494437, 257, 263, 269, 271, 6997901, 6997927, 6997937, 17858849,
                20319251, 181, 191, 193, 197, 199, 211, 223, 227,
                6997967, 6998009, 6998029, 27644437, 6997967, 6998009, 6998029, 27644437,
                20319251, 181, 191, 193, 197, 199, 211, 229, 233, 239, 241, 251, 1597, 28657,
                514229, 433494437, 257, 263, 269, 271, 6997901, 6997927, 6997937, 17858849,
                20319251, 181, 191, 193, 197, 199, 211, 223, 227,
                6997967, 6998009, 6998029, 27644437, 6997967, 6998009, 6998029, 27644437,
                20319251, 181, 191, 193, 197, 199, 211, 229, 233, 239, 241, 251, 1597, 28657,
                514229, 433494437, 257, 263, 269, 271, 6997901, 6997927, 6997937, 17858849,
                6997967, 6998009, 6998029, 27644437, 6998039, 20165149, 6998051, 6998053,
                6997967, 6998009, 6998029, 27644437, 1000000);

        long start;
        long end;


        start = System.currentTimeMillis();
        SequentialSearch.notPrimeSearch(list);
        end = System.currentTimeMillis();

        System.out.println(end - start);

        for (int i = 1; i < 8; i++) {
            start = System.currentTimeMillis();
            ThreadSearch.notPrimeSearch(list, i);
            end = System.currentTimeMillis();

            System.out.println(end - start);
        }

        start = System.currentTimeMillis();
        StreamSearch.notPrimeSearch(list);
        end = System.currentTimeMillis();

        System.out.println(end - start);
    }
}
