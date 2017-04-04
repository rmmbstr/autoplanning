package one;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by ME on 2016/4/9.
 */
public class DoseParams {
//    int XDim, YDim, ZDim;
//    float VoxelSize;
//    float XStart, YStart, ZStart;
//    HashMap<String, List<Double>> DoseWeight;
//    float PrescriptionPercent, UnitsPerFraction, PrescriptionDose;
//    int NumOfFraction;
//    String PrescriptionRoi,PrescriptionMethod,NormalizationMethod;

    List<TreatParams> treatParams = new ArrayList<>();
    public DoseParams() {
    }
    public DoseParams(String PlanTrialPath){
        try {
            String s;
            BufferedReader bR = new BufferedReader(new FileReader(PlanTrialPath));
            while((s = bR.readLine()) != null) {
                if (s.indexOf("Trial ={")==0){
                    treatParams.add(new TreatParams(bR));
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

//        for (int j = 0; j < DoseWeight.length; j++) {
//            if (DoseWeight[j] != -1)
//                DoseWeight[j] = DoseWeight[j]/weightSum;
//        }
//        for (int i = 0; i < 9; i++) {
//            DoseWeight[i]=Float.parseFloat(MyClass.readFile(file1,"      Weight = "));
//        }
    }
    public static String readFile(String FilePath, String pattern) throws IOException{
        //RandomAccessFile file1 = new RandomAccessFile(FilePath, "r");
        BufferedReader file1 = new BufferedReader(new FileReader(FilePath));

        return readFile(file1, pattern);
//        String s;
//        int index,lastIndex;
//        while ((s=file1.readLine())!=null){
//            if ((index = s.indexOf(pattern))!=-1){
//                lastIndex = s.lastIndexOf(";");
//                return s.substring(index+pattern.length(),lastIndex).trim();
//            }
//        }
//        return "-1";
    }
    public static String readFile(BufferedReader file1, String pattern) throws IOException{
        //RandomAccessFile file1 = new RandomAccessFile(FilePath, "r");
        //BufferedReader file1 = new BufferedReader(new FileReader(FilePath));
        String s;
        int index,lastIndex;
        while ((s=file1.readLine())!=null){
            if ((index = s.indexOf(pattern))==0){
                if (s.lastIndexOf(";")!=-1)
                    lastIndex = s.lastIndexOf(";");
                else
                    lastIndex = s.length();
                return s.substring(index+pattern.length(),lastIndex).trim();
            }
        }
        return "-1";
    }

    public static void main(String[] args) {
        DoseParams abc = new DoseParams("F:/FILES/My Projects/postgresqlconnect/out/artifacts/postgresqlconnect_jar/plan.Trial");
        for (int i = 0; i < abc.treatParams.size(); i++) {
            abc.treatParams.get(i).printAll();
        }
    }
}
class Prescription{
    String name;
    double pDose,pPercent, MUpFraction;
    int pFractions;
    String pRoi,normMethod,pMethod;
    public Prescription(BufferedReader trialFile){
        try{
            this.name = DoseParams.readFile(trialFile,"      Name = ");
            this.name = this.name.substring(1,this.name.length()-1);
            this.MUpFraction = Double.parseDouble(DoseParams.readFile(trialFile,"      RequestedMonitorUnitsPerFraction = "));
            this.pDose = Double.parseDouble(DoseParams.readFile(trialFile,"      PrescriptionDose = "));
            this.pPercent = Double.parseDouble(DoseParams.readFile(trialFile,"      PrescriptionPercent = "));
            this.pFractions = Integer.parseInt(DoseParams.readFile(trialFile,"      NumberOfFractions = "));
            this.pRoi = DoseParams.readFile(trialFile,"      PrescriptionRoi = ");
            this.pRoi = this.pRoi.substring(1,this.pRoi.length()-1);
            this.pMethod = DoseParams.readFile(trialFile,"      Method = ");
            this.pMethod = this.pMethod.substring(1,this.pMethod.length()-1);
            this.normMethod = DoseParams.readFile(trialFile,"      NormalizationMethod = ");
            this.normMethod = this.normMethod.substring(1,this.normMethod.length()-1);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void PrintAll(){
        System.out.println(this.name);
        System.out.println(this.pDose);
        System.out.println(this.pPercent);
        System.out.println(this.MUpFraction);
        System.out.println(this.pFractions);
        System.out.println(this.pRoi);
        System.out.println(this.normMethod);
        System.out.println(this.pMethod);
        System.out.println();
    }
}
class TreatParams {
    int XDim, YDim, ZDim;
    float VoxelSize;
    float XStart, YStart, ZStart;
    String name;
    HashMap<String, List<Beam>> BeamMap = new HashMap<>();
    List<Prescription> PrescriptionList = new ArrayList<>();
    public TreatParams(){}
    public TreatParams(BufferedReader PlanTrialPath){
        try {
            this.name = DoseParams.readFile(PlanTrialPath,"  Name = ");
            this.name = this.name.substring(1,this.name.length()-1);
            this.VoxelSize = Float.parseFloat(DoseParams.readFile(PlanTrialPath, "  DoseGrid .VoxelSize .X = "));
            this.XDim = Integer.parseInt(DoseParams.readFile(PlanTrialPath, "  DoseGrid .Dimension .X = "));
            this.YDim = Integer.parseInt(DoseParams.readFile(PlanTrialPath, "  DoseGrid .Dimension .Y = "));
            this.ZDim = Integer.parseInt(DoseParams.readFile(PlanTrialPath, "  DoseGrid .Dimension .Z = "));
            this.XStart = Float.parseFloat(DoseParams.readFile(PlanTrialPath, "  DoseGrid .Origin .X = "));
            this.YStart = Float.parseFloat(DoseParams.readFile(PlanTrialPath, "  DoseGrid .Origin .Y = "));
            this.ZStart = Float.parseFloat(DoseParams.readFile(PlanTrialPath, "  DoseGrid .Origin .Z = "));

            String s;

//            BufferedReader bR = PlanTrialPath;
            while((s = PlanTrialPath.readLine()) != null){
                if (s.indexOf("    Prescription ={") == 0){
                    this.PrescriptionList.add(new Prescription(PlanTrialPath));
                }
                if (s.indexOf("  };") == 0)
                    break;
            }
//            BufferedReader file1 = new BufferedReader(new FileReader(PlanTrialPath));

            for (int i = 0; i < PrescriptionList.size(); i++) {
                BeamMap.put(PrescriptionList.get(i).name,new ArrayList<>());
            }
            while ((s = PlanTrialPath.readLine())!=null){
                if (s.indexOf("    Beam ={") == 0){
                    String name = DoseParams.readFile(PlanTrialPath,"      PrescriptionName = ");
                    name = name.substring(1,name.length()-1);
                    String pat = "      DoseVolume = \\XDR:";
                    String fileName = DoseParams.readFile(PlanTrialPath,pat);
                    fileName = fileName.substring(0,fileName.length()-1);
//                    System.out.println(fileName);
                    double weight = Double.parseDouble(DoseParams.readFile(PlanTrialPath,"      Weight = "));
                    Beam tmp = new Beam(fileName, weight);
                    BeamMap.get(name).add(tmp);
                }
                if ((s.indexOf("  };")) == 0)
                    break;
            }
            Set<Map.Entry<String,List<Beam>>> entrySet = BeamMap.entrySet();
            for (Map.Entry<String,List<Beam>> entry:entrySet){

                String key = entry.getKey();
                double sum = 0;
                Iterator<Beam> it = BeamMap.get(key).iterator();
                for (int i = 0; i < BeamMap.get(key).size(); i++) {
                    sum += it.next().weight;
//                    System.out.println(BeamMap.get(key).get(i).binaryFileName);
                }
                for (int i = 0; i < BeamMap.get(key).size(); i++) {
                    BeamMap.get(key).get(i).weight /= sum;
                }
            }

//            String pattern = "      Weight = ";
//            while ((s = PlanTrialPath.readLine()) != null){
//                if (s.indexOf("  Name = ") == 0) {
//                    float weightSum = 0;
//                    List<Double> tmpLst = new ArrayList<>();
//                    String line;
//                    while (((line = PlanTrialPath.readLine()).indexOf("}")) != 0) {
//                        if (line.indexOf(pattern)==0){
//                            String tmp = line.substring(pattern.length(),line.length()-1).trim();
//                            tmpLst.add(Double.parseDouble(tmp));
//                        }
//                    }
//                    for (int j = 0; j < tmpLst.size(); j++) {
//                        weightSum += tmpLst.get(j);
//                    }
//                    for (int j = 0; j < tmpLst.size(); j++) {
//                        tmpLst.set(j,tmpLst.get(j)/weightSum);
//                    }
//                    DoseWeight.put(s.substring(10,s.length()-2),(ArrayList<Double>) cloneObject(tmpLst));
//                }
//
//            }

//            while (true) {
//                DoseWeight[i] = Float.parseFloat(readFile(file1, "      Weight = "));
//                if (DoseWeight[i] == -1)
//                    break;
//                weightSum += DoseWeight[i];
//                i++;
//            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void printAll(){
        System.out.println(this.XDim);
        System.out.println(this.XStart);
        System.out.println(this.YDim);
        System.out.println(this.YStart);
        System.out.println(this.ZDim);
        System.out.println(this.ZStart);
        System.out.println(this.VoxelSize);
        for (int i = 0; i < PrescriptionList.size(); i++) {
            PrescriptionList.get(i).PrintAll();
        }
        for (Map.Entry<String,List<Beam>> entry:BeamMap.entrySet()){
            String key = entry.getKey();
//            System.out.println(key);
            for (int i = 0; i < BeamMap.get(key).size(); i++) {
                System.out.println(BeamMap.get(key).get(i).binaryFileName);
                System.out.println(BeamMap.get(key).get(i).weight);
                System.out.println();
            }
        }
        System.out.println();
    }
}
class Beam{
    String binaryFileName;
    double weight;
    public Beam(String binaryFileName,double weight){
        this.binaryFileName = binaryFileName;
        this.weight = weight;
    }
}