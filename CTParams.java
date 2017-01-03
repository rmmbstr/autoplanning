import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by ME on 11/01 001.
 */
public class CTParams {
    float CTXstart,CTYstart,CTZstart;
    float CTstep,CTstepZ;
    int CTZdim;
    CTParams(){}
    CTParams(String CTImageSetPath){
        try {
            CTXstart = Float.parseFloat(readFile(CTImageSetPath, "\tx_start = "));
            CTYstart = Float.parseFloat(readFile(CTImageSetPath, "\ty_start = "));
            CTZstart = Float.parseFloat(readFile(CTImageSetPath, "\tz_start = "));
            CTstep = Float.parseFloat(readFile(CTImageSetPath, "\tx_pixdim = "));
            CTstepZ = Float.parseFloat(readFile(CTImageSetPath, "\tz_pixdim = "));
            CTZdim = Integer.parseInt(readFile(CTImageSetPath, "\tz_dim = "));
        }
        catch (IOException e){
            e.printStackTrace();
        }
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
                lastIndex = s.lastIndexOf(";");
                return s.substring(index+pattern.length(),lastIndex).trim();
            }
        }
        return "-1";
    }
}
