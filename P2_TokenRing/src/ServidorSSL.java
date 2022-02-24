import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocketFactory;

public class ServidorSSL {
    public static void main(String[] args) throws Exception {
        int port = 50000;
        SSLServerSocketFactory socket_factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        ServerSocket socket_servidor = socket_factory.createServerSocket(port);
        Socket conexion = socket_servidor.accept();
        DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
        DataInputStream entrada = new DataInputStream(conexion.getInputStream());

        double x = entrada.readDouble();
        System.out.println(x);
        conexion.close();
        /*
         * System.setProperty("javax.net.ssl.keyStore",
         * "src/main/certs/server/serverKey.jks");
         * System.setProperty("javax.net.ssl.keyStorePassword","servpass");
         * System.setProperty("javax.net.ssl.trustStore",
         * "src/main/certs/server/serverTrustedCerts.jks");
         * System.setProperty("javax.net.ssl.trustStorePassword", "servpass");
         */
    }
}
