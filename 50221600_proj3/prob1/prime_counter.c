#include <omp.h>
#include <stdbool.h>

#ifndef MAX_NUMBER
    #define MAX_NUMBER 200000
#endif

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

unsigned long pc_static(void)
{
    unsigned long result;
    unsigned long i;

    #pragma omp parallel default(none) shared(result) private(i)
    result = 0;
    #pragma omp parallel for schedule(static) default(none) shared(result) private(i)
    for (i = 1; i <= MAX_NUMBER; ++i) {
        #pragma omp atomic
        result += is_prime(i);
    }
    return result;
}

unsigned long pc_static_chunk(void)
{
    unsigned long result;
    unsigned long i;

    #pragma omp parallel default(none) shared(result) private(i)
    result = 0;
    #pragma omp parallel for schedule(static, 10) default(none) shared(result) private(i)
    for (i = 1; i <= MAX_NUMBER; ++i) {
        #pragma omp atomic
        result += is_prime(i);
    }
    return result;
}

unsigned long pc_dynamic(void)
{
    unsigned long result;
    unsigned long i;

    #pragma omp parallel default(none) shared(result) private(i)
    result = 0;
    #pragma omp parallel for schedule(dynamic) default(none) shared(result) private(i)
    for (i = 1; i <= MAX_NUMBER; ++i) {
        #pragma omp atomic
        result += is_prime(i);
    }
    return result;
}

unsigned long pc_dynamic_chunk(void)
{
    unsigned long result;
    unsigned long i;

    #pragma omp parallel default(none) shared(result) private(i)
    result = 0;
    #pragma omp parallel for schedule(dynamic, 10) default(none) shared(result) private(i)
    for (i = 1; i <= MAX_NUMBER; ++i) {
        #pragma omp atomic
        result += is_prime(i);
    }
    return result;
}