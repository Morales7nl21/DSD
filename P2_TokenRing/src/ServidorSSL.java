import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ssl.SSLServerSocketFactory;

public class ServidorSSL {
    public static void main(String[] args) throws Exception {

        SSLServerSocketFactory socket_factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        ServerSocket socket_servidor = socket_factory.createServerSocket(50001);

        Socket conexion = socket_servidor.accept();
        DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
        DataInputStream entrada = new DataInputStream(conexion.getInputStream());
        double x = entrada.readDouble();
        System.out.println(x);
        conexion.close();
        /*
         * System.setProperty("javax.net.ssl.keyStore",
         * "src/main/certs/server/keystore_servidor.jks");
         * System.setProperty("javax.net.ssl.keyStorePassword","1234567");
         * System.setProperty("javax.net.ssl.trustStore",
         * "src/main/certs/server/serverTrustedCerts.jks");
         * System.setProperty("javax.net.ssl.trustStorePassword", "servpass");
         */
    }
}
