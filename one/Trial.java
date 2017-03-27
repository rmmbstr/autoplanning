package one;

/**
 * Created by ME on 2016/11/19.
 */
public class Trial{
    String name,planId;
    String Number,mrn;
    public Trial(String name, String number,String mrn, String planId){
        this.name = name;
        Number = number;
        this.mrn = mrn;
        this.planId = planId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setMrn(String mrn) {
        this.mrn = mrn;
    }

    public String getMrn() {
        return mrn;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getNumber() {
        return Number;
    }

    public String getPlanId() {
        return planId;
    }
}
