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
        double [][] m_aEnviar;
        int nmatriz;
        int tipoSuC = 0;
        int nespera;
        Worker(String ip, int tipoSuC, int nmatriz, double [][] m_aEnviar) {
            this.ip = ip;
            this.tipoSuC = tipoSuC;
        }
        Worker(int nespera, int tipoSuC){
            this.nespera = nespera;
            this.tipoSuC = tipoSuC;
        }

        public void run() {

            try {
                //Enviar
                if(tipoSuC==1){
                    boolean env = false;
                    for(;;){
                        Socket conn = null;
                        System.out.println("Tratando de enviar a -> " + String.valueOf(ip) + " la matriz numero -> " + String.valueOf(nmatriz));
                        conn = new Socket(ip, PORT);
                        if(conn.isConnected()){                    
                            DataOutputStream out = new DataOutputStream(conn.getOutputStream());    
                            out.writeInt(nmatriz);
                            /*            
                            for (int i = 0; i < m_aEnviar.length; i++) {
                                for (int j = 0; j < m_aEnviar[0].length; j++) {
                                    out.writeDouble(m_aEnviar[i][j]);
                                }
                            }
                            */
                            env = true;
                            conn.close(); 

                        }
                        if(env == true) break;
                    }
                    
                }else if(tipoSuC == 2){//Reciviendo   
                    boolean recv = false;
                    for(;;){
                        try{
                            ServerSocket servidor = new ServerSocket(PORT);
                            Socket conn = servidor.accept();
                            if (conn.isConnected()) {
                                DataInputStream in = new DataInputStream(conn.getInputStream());
                                int nv = in.readInt();
                                System.out.println("Numero de matriz " + String.valueOf(nv));
                                int x = 0, y = 0;
                                if (nv == 1) {
                                    x = A1.length;
                                    y = A1[0].length;
                                } else if (nv == 2) {
                                    x = A2.length;
                                    y = A2[0].length;
                                } else if (nv == 3) {
                                    x = B1.length;
                                    y = B1[0].length;
                                } else if (nv == 4) {
                                    x = B2.length;
                                    y = B2[0].length;
                                } else {
                                    x = C1.length;
                                    y = C1[0].length;
                                }
                                System.out.println("Value of x -> " + String.valueOf(x) + " Value of y -> " + String.valueOf(y));
                                /*
                                 * for (int i = 0; i < x; i++) {
                                 * for (int j = 0; j < y; j++) {
                                 * if (nv == 1)
                                 * A1[i][j] = in.readDouble();
                                 * if (nv == 2)
                                 * A2[i][j] = in.readDouble();
                                 * if (nv == 3)
                                 * B1[i][j] = in.readDouble();
                                 * if (nv == 4)
                                 * B2[i][j] = in.readDouble();
                                 * if (nv == 5)
                                 * C1[i][j] = in.readDouble();
                                 * if (nv == 6)
                                 * C2[i][j] = in.readDouble();
                                 * if (nv == 7)
                                 * C3[i][j] = in.readDouble();
                                 * if (nv == 8)
                                 * C4[i][j] = in.readDouble();
                                 * }
                                 * }
                                 */     
                                recv = true;                       
                                conn.close();
                            }        
                        } catch (Exception e) {
                            e.printStackTrace();
                        } 
                        if(recv == true) break;
                    }
                    
                                
                }
                else{
                    System.out.println("Algo salio mal");
                }
                                

            }catch(Exception e){
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
            enviaMatriz(A1, 1, 1);
            enviaMatriz(B1, 1, 3);
            
            /*
            enviaMatriz(A1, 2, 1);
            enviaMatriz(B2, 2, 4);
            
            enviaMatriz(A2, 3, 2);
            enviaMatriz(B1, 3, 3);
            
            C4 = multRenglon(A2, B2);
            recibeMatriz(0);
            calcChecksum();
            System.out.println("Imprimiendo C4");
            if (N == 8) {
            imprimeMatriz(A);
            imprimeMatriz(B);
            imprimeMatriz(C1);
            imprimeMatriz(C2);
            imprimeMatriz(C3);
            imprimeMatriz(C4);
            }
            */
        }

        static void funcNodo1() throws InterruptedException {
            recibeMatriz(1);
            recibeMatriz(1);
            C1 = multRenglon(A1, B1);
            enviaMatriz(C1, 0, 5);
        }

        static void funcNodo2() throws InterruptedException {
            recibeMatriz(2);
            C2 = multRenglon(A1, B2);
            enviaMatriz(C2, 0, 6);
        }

        static void funcNodo3() throws InterruptedException {
            recibeMatriz(3);
            C3 = multRenglon(A2, B1);
            enviaMatriz(C3, 0, 7);
        }

        static void imprimeMatriz(double[][] m) {
            for (int i = 0; i < m.length; i++) {
                for (int j = 0; j < m[0].length; j++) {
                    System.out.print(m[i][j] + "\t ");
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
            imprimeMatriz(C);
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

    static void enviaMatriz(double[][] m_aEnviar, int nodo, int nmatriz) throws InterruptedException {
        
        for(;;){ //Salir del bucle
            try {
                Worker envia = new Worker(IPS[nodo], 1, nmatriz, m_aEnviar);
                envia.start();
                envia.join();   
                
                break;             
            }
            catch (Exception e) {
                e.printStackTrace();
            }  
            Thread.sleep(100);         
        }

    }

    static void recibeMatriz(int nespera) throws InterruptedException {
        
        
        for (;;) {
            try {                   
                    Worker matriz_a_esperar = new Worker(nespera,2);
                    matriz_a_esperar.start();
                    matriz_a_esperar.join();
                    break;                
            } catch (Exception e) {
                e.printStackTrace();
                Thread.sleep(100);
            }
        }             
            
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
