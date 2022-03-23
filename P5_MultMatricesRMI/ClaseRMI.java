import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClaseRMI extends UnicastRemoteObject implements InterfaceRMI {
    public ClaseRMI() throws RemoteException {
        /*
         * Invoca al constructor de la clase padre (superclase), para incializar campos
         * que heredas de la superclase
         */
        super();

    }
    
    public double[][] multiplica_matrices(double[][] A, double[][] B, int N) {
        double[][] C = new double[N / 4][N / 4];
        for (int i = 0; i < N / 4; i++)
            for (int j = 0; j < N / 4; j++)
                for (int k = 0; k < N; k++)
                    C[i][j] += A[i][k] * B[j][k];
        return C;
    }
}