import os
import subprocess
import time
import pandas as pd
import matplotlib.pyplot as plt
from tqdm import tqdm

BIN_NAME: str = "./openmp_ray"
THREADS_NUMBER: list[int] = [1, 2, 4, 6, 8, 10, 12, 14, 16]
OUTPUT_FOLDER: str = "./benchmark/results"
HEADERS: list[str] = ["Number of thread", "Execution time", "Performance"]


def benchmark() -> list[list]:
    dest: list[list] = []
    for t in tqdm(THREADS_NUMBER):
        timeStarted: float = time.time()
        subprocess.run([f'{BIN_NAME}', f"{t}"],
                       stdout=subprocess.DEVNULL)
        timeDelta: float = time.time() - timeStarted
        time_ms: float = round(timeDelta * 1000)
        performance: float = 1 / time_ms
        dest.append([t, time_ms, performance])
    return dest


def plot(data: pd.DataFrame) -> None:
    plt.plot(data[HEADERS[0]], data[HEADERS[1]], marker="x")
    plt.title("Execution Time in function of the number of thread")
    plt.xlabel("# Thread")
    plt.ylabel("ms")
    plt.savefig(
        f'{OUTPUT_FOLDER}/openmp_ray_exec_time.png')
    plt.close()
    plt.plot(data[HEADERS[0]], data[HEADERS[2]], marker="x")
    plt.title("Performance in function of the number of thread")
    plt.xlabel("# Thread")
    plt.ylabel("ms")
    plt.savefig(
        f'{OUTPUT_FOLDER}/openmp_ray_performance.png')
    plt.close()


#
if __name__ == "__main__":
    if not os.path.exists(OUTPUT_FOLDER):
        os.makedirs(OUTPUT_FOLDER)
    results: list[list] = benchmark()
    csv: pd.DataFrame = pd.DataFrame(results, columns=HEADERS)
    csv.to_csv(f'{OUTPUT_FOLDER}/openmp_ray.csv',
                   index=False)
    # results = pd.read_csv(f'{OUTPUT_FOLDER}/{OUTPUT_NAMES[1]}.csv', header=None, skiprows=[0])
    plot(csv)
