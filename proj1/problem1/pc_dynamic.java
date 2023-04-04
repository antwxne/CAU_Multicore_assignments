import java.util.Vector;
import java.util.concurrent.PriorityBlockingQueue;

class pcThreadDynamic extends Thread {
    int end;
    int task_size;
    int result = 0;
    PriorityBlockingQueue<Integer> numbersRef;

    pcThreadDynamic(PriorityBlockingQueue<Integer> ref, int t_size, int e) {
        end = e;
        task_size = t_size;
        numbersRef = ref;
    }

    public void run() {
        long startTime = System.currentTimeMillis();

        while (true) {
            try {
                if (numbersRef.isEmpty()) {
                    break;
                }
                int begin = numbersRef.take();
                for (int i = begin; i < begin + task_size; ++i) {
                    result += isPrime(i);
                }
            } catch (Exception e) {
            }
        }
        long endTime = System.currentTimeMillis();
        long timeDiff = endTime - startTime;
        System.out.printf("Execution Time of thread %d : %d ms\n", Thread.currentThread().getId(), timeDiff);
    }

    private static int isPrime(int x) {
        if ((x <= 1) || (x & 1) == 0)
            return 0;
        for (int i = 3; i < x; i++) {
            if ((x % i == 0) && (i != x))
                return 0;
        }
        return 1;
    }
}

class pc_dynamic {
    private static int NUM_END = 200000;
    private static int N_THREAD = 10;
    private static int TASK_SIZE = 10;

    public static void main(String[] args) {
        int result = 0;
        if (args.length == 3) {
            N_THREAD = Integer.parseInt(args[0]);
            NUM_END = Integer.parseInt(args[1]);
            TASK_SIZE = Integer.parseInt(args[2]);
        }
        Vector<Integer> number_list = new Vector<Integer>();

        for (int i = 0; i < NUM_END; i += TASK_SIZE) {
            number_list.add(i);
        }
        PriorityBlockingQueue<Integer> qInt = new PriorityBlockingQueue<Integer>(number_list);

        pcThread[] threads = new pcThread[N_THREAD];
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < threads.length; ++i) {
            threads[i] = new pcThread(qInt, TASK_SIZE, NUM_END);
            threads[i].start();
        }
        for (int i = 0; i < threads.length; ++i) {
            try {
                threads[i].join();
                result += threads[i].result;
            } catch (InterruptedException e) {
            }
        }
        long endTime = System.currentTimeMillis();
        long timeDiff = endTime - startTime;
        System.out.printf("Execution Time : %d ms\n", timeDiff);
        System.out.printf("1... %d prime# counter == %d\n", NUM_END - 1, result);
    }
}