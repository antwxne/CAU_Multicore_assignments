# Problem1 repport

## Environnement

| Os     | Pop!_OS 22.04 LTS x86         |
|--------|-------------------------------|
| CPU    | Intel i7-8665U (8) @ 1.900GHz |
| Memory | 16Gb                          |
|Java version | openjdk 17.0.6 2023-01-17|

## Build

In the `problem1` directory

```sh
javac pc_static_bloc.java
javac pc_static_cyclic.java
javac pc_dynamic.java
```

## How to use

### pc_static_block

To use with the default values
> NUM_END = 200000
> NUM_THREAD = 10

```sh
java pc_static_block
```

To use with custom arguments

```sh
java pc_static_block < NUM_THREAD > < NUM_END>
```

### pc_static_cyclic

To use with the default values
> NUM_END = 200000
> NUM_THREAD = 10
> TASK_SIZE = 10

```sh
java pc_static_cyclic
```

To use with custom arguments

```sh
java pc_static_cyclic < NUM_THREAD > < NUM_END > < TASK_SIZE >
```

### pc_dynamic

To use with the default values
> NUM_END = 200000
> NUM_THREAD = 10
> TASK_SIZE = 10

```sh
java pc_dynamic
```

To use with custom arguments

```sh
java pc_dynamic < NUM_THREAD > < NUM_END > < TASK_SIZE >
```

## Results

### Raw

| Number of threads | time in ms (static block) | time in ms (static cyclic) | time in ms (dynamic) |
|-------------------|---------------------------|----------------------------|----------------------|
| 1                 | 61.000                    | 58.000                     | 7327.000             |
| 2                 | 69.000                    | 58.000                     | 3634.000             |
| 4                 | 72.000                    | 59.000                     | 2056.000             |
| 6                 | 60.000                    | 59.000                     | 1796.000             |
| 8                 | 58.000                    | 74.000                     | 1773.000             |
| 10                | 60.000                    | 59.000                     | 1665.000             |
| 12                | 64.000                    | 60.000                     | 1592.000             |
| 14                | 58.000                    | 58.000                     | 1765.000             |
| 16                | 62.000                    | 62.000                     | 1586.000             |
| 32                | 59.000                    | 57.000                     | 1573.000             |

### Graphs
!["graph for static block"](.report_src/graph_static_block.png)
!["graph for static cyclic"](.report_src/graph_pc_static_cyclic.png)
!["graph for dynamic"](.report_src/graph_pc_dynamic.png)

## Interpretation

## Source code

### pc_static_block

```java
class pcThreadStaticBlock extends Thread {
    int begin;
    int end;
    int result = 0;

    pcThreadStaticBlock(int b, int e) {
        begin = b;
        end = e;
    }

    public void run() {
        long startTime = System.currentTimeMillis();
        for (int i = begin; i < end; ++i) {
            result += isPrime(i);
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

class pc_static_block {
    private static int NUM_END = 200000;
    private static int N_THREAD = 10;

    public static void main(String[] args) {
        int result = 0;
        N_THREAD = args.length >= 1 ? Integer.parseInt(args[0]) : N_THREAD;
        NUM_END = args.length >= 2 ? Integer.parseInt(args[1]) : NUM_END;
        pcThreadStaticBlock[] threads = new pcThreadStaticBlock[N_THREAD];
        int range = NUM_END / N_THREAD;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < threads.length; ++i) {
            threads[i] = new pcThreadStaticBlock(i * range, i * range + range);
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
```

### pc_static_cyclic

```java
class pcThreadStaticCyclic extends Thread {
    int begin;
    int end;
    int task_size;
    int n_thread;
    int result = 0;

    pcThreadStaticCyclic(int b, int e, int o, int n) {
        begin = b;
        end = e;
        task_size = o;
        n_thread = n;
    }

    public void run() {
        long startTime = System.currentTimeMillis();

        for (; begin < end; begin += task_size * n_thread) {
            for (int i = begin; i < begin + task_size; ++i) {
                result += isPrime(i);
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

class pc_static_cyclic {
    private static int NUM_END = 200000;
    private static int N_THREAD = 10;
    private static int TASK_SIZE = 10;

    public static void main(String[] args) {
        int result = 0;
        N_THREAD = args.length >= 1 ? Integer.parseInt(args[0]) : N_THREAD;
        NUM_END = args.length >= 2 ? Integer.parseInt(args[1]) : NUM_END;
        TASK_SIZE = args.length >= 3 ? Integer.parseInt(args[2]) : TASK_SIZE;

        pcThreadStaticCyclic[] threads = new pcThreadStaticCyclic[N_THREAD];
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < threads.length; ++i) {
            threads[i] = new pcThreadStaticCyclic(i * TASK_SIZE, NUM_END, TASK_SIZE, N_THREAD);
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
```

### pc_dynamic

```java
import java.util.PriorityQueue;

class pcThreadDynamic extends Thread {
    int end;
    int task_size;
    int result = 0;
    PriorityQueue<Integer> numbersRef;

    pcThreadDynamic(PriorityQueue<Integer> nRef, int t_size, int e) {
        end = e;
        task_size = t_size;
        numbersRef = nRef;
    }

    public void run() {
        long startTime = System.currentTimeMillis();

        while (true) {
            int begin = getNextNumber(numbersRef);
            if (begin == -1) {
                break;
            }
            for (int i = begin; i < begin + task_size; ++i) {
                result += isPrime(i);
            }

        }
        long endTime = System.currentTimeMillis();
        long timeDiff = endTime - startTime;
        System.out.printf("Execution Time of thread %d : %d ms\n", Thread.currentThread().getId(), timeDiff);
    }

    synchronized private static int getNextNumber(PriorityQueue<Integer> numbersQueue) {

        return numbersQueue.isEmpty() ? -1 : numbersQueue.poll();
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
        N_THREAD = args.length >= 1 ? Integer.parseInt(args[0]) : N_THREAD;
        NUM_END = args.length >= 2 ? Integer.parseInt(args[1]) : NUM_END;
        TASK_SIZE = args.length >= 3 ? Integer.parseInt(args[2]) : TASK_SIZE;

        PriorityQueue<Integer> number_list = new PriorityQueue<Integer>();

        for (int i = 0; i < NUM_END; i += TASK_SIZE) {
            number_list.add(i);
        }

        pcThreadDynamic[] threads = new pcThreadDynamic[N_THREAD];
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < threads.length; ++i) {
            threads[i] = new pcThreadDynamic(number_list, TASK_SIZE, NUM_END);
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
```