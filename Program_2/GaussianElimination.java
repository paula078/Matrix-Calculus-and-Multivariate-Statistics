public class GaussianElimination {


    public static void gaussianElimination(double[][] A, double[] b) {

        int n = b.length;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double factor = A[j][i] / A[i][i];
                for (int k = i; k < n; k++) {
                    A[j][k] -= factor * A[i][k];
                }
                b[j] -= factor * b[i];
            }
            double mainElement = A[i][i];
            for (int k = i; k < n; k++) {
                A[i][k] /= mainElement;
            }
            b[i] /= mainElement;
        }
    }

    public static void gaussianEliminationWithPivoting(double[][] A, double[] b) {

        int n = b.length;

        for (int r = 0; r < n; r++) {

            // Wybieramy pivot
            int maxIndex = r;
            double maxValue = Math.abs(A[r][r]);
            for(int c = r + 1; c< n; c++){
                if(Math.abs(A[c][r]) > maxValue){
                    maxIndex = c;
                    maxValue = Math.abs(A[c][r]);
                }
            }

            // Zamieniamy
            if (maxIndex != r) {
                swapRows(A, r, maxIndex);
                swapValues(b, r, maxIndex);
            }

            gaussianElimination(A, b);
        }
    }

    private static void swapRows(double[][] matrix, int row1, int row2) {
        double[] temp = matrix[row1];
        matrix[row1] = matrix[row2];
        matrix[row2] = temp;
    }
    private static void swapValues(double[] vector, int row1, int row2) {
        double temp = vector[row1];
        vector[row1] = vector[row2];
        vector[row2] = temp;
    }
}
