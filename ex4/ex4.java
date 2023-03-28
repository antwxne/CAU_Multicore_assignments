class Ex4Thread extends Thread {
    int begin;
    int end;
    int offset;
    int result = 0;

    Ex4Thread(int b, int e, int total_thread) {
        begin = b;
        end = e;
        offset = total_thread;
    }

    public void run() {
        for (int i = begin; i < end; i += offset) {
            result += isPrime(i);
        }
    }

    private static int isPrime(int x) {
        if ((x <= 1) || (x & 1) == 0) // check if x % 2 == 0 faster
            return 0;
        for (int i = 3; i < x; i++) {
            if ((x % i == 0) && (i != x))
                return 0;
        }
        return 1;
    }
}

class ex4 {
    private static int NUM_END = 200000;
    private static int N_THREAD = 10;

    public static void main(String[] args) {
        int result = 0;
        if (args.length == 2) {
            N_THREAD = Integer.parseInt(args[0]);
            NUM_END = Integer.parseInt(args[1]);
        }
        Ex4Thread[] threads = new Ex4Thread[N_THREAD];
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < threads.length; ++i) {
            threads[i] = new Ex4Thread(i, NUM_END, N_THREAD);
            threads[i].start();
        }
        for (int i = 0; i < threads.length; ++i) {
            try {
                threads[i].join();
                result += threads[i].result;
            } catch(InterruptedException e) {}
        }
        long endTime = System.currentTimeMillis();
        long timeDiff = endTime - startTime;
        System.out.printf("Execution Time : %d ms\n", timeDiff);
        System.out.printf("1... %d prime# counter= %d\n",NUM_END - 1, result);
    }
}