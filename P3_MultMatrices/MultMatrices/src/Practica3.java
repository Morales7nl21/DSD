import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Practica3 implements InnerIproutesInterface {
    public static void main(String[] args) throws Exception {
        if (args.length == 1) {
            int tipoU = Integer.parseInt(args[0]);
            if (tipoU == 0)
                funcNodo0();

        } else if (args.length == 0) { // si no hay parámetros
            System.out.println("Debes ingresar el numero del nodo");
        } else {
            System.out.println("Demasiados parametros");
        }
    }

    static void funcNodo0() {

        creaMatrices();
        pruebaFunc();
        transponeMatriz(1);
        transponeMatriz(2);
        // Primer valor matriz, segundo el nodo destino para la ip, el tercero el numero
        // de matriz para guardarla en la localidad especifica
        // A1 = 1, A2 = 2, B1 = 3, B2= 4, el ultimo parametro son las dimensiones
        enviaMatriz(A1, 1, 1);
        enviaMatriz(B1, 1, 3);
        enviaMatriz(A1, 2, 1);
        enviaMatriz(B2, 2, 4);
        enviaMatriz(A2, 3, 2);
        enviaMatriz(B1, 3, 3);
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

    static void pruebaFunc() {

        /*
         * imprimeMatriz(A);
         * System.out.println(
         * "----------------------------------------------------------");
         * imprimeMatriz(A1);
         * System.out.println("----------------------A2------------------------------");
         * imprimeMatriz(A2);
         * System.out.println(
         * "----------------------------------------------------------");
         * System.out.println(
         * "----------------------------------------------------------");
         * imprimeMatriz(B);
         * System.out.println(
         * "----------------Normal------------------------------------");
         * imprimeMatriz(B1);
         * System.out.println(
         * "----------------------------------------------------------");
         * imprimeMatriz(B2);
         * 
         * System.out.println();
         * System.out.println(
         * "----------------------------------------------------------");
         * System.out.println(
         * "----------------------------------------------------------");
         * //funcMultNormal();
         * System.out.println(
         * "-------------Transpuesta-B1-------------------------------");
         * transponeMatriz(1);
         * System.out.println(
         * "-------------Transpuesta-B2-------------------------------");
         * transponeMatriz(2);
         */

    }

    static void funcNodo1() {

    }

    static void funcNodo2() {

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
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i < N / 2) {
                    A1[i][j] = i + 5 * j;
                    A2[i][j] = i + N / 2 + 5 * j;
                }
                if (j < N / 2) {
                    B1[i][j] = 5 * i - j;
                    B2[i][j] = 5 * i - j - N / 2;
                }

            }
        }
    }

    static void funcMultNormal() {
        double[][] C = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < i; j++) {
                double x = B[i][j];
                B[i][j] = B[j][i];
                B[j][i] = x;
            }
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int j2 = 0; j2 < N; j2++) {
                    C[i][j] += A[i][j2] * B[j][j2];
                }
            }
        }
        imprimeMatriz(C);
    }

    static void transponeMatriz(int opM) {

        for (int i = 0; i < B1.length; i++) {
            for (int j = 0; j < B1[i].length; j++) {
                if (opM == 1)
                    B1T[j][i] = B1[i][j];
                else if (opM == 2)
                    B2T[j][i] = B2[i][j];
            }
        }
        if (opM == 1)
            imprimeMatriz(B1T);
        else if (opM == 2)
            imprimeMatriz(B2T);

    }

    static void enviaMatriz(double[][] m_aEnviar, int nodo, int nmatriz) {
        try {
            Socket conn = null;
            conn = new Socket(ips[nodo], PORT);
            // DataInputStream in = new DataInputStream(conn.getInputStream());
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.writeInt(nmatriz);
            
            
            out.flush();
            for (int i = 0; i < m_aEnviar.length; i++) {
                for (int j = 0; j < m_aEnviar[0].length; j++) {
                    out.writeDouble(m_aEnviar[i][j]);
                }
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void recibeMatriz(int nespera) {
        int cont = 0;
  
        for (;;) {
            try (ServerSocket servidor = new ServerSocket(PORT)) {
                Socket conn = servidor.accept();
                DataInputStream in = new DataInputStream(conn.getInputStream());
                int nv = in.readInt();
                System.out.println("Numero de matriz " + String.valueOf(nv));
                int x =0, y = 0;
                if(nv == 1){
                    x=A1.length;
                    y=A1[0].length;
                }else if(nv == 2){
                    x=A2.length;
                    y=A2[0].length;
                }else if(nv==3){
                    x=B1T.length;
                    y=B1T[0].length;
                }else if(nv==4){
                    x=B2T.length;
                    y=B2T[0].length;
                }

                for (int i = 0; i < x; i++) {
                    for (int j = 0; j < y; j++) {
                        if(nv==1) A1[i][j] = in.readDouble(); 
                        if(nv==2) A2[i][j] = in.readDouble();
                        if(nv==3) B1T[i][j] = in.readDouble();
                        if(nv==4) B2T[i][j] = in.readDouble();
                    }
                }
                cont++;
                conn.close();                
            } catch (Exception e) {
                // TODO: handle exception
            }
            if(nespera == 0 && cont == 3)break;
            if(nespera == 1 && cont == 2)break;
            if(nespera == 2 && cont == 2)break;
            if(nespera == 3 && cont == 2)break;
        }

    }
}
