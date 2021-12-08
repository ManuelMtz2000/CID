package handson5;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import java.util.ArrayList;
import javax.swing.JOptionPane;

class Invest{
    
    int year;
    float ad, sales;
    
    Invest(){
        year = 0;
        ad = 0;
        sales = 0;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getAd() {
        return ad;
    }

    public void setAd(float ad) {
        this.ad = ad;
    }

    public float getSales() {
        return sales;
    }

    public void setSales(float sales) {
        this.sales = sales;
    }
    
}

class Betas {
    double beta0;
    double beta1;
    
    Betas(){
        beta0 = 0;
        beta1 = 0;
    }

    public double getBeta0() {
        return beta0;
    }

    public void setBeta0(double beta0) {
        this.beta0 = beta0;
    }

    public double getBeta1() {
        return beta1;
    }

    public void setBeta1(double beta1) {
        this.beta1 = beta1;
    }
    
}

public class HandsOn5 extends Agent{
    double alpha = 0.0005;
    double error, lastError;
    Betas b = new Betas();
    public void setup(){
        addBehaviour(new Model());
    }
    
    private class Model extends OneShotBehaviour {
        ArrayList<Invest> data = new ArrayList();
        public void action() {
            String x = "";
            x = JOptionPane.showInputDialog(null, "Advertising to predict: ", "Calcular", 1);
            if(x == null || x.equals(""))
                System.out.println("Agent can't continue...");
            else {
                float ad = Float.parseFloat(x);
                System.out.println("Agent " + getLocalName() + " started");
                LoadDataSet();
                LoadBetas();
                Calculate(ad, b);
            }
        }
        
        public void LoadDataSet(){
            float[] sales = {2,4,6,8,10,12,14,16,18};
            float[] ad = {1,2,3,4,5,6,7,8,9,10};
            for(int i = 0; i < 9; i++){
                Invest inv = new Invest();
                inv.setAd(ad[i]);
                inv.setSales(sales[i]);
                inv.setYear(i + 1);
                data.add(inv);
            }
        }
        
        public void LoadBetas(){
            while(Error() > 0.0001){
                b.setBeta0(b.getBeta0()-(alpha*derBeta0(b.getBeta0(),b.getBeta1())));
                b.setBeta1(b.getBeta1()-(alpha*derBeta1(b.getBeta0(),b.getBeta1())));
            }
        }
        
        public double Error(){
            double sumerror = 0.0;
            for(Invest i :data){
                sumerror += (i.getSales() - (b.getBeta0() + (b.getBeta1() * i.getAd()))) * (i.getSales() - (b.getBeta0() + (b.getBeta1() * i.getAd())));
            }
            error = (1.0/9) * sumerror;
            double aux = Math.abs(lastError - error);
            lastError = error;
            System.out.println("Error: " + error);
            System.out.println("AUX: " + aux);
            return aux;
        }
        
        public double derBeta0(double beta0, double beta1){
            double sum = 0, der = 0;
            for(Invest i :data){
                sum += i.getSales() - (beta0 + beta1 * i.getAd());
            }
            der = (-2.0/9)*sum;
            return der;
        }
        
        public double derBeta1(double beta0, double beta1){
            double sum = 0, der = 0;
            for(Invest i : data){
                sum += i.getAd() * (i.getSales() - (beta0 + beta1 * i.getAd()));
            }
            der = (-2.0/9)*sum;
            return der;
        }
        
        public void Calculate(float x, Betas b){
            System.out.println("Calculating... y = " + b.getBeta0() + " + " + b.getBeta1() + "(" + x + ")");
            double y = b.getBeta0() + (b.getBeta1() * x);
            System.out.println("Result: " + y);
            System.out.println("Advertising: " + x + " Sales: " + y);
        }
        
        public int onEnd(){
            myAgent.doDelete();
            return super.onEnd();
        }
        
    }
}
