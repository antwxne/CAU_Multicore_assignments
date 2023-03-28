class PiThread extends Thread {
    int begin;
    int end;
    int number_of_step;
    double ans = 0;

    PiThread(int b, int e, int s) {
        begin = b;
        end = e;
        number_of_step = s;
    }

    public void run() {
        double step = 1.0 / number_of_step;
        double x = 0;
        double sum = 0;
        for (int i = begin; i < end; ++i) {
            x = (i - 0.5) * step;
            sum += 4.0 / (1 + x * x);
        }
        ans = step * sum;
    }
}

public class ex3 {
    public static void main(String[] args) {
        int number_of_step = 100000;
        int number_of_thread = 10;
        double pi = 0.0;

        PiThread[] t = new PiThread[number_of_thread];
        for (int i = 0; i < t.length; ++i) {
            t[i] = new PiThread(i * (number_of_step / number_of_thread),
                    i * (number_of_step / number_of_thread) + (number_of_step / number_of_thread),
                    number_of_step);
            t[i].start();
        }
        for (int i = 0; i < t.length; ++i) {
            try {
                t[i].join();
                pi += t[i].ans;
                // may cause wrong result because we loss data when do add op -> 3.1416... != 3.1415...
            } catch (InterruptedException e) {
            }
        }
        System.out.printf("Pi = %f\n", pi);
    }
}
