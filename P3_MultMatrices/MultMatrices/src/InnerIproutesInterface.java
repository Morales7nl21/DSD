public interface InnerIproutesInterface {
    final int PORT = 50000;
    final String ip_0= "192.168.0.1"; 
    final String ip_1= "192.168.0.1"; 
    final String ip_2= "192.168.0.1"; 
    final String ip_3= "192.168.0.1"; 
    final int N = 8;
    static double[][] A = new double[N][N];
    static double[][] B = new double[N][N];    
    static double[][] C1 = new double[N/2][N/2];
    static double[][] C2 = new double[N/2][N/2];
    static double[][] C3 = new double[N/2][N/2];
    static double[][] C4 = new double[N/2][N/2];
    static double [][] A1 = new double[N/2][N];
    static double [][] A2 = new double[N/2][N];
    static double [][] B1 = new double[N][N/2];
    static double [][] B2 = new double[N][N/2];
    static double [][] B1T = new double[N/2][N];
    static double [][] B2T = new double[N/2][N];

}
