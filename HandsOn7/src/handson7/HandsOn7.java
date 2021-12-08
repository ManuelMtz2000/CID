package handson7;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

class Matrix{
    public double[][] m;
    
    Matrix(int i, int j){
        m = new double[i][j];
    }
    
    public void newX(int i, int j, double x){
        m[i][j] = x;
    }
    
    public double getXij(int i, int j){
        return m[i][j];
    }
    
    public double[] getXi(int i){
        return m[i];
    }
    
    public void matrixView(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                System.out.print(m[i][j] + "  ");
            }
            System.out.println("");
        }
    }
}

public class HandsOn7 extends Agent{
    
    double[] x1 = {1.0,1.0,1.0};
    double[] x2 = {1.0,4.0,2.0};
    double[] x3 = {1.0,2.0,4.0};
    double[] w = {0.0,0.0,0.0};
    double[] y = {0.0,1.0,1.0};
    double alpha = 0.1;
    Matrix m = new Matrix(3,x1.length);

    public void setup(){
        addBehaviour(new Load());
        addBehaviour(new Calculus());
    }
    
    private class Load extends OneShotBehaviour {
        
        public void action(){
            LoadX();
        }
        
        public void LoadX(){
            for(int i = 0; i < x1.length; i++){
                m.newX(0, i, x1[i]);
                m.newX(1, i, x2[i]);
                m.newX(2, i, x3[i]);
            }
            m.matrixView();
        }
    }
    
    private class Calculus extends OneShotBehaviour {
        public void action(){
            double aux1,aux2,aux3;
            for(int i = 0; i < 100; i++){
                aux1 = calculusW(0);
                aux2 = calculusW(1);
                aux3 = calculusW(2);
                w[0] = aux1;
                w[1] = aux2;
                w[2] = aux3;
                System.out.println((i + 1) + "W(" + w[0] + ", " + w[1] + ", " + w[2] + ")");
            }
        }
        
        public double calculusW(int index){
            double sum = 0, res = 0;
            for(int i = 0; i < 3; i++){
                sum += (sig_funct(m.getXi(i)) - y[i]) * m.getXij(index, i);
            }
            res = w[index] - (alpha * sum);
            return res;
        }
        
        public double sig_funct(double[] x){
            return (1/(1+Math.exp(sig_Ws(x))));
        }
        
        public double sig_Ws(double[] x){
            double res = 0;
            for(int i = 0; i < x.length; i++){
                res += x[i] * w[i];
            }
            return res * -1;
        }
        
        public int onEnd(){
            myAgent.doDelete();
            return super.onEnd();
        }
    }
}
