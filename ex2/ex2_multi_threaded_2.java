class SumThread extends Thread {
    int lo, hi; // fields for communicating inputs
    int[] arr;
    int sequential_cutoff;
    int ans = 0; // for communicating result

    SumThread(int[] a, int l, int h) {
        lo = l;
        hi = h;
        arr = a;
        sequential_cutoff = 500;
    }

    public void run() {
        if (hi - lo < sequential_cutoff)
            for (int i = lo; i < hi; i++)
                ans += arr[i];
        else {
            SumThread left = new SumThread(arr, lo, (hi + lo) / 2);
            SumThread right = new SumThread(arr, (hi + lo) / 2, hi);
            left.start();
            right.start();
            try {
                left.join();
            } catch (InterruptedException e) {
            }
            try {
                right.join();
            } catch (InterruptedException e) {
            }
            ans = left.ans + right.ans;
        }
    }
}

class ex2_multi_threaded_2 {
    private static int NUM_END = 10000;

    public static void main(String[] args) {
        if (args.length == 1) {
            NUM_END = Integer.parseInt(args[0]);
        }
        int[] int_arr = new int[NUM_END];
        int i, s;
        for (i = 0; i < NUM_END; i++)
            int_arr[i] = i + 1;
        s = sum(int_arr);
        System.out.println("sum=" + s);
    }

    static int sum(int[] arr) {
        SumThread t = new SumThread(arr, 0, arr.length);
        t.start();
        try {
            t.join();
            return t.ans;
        } catch (InterruptedException e) {
            return 0;
        }

    }
}