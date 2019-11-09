package pl.dkostrzewa.fem;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;

public class Globals {
    private double H;
    private double W;

    private double nH;
    private double nW;


    private double nN;
    private double nE;

    private List pc;
    private int countPc;

    private List wagi;
    private int countWagi;

    public Globals(){
        this.readDataFromFile();
    }

    public void readDataFromFile(){
        JSONParser jsonParser = new JSONParser();

        try {
            Object object = jsonParser.parse(new FileReader(System.getProperty("user.dir") + "/src/main/java/pl/dkostrzewa/fem/femdata.json"));
            //Read JSON file
            JSONObject jsonObject = (JSONObject) object;

            this.H = (double) jsonObject.get("H");
            this.W = (double) jsonObject.get("W");
            this.nH = (double) jsonObject.get("nH");
            this.nW = (double) jsonObject.get("nW");

            JSONArray pcJsonArray = (JSONArray) jsonObject.get("pC");
            this.countPc = pcJsonArray.size();
            for(Object item: pcJsonArray){
                Double pcItem = (Double)item;
                this.pc.add(pcItem);
            }

            JSONArray wagiJsonArray = (JSONArray) jsonObject.get("wagi");
            this.countWagi = wagiJsonArray.size();
            for(Object wagi: wagiJsonArray){
                this.wagi.add((Double)wagi);
            }


        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
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

    public double getnN() {
        return nN;
    }

    public void setnN() {
        this.nN = nN;
    }

    public double getnE() {
        return nE;
    }

    public void setnE() {
        this.nE = nE;
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

    @Override
    public String toString() {
        return "Globals{" +
                "H=" + H +
                ", W=" + W +
                ", nH=" + nH +
                ", nW=" + nW +
                ", nN=" + nN +
                ", nE=" + nE +
                ", pc=" + pc +
                ", countPc=" + countPc +
                ", wagi=" + wagi +
                ", countWagi=" + countWagi +
                '}';
    }
}
