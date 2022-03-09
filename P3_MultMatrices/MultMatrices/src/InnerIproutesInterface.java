public interface InnerIproutesInterface {
    final int PORT = 50000;
    final String[] ips= {"192.168.0.1","192.168.0.1","192.168.0.1","192.168.0.1"}; 
    
    final int N = 8;
     double[][] A = new double[N][N];
     double[][] B = new double[N][N];    
     double[][] C1 = new double[N/2][N/2];
     double[][] C2 = new double[N/2][N/2];
     double[][] C3 = new double[N/2][N/2];
     double[][] C4 = new double[N/2][N/2];
     double [][] A1 = new double[N/2][N];
     double [][] A2 = new double[N/2][N];
     double [][] B1 = new double[N][N/2];
     double [][] B2 = new double[N][N/2];
     double [][] B1T = new double[N/2][N];
     double [][] B2T = new double[N/2][N];

}
