import java.util.concurrent.CyclicBarrier;

class T1 extends Thread {
    CyclicBarrier _barrier;

    T1(CyclicBarrier b) {
        _barrier = b;
    }

    @Override
    public void run() {
        int sleep_time = 100;
        while (true) {
            try {
                System.out.printf("Thread %d: will sleep for %dms\n", getId(), sleep_time);
                sleep(sleep_time);
                _barrier.await();
            } catch (Exception e) {
            }
        }
    }
}

class T2 extends Thread {
    CyclicBarrier _barrier;

    T2(CyclicBarrier b) {
        _barrier = b;
    }

    @Override
    public void run() {
        int sleep_time = 300;
        while (true) {
            try {
                System.out.printf("Thread %d: will sleep for %dms\n", getId(), sleep_time);
                sleep(sleep_time);
                _barrier.await();
            } catch (Exception e) {
            }
        }
    }
}

class T3 extends Thread {
    CyclicBarrier _barrier;

    T3(CyclicBarrier b) {
        _barrier = b;
    }

    @Override
    public void run() {
        int sleep_time = 500;
        while (true) {
            try {
                System.out.printf("Thread %d: will sleep for %dms\n", getId(), sleep_time);
                sleep(sleep_time);
                _barrier.await();
            } catch (Exception e) {
            }
        }
    }
}

class T4 extends Thread {
    CyclicBarrier _barrier;

    T4(CyclicBarrier b) {
        _barrier = b;
    }

    @Override
    public void run() {
        int sleep_time = 700;
        while (true) {
            try {
                System.out.printf("Thread %d: will sleep for %dms\n", getId(), sleep_time);
                sleep(sleep_time);
                _barrier.await();
            } catch (Exception e) {
            }
        }
    }
}

public class ex4 {
    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(4, new Runnable() {
            public void run() {
                System.out.println("Threads synchronized");
            }
        });
        Thread[] threads = { new T1(barrier), new T2(barrier), new T3(barrier), new T4(barrier) };
        for (int i = 0; i < threads.length; ++i) {
            threads[i].start();
        }
        for (int i = 0; i < threads.length; ++i) {
            try {
            threads[i].join();
            } catch (Exception e) {}
        }
    }
}
