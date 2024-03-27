//package org.example;

import java.util.Random;

public class Main {

    public static void main(String[] args) {

        // Urodziny Karola = 08.01 = 9
        // Urodziny Pauliny: 10.08 = 18
        // 3.
  //      gaussianElimination(9, false);

        // 6.
//gaussianElimination(9, true);

        // 9.
 //       luDecomposition(9, false);

        // 12.
        luDecomposition(9, true);

    }
    private static void luDecomposition(int n, boolean pivoting){
        double[][] A = generateRandomMatrix(n);
        double[] b = generateRandomVector(n);

//        double[][] A = {
//                {-8.341966522648791, 7.314885645946639, 1.3956112936586464, -1.7123728019334106, 8.305936838683522, -7.95377280427285, 5.316754256250851, 7.597985793359538, -3.3158779149780866},
//                {8.293268789132554, 9.82844375668115, -6.9165770193657945, 3.108604985911306, 4.718572973967499, -4.745631785798974, 9.067241825467896, 1.3349528744747765, -4.746879865284283},
//                {3.304658130010152, -4.518978190740659, -0.8960446022352269, -2.9351137243294634, 7.088745515483904, -4.11109817662505, -3.3940415770296717, -8.245781906952796, 0.537423932494562},
//                {1.0265597244230875, 1.615441030269654, -7.244872924323442, 6.604835788536196, 0.8437849953316423, 6.691958399806698, -0.4025851871679169, 9.578141192898194, 3.527811253560433},
//                {-9.886900961821329, -2.0146874217662063, 8.40466633447599, 6.097865484052537, -5.900365055355645, 3.5741077482758854, 5.153343985452938, -2.1003288519048997, 1.4125776909771623},
//                {6.877010579926782, 5.55930754895785, 5.691916008376099, -5.453613084902509, -3.0897129784726403, -2.9424881746517713, 7.004990927395053, 0.5120987844202336, 1.9854772542572867},
//                {3.7933091352829464, 7.065571781018416, -1.4013139649910773, 7.483292051960444, -2.195087951294095, -6.18109397050193, -1.479514976229357, -7.505438607220462, -9.095266302166628},
//                {-4.556049907512161, 2.003056559215125, 9.88939699125272, 2.743604124699324, 5.244772753662048, 2.129379247203243, -2.323499211523936, -2.8760906869892366, 9.914937481899123},
//                {-8.337188530003685, -6.559651547974672, 0.8347229148897704, -1.0901594872117677, 4.082775279790411, 8.194736819589345, 7.114028709829704, 5.025278543650051, -0.7863366062489074}
//        };
//
//        double[] b = {-1.8006383470387952, 3.2326077606304153, 3.4404355809235465, -0.8743375728036362, -2.142042334698897, -4.275826144744743, -6.066902885044496, -7.118497217316713, -2.81422387265426};




        System.out.println("A matrix:");
        printMatrix(A);
        System.out.println("b vector:");
        printVector(b);

        double[][] U = new double[n][n];
        double[] y;
        double[] x;
        if (pivoting) {
            double[][] L = identityMatrix(n);
            double[][] P = identityMatrix(n);


            LUDecomposition.luDecompositionWithPivoting(A, U, L, P);
            System.out.println("L matrix: ");
            printMatrix(L);
            System.out.println("U matrix: ");
            printMatrix(U);
            System.out.println("P matrix: ");
            printMatrix(P);

            y  = forwardSubstitution(L, b);
            System.out.println("y: ");
            printVector(y);
            x = backwardSubstitution(U, y);
            System.out.println("x: ");
            printVector(x);
        }
        else{
            double[][] L = new double[n][n];
            LUDecomposition.luDecomposition(A, U, L);
            System.out.println("L matrix: ");
            printMatrix(L);
            System.out.println("U matrix: ");
            printMatrix(U);

            y  = forwardSubstitution(L, b);
            System.out.println("y: ");
            printVector(y);
            x = backwardSubstitution(U, y);
            System.out.println("x: ");
            printVector(x);
        }
    }

