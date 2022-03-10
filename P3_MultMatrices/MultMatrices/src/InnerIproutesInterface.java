public interface InnerIproutesInterface {
    final int PORT = 50000;
    final String[] IPS= {"20.25.58.25","20.25.74.247","20.127.115.244","13.68.146.127"}; 
    //int [] PORTS= {50001,50002,50003,5004}; 
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
