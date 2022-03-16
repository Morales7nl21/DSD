import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.util.Scanner;

public class Chat {
    static class Worker extends Thread {

        public void run() {
            for (;;) {
                try {
                    MulticastSocket socket = new MulticastSocket(50000);
                    InetSocketAddress grupo = new InetSocketAddress(InetAddress.getByName("230.0.0.0"), 50000);
                    NetworkInterface netInter = NetworkInterface.getByName("em1");
                    socket.joinGroup(grupo, netInter);
                    byte[] buffer = recibe_mensaje_multicast(socket, 70);
                    System.out.println(new String(buffer, "windows-1252"));
                    System.out.println("Ingrese el mensaje a enviar: ");

                    socket.leaveGroup(grupo, netInter);
                    socket.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    static void enviar_mensaje_multicast(byte[] buffer, String ip, int puerto) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        socket.send(new DatagramPacket(buffer, buffer.length, InetAddress.getByName(ip), puerto));
        socket.close();
    }

    static byte[] recibe_mensaje_multicast(MulticastSocket socket, int longitud_mensaje) throws IOException {
        byte[] buffer = new byte[longitud_mensaje];
        DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);
        socket.receive(paquete);

        return paquete.getData();
    }

    public static void main(String[] args) throws IOException {
        System.setProperty("java.net.preferIPv4Stack", "true");
        Worker w = new Worker();
        w.start();
        String mensaje;
        String nombre = args[0];
        byte[] enviar;
        Scanner console = new Scanner(System.in);

        System.out.println("Ingrese el mensaje a enviar: ");
        for (;;) {
            mensaje = console.nextLine();
            enviar = (nombre + " dice: " + mensaje).getBytes();
            enviar_mensaje_multicast(enviar, "230.0.0.0", 50000);
        }
    }

}