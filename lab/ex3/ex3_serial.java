public class ex3_serial {
    private static final int NUM_STEP = 100000;

    public static void main(String[] args) {
        double pi = 0;
        double step = 1.0 / NUM_STEP;
        double sum = 0;
        double x = 0;
        for (int i = 0; i < NUM_STEP; ++i) {
            x = (i + 0.5) * step;
            sum += 4 / (1 + x * x);
        }
        pi = step * sum;
        System.out.printf("Pi = %f\n", pi);
    }

}
