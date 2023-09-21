
public class HeapSort {
    /**
     * The method sorts an array using heap.
     *
     * @param array randomly filled.
     * @return sorted array.
     *
     */
    public static int[] heapsort(int[] array) {

        int length = array.length;

        for (int i = length / 2 - 1; i >= 0; i--) {
            makeheap(array, length, i);
        }
        for (int i = length - 1; i >= 0; i--) {
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;

            makeheap(array, i, 0);
        }

        return  array;
    }

    private static void makeheap(int[] array, int len, int i) {
        int max = i;
        int leftSon = 2 * i + 1;
        int rightSon = 2 * i + 2;

        if (leftSon < len && array[leftSon] > array[max]) {
            max = leftSon;
        }

        if (rightSon < len && array[rightSon] > array[max]) {
            max = rightSon;
        }

        if (max != i) {
            int tmp = array[i];
            array[i] = array[max];
            array[max] = tmp;

            makeheap(array, len, max);
        }
    }
}