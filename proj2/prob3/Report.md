# Problem 3 report

## 1 A

### BlockingQueue

`BlockingQueue` is an interface for a queue that is thread safe. That means that the implementation of this queue allow one thread at a time to acces the queue data.

### ArrayBlockingQueue

`ArrayBlockingQueue` is one implementation of the `BlockingQueue` interface that use an array. That means that the data structure use to store the data is an array. The array has a predifined memory size, it means that it's not dynamic. It implements the `BlockingQueue` methods in order to use the array as a queue, also those methods are thread safe.

## 2 A

### ReadWriteLock

`ReadWriteLock` is a specific type of lock. It can allow multiple threads to acces the data to read at the same time. But it only allow one thread at a time to acces the data to modify it.

## 3 A

### AtomicInteger

`AtomicInteger` is an `atomic` variable. It allows to do atomic operations on `int` type variable. Atomic variables allows to do operations on variables on different thread at the same time without concurency problems.

## 4 A

### CyclicBarrier

`CyclicBarrier` is used to syncronize threads. It kind of works like a break point in the source code. Once a thread reach this point it will wait until the others reach this point too. 