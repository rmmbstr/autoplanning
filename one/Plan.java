package one;

import java.util.List;

/**
 * Created by ME on 2016/11/16.
 */
public class Plan{
    String id;
    String Number,mrn,institution;
    List<Trial> trialList;
    Plan(String id, List<Trial> trialList, String Number,String mrn,String institution){
        this.id = id;
        this.trialList = trialList;
        this.Number = Number;
        this.mrn = mrn;
        this.institution = institution;
    }
    Plan(String id){
        this.id = id;
    }
    public List<Trial> getTrialList() {
        return trialList;
    }

    public void setTrialList(List<Trial> trialList) {
        this.trialList = trialList;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getMrn() {
        return mrn;
    }

    public String getInstitution() {
        return institution;
    }
}