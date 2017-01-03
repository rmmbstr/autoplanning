import org.jdesktop.swingx.treetable.AbstractTreeTableModel;

import java.util.List;

/**
 * Created by ME on 2016/11/16.
 */
public class PatientTreeTable extends AbstractTreeTableModel{
    List<Patient> patientList;
    private final static String[] COLUMN_NAMES = {"medicalrecordnumber", "patientid", "firstname",
            "lastname","middlename","institutionid","gender","patientpath"};
    public PatientTreeTable(List<Patient> patientList){
        super(new Object());
        this.patientList = patientList;
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    public boolean isCellEditable(Object node, int column) {
        return false;
    }

    @Override
    public boolean isLeaf(Object node) {
        return node instanceof Trial;
    }

    @Override
    public int getChildCount(Object parent) {
        if (parent instanceof Patient){
            Patient patient = (Patient) parent;
            return patient.getPlanList().size();
        }
        if (parent instanceof Plan){
            Plan plan = (Plan) parent;
            return plan.getTrialList().size();
        }
        return patientList.size();
    }

    @Override
    public Object getChild(Object parent, int index) {
        if (parent instanceof Patient){
            Patient patient = (Patient) parent;
            return patient.getPlanList().get(index);
        }
        if (parent instanceof Plan){
            Plan plan = (Plan) parent;
            return plan.getTrialList().get(index);
        }
        return patientList.get(index);
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        if (parent instanceof Patient){
            Patient patient = (Patient) parent;
            Plan plan = (Plan) child;
            return patient.getPlanList().indexOf(plan);
        }
        if (parent instanceof Plan) {
            Plan plan = (Plan) parent;
            Trial trial = (Trial) child;
            return plan.getTrialList().indexOf(trial);
        }
        return patientList.indexOf((Patient) child);
    }

    @Override
    public Object getValueAt(Object node, int column) {
        if (node instanceof Patient){
            Patient patient = (Patient) node;
            switch (column){
                case 0: return patient.getMrn();
                case 1: return patient.getPatientId();
                case 2: return patient.getFirstName();
                case 3: return patient.getLastName();
                case 4: return patient.getMidName();
                case 5: return patient.getInstitutionid();
                case 6: return patient.getGender();
                case 7: return patient.getPath();
            }
        }
        if (node instanceof Plan){
            Plan plan = (Plan) node;
            switch (column){
                case 0:return plan.getId();
            }
        }
        if (node instanceof Trial){
            Trial trial = (Trial) node;
            switch (column){
                case 0:return trial.getName();
            }
        }
        return null;
    }
}

class Patient{
    String mrn;
    String patientId;
    String firstName;
    String lastName;
    String midName;
    String institutionid;
    String gender;
    String path;
    List<Plan> planList;
    public Patient(String mrn, String patientId, String firstName, String lastName, String midName, String institutionid,
                   String gender, String path, List<Plan> planList){
        this.mrn = mrn;
        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.midName = midName;
        this.institutionid = institutionid;
        this.gender = gender;
        this.path = path;
        this.planList = planList;
    }
    public List<Plan> getPlanList() {
        return planList;
    }
    public void setPlanList(List<Plan> planList) {
        this.planList = planList;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getMrn() {
        return mrn;
    }

    public String getInstitutionid() {
        return institutionid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getGender() {
        return gender;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMidName() {
        return midName;
    }

    public String getPath() {
        return path;
    }
}