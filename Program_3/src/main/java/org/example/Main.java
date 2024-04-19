package org.example;
import org.apache.commons.math3.linear.*;

import java.util.function.ToDoubleFunction;


public class Main {

    public static double matrixNorm1(double[][] matrix){
        double maxColSum = 0;
        for (int c = 0; c < matrix[0].length; c++){
            double colSum = 0;
            for (int r = 0; r < matrix.length; r++){
                colSum += Math.abs(matrix[r][c]);
            }
            if (colSum > maxColSum){
                maxColSum = colSum;
            }
        }
        return maxColSum;
    }

    public static double matrixNorm2(double[][] matrix) {
        // Obliczenie macierzy M^T * M
        int cols = matrix[0].length;
        double[][] mtm = new double[cols][cols];

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < cols; j++) {
                for (int r = 0; r < matrix.length; r++) {
                    mtm[i][j] += matrix[r][i] * matrix[r][j];
                }
            }
        }
        // Obliczenie wartości własnych macierzy M^T * M
        RealMatrix mtmMatrix = new Array2DRowRealMatrix(mtm);
        EigenDecomposition eig = new EigenDecomposition(mtmMatrix);
        double maxEigenvalue = eig.getRealEigenvalue(0); // Pierwsza największa wartość własna

        // Zwrócenie pierwiastka kwadratowego z największej wartości własnej
        return Math.sqrt(maxEigenvalue);
    }

    public static double matrixNorm3(double[][] matrix) {
        double maxColumnNorm = 0;
        for (int c = 0; c < matrix[0].length; c++){
            double columnNorm = 0;
            for (double[] row : matrix) {
                columnNorm += Math.pow(Math.abs(row[c]), 3);
            }
            maxColumnNorm = Math.max(maxColumnNorm, Math.pow(columnNorm, 1.0 / 3.0));
        }
        return maxColumnNorm;
    }


    public static double matrixNormInfinity(double[][] matrix){
        double maxRowSum = 0;
        for (int r = 0; r < matrix.length; r++){
            double rowSum = 0;
            for (int c = 0; c < matrix[0].length; c++){
                rowSum += Math.abs(matrix[r][c]);
            }
            if (rowSum > maxRowSum){
                maxRowSum = rowSum;
            }
        }
        return maxRowSum;
    }



    //----------------------------------------------------------------------------------------------------------------
    public static double conditionNumber(double[][] matrix, ToDoubleFunction<double[][]> normFunction) {
        double[][] inverse;

        RealMatrix matrix1 = new Array2DRowRealMatrix(matrix);
        RealMatrix inverseMatrix = MatrixUtils.inverse(matrix1);
        inverse = inverseMatrix.getData();


        double norm = normFunction.applyAsDouble(matrix);
        double inverseNorm = normFunction.applyAsDouble(inverse);

        return norm * inverseNorm;
    }


    private static void matrixSVD(double[][] matrix){
        SingularValueDecomposition svd = new SingularValueDecomposition(matrix);

        System.out.println("U matrix:");
        printMatrix(svd.getU());
        System.out.println("S matrix:");
        printMatrix(svd.getS());
        System.out.println("V matrix:");
        printMatrix(svd.getV());
    };


    public static void printMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            for (double val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        double[][] tibetanMatrix = {
                {4, 9, 2},
                {3, 5, 7},
                {8, 1, 6}
        };
        double[][] matrix = {
                {-3, 5, 7},
                {2, 6, 4},
                {0, 2, 8}
        };
        double[][] matrix2 = {
                {1, 0, -2},
                {3, 4,  6},
                {-1, 5,  7}
        };
        double[][] matrix3 = {
                {1, 2, 0},
                {2, 0, 2}};

        double[][] matrix4 = {
                {5, 1, 5},
                {2, 0,  6},
                {1, -4,  -1}
        };


        matrixSVD(tibetanMatrix);

        System.out.println("Norma macierzowa ||M||1 wynosi: " + matrixNorm1(tibetanMatrix));
        System.out.println("Norma macierzowa ||M||2 wynosi: " + matrixNorm2(tibetanMatrix));
        System.out.println("Norma macierzowa ||M||3 wynosi: " + matrixNorm3(tibetanMatrix));
        System.out.println("Norma macierzowa ||M||Inf wynosi: " + matrixNormInfinity(tibetanMatrix));
        System.out.println("Współczynnik uwarunkowania macierzowego ||M||1 wynosi: " + conditionNumber(tibetanMatrix, Main::matrixNorm1));
        System.out.println("Współczynnik uwarunkowania macierzowego ||M||2 wynosi: " + conditionNumber(tibetanMatrix, Main::matrixNorm2));
        System.out.println("Współczynnik uwarunkowania macierzowego ||M||3 wynosi: " + conditionNumber(tibetanMatrix, Main::matrixNorm3));
        System.out.println("Współczynnik uwarunkowania macierzowego ||M||inf wynosi: " + conditionNumber(tibetanMatrix, Main::matrixNormInfinity));
    }
}