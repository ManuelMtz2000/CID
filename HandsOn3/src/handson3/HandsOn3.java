package handson3;

import static java.lang.Math.pow;
import java.util.ArrayList;
import java.util.Scanner;

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

final class Modelo {
    // y = b0 + b1x1
    ArrayList<Invest> data = new ArrayList();
    float beta0, beta1;
    
    Modelo(){
        LoadDataSet();
        Show();
        LoadBetas();
    }
    
    void LoadDataSet(){
        float[] sales = {651,762,856,1063,1190,1298,1421,1440,1518};
        float[] ad = {23,26,30,34,43,48,52,57,58};
        for(int i=0; i < 9; i++){
            Invest inv = new Invest();
            inv.setYear(i + 1);
            inv.setSales(sales[i]);
            inv.setAd(ad[i]);
            data.add(inv);
        }
    }
    
    void LoadBetas(){
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
    
    void Show(){
        System.out.println("Year \t Sales \t\t Advertising");
        for(Invest i :data){
            System.out.println(i.getYear() + " \t "
            + i.getSales() + "\t\t " + i.getAd());
        }
    }
    
    void Add(float x){
        Invest i = Calculate(x);
        data.add(i);
        Show();
    }
    
    Invest Calculate(float x){
        Invest i = new Invest();
        System.out.println("Calculating... y = " + beta0 + " + " + beta1 + "(" + x + ")");
        float y = beta0 + (beta1 * x);
        i.setAd(x);
        i.setSales(y);
        i.setYear(data.size() + 1);
        return i;
    }
}

public class HandsOn3 {

    public static void main(String[] args) {
        Modelo m = new Modelo();
        Scanner sc = new Scanner(System.in);
        while(true){
            float n;
            System.out.println("Advertising to predict: ");
            n = sc.nextFloat();
            m.Add(n);
        }
    }
    
}

