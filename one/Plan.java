package one;

import java.util.List;

/**
 * Created by ME on 2016/11/16.
 */
public class Plan{
    String id;
    String Number;
    List<Trial> trialList;
    Plan(String id, List<Trial> trialList, String Number){
        this.id = id;
        this.trialList = trialList;
        this.Number = Number;
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
}