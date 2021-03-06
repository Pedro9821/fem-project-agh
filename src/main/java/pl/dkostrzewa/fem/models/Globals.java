package pl.dkostrzewa.fem.models;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Globals {
    private double H;
    private double W;

    private double nH;
    private double nW;


    private double nN;
    private double nE;
    private double k; //conductivity [W/mC]
    private double c; //specific heat [J/kgC]
    private double ro; //density [kg/m3]
    private double alfa; //alfa [W/m2K]
    private double T_oo; //ambient temperature [C]
    private double stepTime;
    private double initialTemperature; //initial temperature

    private List<Double> pc = new ArrayList<>();
    private List<GaussInterpolationNode> integrationPoints = new ArrayList<>();
    private int countPc;

    private List<Double> wagi = new ArrayList<>();
    private int countWagi;


    public Globals() {
        this.readDataFromFile();
    }

    public void readDataFromFile() {
        JSONParser jsonParser = new JSONParser();

        try {
            System.out.println("Select Test Case (1 or 2)");
            Scanner scanner = new Scanner(System.in);
            int testCase = scanner.nextInt();
            Object object;
            if(testCase == 1){
                object = jsonParser.parse(new FileReader(System.getProperty("user.dir") + "/src/main/java/pl/dkostrzewa/fem/data/femdata.json"));
            }
            else {
                object = jsonParser.parse(new FileReader(System.getProperty("user.dir") + "/src/main/java/pl/dkostrzewa/fem/data/femdata2.json"));
            }

            //Read JSON file
            JSONObject jsonObject = (JSONObject) object;

            this.H = (double) jsonObject.get("H");
            this.W = (double) jsonObject.get("W");
            this.k = (double) jsonObject.get("k");
            this.nH = (double) jsonObject.get("nH");
            this.nW = (double) jsonObject.get("nW");
            this.c = (double) jsonObject.get("c");
            this.ro = (double) jsonObject.get("ro");
            this.alfa = (double) jsonObject.get("alfa");
            this.T_oo = (double) jsonObject.get("T_oo");
            this.stepTime =  (double) jsonObject.get("stepTime");
            this.initialTemperature = (double) jsonObject.get("initialTemperature");
            this.nN = this.nH * this.nW;
            this.nE = (nH - 1) * (nW - 1);

            JSONArray pcJsonArray = (JSONArray) jsonObject.get("pC");
            this.countPc = pcJsonArray.size();
            for (Object item : pcJsonArray) {
                Double pcItem = (Double) item;

                this.pc.add(pcItem);
            }
//            Double pc1 = -(1/Math.sqrt(3));
//            Double pc2 = (1/Math.sqrt(3));
//            this.pc.add(pc1);
//            this.pc.add(pc2);


            JSONArray wagiJsonArray = (JSONArray) jsonObject.get("weights");
            this.countWagi = wagiJsonArray.size();
            for (Object wagi : wagiJsonArray) {
                this.wagi.add((Double) wagi);
            }

            for (int i = 0; i < countPc; i++) {
                for (int j = 0; j < countPc; j++) {
                    //ustawienie wspolrzednych ksi i eta punktow calkowania
                    integrationPoints.add(new GaussInterpolationNode(this.pc.get(j), this.pc.get(i)));
                }
            }

//            for(Object item : integrationPoints){
//                System.out.println(item);
//            }


            //this.setnE();
            //this.setnN();


        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }


    public void setnN(double nN) {
        this.nN = nN;
    }

    public void setnE(double nE) {
        this.nE = nE;
    }

    public double getnN() {
        return nN;
    }

    public double getnE() {
        return nE;
    }

    public double getH() {
        return H;
    }

    public void setH(double h) {
        H = h;
    }

    public double getW() {
        return W;
    }

    public void setW(double w) {
        W = w;
    }

    public double getnH() {
        return nH;
    }

    public void setnH(double nH) {
        this.nH = nH;
    }

    public double getnW() {
        return nW;
    }

    public void setnW(double nW) {
        this.nW = nW;
    }


    public List getPc() {
        return pc;
    }

    public void setPc(List pc) {
        this.pc = pc;
    }

    public int getCountPc() {
        return countPc;
    }

    public void setCountPc(int countPc) {
        this.countPc = countPc;
    }

    public List getWagi() {
        return wagi;
    }

    public void setWagi(List wagi) {
        this.wagi = wagi;
    }

    public int getCountWagi() {
        return countWagi;
    }

    public void setCountWagi(int countWagi) {
        this.countWagi = countWagi;
    }

    public List<GaussInterpolationNode> getIntegrationPoints() {
        return integrationPoints;
    }

    public void setIntegrationPoints(List<GaussInterpolationNode> integrationPoints) {
        this.integrationPoints = integrationPoints;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    public double getRo() {
        return ro;
    }

    public void setRo(double ro) {
        this.ro = ro;
    }

    public double getK() {
        return k;
    }

    public double getInitialTemperature() {
        return initialTemperature;
    }

    public void setInitialTemperature(double initialTemperature) {
        this.initialTemperature = initialTemperature;
    }

    public double getAlfa() {
        return alfa;
    }

    public void setAlfa(double alfa) {
        this.alfa = alfa;
    }

    public void setK(double k) {
        this.k = k;
    }

    public double getStepTime() {
        return stepTime;
    }

    public void setStepTime(double stepTime) {
        this.stepTime = stepTime;
    }

    public double getT_oo() {
        return T_oo;
    }

    public void setT_oo(double t_oo) {
        T_oo = t_oo;
    }

    @Override
    public String toString() {
        return "Globals{" +
                "H=" + H +
                ", W=" + W +
                ", nH=" + nH +
                ", nW=" + nW +
                ", nN=" + nN +
                ", nE=" + nE +
                ", k=" + k +
                ", c=" + c +
                ", ro=" + ro +
                ", alfa=" + alfa +
                ", T_oo=" + T_oo +
                ", stepTime=" + stepTime +
                ", initialTemperature=" + initialTemperature +
                ", pc=" + pc +
                ", integrationPoints=" + integrationPoints +
                ", countPc=" + countPc +
                ", wagi=" + wagi +
                ", countWagi=" + countWagi +
                '}';
    }
}
