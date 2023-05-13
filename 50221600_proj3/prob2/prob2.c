#include <omp.h>
#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <stdbool.h>
#include <string.h>

#ifndef NUM_STEP
    #define NUM_STEP 10000000
#endif

static double compute_pi(const int scheduling_type, const int chunk_size)
{
    long i;
    double x;
    double sum;
    double step;

    omp_set_schedule(scheduling_type, chunk_size);
    #pragma omp parallel default(none) shared(sum, step) private(i, x)
    step = 1.0 / NUM_STEP;
    x = 0;
    sum = 0;
    #pragma omp parallel for schedule(runtime) default(none) reduction(+: sum) shared(step) private(i, x)
    for (i = 0; i < NUM_STEP; ++i) {
        x = (i + 0.5) * step;
        sum += 4.0 / (1.0 + x * x);
    }
    return sum * step;
}

static bool get_chunk_size(const char *arg, int *chunk_size_ptr)
{
    char *end_ptr;

    errno = 0;
    *chunk_size_ptr = (int)strtol(arg, &end_ptr, 10);
    if (errno != 0 || arg == end_ptr || &arg[strlen(arg)] != end_ptr ||
        *chunk_size_ptr < 1) {
        fputs("Please provide a correct positive integer for the chunk size",
            stderr);
        return false;
    }
    return true;
}

static bool get_scheduling_type(const char *arg, int *scheduling_type_ptr)
{
    char *end_ptr;

    errno = 0;
    *scheduling_type_ptr = (int)strtol(arg, &end_ptr, 10);
    if (errno != 0 || arg == end_ptr || &arg[strlen(arg)] != end_ptr ||
        *scheduling_type_ptr > 3 || *scheduling_type_ptr < 1) {
        fputs(
            "Please provide a correct integer between 1 and 3 for the scheduling_type",
            stderr);
        return false;
    }
    return true;
}

static bool get_thread_number(const char *arg, int *thread_number_ptr)
{
    char *end_ptr;

    errno = 0;
    *thread_number_ptr = (int)strtol(arg, &end_ptr, 10);
    if (errno != 0 || arg == end_ptr || &arg[strlen(arg)] != end_ptr ||
        *thread_number_ptr < 1) {
        fputs(
            "Please provide a correct positive integer for the number of threads",
            stderr);
        return false;
    }
    return true;
}

static bool error_handling(const int ac, const char **av, int *scheduling_type,
    int *chunk_size, int *thread_number
)
{
    if (ac != 4) {
        fputs("Please provide 3 args\n", stderr);
        return false;
    }
    return get_scheduling_type(av[1], scheduling_type) &&
        get_chunk_size(av[2], chunk_size) &&
        get_thread_number(av[3], thread_number);
}

int main(int ac, char **av)
{
    int scheduling_type;
    int chunk_size;
    int number_of_thread;
    double pi = 0;
    double start_time = 0;
    double end_time = 0;

    if (!error_handling(ac, (const char **)av, &scheduling_type, &chunk_size,
        &number_of_thread)) {
        return 1;
    }
    #ifndef _OPENMP
    fputs("OPEN MP undefined", stderr);
    return 1;
    #endif
    omp_set_num_threads(number_of_thread);
    start_time = omp_get_wtime();
    pi = compute_pi(scheduling_type, chunk_size);
    end_time = omp_get_wtime();
    printf("Execution Time : %.0lfms\n", (end_time - start_time) * 1000);
    printf("pi=%.24lf\n", pi);
    return 0;
}
