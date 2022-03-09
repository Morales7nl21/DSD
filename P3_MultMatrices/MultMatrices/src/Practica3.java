import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Practica3 implements InnerIproutesInterface {
    public static void main(String[] args) throws Exception {
        if (args.length == 1) {
            int tipoU = Integer.parseInt(args[0]);
            funcNodo0();

        } else if (args.length == 0) { // si no hay parámetros
            System.out.println("Debes ingresar el numero del nodo");
        } else {
            System.out.println("Demasiados parametros");
        }
    }

    static void funcNodo0() {

        creaMatrices();
        imprimeMatriz(A);
        System.out.println("----------------------------------------------------------");
        imprimeMatriz(A1);
        System.out.println("----------------------------------------------------------");
        imprimeMatriz(A2);
        System.out.println("----------------------------------------------------------");
        System.out.println("----------------------------------------------------------");
        imprimeMatriz(B);
        System.out.println("----------------------------------------------------------");
        imprimeMatriz(B1);
        System.out.println("----------------------------------------------------------");
        imprimeMatriz(B2);
        /*
         * for (;;) {
         * try (ServerSocket servidor = new ServerSocket(PORT)) {
         * Socket conexion = servidor.accept();
         * //DataOutputStream out = new DataOutputStream(conexion.getOutputStream());
         * DataInputStream in = new DataInputStream(conexion.getInputStream());
         * 
         * } catch (Exception e) {
         * // TODO: handle exception
         * }
         * 
         * }
         */
    }

    static void imprimeMatriz(double[][] m) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                System.out.print(m[i][j] + "\t  ");
            }
            System.out.println("");
        }
    }

    static void calcChecksum() {

    }

    static void creaMatrices() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                A[i][j] = i + 5 * j;
                B[i][j] = 5 * i - j;
            }
        }
        for (int i = 0; i < N ; i++) {
            for (int j = 0; j < N; j++) {
                if (i < N / 2) {
                    A1[i][j] = i + 5 * j;
                    A2[i][j] = i + N / 2 + 5 * j;
                }
                if(j<N/2){
                    B1[i][j] = 5 * i - j;
                    B2[i][j] = 5 * i - j - N / 2;
                }

            }
        }        
    }
}
