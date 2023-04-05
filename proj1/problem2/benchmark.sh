#!/usr/bin/env bash

output_file=benchmark.csv
input_file=mat500.txt
n_threads=(1 2 4 6 8 10 12 14 16 32)

javac MatmultD.java

echo "Number of threads,Execution time in ms" > $output_file

export TIMEFORMAT=%R

function benchmark() {
  local result
  echo "Test for $1 threads"

  result=$(echo "$({ time (java MatmultD $1 < $input_file &> /dev/null) } 2>&1)" | tr "," ".")
  result=$(echo "$result * 1000" | bc)
  
  echo $1","$result >> $output_file
}

for t in "${n_threads[@]}";
do
   benchmark $t
done
