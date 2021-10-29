package handson4;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import static java.lang.Math.pow;
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

public class HandsOn4 extends Agent{

    public void setup(){
        addBehaviour(new Model());
    }
    
    private class Model extends OneShotBehaviour {
        ArrayList<Invest> data = new ArrayList();
        float beta0 = 0, beta1 = 0;
        public void action() {
            String x = "";
            x = JOptionPane.showInputDialog(null, "Advertising to predict: ", "Calcular", 1);
            if(x == null || x.equals("")){
                System.out.println("Agent can't continue...");
                onEnd();
            }
            else {
                float ad = Float.parseFloat(x);
                System.out.println("Agent " + getLocalName() + " started");
                LoadDataSet();
                LoadBetas();
                Calculate(ad);
            }
        }
        
        public void LoadDataSet(){
            float[] sales = {651,762,856,1063,1190,1298,1421,1440,1518};
            float[] ad = {23,26,30,34,43,48,52,57,58};
            for(int i = 0; i < 9; i++){
                Invest inv = new Invest();
                inv.setAd(ad[i]);
                inv.setSales(sales[i]);
                inv.setYear(i + 1);
                data.add(inv);
            }
        }
        
        public void LoadBetas(){
            float accuY = 0, accuX = 0, accuXY = 0, accuX2 = 0;
            for(int i = 0; i < data.size(); i++){
                accuY += data.get(i).getSales();
                accuX += data.get(i).getAd();
                accuXY += data.get(i).getAd() * data.get(i).getSales();
                accuX2 += pow(data.get(i).ad,2);
            }
            beta1 = ((data.size() * accuXY) - (accuX * accuY))/((data.size()*accuX2) - (accuX * accuX));
            beta0 = (accuY - (beta1 * accuX))/data.size();
            System.out.println("Beta 0 = " + beta0 + "\tBeta 1 = " + beta1);
        }
        
        public void Calculate(float x){
            System.out.println("Calculating... y = " + beta0 + " + " + beta1 + "(" + x + ")");
            float y = beta0 + (beta1 * x);
            System.out.println("Result: " + y);
            System.out.println("Advertising: " + x + " Sales: " + y);
        }
        
        public int onEnd(){
            myAgent.doDelete();
            return super.onEnd();
        }
        
    }
    
}
