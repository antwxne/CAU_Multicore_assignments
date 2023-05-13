#!/usr/bin/env bash

make re

scheduling_types=(1 2 3 4)
n_threads=(1 2 4 6 8 10 12 14 16)

for s in "${scheduling_types[@]}"; do
  for t in "${n_threads[@]}"; do
    set -x
    ./a.out $s $t
    set +x
  done
done
