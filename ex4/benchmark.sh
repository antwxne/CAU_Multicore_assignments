#!/usr/bin/env bash

bin_name=ex4
output_file=benchmark.csv
n_threads=($(seq 1 1 30))
max_number=200000

javac $bin_name.java

echo "Number of threads,time in s" >> $output_file

export TIMEFORMAT=%R

function benchmark() {
  local result
  echo "Test for $1/${#n_threads[@]} threads"
  result=$(echo "$({ time (java $bin_name $1 $max_number  &> /dev/null) } 2>&1)" | tr "," ".")
  echo $1","$result >> $output_file
}

for t in "${n_threads[@]}";
do
   benchmark $t
done
