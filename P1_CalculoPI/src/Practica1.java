import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Practica1 {

    static class Servidor {

        static void iniciaServidor(int port) throws Exception {
            try (ServerSocket servidor = new ServerSocket(port)) {
                for (;;) {
                    System.out.println("Esperando");
                    Socket conexion = servidor.accept();
                    System.out.println(conexion.getPort());
                    int valServ = port - 50000;
                    double resDouble = 0;
                    DataOutputStream out = new DataOutputStream(conexion.getOutputStream());
                    // DataInputStream in = new DataInputStream(conexion.getInputStream());
                    out.writeUTF("Hola soy " + String.valueOf(port));
                    System.out.println("Valor de servidor (i) = " + String.valueOf(valServ));
                    for (int i = 0; i <= 999999; i++) {
                        resDouble += (4.0 / (8 * i + 2 * (valServ - 2) + 3));
                    }
                    if (valServ % 2 == 0) {// Si es par
                        resDouble *= -1;
                    }

                    out.writeDouble(resDouble);
                    conexion.close();
                }
            }
        }

    }

    static class Cliente {

        static Object lock = new Object();
        static int port = 50001;

        static class Worker extends Thread {
            int port;
            static double pi;
            static int cont = 0;

            Worker(int port) {
                this.port = port;
            }

            public void run() {

                try {
                    Socket conn = null;
                    conn = new Socket("localhost", port);
                    DataInputStream in = new DataInputStream(conn.getInputStream());
                    synchronized (lock) {
                        String dev = in.readUTF();
                        System.out.println(dev);
                        pi += in.readDouble();
                        cont++;
                        if (cont == 4) {
                            System.out.println(pi);
                        }
                    }
                    conn.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }

        public static void iniciaCliente() throws Exception {

            for (;;) {
                try {
                    Worker[] cth = new Worker[4];
                    for (int i = 0; i < 4; i++) {
                        cth[i] = new Worker(port++);
                    }
                    for (int i = 0; i < 4; i++) {
                        cth[i].start();
                    }
                    for (int i = 0; i < 4; i++) {
                        cth[i].join();
                    }
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    Thread.sleep(3000);
                }
            }
        }

    }

    public static void main(String[] args) throws Exception {
        if (args.length == 1) { // si hay más de 1 parámetro
            int tipoU = Integer.parseInt(args[0]);
            if (tipoU == 1)
                Servidor.iniciaServidor(50001);
            else if (tipoU == 2)
                Servidor.iniciaServidor(50002);
            else if (tipoU == 3)
                Servidor.iniciaServidor(50003);
            else if (tipoU == 4)
                Servidor.iniciaServidor(50004);
            else if (tipoU == 0)
                Cliente.iniciaCliente();

            System.out.println("Tipo de usuario: " + String.valueOf(tipoU));
        } else if (args.length == 0) { // si no hay parámetros
            System.out.println("Debes ingresar el numero del nodo");
        } else {
            System.out.println("Demasiados parametros");
        }
    }

}
