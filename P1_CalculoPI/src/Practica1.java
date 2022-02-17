import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

public class Practica1 {

    static class Servidor {

        static void iniciaServidor(int port) throws Exception {
            try (ServerSocket servidor = new ServerSocket(port)) {
                for (;;) {
                    Socket conexion = servidor.accept();
                    System.out.println(conexion.getPort());

                    DataOutputStream out = new DataOutputStream(conexion.getOutputStream());
                    DataInputStream in = new DataInputStream(conexion.getInputStream());
                    out.writeUTF("Hola soy " + String.valueOf(port));
                    conexion.close();
                }
            }
        }

    }

    static class Cliente {
        Socket conn = null;
        static int port = 5001;
        static Object lock = new Object();

        static class Worker extends Thread {

            int port;

            Worker(int port) {
                this.port = port;
            }

            public void run() {
                Socket conn = null;
                try {
                    synchronized (lock) {
                        conn = new Socket("localhost", port);
                        port++;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                try (DataInputStream in = new DataInputStream(conn.getInputStream());) {
                    String dev = in.readUTF();
                    System.out.println(dev);
                } catch (IOException e) {

                    e.printStackTrace();
                }

            }

        }

        public static void iniciaCliente() throws Exception {
            for (;;) {
                try {
                    Worker[] cth = new Worker[4];
                    for (int i = 0; i < 4; i++) {
                        cth[i] = new Worker(port);
                    }
                    for (int i = 0; i < 4; i++) {
                        cth[i].start();
                    }
                    for (int i = 0; i < cth.length; i++) {
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
            if (tipoU == 1) {
                Servidor.iniciaServidor(50001);
            } else if (tipoU == 2) {
                Cliente.iniciaCliente();

            }
            System.out.println("Tipo de usario:" + String.valueOf(tipoU));
        } else if (args.length == 0) { // si no hay parámetros
            System.out.println("Debes ingresar el numero del nodo");
        } else {
            System.out.println("Demaciados parametros");
        }
    }
}
