import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Consumer extends Thread {
    ReadWriteLock _lock;
    List<Integer> _list;

    Consumer(ReadWriteLock lock, List<Integer> l) {
        _lock = lock;
        _list = l;
    }

    @Override
    public void run() {
        int n = 0;
        while (true) {
            _lock.readLock().lock();
            if (_list.size() == 0) {
                _lock.readLock().unlock();
                continue;
            }
            n = _list.get(0);
            _lock.readLock().unlock();

            System.out.printf("Thread number %d: first number == %d\n", getId(), n);
            try {
                sleep(500);
            } catch (Exception e) {
            }
        }
    }
}

class Sender extends Thread {
    ReadWriteLock _lock;
    List<Integer> _list;
    Random rand = new Random();

    Sender(ReadWriteLock lock, List<Integer> l) {
        _lock = lock;
        _list = l;
    }

    @Override
    public void run() {
        int n;
        while (true) {
            n = rand.nextInt();
            _lock.writeLock().lock();
            System.out.printf("Thread number %d: adding %d to list\n", this.getId(), n);
            _list.add(0, n);
            _lock.writeLock().unlock();
            try {
                sleep(500);
            } catch (Exception e) {
            }
        }
    }
}

public class ex2 {
    static private final int N_CONSUMER = 10;
    static private final int N_SENDER = 10;

    public static void main(String[] args) {
        List<Integer> l = new ArrayList<Integer>();
        ReadWriteLock lock = new ReentrantReadWriteLock();
        Consumer[] consumer_threads = new Consumer[N_CONSUMER];
        Sender[] sender_threads = new Sender[N_SENDER];

        for (int i = 0; i < N_CONSUMER; ++i) {
            consumer_threads[i] = new Consumer(lock, l);
            consumer_threads[i].start();
        }
        for (int i = 0; i < N_SENDER; ++i) {
            sender_threads[i] = new Sender(lock, l);
            sender_threads[i].start();
        }
        for (int i = 0; i < N_CONSUMER; ++i) {
            try {
                consumer_threads[i].join();
            } catch (Exception e) {
            }
        }
        for (int i = 0; i < N_SENDER; ++i) {
            try {
                sender_threads[i].join();
            } catch (Exception e) {
            }
        }
    }
}
