import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;

class Cooker extends Thread {
    BlockingQueue<String> _orders;

    Cooker(BlockingQueue<String> o) {
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
                System.out.println(e.getMessage());
                break;
            }
        }
    }
}

class Waiter extends Thread {
    BlockingQueue<String> _orders;
    Random rand = new Random(); 


    Waiter(BlockingQueue<String> o) {
        _orders = o;
    }

    @Override
    public void run() {
        String currentPizza;
        String[] pizzas = {"Peperoni", "Sweat potato", "Bulgogi", "Chicken"};
        while (true) {
            try {
                currentPizza = pizzas[rand.nextInt(pizzas.length)];
                _orders.add(currentPizza);
                System.out.printf("Waiter: Adding %s pizza to queue\n", currentPizza);
                sleep(500);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }
        }
    }

}


public class ex1 {
    public static void main(String[] args) {
        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(100);
        Cooker c = new Cooker(queue);
        Waiter w = new Waiter(queue);

        c.start();
        w.start();
        try {
            c.join();
            w.join();
        } catch (Exception e) {}
    }
}
