import java.util.*;
import java.lang.*;

class MatMulThread extends Thread {
  int _matrixA[][];
  int _matrixB[][];
  int _matrixResult[][];
  int _startLine;
  int _nThread;


  MatMulThread(int a[][], int b[][], int c[][], int startLine, int nThread) {
    _matrixA = a;
    _matrixB = b;
    _matrixResult = c;
    _startLine = startLine;
    _nThread = nThread;
  }

  public void run() {
    long startTime = System.currentTimeMillis();
    multMatrix();
    long endTime = System.currentTimeMillis();
    long timeDiff = endTime - startTime;
    System.out.printf("Execution Time of thread %d : %d ms\n", Thread.currentThread().getId(), timeDiff);
  }
  
  private void multMatrix() {// a[m][n], b[n][p]
    if (_matrixA.length == 0)
      return ;
    if (_matrixA[0].length != _matrixB.length)
      return; // invalid dims

    int A_MatrixLineSize = _matrixA[0].length; // size line A
    int A_MatrixColSize = _matrixA.length; // size colone A
    int B_MatrixColSize = _matrixB.length; // size line B

    for (int i = _startLine; i < A_MatrixColSize; i += _nThread) {
      for (int j = 0; j < B_MatrixColSize; j++) {
        for (int k = 0; k < A_MatrixLineSize; k++) {
          // no lock because we can assume that only one thread will acces this memory location
          _matrixResult[i][j] += _matrixA[i][k] * _matrixB[k][j];
        }
      }
    }
  }

}

// command-line execution example) java MatmultD 6 < mat500.txt
// 6 means the number of threads to use
// < mat500.txt means the file that contains two matrices is given as standard input
//
// In eclipse, set the argument value and file input by using the menu [Run]->[Run Configurations]->{[Arguments], [Common->Input File]}.

// Original JAVA source code: http://stackoverflow.com/questions/21547462/how-to-multiply-2-dimensional-arrays-matrix-multiplication
public class MatmultD {
  private static Scanner sc = new Scanner(System.in);

  public static void main(String[] args) {
    int thread_no = args.length == 1 ? Integer.valueOf(args[0]) : 1;

    int a[][] = readMatrix();
    int b[][] = readMatrix();
    int m = a.length;
    int p = b[0].length;
    int c[][] = new int[m][p];
    MatMulThread threads[] = new MatMulThread[thread_no];

    long startTime = System.currentTimeMillis();

    for (int i = 0; i < threads.length; ++i) {
      threads[i] = new MatMulThread(a, b, c, i, thread_no);
      threads[i].start();
  }
  for (int i = 0; i < threads.length; ++i) {
      try {
          threads[i].join();
      } catch (InterruptedException e) {
      }
  }

    long endTime = System.currentTimeMillis();

    // printMatrix(a);
    // printMatrix(b);
    printMatrix(c);

    // System.out.printf("thread_no: %d\n" , thread_no);
    // System.out.printf("Calculation Time: %d ms\n" , endTime-startTime);

    System.out.printf("[thread_no]:%2d , [Time]:%4d ms\n", thread_no, endTime - startTime);
  }

  public static int[][] readMatrix() {
    int rows = sc.nextInt();
    int cols = sc.nextInt();
    int[][] result = new int[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        result[i][j] = sc.nextInt();
      }
    }
    return result;
  }

  public static void printMatrix(int[][] mat) {
    System.out.println("Matrix[" + mat.length + "][" + mat[0].length + "]");
    int rows = mat.length;
    int columns = mat[0].length;
    int sum = 0;
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        System.out.printf("%4d ", mat[i][j]);
        sum += mat[i][j];
      }
      System.out.println();
    }
    System.out.println();
    System.out.println("Matrix Sum = " + sum + "\n");
  }

  public static int[][] multMatrix(int a[][], int b[][]) {// a[m][n], b[n][p]
    if (a.length == 0)
      return new int[0][0];
    if (a[0].length != b.length)
      return null; // invalid dims

    int n = a[0].length;
    int m = a.length;
    int p = b[0].length;
    int ans[][] = new int[m][p];

    for (int i = 0; i < m; i++) {
      for (int j = 0; j < p; j++) {
        for (int k = 0; k < n; k++) {
          ans[i][j] += a[i][k] * b[k][j];
        }
      }
    }
    return ans;
  }
}
