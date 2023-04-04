#!/usr/bin/env bash

output_file=benchmark.csv
n_threads=(1 2 4 6 8 10 12 14 16 32)
max_number=200000

javac pc_static_block.java
javac pc_static_cyclic.java
javac pc_dynamic.java

echo "Number of threads,time in ms (static block)," \
"time in ms (static cyclic)," \
"time in ms (dynamic)" > $output_file

export TIMEFORMAT=%R

function benchmark() {
  local result_static_bloc
  local result_static_clyclic
  local result_dynamic
  echo "Test for $1 threads"

  result_static_bloc=$(echo "$({ time (java pc_static_blovck $1 $max_number  &> /dev/null) } 2>&1)" | tr "," ".")
  result_static_clyclic=$(echo "$({ time (java pc_static_clyclic $1 $max_number  &> /dev/null) } 2>&1)" | tr "," ".")
  result_dynamic=$(echo "$({ time (java pc_dynamic $1 $max_number  &> /dev/null) } 2>&1)" | tr "," ".")
  result_static_bloc=$(echo "$result_static_bloc * 1000" | bc)
  result_static_clyclic=$(echo "$result_static_clyclic * 1000" | bc)
  result_dynamic=$(echo "$result_dynamic * 1000" | bc)
  
  
  echo $1","$result_static_bloc","$result_static_clyclic","$result_dynamic >> $output_file
}

for t in "${n_threads[@]}";
do
   benchmark $t
done
