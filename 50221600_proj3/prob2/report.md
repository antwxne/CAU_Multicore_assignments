# Proj3 prob2 report

__Antoine DESRUET 50221600__

## Environment

| Os               | Pop!_OS 22.04 LTS x86         |
|------------------|-------------------------------|
| CPU              | Intel i7-8665U (8) @ 1.900GHz |
| Memory           | 16Gb                          |
| GCC version      | 14.0.0                        |
| GNU Make version | 4.3                           | 

## Build

In the prob2 folder:

### GNU Make

```shell
make
```

### CLI

```shell
gcc -o a.out ./prob2.c -fopenmp
```

## Static

### Tables

#### Execution time

|Number of thread|Execution time 1 chunks|Execution time 5 chunks|Execution time 10 chunks|Execution time 100 chunks|
|----------------|-----------------------|-----------------------|------------------------|-------------------------|
|1               |27                     |27                     |26                      |26                       |
|2               |73                     |24                     |19                      |15                       |
|4               |45                     |15                     |12                      |9                        |
|6               |48                     |19                     |15                      |12                       |
|8               |41                     |16                     |12                      |10                       |
|10              |47                     |19                     |15                      |14                       |
|12              |49                     |19                     |15                      |12                       |
|14              |43                     |17                     |14                      |11                       |
|16              |43                     |16                     |13                      |11                       |

#### Performance

|Number of thread|Performance 1 chunks|Performance 5 chunks|Performance 10 chunks|Performance 100 chunks|
|----------------|--------------------|--------------------|---------------------|----------------------|
|1               |0.037037037037037035|0.037037037037037035|0.038461538461538464 |0.038461538461538464  |
|2               |0.0136986301369863  |0.041666666666666664|0.05263157894736842  |0.06666666666666667   |
|4               |0.022222222222222223|0.06666666666666667 |0.08333333333333333  |0.1111111111111111    |
|6               |0.020833333333333332|0.05263157894736842 |0.06666666666666667  |0.08333333333333333   |
|8               |0.024390243902439025|0.0625              |0.08333333333333333  |0.1                   |
|10              |0.02127659574468085 |0.05263157894736842 |0.06666666666666667  |0.07142857142857142   |
|12              |0.02040816326530612 |0.05263157894736842 |0.06666666666666667  |0.08333333333333333   |
|14              |0.023255813953488372|0.058823529411764705|0.07142857142857142  |0.09090909090909091   |
|16              |0.023255813953488372|0.0625              |0.07692307692307693  |0.09090909090909091   |

### Graphs

#### Execution time

![static_exec_time.png](benchmark%2Fresults%2Fstatic_exec_time.png)

#### Performance

![static_performance.png](benchmark%2Fresults%2Fstatic_performance.png)

## Dynamic

### Tables

#### Execution time

|Number of thread|Execution time 1 chunks|Execution time 5 chunks|Execution time 10 chunks|Execution time 100 chunks|
|----------------|-----------------------|-----------------------|------------------------|-------------------------|
|1               |194                    |57                     |41                      |29                       |
|2               |207                    |43                     |27                      |16                       |
|4               |195                    |44                     |24                      |10                       |
|6               |182                    |40                     |21                      |10                       |
|8               |163                    |38                     |19                      |10                       |
|10              |180                    |34                     |19                      |10                       |
|12              |162                    |33                     |19                      |10                       |
|14              |144                    |33                     |20                      |10                       |
|16              |180                    |36                     |19                      |10                       |

#### Performance

|Number of thread|Performance 1 chunks|Performance 5 chunks|Performance 10 chunks|Performance 100 chunks|
|----------------|--------------------|--------------------|---------------------|----------------------|
|1               |0.005154639175257732|0.017543859649122806|0.024390243902439025 |0.034482758620689655  |
|2               |0.004830917874396135|0.023255813953488372|0.037037037037037035 |0.0625                |
|4               |0.005128205128205128|0.022727272727272728|0.041666666666666664 |0.1                   |
|6               |0.005494505494505495|0.025               |0.047619047619047616 |0.1                   |
|8               |0.006134969325153374|0.02631578947368421 |0.05263157894736842  |0.1                   |
|10              |0.005555555555555556|0.029411764705882353|0.05263157894736842  |0.1                   |
|12              |0.006172839506172839|0.030303030303030304|0.05263157894736842  |0.1                   |
|14              |0.006944444444444444|0.030303030303030304|0.05                 |0.1                   |
|16              |0.005555555555555556|0.027777777777777776|0.05263157894736842  |0.1                   |

### Graphs

#### Execution time

![dynamic_exec_time.png](benchmark%2Fresults%2Fdynamic_exec_time.png)

#### Performance

![dynamic_performance.png](benchmark%2Fresults%2Fdynamic_performance.png)

## Guided

### Tables

#### Execution time

|Number of thread|Execution time 1 chunks|Execution time 5 chunks|Execution time 10 chunks|Execution time 100 chunks|
|----------------|-----------------------|-----------------------|------------------------|-------------------------|
|1               |32                     |31                     |27                      |26                       |
|2               |15                     |15                     |15                      |15                       |
|4               |10                     |9                      |15                      |9                        |
|6               |11                     |11                     |11                      |12                       |
|8               |11                     |9                      |9                       |9                        |
|10              |9                      |9                      |10                      |9                        |
|12              |11                     |11                     |11                      |12                       |
|14              |12                     |11                     |10                      |9                        |
|16              |11                     |11                     |10                      |10                       |

#### Performance

|Number of thread|Performance 1 chunks|Performance 5 chunks|Performance 10 chunks|Performance 100 chunks|
|----------------|--------------------|--------------------|---------------------|----------------------|
|1               |0.03125             |0.03225806451612903 |0.037037037037037035 |0.038461538461538464  |
|2               |0.06666666666666667 |0.06666666666666667 |0.06666666666666667  |0.06666666666666667   |
|4               |0.1                 |0.1111111111111111  |0.06666666666666667  |0.1111111111111111    |
|6               |0.09090909090909091 |0.09090909090909091 |0.09090909090909091  |0.08333333333333333   |
|8               |0.09090909090909091 |0.1111111111111111  |0.1111111111111111   |0.1111111111111111    |
|10              |0.1111111111111111  |0.1111111111111111  |0.1                  |0.1111111111111111    |
|12              |0.09090909090909091 |0.09090909090909091 |0.09090909090909091  |0.08333333333333333   |
|14              |0.08333333333333333 |0.09090909090909091 |0.1                  |0.1111111111111111    |
|16              |0.09090909090909091 |0.09090909090909091 |0.1                  |0.1                   |

### Graphs

#### Execution time

![guided_exec_time.png](benchmark%2Fresults%2Fguided_exec_time.png)

#### Performance

![guided_performance.png](benchmark%2Fresults%2Fguided_performance.png)

## Explanation

We can see that the more important the chunk size is, the more high the performance is.

This work for every scheduling types.

This is because each task take the same time so threads can do a lot of tasks in one time. We don't need to split thoses tasks.