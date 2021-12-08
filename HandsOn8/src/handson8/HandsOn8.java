package handson8;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import java.util.ArrayList;

class Data {
    int id;
    double age,weight,height;

    Data(){
        id = 0;
        height = 0;
        age = 0;
        weight = 0;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}

class EuclidianData {
    double root;
    Data data;
    
    EuclidianData(double root, Data data){
        this.root = root;
        this.data = data;
    }
    
    public double getRoot(){
        return this.root;
    }
    
    public Data getData(){
        return this.data;
    }
}

public class HandsOn8 extends Agent{
    
    ArrayList<Data> d = new ArrayList();
    ArrayList<Data> aux = new ArrayList();
    
    public void setup(){
        addBehaviour(new LoadData());
        addBehaviour(new Euclidean());
    }
    
    private class LoadData extends OneShotBehaviour{
            
        double[] height = {5,5.11,5.6,5.9,4.8,5.8,5.3,5.8,5.5,5.6};
        int[] age = {45,26,30,34,40,36,19,28,23,32};
        int[] weight = {77,47,55,59,72,60,40,60,45,58};
            
        public void action() {
            Loading();
            PrintData();
        }
        
        public void Loading(){
            for(int i = 0; i < 10; i++){
                Data data = new Data();
                data.setId(i+1);
                data.setAge(age[i]);
                data.setHeight(height[i]);
                data.setWeight(weight[i]);
                d.add(data);
            }
            System.out.println("The data was loaded correctly!");
        }
        
        public void PrintData(){
            System.out.println("Data loaded:");
            for(Data data :d){
                System.out.println(data.getId() + " - " + data.getHeight() + " - " + 
                        data.getAge() + " - " + data.getWeight());
            }
        }
        
    } 
    
    private class Euclidean extends OneShotBehaviour {
        public void action(){
            Data x = new Data();
            int k = 3;
            x.setAge(38);
            x.setHeight(5.5);
            ArrayList<EuclidianData> suma = Sumatoria(x);
            for(EuclidianData e :suma){
                System.out.println(e.getRoot() + " - " + e.getData().getId());
            }
            newPoint(k,x,suma);
        }
        
        public ArrayList Sumatoria(Data x){
            double m1 = 0,m2 = 0,de1 = 0, de2 = 0;
            for(Data e1: d){
                m1 += e1.getAge();
                m2 += e1.getHeight();
            }
            m1 = m1/d.size();
            m2 = m2/d.size();
            
            for(Data e1: d){
                de1 += Math.pow(e1.getAge() - m1,2);
                de2 += Math.pow(e1.getHeight() - m2,2);
            }
            de1 = Math.sqrt(de1/d.size());
            de2 = Math.sqrt(de2/d.size());
            
            for(Data e1: d){
                e1.setAge((e1.getAge() - m1) / de1);
            }
            for(Data e1: d){
                e1.setHeight((e1.getHeight() - m2) / de2);
            }
            ArrayList<EuclidianData> nearest = new ArrayList();
            EuclidianData aux2;
            double age, height, root;
            for(int i = 0; i < d.size(); i++){
                age = Math.pow((d.get(i).getAge() - x.getAge()), 2);
                height = Math.pow((d.get(i).getHeight() - x.getHeight()), 2);
                root = Math.sqrt(age + height);
                nearest.add(new EuclidianData(root,d.get(i)));
            }
            for(int i = 0; i < nearest.size(); i++){
                for(int j = 1; j < (nearest.size() - i); j++){
                    if(nearest.get(j-1).getRoot() > nearest.get(j).getRoot()){
                        aux2 = nearest.get(j - 1);
                        nearest.set(j - 1, nearest.get(j));
                        nearest.set(j, aux2);
                    }
                }
            }
            return nearest;
        }
        
        public void newPoint(int k, Data x, ArrayList<EuclidianData> n){
            double np = 0.0;
            for(int i = 0; i < k; i++){
                np += n.get(i).getData().getWeight();
            }
            np = np/k;
            System.out.println("ID11: " + np);
        }
    }
    
}
