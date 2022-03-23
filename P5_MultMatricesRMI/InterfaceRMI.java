import java.rmi.RemoteException;
import java.rmi.Remote;

public interface InterfaceRMI extends Remote {
    /**
     * Multiplica las matrices A y B de tamaño N
     * @param A matriz
     * @param B matriz
     * @param N tamaño de matrices
     * @return A*B=C[N/4][N/4]
     * @throws RemoteException
     */
    public double[][] multiplica_matrices(double[][] A, double [][] B, int N) throws RemoteException;
}