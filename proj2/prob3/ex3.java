import java.util.concurrent.atomic.AtomicInteger;

class T1 extends Thread {
    AtomicInteger _i;

    T1(AtomicInteger i) {
        _i = i;
    }

    @Override
    public void run() {
        while (true) {
            if (_i.get() > 20) {
                _i.set(0);
                System.out.println("Reseting the integer to 0");
                try {
                    sleep(1000);
                } catch (Exception e) {
                }
            }

        }
    }
}

class T2 extends Thread {
    AtomicInteger _i;

    T2(AtomicInteger i) {
        _i = i;
    }

    @Override
    public void run() {
        while (true) {
            System.out.printf("Thread %d: after increment %d\n", getId(), _i.addAndGet(1));
            try {
                sleep(500);
            } catch (Exception e) {
            }
        }
    }
}

class T3 extends Thread {
    AtomicInteger _i;

    T3(AtomicInteger i) {
        _i = i;
    }

    @Override
    public void run() {
        while (true) {
            System.out.printf("Thread %d: before increment %d\n", getId(), _i.getAndAdd(1));
            try {
                sleep(500);
            } catch (Exception e) {
            }
        }
    }
}

public class ex3 {
    public static void main(String[] args) {
        AtomicInteger AInteger = new AtomicInteger(0);
        T1 t1 = new T1(AInteger);
        T2 t2 = new T2(AInteger);
        T3 t3 = new T3(AInteger);

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();

        } catch (Exception e) {
        }
    }
}
