

#ifndef IS_PRIME_H
#define IS_PRIME_H

#include <stdbool.h>

static inline bool is_prime(const long x) {
    if ((x <= 1) || (x & 1) == 0)
        return false;
    for (long i = 3; i < x; i++) {
        if ((x % i == 0) && (i != x))
            return false;
    }
    return true;
}

#endif //IS_PRIME_H
