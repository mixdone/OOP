public class HeapSort {
    public static int[] heapsort(int array[]){

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

    static void makeheap(int array[], int len, int i)
    {
        int max = i;
        int left_son = 2 * i + 1;
        int right_son = 2 * i + 2;

        if (left_son < len && array[left_son] > array[max])
            max = left_son;

        if (right_son < len && array[right_son] > array[max])
            max = right_son;

        if (max != i) {
            int tmp = array[i];
            array[i] = array[max];
            array[max] = tmp;

            makeheap(array, len, max);
        }
    }

    public static void main(String args[]) {

    }

}
