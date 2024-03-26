package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.lang.Math;




public class Main {
    static int operationsCount = 0;
    public static void main(String[] args) {

        int maxPower = 11;
        createFile(maxPower, "timeMesaurements.txt");
        createFile(maxPower, "operationsCount.txt");
        for(int i=2; i<maxPower; i++) {
            getMeasurements(maxPower, i);
        }
    }

    private static void getMeasurements(int maxPower, int l) {
        Long[] mixedTimes = new Long[maxPower];
        Float[] operations = new Float[maxPower];

        for (int p = 0; p < maxPower; p++) {

            int matrix_size = (int) Math.pow(2, p);
            float[][] A = randomMatrix(matrix_size);
            float[][] B = randomMatrix(matrix_size);
            long startTime, endTime;

            startTime = System.currentTimeMillis();
            combinedMatrixMultiplication(A, B, l);
            endTime = System.currentTimeMillis();

            long multiplicationTime = endTime - startTime;
            mixedTimes[p] = multiplicationTime;

            if (multiplicationTime/1000 != 0){
                operations[p] = (float) operationsCount / ((float)(multiplicationTime) / 1000);
            }

            operations[p] = (float)operationsCount;
            operationsCount = 0;
        }
        saveMeasurements(mixedTimes, l, "timeMesaurements.txt");
        saveMeasurements(operations, l, "operationsCount.txt");
    }

