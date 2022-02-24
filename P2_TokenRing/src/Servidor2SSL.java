import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocketFactory;

public class Servidor2SSL {
    public static void main(String[] args) throws Exception, InterruptedException {

        SSLServerSocketFactory socket_factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        ServerSocket socket_servidor = socket_factory.createServerSocket(50000);
        Socket conexion = socket_servidor.accept();
        DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
        DataInputStream entrada = new DataInputStream(conexion.getInputStream());
        double x = entrada.readDouble();
        System.out.println(x);
        conexion.close();

        Thread.sleep(1000);
        SSLSocketFactory cl = (SSLSocketFactory) SSLSocketFactory.getDefault();
        Socket con = cl.createSocket("localhost", 50001);
        DataOutputStream s = new DataOutputStream(con.getOutputStream());
        DataInputStream e = new DataInputStream(con.getInputStream());
        s.writeDouble(3.14167198);
        Thread.sleep(1000);
        con.close();
    }
}
