public interface InnerIproutesInterface {
    final int PORT = 50000;
    final String[] ips= {"192.168.0.1","192.168.0.1","192.168.0.1","192.168.0.1"}; 
    
    final int N = 8;
     static double[][] A = new double[N][N];
     static double[][] B = new double[N][N];    
    
     double [][] A1 = new double[N/2][N];
     double [][] A2 = new double[N/2][N];
     double [][] B1 = new double[N/2][N];
     double [][] B2 = new double[N/2][N];

     
     double [][] B1T = new double[N/2][N];
     double [][] B2T = new double[N/2][N];

}