    private static void gaussianElimination(int n, boolean pivoting){
        double[][] A = generateRandomMatrix(n);
        double[] b = generateRandomVector(n);

//        double[][] A = {
//                {-8.341966522648791, 7.314885645946639, 1.3956112936586464, -1.7123728019334106, 8.305936838683522, -7.95377280427285, 5.316754256250851, 7.597985793359538, -3.3158779149780866},
//                {8.293268789132554, 9.82844375668115, -6.9165770193657945, 3.108604985911306, 4.718572973967499, -4.745631785798974, 9.067241825467896, 1.3349528744747765, -4.746879865284283},
//                {3.304658130010152, -4.518978190740659, -0.8960446022352269, -2.9351137243294634, 7.088745515483904, -4.11109817662505, -3.3940415770296717, -8.245781906952796, 0.537423932494562},
//                {1.0265597244230875, 1.615441030269654, -7.244872924323442, 6.604835788536196, 0.8437849953316423, 6.691958399806698, -0.4025851871679169, 9.578141192898194, 3.527811253560433},
//                {-9.886900961821329, -2.0146874217662063, 8.40466633447599, 6.097865484052537, -5.900365055355645, 3.5741077482758854, 5.153343985452938, -2.1003288519048997, 1.4125776909771623},
//                {6.877010579926782, 5.55930754895785, 5.691916008376099, -5.453613084902509, -3.0897129784726403, -2.9424881746517713, 7.004990927395053, 0.5120987844202336, 1.9854772542572867},
//                {3.7933091352829464, 7.065571781018416, -1.4013139649910773, 7.483292051960444, -2.195087951294095, -6.18109397050193, -1.479514976229357, -7.505438607220462, -9.095266302166628},
//                {-4.556049907512161, 2.003056559215125, 9.88939699125272, 2.743604124699324, 5.244772753662048, 2.129379247203243, -2.323499211523936, -2.8760906869892366, 9.914937481899123},
//                {-8.337188530003685, -6.559651547974672, 0.8347229148897704, -1.0901594872117677, 4.082775279790411, 8.194736819589345, 7.114028709829704, 5.025278543650051, -0.7863366062489074}
//        };
//
//        double[] b = {-1.8006383470387952, 3.2326077606304153, 3.4404355809235465, -0.8743375728036362, -2.142042334698897, -4.275826144744743, -6.066902885044496, -7.118497217316713, -2.81422387265426};


        System.out.println("A matrix: ");
        printMatrix(A);
        System.out.println("b vector: ");
        printVector(b);

        if(pivoting)
            GaussianElimination.gaussianEliminationWithPivoting(A,b);
        else
            GaussianElimination.gaussianElimination(A, b);
        System.out.println("A matrix [After gaussian elimination]:");
        printMatrix(A);
        System.out.println("b vector [After gaussian elimination]: ");
        printVector(b);

        System.out.println("Solution:");
        double[] solution = solveEquations(A, b);
        printVector(solution);
    }

    public static double[][] identityMatrix(int size){
        double[][] identityMatrix = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    identityMatrix[i][j] = 1.0;
                } else {
                    identityMatrix[i][j] = 0.0;
                }
            }
        }
        return identityMatrix;
    }

    private static double[] solveEquations(double[][] A, double[] b){
        int n = b.length;
        double[] result = new double[n];

        result[n-1] = b[n-1];

        for(int i=n-2; i>= 0; i--){
            double sum = b[i];
            for(int j=i+1; j<n; j++){
                sum -= A[i][j] * result[j];
            }
            result[i] = sum;
        }

        return result;
    }

    public static double[] forwardSubstitution(double[][] L, double[] b) {
        int n = L.length;
        double[] y = new double[n];
        for (int i = 0; i < n; i++) {
            double sum = 0;
            for (int j = 0; j < i; j++) {
                sum += L[i][j] * y[j];
            }
            y[i] = (b[i] - sum) / L[i][i];
        }
        return y;
    }

    public static double[] backwardSubstitution(double[][] U, double[] y) {
        int n = U.length;
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0;
            for (int j = i + 1; j < n; j++) {
                sum += U[i][j] * x[j];
            }
            x[i] = (y[i] - sum) / U[i][i];
        }
        return x;
    }


    public static double[][] generateRandomMatrix(int size) {
        double[][] matrix = new double[size][size];
        Random random = new Random();

        // Wypełniamy losowymi wartościami z przedziału (-10, 10)
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = random.nextDouble() * 20 - 10;
            }
        }
        return matrix;
    }

    public static double[] generateRandomVector(int size){
        double[] vector = new double[size];
        Random random = new Random();

        for(int i=0; i<size; i++){
            vector[i] = random.nextDouble() * 20 - 10;
        }
        return vector;
    }

    public static void printMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            for (double val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    public static void printVector(double[] vector) {
        for (double val : vector) {
            System.out.print(val + " ");
        }
        System.out.println();
        System.out.println();
    }

}

