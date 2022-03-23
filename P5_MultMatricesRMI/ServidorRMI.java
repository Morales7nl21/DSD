import java.rmi.Naming;

public class ServidorRMI {
    public static void main(String[] args) throws Exception {
        /* rmi://[IP]:[Puerto]/[Nombre] */
        // ip donde ejecuta el servidor, normalmente 100pre es localhost
        String url = "rmi://localhost/matriz";
        // crea instancia de la clase
        ClaseRMI obj = new ClaseRMI();
        // registra la instancia en rmiregistry y se queda bloqueado
        Naming.rebind(url, obj);
    }
}