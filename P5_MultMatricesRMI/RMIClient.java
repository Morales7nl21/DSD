import java.rmi.Naming;

public class RMIClient {
    static int N = 8;
    static double[][] A = new double[N][N];
    static double[][] B = new double[N][N];
    static double[][] C = new double[N][N];

    /**
     * Separa la matriz de entrada A en una fila a partir del punto de inicio
     * indicado
     * 
     * @param A      matriz
     * @param inicio fila de inicio
     * @return Matriz fila separada de la matriz A
     */
    static double[][] separa_matriz(double[][] A, int inicio) {
        double[][] M = new double[N / 4][N];
        for (int i = 0; i < N / 4; i++)
            for (int j = 0; j < N; j++)
                M[i][j] = A[i + inicio][j];
        return M;
    }

    /**
     * Acomoda la matriz C
     * 
     * @param C       Matriz C completa
     * @param c       Submatriz c
     * @param renglon renglon de la matriz C en donde se colocará la submatriz
     * @param columna columna de la matriz C en donde se colocará la submatriz
     */
    static void acomoda_matriz(double[][] C, double[][] c, int renglon, int columna) {
        for (int i = 0; i < N / 4; i++)
            for (int j = 0; j < N / 4; j++)
                C[i + renglon][j + columna] = c[i][j];
    }

    /**
     * Funcion para imprimir una matriz de forma epica
     * 
     * @param matriz
     */
    public static void ImprimirMatriz(double[][] matriz) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(matriz[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws Exception{
        /* rmi://[IP]:[Puerto]/[Nombre] */

        // Nodo 1
        String url1 = "rmi://localhost/matriz";
        InterfaceRMI r1 = (InterfaceRMI) Naming.lookup(url1);
        // Nodo 2
        String url2 = "rmi://localhost/matriz";
        InterfaceRMI r2 = (InterfaceRMI) Naming.lookup(url2);
        // Nodo 3
        String url3 = "rmi://localhost/matriz";
        InterfaceRMI r3 = (InterfaceRMI) Naming.lookup(url3);
        // Nodo 4
        String url4 = "rmi://localhost/matriz";
        InterfaceRMI r4 = (InterfaceRMI) Naming.lookup(url4);

        // Inicializa las matrices A y B
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                A[i][j] = i + 2 * j;
                B[i][j] = 3 * i - j;
                C[i][j] = 0;
            }

        // Impresion de matrices A y B (Solo si N=8)
        if (N == 8) {
            System.out.println("\n Matriz A\n");
            ImprimirMatriz(A);
            System.out.println("\n Matriz B\n");
            ImprimirMatriz(B);
        }

        // transpone la matriz B, la matriz traspuesta queda en B
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < i; j++) {
                double x = B[i][j];
                B[i][j] = B[j][i];
                B[j][i] = x;
            }
        }

        // Separa las matrices
        double[][] A1 = separa_matriz(A, 0);
        double[][] A2 = separa_matriz(A, N / 4);
        double[][] A3 = separa_matriz(A, N / 2);
        double[][] A4 = separa_matriz(A, (N / 4) * 3);
        double[][] B1 = separa_matriz(B, 0);
        double[][] B2 = separa_matriz(B, N / 4);
        double[][] B3 = separa_matriz(B, N / 2);
        double[][] B4 = separa_matriz(B, (N / 4) * 3);

        // Multiplica matrices
        // Nodo 1
        double[][] C1 = r1.multiplica_matrices(A1, B1, N);
        double[][] C2 = r1.multiplica_matrices(A1, B2, N);
        double[][] C3 = r1.multiplica_matrices(A1, B3, N);
        double[][] C4 = r1.multiplica_matrices(A1, B4, N);
        // Nodo 2
        double[][] C5 = r2.multiplica_matrices(A2, B1, N);
        double[][] C6 = r2.multiplica_matrices(A2, B2, N);
        double[][] C7 = r2.multiplica_matrices(A2, B3, N);
        double[][] C8 = r2.multiplica_matrices(A2, B4, N);
        // Nodo 3
        double[][] C9 = r3.multiplica_matrices(A3, B1, N);
        double[][] C10 = r3.multiplica_matrices(A3, B2, N);
        double[][] C11 = r3.multiplica_matrices(A3, B3, N);
        double[][] C12 = r3.multiplica_matrices(A3, B4, N);
        // Nodo 4
        double[][] C13 = r4.multiplica_matrices(A4, B1, N);
        double[][] C14 = r4.multiplica_matrices(A4, B2, N);
        double[][] C15 = r4.multiplica_matrices(A4, B3, N);
        double[][] C16 = r4.multiplica_matrices(A4, B4, N);
        // Acomoda las matrices
        // Fila 1
        acomoda_matriz(C, C1, 0, 0);
        acomoda_matriz(C, C2, 0, N / 4);
        acomoda_matriz(C, C3, 0, N / 2);
        acomoda_matriz(C, C4, 0, (N / 4) * 3);
        // Fila 2
        acomoda_matriz(C, C5, N / 4, 0);
        acomoda_matriz(C, C6, N / 4, N / 4);
        acomoda_matriz(C, C7, N / 4, N / 2);
        acomoda_matriz(C, C8, N / 4, (N / 4) * 3);
        // Fila 3
        acomoda_matriz(C, C9, N / 2, 0);
        acomoda_matriz(C, C10, N / 2, N / 4);
        acomoda_matriz(C, C11, N / 2, N / 2);
        acomoda_matriz(C, C12, N / 2, (N / 4) * 3);
        // Fila 4
        acomoda_matriz(C, C13, (N / 4) * 3, 0);
        acomoda_matriz(C, C14, (N / 4) * 3, N / 4);
        acomoda_matriz(C, C15, (N / 4) * 3, N / 2);
        acomoda_matriz(C, C16, (N / 4) * 3, (N / 4) * 3);

        // TODO: Implementarlo con hilos

        // Imprime matrices si N=8
        if (N == 8) {
            System.out.println("\n Matriz A\n");
            ImprimirMatriz(A);
            System.out.println("\n Matriz B\n");
            ImprimirMatriz(B);
            System.out.println("\n Matriz C\n");
            ImprimirMatriz(C);
        }

        // Calcular checksum
        double checksum = 0.0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                checksum += C[i][j];
            }
        }
        System.out.println("\nChecksum: " + checksum);
    }
}
