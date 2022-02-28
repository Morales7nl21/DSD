import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.net.ssl.SSLServerSocketFactory;

public class Token {
    static DataOutputStream salida;
    static DataInputStream entrada;
    static short token;
    static int nodo;

    static void enviar(int nodo, short token) throws UnknownHostException, IOException, InterruptedException {
        // Realizamos reintentos
        Socket conexion = null;
        for (;;) {
            try {
                System.out.println("Intentando conectar");
                conexion = new Socket("localhost", (50000 + nodo));
                break;
            } catch (Exception e) {
                Thread.sleep(100);
            }
        }

        salida = new DataOutputStream(conexion.getOutputStream());
        salida.writeInt(token);
        conexion.close();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        SSLServerSocketFactory socket_factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        ServerSocket socket_servidor = socket_factory.createServerSocket(50000);
        nodo = Integer.parseInt(args[0]);
        if (nodo == 0) {
            token = 1;
            enviar(1, token);
        } else {
            for (;;) {
                System.out.println("Iniciando");
                Socket conexion = socket_servidor.accept();
                entrada = new DataInputStream(conexion.getInputStream());
                short token = entrada.readShort();
                token++;
                if (nodo == 0 && token >= 500)
                    System.exit(0);
                System.out.println(token);
                enviar((nodo + 1) % 6, token);
            }
        }
        // for (;;) {
        // System.out.println("Iniciando");
        // Socket conexion = socket_servidor.accept();
        // entrada = new DataInputStream(conexion.getInputStream());
        // short token = entrada.readShort();
        // token++;
        // if (nodo == 0 && token >= 500)
        // System.exit(0);
        // System.out.println(token);
        // enviar((nodo + 1) % 6, token);
        // }

    }

}
