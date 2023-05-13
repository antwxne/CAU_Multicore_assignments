#!/usr/bin/env bash

make re -C prob1

scheduling_types=(1 2 3 4)
n_threads=(1 2 4 6 8 10 12 14 16)

for s in "${scheduling_types[@]}"; do
  for t in "${n_threads[@]}"; do
    set -x
    ./prob1/a.out $s $t
    set +x
  done
done

make re -C prob2

scheduling_types=(1 2 3)
n_threads=(1 2 4 6 8 10 12 14 16)
chunk_size=(1 5 10 100)

for s in "${scheduling_types[@]}"; do
  for c in "${chunk_size[@]}"; do
    for t in "${n_threads[@]}"; do
      set -x
      ./prob2/a.out $s $c $t
      set +x
    done
  done
done
