#include <omp.h>
#include <stdio.h>
#include <stdbool.h>

// #include "is_prime.h"

#ifndef MAX_NUMBER
    #define MAX_NUMBER 200000
#endif

static inline bool is_prime(const long x) {
    if ((x <= 1) || (x & 1) == 0)
        return false;
    for (long i = 3; i < x; i++) {
        if ((x % i == 0) && (i != x))
            return false;
    }
    return true;
}

long pc_static(void)
{
    long result;
    long i;

    #pragma omp parallel default(none) shared(result) private(i)
    result = 0;
    #pragma omp parallel for schedule(static) default(none) shared(result) private(i)
    for (i = 1; i <= MAX_NUMBER; ++i) {
        #pragma omp atomic
        result += is_prime(i);
    }
    return result;
}

long pc_static_chunk(void)
{
    long result;
    long i;

    #pragma omp parallel default(none) shared(result) private(i)
    result = 0;
    #pragma omp parallel for schedule(static, 10) default(none) shared(result) private(i)
    for (i = 1; i <= MAX_NUMBER; ++i) {
        #pragma omp atomic
        result += is_prime(i);
    }
    return result;
}

long pc_dynamic(void)
{
    long result;
    long i;

    #pragma omp parallel default(none) shared(result) private(i)
    result = 0;
    #pragma omp parallel for schedule(dynamic) default(none) shared(result) private(i)
    for (i = 1; i <= MAX_NUMBER; ++i) {
        #pragma omp atomic
        result += is_prime(i);
    }
    return result;
}

long pc_dynamic_chunk(void)
{
    long result;
    long i;

    #pragma omp parallel default(none) shared(result) private(i)
    result = 0;
    #pragma omp parallel for schedule(dynamic) default(none) shared(result) private(i)
    for (i = 1; i <= MAX_NUMBER; ++i) {
        #pragma omp atomic
        result += is_prime(i);
    }
    return result;
}