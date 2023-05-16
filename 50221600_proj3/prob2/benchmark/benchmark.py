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
                                     CHUNK_SIZE)]

HEADERS_PERFORMANCE: list[str] = ["Number of thread",
                                  *map(lambda x: f"Performance {x} chunks",
                                       CHUNK_SIZE)]

OUTPUT_NAMES: dict[int, str] = {
    1: "static",
    2: "dynamic",
    3: "guided",
}


def benchmark(scheduling_type: int) -> tuple[list[list], list[list]]:
    result_exec_time: list[list] = []
    result_performance: list[list] = []
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
        result_exec_time.append([t, *chunk_results_exec])
        result_performance.append([t, *chunk_results_performance])
    return result_exec_time, result_performance


def plot_exec_time(data: pd.DataFrame, scheduling_type: int) -> None:
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


def plop_performance(data: pd.DataFrame, scheduling_type: int) -> None:
    plt.plot(data[HEADERS_PERFORMANCE[0]], data[HEADERS_PERFORMANCE[1]],
             color='r',
             label="1 chunk", marker="x")
    plt.plot(data[HEADERS_PERFORMANCE[0]], data[HEADERS_PERFORMANCE[2]],
             color='g',
             label="5 chunks", marker="x")
    plt.plot(data[HEADERS_PERFORMANCE[0]], data[HEADERS_PERFORMANCE[3]],
             color='b',
             label="10 chunks", marker="x")
    plt.plot(data[HEADERS_PERFORMANCE[0]], data[HEADERS_PERFORMANCE[4]],
             color='k',
             label="100 chunks", marker="x")
    plt.title("Performance in function of the number of thread")
    plt.xlabel("# Thread")
    plt.ylabel("1/exec time")
    plt.legend()
    plt.savefig(
        f'{OUTPUT_FOLDER}/{OUTPUT_NAMES[scheduling_type]}_performance.png')
    plt.close()


if __name__ == "__main__":
    if not os.path.exists(OUTPUT_FOLDER):
        os.makedirs(OUTPUT_FOLDER)
    for scheduling_type in OUTPUT_NAMES.keys():
        result_exec_time, result_performance = benchmark(scheduling_type)
        data_exec_time = pd.DataFrame(result_exec_time,
                                      columns=HEADERS_EXEC_TIME)
        data_exec_time.to_csv(
            f'{OUTPUT_FOLDER}/{OUTPUT_NAMES[scheduling_type]}_exec_time.csv',
            index=False)
        data_performance = pd.DataFrame(result_performance,
                                        columns=HEADERS_PERFORMANCE)
        data_performance.to_csv(
            f'{OUTPUT_FOLDER}/{OUTPUT_NAMES[scheduling_type]}_performance.csv',
            index=False)
        # results = pd.read_csv(f'{OUTPUT_FOLDER}/{OUTPUT_NAMES[1]}.csv', header=None, skiprows=[0])
        plot_exec_time(data_exec_time, scheduling_type)
        plop_performance(data_performance, scheduling_type)