    private static float[][] randomMatrix(int size){
        Random random = new Random();
        float[][] matrix = new float[size][size];
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                float element = random.nextFloat();
                matrix[i][j] = element;
            }
        }
        return matrix;
    }

    private static float[][] classicalMatrixMultiplication(float[][] A, float[][] B){
        int result_rows = A.length;
        int result_columns = B[0].length;
        int rows = B.length;
        float[][] product = new float[result_rows][result_columns];

        for (int i=0; i < result_rows; i++){
            for(int j=0; j < result_columns; j++){
                product[i][j] = 0;
                for(int k=0; k < rows; k++){
                    product[i][j] += A[i][k] * B[k][j];
                    operationsCount += 2;
                }
            }
        }
        operationsCount -= (int) Math.pow(A.length,2);
        return product;
    }

    private static float[][] binetMatrixMultiplication(float[][] A, float[][] B){
        int n = A.length;
        float[][] C = new float[n][n];
        int halfSize = n/2;
        if(n == 2){
            C = classicalMatrixMultiplication(A, B);
        }
        else{
            float[][] A11 = new float[halfSize][halfSize];
            float[][] A12 = new float[halfSize][halfSize];
            float[][] A21 = new float[halfSize][halfSize];
            float[][] A22 = new float[halfSize][halfSize];
            float[][] B11 = new float[halfSize][halfSize];
            float[][] B12 = new float[halfSize][halfSize];
            float[][] B21 = new float[halfSize][halfSize];
            float[][] B22 = new float[halfSize][halfSize];

            splitMatrix(A, A11, 0, 0);
            splitMatrix(A, A12, 0, halfSize);
            splitMatrix(A, A21, halfSize, 0);
            splitMatrix(A, A22, halfSize, halfSize);
            splitMatrix(B, B11, 0, 0);
            splitMatrix(B, B12, 0, halfSize);
            splitMatrix(B, B21, halfSize, 0);
            splitMatrix(B, B22, halfSize, halfSize);

            float[][] C11 = matrixAddition(binetMatrixMultiplication(A11, B11), binetMatrixMultiplication(A12, B21));
            float[][] C12 = matrixAddition(binetMatrixMultiplication(A11, B12), binetMatrixMultiplication(A12, B22));
            float[][] C21 = matrixAddition(binetMatrixMultiplication(A21, B11), binetMatrixMultiplication(A22, B21));
            float[][] C22 = matrixAddition(binetMatrixMultiplication(A21, B12), binetMatrixMultiplication(A22, B22));

            combineMatrix(C11, C, 0, 0);
            combineMatrix(C12, C, 0, halfSize);
            combineMatrix(C21, C, halfSize, 0);
            combineMatrix(C22, C, halfSize, halfSize);
        }
        return C;
    }

    private static float[][] combinedMatrixMultiplication(float[][] A, float[][] B, int l){
        int n = A.length;
        float[][] C = new float[n][n];
        int halfSize = n/2;
        if(n <= Math.pow(2,l)){
            C = classicalMatrixMultiplication(A, B);
        }
        else{
            float[][] A11 = new float[halfSize][halfSize];
            float[][] A12 = new float[halfSize][halfSize];
            float[][] A21 = new float[halfSize][halfSize];
            float[][] A22 = new float[halfSize][halfSize];
            float[][] B11 = new float[halfSize][halfSize];
            float[][] B12 = new float[halfSize][halfSize];
            float[][] B21 = new float[halfSize][halfSize];
            float[][] B22 = new float[halfSize][halfSize];

            splitMatrix(A, A11, 0, 0);
            splitMatrix(A, A12, 0, halfSize);
            splitMatrix(A, A21, halfSize, 0);
            splitMatrix(A, A22, halfSize, halfSize);
            splitMatrix(B, B11, 0, 0);
            splitMatrix(B, B12, 0, halfSize);
            splitMatrix(B, B21, halfSize, 0);
            splitMatrix(B, B22, halfSize, halfSize);


            float[][] C11 = matrixAddition(combinedMatrixMultiplication(A11, B11, l), combinedMatrixMultiplication(A12, B21, l));
            float[][] C12 = matrixAddition(combinedMatrixMultiplication(A11, B12, l), combinedMatrixMultiplication(A12, B22, l));
            float[][] C21 = matrixAddition(combinedMatrixMultiplication(A21, B11, l), combinedMatrixMultiplication(A22, B21, l));
            float[][] C22 = matrixAddition(combinedMatrixMultiplication(A21, B12, l), combinedMatrixMultiplication(A22, B22, l));

            combineMatrix(C11, C, 0, 0);
            combineMatrix(C12, C, 0, halfSize);
            combineMatrix(C21, C, halfSize, 0);
            combineMatrix(C22, C, halfSize, halfSize);
            operationsCount += 1;
        }
        return C;
    }

    private static void splitMatrix(float[][] inputMatrix, float[][] resultMatrix, int rowID, int colID){
        for (int i1 = 0, i2 = rowID; i1 < resultMatrix.length; i1++, i2++) {
            for (int j1 = 0, j2 = colID; j1 < resultMatrix.length; j1++, j2++) {
                resultMatrix[i1][j1] = inputMatrix[i2][j2];
            }
        }
    }

    private static float[][] matrixAddition(float[][] A, float[][] B) {
        int n = A.length;
        float[][] C = new float[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j] + B[i][j];
                operationsCount += 1;
            }
        }
        return C;
    }
    private static void combineMatrix(float[][] matrix, float[][] biggerMatrix, int rowID, int colID) {
        for (int i1 = 0, i2 = rowID; i1 < matrix.length; i1++, i2++) {
            for (int j1 = 0, j2 = colID; j1 < matrix.length; j1++, j2++) {
                biggerMatrix[i2][j2] = matrix[i1][j1];
            }
        }
    }

    private static void createFile(int maxPower, String fileName){

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            for (int i=0; i<maxPower; i++) {
                writer.write(String.valueOf((int)Math.pow(2, i))+" ");
            }
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Writing to a file ERROR: " + e.getMessage());
        }
    }


    private static <T> void saveMeasurements(T[] array, int l, String fileName){

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write("l="+l);
            writer.newLine();
            for (T element : array) {
                writer.write(String.valueOf(element)+" ");
            }
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Writing to a file ERROR: " + e.getMessage());
        }
    }
}