#include <stdio.h>
#include <chrono>
#include <thrust/host_vector.h>
#include <thrust/device_vector.h>

#include <thrust/copy.h>
#include <thrust/fill.h>

const long num_steps = 1000000000;

struct pi_fct
{
    const double step;
    pi_fct(double _step) : step(_step) {}

    __host__ __device__ double operator()(const long i) const
    {
        double x = (i + 0.5) * step;
        return 4.0 / (1.0 + x * x);
    }
};

int main(void)
{
    double pi = 0.0;
    double sum = 0.0;
    double step = 1.0 / (double)num_steps;
    double timeDiff;

    thrust::device_vector<int> numbers(num_steps);
    thrust::sequence(numbers.begin(), numbers.end());
    auto start = std::chrono::steady_clock::now();
    sum = thrust::transform_reduce(numbers.begin(), numbers.end(), pi_fct(step), 0, thrust::plus<float>());
    pi = step * sum;
    auto end = std::chrono::steady_clock::now();
    timeDiff = std::chrono::duration<double>(end - start).count();
    printf("Execution Time : %.10lfsec\n", timeDiff);
    printf("pi=%.10lf\n", pi);
    return 0;
}