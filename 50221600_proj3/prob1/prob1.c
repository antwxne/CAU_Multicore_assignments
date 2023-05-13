#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <stdbool.h>
#include <omp.h>

long pc_static(void);
long pc_static_chunk(void);
long pc_dynamic(void);
long pc_dynamic_chunk(void);
static long (*pc_fct_array[])(void) = {NULL, pc_static, pc_static_chunk,
    pc_dynamic, pc_dynamic_chunk};

static bool get_scheduling_type(const char *arg, int *scheduling_type_ptr)
{
    char *end_ptr;

    errno = 0;
    *scheduling_type_ptr = (int)strtol(arg, &end_ptr, 10);
    if (errno != 0 || arg == end_ptr || *scheduling_type_ptr > 4 ||
        *scheduling_type_ptr < 1) {
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
    if (errno != 0 || arg == end_ptr || *thread_number_ptr < 1) {
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
    long result;

    if (!error_handling(ac, (const char **)av, &scheduling_type,
        &thread_number)) {
        return 1;
    }
    #ifdef _OPENMP
    omp_set_num_threads(thread_number);
    #endif
    result = pc_fct_array[scheduling_type]();
    printf("Result == %ld\n", result);
    return 0;
}