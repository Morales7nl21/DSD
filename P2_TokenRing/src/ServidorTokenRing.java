
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocketFactory;

public class ServidorTokenRing {
    public static Short Token = 0;
    public static int verIni = 0;

    public static void main(String[] args) throws IOException, InterruptedException {
        System.setProperty("javax.net.ssl.trustStore", "keystore_Ring.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "1234567");
        System.setProperty("javax.net.ssl.keyStore", "keystore_Ring.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "1234567");
        if (args.length == 1) {
            int tipoU = Integer.parseInt(args[0]);

            if (verIni == 0 && tipoU == 0) {
                mandarToken(tipoU, Token);
                Token++;
                verIni++;
            }
            System.out.println("Tipo de usuario: " + String.valueOf(tipoU));
            SSLServerSocketFactory socket_factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            ServerSocket socket_servidor = socket_factory.createServerSocket(50000 + tipoU);
            Socket conexion = null;
            for (;;) {

                conexion = socket_servidor.accept();
                if (conexion.isConnected() == true) {
                    DataInputStream entrada = new DataInputStream(conexion.getInputStream());
                    Short x = (short) entrada.readUnsignedShort();
                    System.out.println(x);
                    conexion.close();
                }

                Thread.sleep(1000);
            }

        } else if (args.length == 0) { // si no hay parámetros
            System.out.println("Debes ingresar el numero del nodo");
        } else {
            System.out.println("Demasiados parametros");
        }

    }

    private static void mandarToken(int tipoU, Short token2) throws IOException, InterruptedException {

        SSLSocketFactory cl = (SSLSocketFactory) SSLSocketFactory.getDefault();
        Socket con = cl.createSocket("localhost", 50000 + (tipoU + 1) % 6);
        DataOutputStream s = new DataOutputStream(con.getOutputStream());
        s.writeShort(token2);
        Thread.sleep(1000);
        con.close();

    }
}
