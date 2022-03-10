import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Practica3 {
    final static int N = 8;
    static double[][] C1 = new double[N / 2][N / 2];
    static double[][] C2 = new double[N / 2][N / 2];
    static double[][] C3 = new double[N / 2][N / 2];
    static double[][] C4 = new double[N / 2][N / 2];

    final static int PORT = 50000;
    final static String[] IPS = { "52.224.56.98", "20.25.74.247", "20.127.115.244", "23.101.141.39" };

    static double[][] A = new double[N][N];
    static double[][] B = new double[N][N];

    static double[][] A1 = new double[N / 2][N];
    static double[][] A2 = new double[N / 2][N];
    static double[][] B1 = new double[N / 2][N];
    static double[][] B2 = new double[N / 2][N];

    static class Worker extends Thread {
        String ip;
        double[][] m_aEnviar;
        double[][] m1_aEnviar;
        double[][] m2_aEnviar;
        int numMatricesAEnviar;
        int nmatriz;
        int nmatriz2;
        int tipoSuC = 0;
        int nespera;

        Worker(String ip, int tipoSuC, int numMatricesAEnviar, int nmatriz, int nmatriz2, double[][] m1_aEnviar,
                double[][] m2_aEnviar) {
            this.ip = ip;
            this.tipoSuC = tipoSuC;
            this.nmatriz = nmatriz;
            this.nmatriz2 = nmatriz2;
            this.m2_aEnviar = m2_aEnviar;
            this.m1_aEnviar = m1_aEnviar;
            this.numMatricesAEnviar = numMatricesAEnviar;
        }
        Worker(String ip, int tipoSuC, int numMatricesAEnviar, int nmatriz, double[][] m_aEnviar) {
            this.ip = ip;
            this.tipoSuC = tipoSuC;
            this.m_aEnviar = m_aEnviar;
            this.nmatriz = nmatriz;
            this.numMatricesAEnviar = numMatricesAEnviar;
        }
        Worker(int nespera, int tipoSuC) {
            this.nespera = nespera;
            this.tipoSuC = tipoSuC;
        }
    
        public void run() {
            try {
                // Enviar
                if (tipoSuC == 1) {
                    boolean env = false;
                    for (;;) {

                        if (numMatricesAEnviar == 2) {
                            Socket conn = null;
                            System.out.println("Tratando de enviar a -> " + String.valueOf(ip) + " la matriz numero -> "
                                    + String.valueOf(nmatriz));
                            System.out.println("Tratando de enviar a -> " + String.valueOf(ip) + " la matriz numero -> "
                                    + String.valueOf(nmatriz2));
                            conn = new Socket(ip, PORT);

                            if (conn.isConnected()) {
                                DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                                out.writeInt(nmatriz);
                                out.writeInt(nmatriz2);
                                
                                for (int i = 0; i < m1_aEnviar.length; i++) {
                                    for (int j = 0; j < m1_aEnviar[0].length; j++) {
                                        out.writeDouble(m1_aEnviar[i][j]);
                                    }
                                }
                                for (int i = 0; i < m2_aEnviar.length; i++) {
                                    for (int j = 0; j < m2_aEnviar[0].length; j++) {
                                        out.writeDouble(m2_aEnviar[i][j]);
                                    }
                                }                                
                                env = true;
                                conn.close();

                            }

                        } else if (numMatricesAEnviar == 1) {
                            Socket conn = null;
                            System.out.println("Tratando de enviar a -> " + String.valueOf(ip) + " la matriz numero -> "
                                    + String.valueOf(nmatriz));
                            conn = new Socket(ip, PORT);
                            if (conn.isConnected()) {
                                DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                                out.writeInt(nmatriz);
                                
                                for (int i = 0; i < m_aEnviar.length; i++) {
                                    for (int j = 0; j < m_aEnviar[0].length; j++) {
                                        out.writeDouble(m_aEnviar[i][j]);
                                    }
                                }
                                
                                env = true;
                                conn.close();

                            }
                        }
                        if (env == true)
                            break;
                    }

                } else if (tipoSuC == 2) {// Reciviendo
                    boolean recv = false;
                    if (nespera == 0) {
                        for(;;){
                            try (ServerSocket servidor = new ServerSocket(PORT)) {
                                Socket conn = servidor.accept();
                                if (conn.isConnected()) {
                                    DataInputStream in = new DataInputStream(conn.getInputStream());
                                    int nv = in.readInt();
                                    System.out.println("Numero de matriz  1" + String.valueOf(nv));
                                    int x = 0, y = 0;
                                    if (nv >= 1 && nv <= 4) {
                                        x = A1.length;
                                        y = A1[0].length;
                                    } else {
                                        x = C1.length;
                                        y = C1[0].length;
                                    }
    
                                    System.out.println("Value of x -> " + String.valueOf(x) + " Value of y -> "
                                            + String.valueOf(y));
                                    
                                     for (int i = 0; i < x; i++) {
                                        for (int j = 0; j < y; j++) {
                                            if (nv == 1)
                                            A1[i][j] = in.readDouble();
                                            if (nv == 2)
                                            A2[i][j] = in.readDouble();
                                            if (nv == 3)
                                            B1[i][j] = in.readDouble();
                                            if (nv == 4)
                                            B2[i][j] = in.readDouble();
                                            if (nv == 5)
                                            C1[i][j] = in.readDouble();
                                            if (nv == 6)
                                            C2[i][j] = in.readDouble();
                                            if (nv == 7)
                                            C3[i][j] = in.readDouble();
                                            if (nv == 8)
                                            C4[i][j] = in.readDouble();
                                        }
                                     }
                                    
                                    recv = true;
                                    conn.close();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if(recv) break;
                        }//end ;;                        
                    } else {
                        for (;;) {
                            try (ServerSocket servidor = new ServerSocket(PORT)) {
                                Socket conn = servidor.accept();
                                if (conn.isConnected()) {
                                    DataInputStream in = new DataInputStream(conn.getInputStream());
                                    int nv = in.readInt();
                                    int nv2 = in.readInt();

                                    System.out.println("Numero de matriz  1" + String.valueOf(nv));
                                    System.out.println("Numero de matriz  2" + String.valueOf(nv2));

                                    int x = 0, y = 0, x1 = 0, y1 = 0;
                                    if (nv >= 1 && nv <= 4) {
                                        x = A1.length;
                                        y = A1[0].length;
                                    } else {
                                        x = C1.length;
                                        y = C1[0].length;
                                    }
                                    if (nv2 >= 1 && nv2 <= 4) {
                                        x1 = A1.length;
                                        y1 = A1[0].length;
                                    } else {
                                        x1 = C1.length;
                                        y1 = C1[0].length;
                                    }
                                    System.out.println("Value of x -> " + String.valueOf(x) + " Value of y -> "
                                            + String.valueOf(y));
                                    System.out.println("Value of x1 -> " + String.valueOf(x1) + " Value of y1 -> "
                                            + String.valueOf(y1));
                                    
                                     for (int i = 0; i < x; i++) {
                                        for (int j = 0; j < y; j++) {
                                            if (nv == 1)
                                            A1[i][j] = in.readDouble();
                                            if (nv == 2)
                                            A2[i][j] = in.readDouble();
                                            if (nv == 3)
                                            B1[i][j] = in.readDouble();
                                            if (nv == 4)
                                            B2[i][j] = in.readDouble();
                                            if (nv == 5)
                                            C1[i][j] = in.readDouble();
                                            if (nv == 6)
                                            C2[i][j] = in.readDouble();
                                            if (nv == 7)
                                            C3[i][j] = in.readDouble();
                                            if (nv == 8)
                                            C4[i][j] = in.readDouble();
                                        }
                                     }
                                     for (int i = 0; i < x1; i++) {
                                        for (int j = 0; j < y1; j++) {
                                            if (nv2 == 1)
                                            A1[i][j] = in.readDouble();
                                            if (nv2 == 2)
                                            A2[i][j] = in.readDouble();
                                            if (nv2 == 3)
                                            B1[i][j] = in.readDouble();
                                            if (nv2 == 4)
                                            B2[i][j] = in.readDouble();
                                            if (nv2 == 5)
                                            C1[i][j] = in.readDouble();
                                            if (nv2 == 6)
                                            C2[i][j] = in.readDouble();
                                            if (nv2 == 7)
                                            C3[i][j] = in.readDouble();
                                            if (nv2 == 8)
                                            C4[i][j] = in.readDouble();
                                        }
                                     }
                                    
                                    recv = true;
                                    conn.close();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (recv)
                                break;
                        } // ;;
                    } // End else
                } else { // en cond de espera
                    System.out.println("Algo salio mal");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 1) {
            int tipoU = Integer.parseInt(args[0]);
            if (tipoU == 0)
                funcNodo0();
            if (tipoU == 1)
                funcNodo1();
            if (tipoU == 2)
                funcNodo2();
            if (tipoU == 3)
                funcNodo3();

        } else if (args.length == 0) { // si no hay parámetros
            System.out.println("Debes ingresar el numero del nodo");
        } else {
            System.out.println("Demasiados parametros");
        }
    }

    static void funcNodo0() throws InterruptedException {

        creaMatrices();
        // matrices, modo de envio 1, num matriz 1, num matriz 2, nodo dest
        enviaMatriz(A1, B1, 1, 3, 1);
        recibeMatriz(0);
        enviaMatriz(A1, B2, 1, 4, 2);
        recibeMatriz(0);
        enviaMatriz(A2, B1, 2, 3, 3);
        recibeMatriz(0);
        C4 = multRenglon(A2, B2);
        calcChecksum();

        if(N==8){
            System.out.println("-------Imprimiendo matriz A-------");
            imprimeMatriz(A);
            System.out.println("-------Imprimiendo matriz B-------");
            imprimeMatriz(B);
            System.out.println("-------Imprimiendo matriz C-------");
            imprimeMatrizC();
        }
    }

    static void funcNodo1() throws InterruptedException {
        recibeMatriz(1);
        C1 = multRenglon(A1, B1);
        enviaMatriz(C1, null, 5, 5, 0);
    }

    static void funcNodo2() throws InterruptedException {
        recibeMatriz(2);
        C2 = multRenglon(A1, B2);
        enviaMatriz(C2, null, 6, 6, 0);
    }

    static void funcNodo3() throws InterruptedException {
        recibeMatriz(3);
        C3 = multRenglon(A2, B1);
        enviaMatriz(C3, null, 7, 7, 0);
    }

    static void imprimeMatriz(double[][] m) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                System.out.print(m[i][j] + "\t ");
            }
            System.out.println("");
        }
    }
    static void imprimeMatrizC(){
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if(i<N/2 && j<N/2)
                    System.out.print(C1[i][j] + "\t ");
                else if(i<N/2 && j>=N/2)
                    System.out.print(C2[i][j-(N/2)] + "\t ");
                else if(i>=N/2 && j<N/2)
                    System.out.print(C3[i-(N/2)][j] + "\t ");
                else if(i>=N/2 && j >= N/2)
                    System.out.print(C4[i-(N/2)][j-(N/2)] + "\t ");
            }
            System.out.println("");
        }
    }

    static void calcChecksum() {
        double ckm = 0;
        for (int i = 0; i < N / 2; i++) {
            for (int j = 0; j < N / 2; j++) {
                ckm += C1[i][j];
                ckm += C2[i][j];
                ckm += C3[i][j];
                ckm += C4[i][j];
            }
        }
        System.out.println("Checksum = " + String.valueOf(ckm));
    }

    static void creaMatrices() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                A[i][j] = i + 5 * j;
                B[i][j] = 5 * i - j;
            }
        }
        transponeMatriz();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i < N / 2) {
                    A1[i][j] = i + 5 * j;
                    A2[i][j] = i + N / 2 + 5 * j;
                }
            }
        }
        for (int i = 0; i < B.length / 2; i++) {
            for (int j = 0; j < B[0].length; j++) {
                B1[i][j] = B[i][j];
            }
        }
        for (int i = N / 2; i < B.length; i++) {
            for (int j = 0; j < B[0].length; j++) {
                B2[i - N / 2][j] = B[i][j];
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
        //imprimeMatriz(C);
    }

    static void transponeMatriz() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < i; j++) {
                double x = B[i][j];
                B[i][j] = B[j][i];
                B[j][i] = x;
            }
        }
    }

    static void enviaMatriz(double[][] m1_aEnviar, double[][] m2_aEnviar, int nmatriz1, int nmatriz2, int nodo)
            throws InterruptedException {
        try {
            if (nodo != 0) {
                // Nodo que va, modo envio, num de matrices a enviar, que matriz es, matrices 1
                // y 2
                Worker envia = new Worker(IPS[nodo], 1, 2, nmatriz1, nmatriz2, m1_aEnviar, m2_aEnviar);
                envia.start();
                envia.join();
            } else {
                Worker envia = new Worker(IPS[nodo], 1, 1, nmatriz1, m1_aEnviar);
                envia.start();
                envia.join();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread.sleep(100);
    }

    static void recibeMatriz(int nespera) throws InterruptedException {

        Worker matriz_a_esperar = new Worker(nespera, 2);
        matriz_a_esperar.start();
        matriz_a_esperar.join();
        Thread.sleep(100);

    }

    static double[][] multRenglon(double[][] m1, double[][] m2) {
        double[][] Ci = new double[N / 2][N / 2];
        for (int i = 0; i < Ci.length; i++) {
            for (int j = 0; j < Ci.length; j++) {
                Ci[i][j] = 0;
            }
        }
        for (int i = 0; i < (N / 2); i++) {
            for (int j = 0; j < (N / 2); j++) {
                for (int k = 0; k < N; k++) {
                    Ci[i][j] += m1[i][k] * m2[j][k];
                }
            }
        }
        return Ci;
    }
}
