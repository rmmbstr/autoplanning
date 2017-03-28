package one;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static one.mainconnect.cloneObject;

/**
 * Created by ME on 2016/4/9.
 */
public class DoseParams {
    HashMap<String, List<Double>> DoseWeight = new HashMap<>();
    int XDim, YDim, ZDim;
    float VoxelSize;
    float XStart, YStart, ZStart;
    float PrescriptionPercent, UnitsPerFraction, PrescriptionDose;
    int NumOfFraction;
    String PrescriptionRoi,PrescriptionMethod,NormalizationMethod;
    List<String> PrescriptionList;
    int i = 0;
    public DoseParams() {
    }
    public DoseParams(String PlanTrialPath){

        try {
            this.XDim = Integer.parseInt(readFile(PlanTrialPath, "  DoseGrid .Dimension .X = "));
            this.YDim = Integer.parseInt(readFile(PlanTrialPath, "  DoseGrid .Dimension .Y = "));
            this.ZDim = Integer.parseInt(readFile(PlanTrialPath, "  DoseGrid .Dimension .Z = "));
            this.VoxelSize = Float.parseFloat(readFile(PlanTrialPath, "  DoseGrid .VoxelSize .X = "));
            this.XStart = Float.parseFloat(readFile(PlanTrialPath, "  DoseGrid .Origin .X = "));
            this.YStart = Float.parseFloat(readFile(PlanTrialPath, "  DoseGrid .Origin .Y = "));
            this.ZStart = Float.parseFloat(readFile(PlanTrialPath, "  DoseGrid .Origin .Z = "));
            this.PrescriptionPercent = Float.parseFloat(readFile(PlanTrialPath, "      PrescriptionPercent = "));
            this.UnitsPerFraction = Float.parseFloat(readFile(PlanTrialPath, "      RequestedMonitorUnitsPerFraction = "));
            this.PrescriptionDose = Float.parseFloat(readFile(PlanTrialPath, "      PrescriptionDose = "));
            this.NumOfFraction = Integer.parseInt(readFile(PlanTrialPath, "      NumberOfFractions = "));
            this.PrescriptionRoi = readFile(PlanTrialPath,"      PrescriptionRoi = ");
            this.PrescriptionRoi = this.PrescriptionRoi.substring(1,this.PrescriptionRoi.length()-1);
            this.PrescriptionMethod = readFile(PlanTrialPath,"      Method = ");
            this.PrescriptionMethod = this.PrescriptionMethod.substring(1,this.PrescriptionMethod.length()-1);
            this.NormalizationMethod = readFile(PlanTrialPath,"      NormalizationMethod = ");
            this.NormalizationMethod = this.NormalizationMethod.substring(1,this.NormalizationMethod.length()-1);
            BufferedReader file1 = new BufferedReader(new FileReader(PlanTrialPath));



            String s;
            String pattern = "      Weight = ";
            while ((s = file1.readLine()) != null){
                if (s.indexOf("  Name = ") == 0) {
                    float weightSum = 0;
                    List<Double> tmpLst = new ArrayList<>();
                    String line;
                    while (((line = file1.readLine()).indexOf("}")) != 0) {
                        if (line.indexOf(pattern)==0){
                            String tmp = line.substring(pattern.length(),line.length()-1).trim();
                            tmpLst.add(Double.parseDouble(tmp));
                        }
                    }
                    for (int j = 0; j < tmpLst.size(); j++) {
                        weightSum += tmpLst.get(j);
                    }
                    for (int j = 0; j < tmpLst.size(); j++) {
                        tmpLst.set(j,tmpLst.get(j)/weightSum);
                    }
                    DoseWeight.put(s.substring(10,s.length()-2),(ArrayList<Double>) cloneObject(tmpLst));
                }

            }
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
}