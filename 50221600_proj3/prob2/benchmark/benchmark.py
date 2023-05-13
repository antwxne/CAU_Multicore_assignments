import os
import subprocess
import time
import pandas as pd
import matplotlib.pyplot as plt
from tqdm import tqdm

BIN_NAME: str = "./a.out"
THREADS_NUMBER: list[int] = [1, 2, 4, 6, 8, 10, 12, 14, 16]
CHUNK_SIZE: list[int] = [1, 5, 10, 100]
OUTPUT_FOLDER: str = "./benchmark/results"
HEADERS_EXEC_TIME: list[str] = ["Number of thread",
                                *map(lambda x: f"Execution time {x} chunks",
                                     CHUNK_SIZE),
                                *map(lambda x: f"Performance {x} chunks",
                                     CHUNK_SIZE)]
OUTPUT_NAMES: dict[int, str] = {
    1: "static",
    2: "dynamic",
    3: "guided",
}


def benchmark(scheduling_type: int) -> list[list]:
    dest: list[list] = []
    for t in tqdm(THREADS_NUMBER):
        chunk_results_exec: list = []
        chunk_results_performance: list = []
        for chunk in CHUNK_SIZE:
            timeStarted: float = time.time()
            subprocess.run(
                ["./a.out", f'{scheduling_type}', f'{chunk}', f"{t}"],
                stdout=subprocess.DEVNULL)
            timeDelta: float = time.time() - timeStarted
            time_ms: float = round(timeDelta * 1000)
            performance: float = 1 / time_ms
            chunk_results_exec.append(time_ms)
            chunk_results_performance.append(performance)
        dest.append([t, *chunk_results_exec, *chunk_results_performance])
    return dest


def plot(data: pd.DataFrame, scheduling_type: int) -> None:
    plt.plot(data[HEADERS_EXEC_TIME[0]], data[HEADERS_EXEC_TIME[1]], color='r',
             label="1 chunk", marker="x")
    plt.plot(data[HEADERS_EXEC_TIME[0]], data[HEADERS_EXEC_TIME[2]], color='g',
             label="5 chunks", marker="x")
    plt.plot(data[HEADERS_EXEC_TIME[0]], data[HEADERS_EXEC_TIME[3]], color='b',
             label="10 chunks", marker="x")
    plt.plot(data[HEADERS_EXEC_TIME[0]], data[HEADERS_EXEC_TIME[4]], color='k',
             label="100 chunks", marker="x")
    plt.title("Execution Time in function of the number of thread")
    plt.xlabel("# Thread")
    plt.ylabel("ms")
    plt.legend()
    plt.savefig(
        f'{OUTPUT_FOLDER}/{OUTPUT_NAMES[scheduling_type]}_exec_time.png')
    plt.close()
    plt.plot(data[HEADERS_EXEC_TIME[0]], data[HEADERS_EXEC_TIME[5]], color='r',
             label="1 chunk", marker="x")
    plt.plot(data[HEADERS_EXEC_TIME[0]], data[HEADERS_EXEC_TIME[6]], color='g',
             label="5 chunks", marker="x")
    plt.plot(data[HEADERS_EXEC_TIME[0]], data[HEADERS_EXEC_TIME[7]], color='b',
             label="10 chunks", marker="x")
    plt.plot(data[HEADERS_EXEC_TIME[0]], data[HEADERS_EXEC_TIME[8]], color='k',
             label="100 chunks", marker="x")
    plt.title("Performance in function of the number of thread")
    plt.xlabel("# Thread")
    plt.ylabel("1/exec time")
    plt.legend()
    plt.savefig(
        f'{OUTPUT_FOLDER}/{OUTPUT_NAMES[scheduling_type]}_performance.png')
    plt.close()


#
if __name__ == "__main__":
    if not os.path.exists(OUTPUT_FOLDER):
        os.makedirs(OUTPUT_FOLDER)
    for scheduling_type in OUTPUT_NAMES.keys():
        results = benchmark(scheduling_type)
        data = pd.DataFrame(results, columns=HEADERS_EXEC_TIME)
        data.to_csv(f'{OUTPUT_FOLDER}/{OUTPUT_NAMES[scheduling_type]}.csv',
                    index=False)
        # results = pd.read_csv(f'{OUTPUT_FOLDER}/{OUTPUT_NAMES[1]}.csv', header=None, skiprows=[0])
        plot(data, scheduling_type)
