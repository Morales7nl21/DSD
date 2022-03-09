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
        double[][] A = new double[N][N];
        double[][] B = new double[N][N];
        double[][] C1 = new double[N/2][N/2];
        double[][] C2 = new double[N/2][N/2];
        double[][] C3 = new double[N/2][N/2];
        double[][] C4 = new double[N/2][N/2];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                A[i][j] = i + 5 * j;
                A[i][j] = 5 * i - j;
            }
        }
        double [][] A1 = new double[N/2][N];
        for (int i = 0; i < N/2; i++) {
            for (int j = 0; j < N; j++) {
                A1[i][j] = A[i][j];
            }
        }
        imprimeMatriz(A);        
        System.out.println("----------------------------------------------------------");
        imprimeMatriz(A1);
        /*
        for (;;) {
            try (ServerSocket servidor = new ServerSocket(PORT)) {
                Socket conexion = servidor.accept();
                //DataOutputStream out = new DataOutputStream(conexion.getOutputStream());
                DataInputStream in = new DataInputStream(conexion.getInputStream());

            } catch (Exception e) {
                // TODO: handle exception
            }

        }
        */
    }
    static void imprimeMatriz(double [][] m){
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                System.out.print(m[i][j]+"    ");
            }   
            System.out.println("");
        }
    }
    static void calcChecksum(){

    }
}
