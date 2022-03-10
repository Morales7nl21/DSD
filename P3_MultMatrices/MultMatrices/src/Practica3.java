import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Practica3 implements InnerIproutesInterface {
    static double[][] C1 = new double[N / 2][N / 2];
    static double[][] C2 = new double[N / 2][N / 2];
    static double[][] C3 = new double[N / 2][N / 2];
    static double[][] C4 = new double[N / 2][N / 2];

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

    static void funcNodo0() {

        creaMatrices();
        System.out.println("Imprimiendo A");
        imprimeMatriz(A);
        System.out.println("Imprimiendo B1");
        imprimeMatriz(B1);
        System.out.println("Imprimiendo B2");
        imprimeMatriz(B2);
        System.out.println("Imprimiendo A1");
        imprimeMatriz(A1);
        System.out.println("Imprimiendo A2");
        imprimeMatriz(A2);
        System.out.println("Imprimiendo B2T");
        C4 = multRenglon(A2, B2);
        System.out.println("Imprimiendo C4");
        imprimeMatriz(C4);

    }

    static void funcNodo1() {
        recibeMatriz(1);
        C1 = multRenglon(A1, B1);
        enviaMatriz(C1, 0, 5);
    }

    static void funcNodo2() {
        recibeMatriz(2);
        C2 = multRenglon(A1, B2);
        enviaMatriz(C2, 0, 6);
    }

    static void funcNodo3() {
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
            for (int j = 0; j < N; j++) {
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
        System.out.println("-------------------B");
        imprimeMatriz(B);
        System.out.println("-------------------");
        transponeMatriz();
        System.out.println("-------------------BT");
        imprimeMatriz(B);
        System.out.println("-------------------");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i < N / 2) {
                    A1[i][j] = i + 5 * j;
                    A2[i][j] = i + N / 2 + 5 * j;
                }
            }
        }
        for (int i = 0; i < B.length/2; i++) {
            for (int j = 0; j < B[0].length; j++) {
                B1[i][j] = B[i][j];
            }
        }
        for (int i = N/2; i < B.length; i++) {
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
                cont++;
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (nespera == 0 && cont == 3)
                break;
            if (nespera == 1 && cont == 2)
                break;
            if (nespera == 2 && cont == 2)
                break;
            if (nespera == 3 && cont == 2)
                break;
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
