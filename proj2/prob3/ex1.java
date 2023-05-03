import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;

interface WBQueue<E> {
    public void put(E element) throws InterruptedException;
}

interface RBQueue<E> {
    public E take() throws InterruptedException;
}

class BQueue<E> implements RBQueue<E>, WBQueue<E> {
    BlockingQueue<E> _queue;

    BQueue(int size) {
        _queue = new ArrayBlockingQueue<E>(size);
    }

    @Override
    public void put(E element) throws InterruptedException {
        _queue.put(element);
    }

    @Override
    public E take() throws InterruptedException {
        return _queue.take();
    }
}

class Cooker extends Thread {
    RBQueue<String> _orders;

    Cooker(RBQueue<String> o) {
        _orders = o;
    }

    @Override
    public void run() {
        String pizza;
        while (true) {
            try {
                pizza = _orders.take();
                System.out.printf("Cooker: Making %s pizza\n", pizza);
                sleep(1000);
            } catch (Exception e) {
            }
        }
    }
}

class Waiter extends Thread {
    WBQueue<String> _orders;
    Random rand = new Random();

    Waiter(WBQueue<String> o) {
        _orders = o;
    }

    @Override
    public void run() {
        String currentPizza;
        String[] pizzas = { "Peperoni", "Sweat potato", "Bulgogi", "Chicken" };
        while (true) {
            try {
                currentPizza = pizzas[rand.nextInt(pizzas.length)];
                _orders.put(currentPizza);
                System.out.printf("Waiter: Adding %s pizza to queue\n", currentPizza);
                sleep(500);
            } catch (Exception e) {
            }
        }
    }

}

public class ex1 {
    public static void main(String[] args) {
        BQueue<String> queue = new BQueue<String>(100);
        Cooker c = new Cooker(queue);
        Waiter w = new Waiter(queue);

        c.start();
        w.start();
        try {
            c.join();
            w.join();
        } catch (Exception e) {
        }
    }
}
