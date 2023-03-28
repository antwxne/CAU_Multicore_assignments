class SumThread extends Thread {
    int lo, hi; // fields for communicating inputs
    // on peut pas slice ne java... c'est pour ca lo = debut et hi = fin
    int[] arr;
    int ans = 0; // for communicating result

    SumThread(int[] a, int l, int h) {
        lo = l;
        hi = h;
        arr = a;
    }

    public void run() {
        for (int i = lo; i < hi; ++i) {
            ans += arr[i];
        }
    }
}

class ex2_multi_threaded_1 {
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
        SumThread[] t = new SumThread[NUM_THREAD];
        int offset = NUM_END / NUM_THREAD;
        int result = 0;
        for (int i = 0; i < t.length; ++i) {
            t[i] = new SumThread(arr, i * offset, i * offset + offset);
            t[i].start();
        }
        for (int i = 0; i < t.length; ++i) {
            try {
            t[i].join();
            result += t[i].ans;
        } catch (InterruptedException e) {
            }  
        }
        return result;
    }
}