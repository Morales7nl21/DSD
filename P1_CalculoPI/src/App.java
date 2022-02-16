import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

public class App {

    static class Servidor {
        static class Worker extends Thread {
            Socket conexion;

            Worker(Socket conexion) {
                this.conexion = conexion;
            }

            public void run() {
                try {
                    DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
                    DataInputStream entrada = new DataInputStream(conexion.getInputStream());
                    int n = entrada.readInt();
                    System.out.println("Entero recivido por el cliente con el puerto: " + conexion.getPort()
                            + " el entero de: " + String.valueOf(n));
                    System.out.println("Reciviendo doubles");
                    recvDoubles(entrada);
                    salida.write("Hola".getBytes());
                    conexion.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        static void iniciaServidor(int port) throws Exception {
            try (ServerSocket servidor = new ServerSocket(port)) {
                for (;;) {
                    Socket conexion = servidor.accept();
                    System.out.println(conexion.getPort());
                    Worker w = new Worker(conexion);
                    w.start();
                }
            }
        }

        static void recvDoubles(DataInputStream in) throws IOException {
            long start = System.currentTimeMillis();
            for (int i = 1; i <= 10; i++) {
                System.out.println(in.readDouble());
            }
            long end = System.currentTimeMillis();
            System.out.println("Total: " + (end - start) + " ms");
        }
    }

    static class Cliente {
        Socket conn = null;

        public static void iniciaCliente() throws Exception {
            try {
                Socket conn = null;

                for (;;) {
                    try {
                        conn = new Socket("localhost", 50001);
                        break;
                    } catch (Exception ex) {
                        Thread.sleep(3000);
                    }
                }

                DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                DataInputStream in = new DataInputStream(conn.getInputStream());
                out.writeInt(123123123);
                sendDoubles(out);
                byte[] buffer = new byte[4];
                read(in, buffer, 0, 4);
                System.out.println(new String(buffer, "UTF-8"));
                conn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        static void sendDoubles(DataOutputStream out) throws IOException {
            long start = System.currentTimeMillis();
            for (int i = 1; i <= 10; i++) {
                out.writeDouble((double) i);
            }
            long end = System.currentTimeMillis();
            System.out.println("Total: " + (end - start) + " ms");
        }

        static void sendBuffer(DataOutputStream out) throws IOException {
            ByteBuffer b = ByteBuffer.allocate(10000 * 8);

            for (int i = 1; i <= 10000; i++) {
                b.putDouble((double) i);
            }

            long start = System.currentTimeMillis();

            out.write(b.array());

            long end = System.currentTimeMillis();

            System.out.println("Total: " + (end - start) + " ms");
        }

        public static void read(DataInputStream f, byte[] b, int posicion, int longitud) throws Exception {
            while (longitud > 0) {
                int n = f.read(b, posicion, longitud);
                posicion += n;
                longitud -= n;
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
