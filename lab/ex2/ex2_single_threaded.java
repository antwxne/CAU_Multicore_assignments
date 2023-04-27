import java.util.Arrays;

class SumThread extends Thread {
    int lo, hi; // fields for communicating inputs
    int[] arr;
    int ans = 0; // for communicating result

    SumThread(int[] a, int l, int h) {
        lo = l;
        hi = h;
        arr = a;
    }

    public void run() {
        // insert your code here
    }
}

class ex2_single_threaded {
    private static int NUM_END = 10000;
    private static int NUM_THREAD = 4; // assume NUM_END is divisible by NUM_THREAD

    public static void main(String[] args) {
        if (args.length == 2) {
            NUM_THREAD = Integer.parseInt(args[0]);
            NUM_END = Integer.parseInt(args[1]);
        }
        int[] int_arr = new int[NUM_END];
        int i, s;
        for (i = 0; i < NUM_END; i++)
            int_arr[i] = i + 1;
        s = sum(int_arr);
        System.out.println("sum=" + s);
    }

    static int sum(int[] arr) {
        return Arrays.stream(arr).sum();
    }
}