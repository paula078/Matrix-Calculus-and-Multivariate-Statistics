package org.example;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.Arrays;
import java.util.Comparator;

public class SingularValueDecomposition {

    // zakladamy kwadratowa macierz
    private final int rows;
    private final int columns;
    private double[][] A;
    private double[][] U;
    private double[][] S;
    private double[][] V;

    public SingularValueDecomposition(double[][] matrix){
        rows = matrix.length;
        columns = matrix[0].length;
        A = matrix;
        U = new double[rows][rows];
        S = new double[rows][columns];
        V = new double[columns][columns];
        performSVD();
    }

    public double[][] getA(){
        return A;
    }
    public double[][] getU(){
        return U;
    }
    public double[][] getS(){
        return S;
    }
    public double[][] getV(){
        return V;
    }


    private void performSVD(){
        double[][] transposed_A = transpose(A);
        double[][] product1 = matrixMultiplication(A, transposed_A);
        double[][] product2 = matrixMultiplication(transposed_A, A);


        // U CALCULATION

        U = getMatrixFromEigenvectors(product1);

        // S CALCULATION
        RealMatrix matrix = new Array2DRowRealMatrix(product1);
        EigenDecomposition eigenDecomposition =  new EigenDecomposition(matrix);
        double[] eigenvalues = eigenDecomposition.getRealEigenvalues();
        Double[] eigenvaluesSorted = sortArray(eigenvalues);
        S = getSigmaMatrix(eigenvaluesSorted);

        // V CALCULATION
        V = getMatrixFromEigenvectors(product2);
    }

    private double[][] getMatrixFromEigenvectors(double[][] productMatrix){
        RealMatrix matrix = new Array2DRowRealMatrix(productMatrix);
        EigenDecomposition eigenDecomposition =  new EigenDecomposition(matrix);
        RealMatrix eigenvectors = eigenDecomposition.getV();
        return transpose(eigenvectors.getData());
    }

    private double[][] getSigmaMatrix(Double[] eigenValues){
        double[][] result = new double[rows][columns];
        for(int i=0; i<rows; i++){
            for(int j=0; j<columns; j++){
                if(i == j)
                    result[i][j] = Math.sqrt(eigenValues[i]);
            }
        }
        return result;
    }

    private double[][] transpose(double[][] matrix){
        int rows = matrix.length;
        int cols = matrix[0].length;

        double[][] transposed = new double[cols][rows];
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                transposed[j][i] = matrix[i][j];
            }
        }
        return transposed;
    }

    private double[][] matrixMultiplication(double[][] A, double[][] B){
        int resultRows = A.length;
        int resultColumns = B[0].length;
        int rows = B.length;
        double[][] product = new double[resultRows][resultColumns];

        for (int i=0; i < resultRows; i++){
            for(int j=0; j < resultColumns; j++){
                product[i][j] = 0;
                for(int k=0; k < rows; k++){
                    product[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return product;
    }

    private Double[] sortArray(double[] array){

        Double[] doubleObjectArray = new Double[array.length];
        for (int i = 0; i < array.length; i++) {
            doubleObjectArray[i] = array[i];
        }

        Arrays.sort(doubleObjectArray, new Comparator<Double>() {
            @Override
            public int compare(Double o1, Double o2) {
                return o2.compareTo(o1);
            }
        });
        return doubleObjectArray;
    }

}
