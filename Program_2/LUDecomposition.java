public class LUDecomposition {

    public static void luDecomposition(double[][] A, double[][] U, double[][] L) {

        int n = A.length;

        for (int k = 0; k < n; k++) {
            System.arraycopy(A[k], k, U[k], k, n - k);
            for (int i = k + 1; i < n; i++) {
                L[i][k] = A[i][k] / U[k][k];
                for (int j = k + 1; j < n; j++) {
                    A[i][j] = A[i][j] - L[i][k] * U[k][j];
                }
            }
        }
        for (int i = 0; i < n; i++) {
            L[i][i] = 1;
        }
    }

    public static void luDecompositionWithPivoting(double[][] A, double[][] U, double[][] L, double[][] P) {

        int n = A.length;

        for (int k = 0; k < n; k++) {
            // Wybór elementu maksymalnego w kolumnie k
            int maxRowIndex = k;
            double maxVal = Math.abs(A[k][k]);
            for (int i = k + 1; i < n; i++) {
                if (Math.abs(A[i][k]) > maxVal) {
                    maxVal = Math.abs(A[i][k]);
                    maxRowIndex = i;
                }
            }

            // Zamiana wierszy macierzy A i P
            if (maxRowIndex != k) {
                swapRows(A, k, maxRowIndex);
                swapRows(P, k, maxRowIndex);
                for (int i = 0; i < k; i++) {
                    double temp = L[k][i];
                    L[k][i] = L[maxRowIndex][i];
                    L[maxRowIndex][i] = temp;
                }
            }

            // Uzupelnienie k-tej kolumny macierzy U
            System.arraycopy(A[k], k, U[k], k, n - k);

            for (int i = k + 1; i < n; i++) {
                L[i][k] = A[i][k] / A[k][k];
                for (int j = k; j < n; j++) {
                    A[i][j] = A[i][j] - L[i][k] * U[k][j];
                }
            }
        }
    }

    private static void swapRows(double[][] matrix, int row1, int row2) {
        double[] temp = matrix[row1];
        matrix[row1] = matrix[row2];
        matrix[row2] = temp;
    }
}
