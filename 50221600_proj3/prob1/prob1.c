#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <stdbool.h>
#include <omp.h>
#include <string.h>

#ifndef MAX_NUMBER
    #define MAX_NUMBER 200000
#endif

static unsigned long pc_static(void);
static unsigned long pc_static_chunk(void);
static unsigned long pc_dynamic(void);
static unsigned long pc_dynamic_chunk(void);
static unsigned long (*pc_fct_array[])(void) = {NULL, pc_static,
    pc_static_chunk, pc_dynamic, pc_dynamic_chunk};

static inline bool is_prime(const unsigned long x)
{
    if ((x <= 1) || (x & 1) == 0)
        return false;
    for (unsigned long i = 3; i < x; i++) {
        if ((x % i == 0) && (i != x))
            return false;
    }
    return true;
}

static unsigned long pc_static(void)
{
    unsigned long result;
    unsigned long i;

    #pragma omp parallel default(none) shared(result) private(i)
    result = 0;
    #pragma omp parallel for schedule(static) default(none) reduction(+: result) private(i)
    for (i = 1; i <= MAX_NUMBER; ++i) {
        result += is_prime(i);
    }
    return result;
}

static unsigned long pc_static_chunk(void)
{
    unsigned long result;
    unsigned long i;

    #pragma omp parallel default(none) shared(result) private(i)
    result = 0;
    #pragma omp parallel for schedule(static, 10) default(none) reduction(+: result) private(i)
    for (i = 1; i <= MAX_NUMBER; ++i) {
        result += is_prime(i);
    }
    return result;
}

static unsigned long pc_dynamic(void)
{
    unsigned long result;
    unsigned long i;

    #pragma omp parallel default(none) shared(result) private(i)
    result = 0;
    #pragma omp parallel for schedule(dynamic) default(none) reduction(+: result) private(i)
    for (i = 1; i <= MAX_NUMBER; ++i) {
        result += is_prime(i);
    }
    return result;
}

static unsigned long pc_dynamic_chunk(void)
{
    unsigned long result;
    unsigned long i;

    #pragma omp parallel default(none) shared(result) private(i)
    result = 0;
    #pragma omp parallel for schedule(dynamic, 10) default(none) reduction(+: result) private(i)
    for (i = 1; i <= MAX_NUMBER; ++i) {
        result += is_prime(i);
    }
    return result;
}

static bool get_scheduling_type(const char *arg, int *scheduling_type_ptr)
{
    char *end_ptr;

    errno = 0;
    *scheduling_type_ptr = (int)strtol(arg, &end_ptr, 10);
    if (errno != 0 || arg == end_ptr || &arg[strlen(arg)] != end_ptr ||
        *scheduling_type_ptr > 4 || *scheduling_type_ptr < 1) {
        fputs(
            "Please provide a correct integer between 1 and 4 for the scheduling_type",
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
    int *thread_number
)
{
    if (ac != 3) {
        fputs("Please provide 2 args\n", stderr);
        return false;
    }
    return get_scheduling_type(av[1], scheduling_type) &&
        get_thread_number(av[2], thread_number);
}

int main(int ac, char **av)
{
    int scheduling_type;
    int thread_number;
    unsigned long result = 0;
    double start_time = 0;
    double end_time = 0;

    if (!error_handling(ac, (const char **)av, &scheduling_type,
        &thread_number)) {
        return 1;
    }
    #ifndef _OPENMP
    fputs("OPEN MP undefined", stderr);
    return 1;
    #endif
    omp_set_num_threads(thread_number);
    start_time = omp_get_wtime();
    result = pc_fct_array[scheduling_type]();
    end_time = omp_get_wtime();
    printf("Execution Time : %.0lfms\n", (end_time - start_time) * 1000);
    printf("Number of primes : %ld\n", result);
    return 0;
}