package one;
/**
 * Created by ME on 2016/10/3.
 */
import com.csvreader.CsvWriter;
import org.jdesktop.swingx.JXTreeTable;
import org.rosuda.REngine.*;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Point2D;
import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.List;

public class mainconnect implements ActionListener,ItemListener {
    private JFrame frame = new JFrame("JDB");
    private JLabel addrLabel = new JLabel("Server Address");
    private JLabel portLabel = new JLabel("Server Port");
    private JLabel unLabel = new JLabel("Username");
    private JLabel passLabel = new JLabel("Password");
    private JLabel dbLabel = new JLabel("Database");
    private JLabel cmdLabel =new JLabel("Command");
    private JTextField addrText = new JTextField("192.168.1.121");
//    private JTextField addrText = new JTextField("170.16.1.140");
    private JTextField portText = new JTextField("5432");
    private JTextField unText = new JTextField("postgres");
//    private JTextField unText = new JTextField("lpuser");
    private JPasswordField passText = new JPasswordField("59623528");
//    private JPasswordField passText = new JPasswordField("launchpad");
    private JTextField dbText = new JTextField("clinical");
    private JTextArea cmdArea = new JTextArea("SELECT * FROM pros.patient WHERE institutionid = 4256\n" +
            "ORDER BY medicalrecordnumber\n" +
            "ASC ",3,60);
    private JTextArea queryArea = new JTextArea(10,60);
    private JButton connButton = new JButton("Connect");
    private JButton queryButton = new JButton("Query");
    private JButton discButton = new JButton("Disconnect");
    private JButton slcButton = new JButton("Select");
    private JButton prcButton = new JButton("Process");
    private Connection c = null;

//    private JCheckBox checkBox[] = null;
    private String result[][] = null;
//    private String patientPath[] = null;


    private List<TreePath> treePathList = new ArrayList<>();

    private String sep = File.separator;
    private String rootpath = "F:\\";
//    private String rootpath = "/";
    public static void main(String[] args) {
        mainconnect connect = new mainconnect();
        connect.go();

//        Connection c = null;
//        Statement stmt = null;
//        try {
//            Class.forName("org.postgresql.Driver");
//            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/clinical",
//                            "postgres", "59623528");
//            stmt = c.createStatement();
//            ResultSet rs = stmt.executeQuery( "SELECT * FROM pros.mountpointlist;" );
//            while ( rs.next() ) {
//                int mountpointid = rs.getInt("mountpointid");
//                String  mountpoint = rs.getString("mountpoint");
//                int institutionid  = rs.getInt("institutionid");
//                System.out.println( "MOUNTPOINTID = " + mountpointid );
//                System.out.println( "MOUNTPOINT = " + mountpoint );
//                System.out.println( "INSTITUTIONID = " + institutionid );
//                System.out.println();
//            }
//            rs.close();
//            stmt.close();
//            c.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.err.println(e.getClass().getName()+": "+e.getMessage());
//            System.exit(0);
//        }
//        System.out.println("Opened database successfully");
    }
    private void go(){
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(5,1));
        labelPanel.add(addrLabel);
        labelPanel.add(portLabel);
        labelPanel.add(unLabel);
        labelPanel.add(passLabel);
        labelPanel.add(dbLabel);


        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new GridLayout(5,1));
        fieldPanel.add(addrText);
        fieldPanel.add(portText);
        fieldPanel.add(unText);
        fieldPanel.add(passText);
        fieldPanel.add(dbText);

        JPanel cmdPanel = new JPanel();
        cmdPanel.add(cmdLabel);
        cmdPanel.add(cmdArea);

        queryArea.setEditable(false);
        JScrollPane jsp = new JScrollPane(queryArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(1,2));
        northPanel.add(labelPanel);
        northPanel.add(fieldPanel);

        JPanel midPanel = new JPanel();
        midPanel.setLayout(new GridLayout(1,3));
        midPanel.add(connButton);
        midPanel.add(discButton);
        midPanel.add(queryButton);
        midPanel.add(slcButton);
        midPanel.add(prcButton);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(2,1));
        southPanel.add(cmdPanel);
        southPanel.add(jsp);

        connButton.addActionListener(this);
        queryButton.addActionListener(this);
        discButton.addActionListener(this);
        slcButton.addActionListener(this);
        prcButton.addActionListener(this);

        frame.add(northPanel,BorderLayout.NORTH);
        frame.add(midPanel,BorderLayout.CENTER);
        frame.add(southPanel,BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocation(160,180);
//        frame.setSize(600,500);
        frame.pack();
        frame.setVisible(true);
    }
    private void connectServer(){

//        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            String connector;
            connector = "jdbc:postgresql://"+addrText.getText()+":"+portText.getText()
                +"/"+dbText.getText();
//            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/clinical",
//                            "postgres", "59623528");
            c = DriverManager.getConnection(connector,unText.getText(),new String(passText.getPassword()));
            System.out.println("DatabaseConnected");
//            stmt = c.createStatement();
//            ResultSet rs = stmt.executeQuery( "SELECT * FROM pros.mountpointlist;" );
//            while ( rs.next() ) {
//                int mountPointId = rs.getInt("mountpointid");
//                String  mountPoint = rs.getString("mountpoint");
//                int institutionid  = rs.getInt("institutionid");
//                System.out.println( "MOUNTPOINTID = " + mountPointId );
//                System.out.println( "MOUNTPOINT = " + mountPoint );
//                System.out.println( "INSTITUTIONID = " + institutionid );
//                System.out.println();
//            }
//            rs.close();
//            stmt.close();
//            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }
    private void disConnectServer(){
        try{
            c.close();
            System.out.println("Database Disconnected");
        }
        catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
    }
    private void queryDatabase(){
        DefaultMutableTreeNode treeNode[];
        JTree tree[];
        globalTrialList.clear();
        treePathList.clear();
        JFrame dataFrame = new JFrame("data");
        Container container = dataFrame.getContentPane();
        JPanel dataPanel = new JPanel();
        int rowNum;
        int i = 0;
        Statement stmt;
        try {
            stmt = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(cmdArea.getText());
            rs.last();
            rowNum = rs.getRow();
            rs.beforeFirst();
            int colNum = 8;
            String column[] = {"medicalrecordnumber","patientid","firstname","lastname","middlename"
                    ,"institutionid","gender","patientpath"};
            result = new String[rowNum][colNum];
//            patientPath = new String[rowNum];
//            JCheckBox[] checkBox = new JCheckBox[rowNum];
            treeNode = new DefaultMutableTreeNode[rowNum];
            tree = new JTree[rowNum];

            while (rs.next()) {
                int mrc = rs.getInt(column[0]);
                int patientid = rs.getInt(column[1]);
                String firstname = rs.getString(column[2]);
                String lastname = rs.getString(column[3]);
                String middlename = rs.getString(column[4]);
                int institutionid = rs.getInt(column[5]);
                String gender = rs.getString(column[6]);
                String patientpath = rs.getString(column[7]);
//                String oncologist = rs.getString("radiationoncologist");
                result[i][0] = Integer.toString(mrc);
                result[i][1] = Integer.toString(patientid);
                result[i][2] = firstname;
                result[i][3] = lastname;
                result[i][4] = middlename;
                result[i][5] = Integer.toString(institutionid);
                result[i][6] = gender;
                result[i][7] = patientpath;

//                checkBox[i] = new JCheckBox(result[i][0]);
                treeNode[i] = new DefaultMutableTreeNode(result[i][0]);
                tree[i] = new JTree(treeNode[i]);
                treeNode[i].add(new DefaultMutableTreeNode("Plan_0"));
                treeNode[i].add(new DefaultMutableTreeNode("Plan_1"));

//                tree[i].addTreeSelectionListener(new TreeSelectionListener() {
//                    public void valueChanged(TreeSelectionEvent e) {
//                        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) e.getNewLeadSelectionPath()
//                                .getLastPathComponent();
//                        System.out.println("Current selected: " + selectedNode + ", leaf: " + selectedNode.isLeaf()
//                                + ", originObj: " + selectedNode.getUserObject().getClass());
//                    }
//                });
//                checkBox[i].setText(result[i][0]);

                i++;

//                queryArea.append("patientid = " + patientid+"\t");
//                queryArea.append("firstname = " + firstname+"\t\t");
//                queryArea.append("lastname = " + lastname+"\t\t");
//                queryArea.append("middlename = " + middlename+"\t");
//                queryArea.append("institutionid = " + institutionid+"\t");
//                queryArea.append("gender = " + gender+"\t");
//                queryArea.append("patientpath = " + patientpath+"\n");
//                queryArea.append("radiationoncologist = " + oncologist+"\t");
            }
            rs.close();
            stmt.close();

            dataPanel.setLayout(new GridLayout(rowNum+1,8));

//            for (int j = 0; j < column.length; j++) {
//                dataPanel.add(new JLabel(column[j]));
//            }
            for (String s : column) {
                dataPanel.add(new JLabel(s));
            }
            for (int j = 0; j < rowNum; j++) {
//                dataPanel.add(checkBox[j]);
                dataPanel.add(tree[j]);
                for (int k = 1; k < 8 ; k++) {
                    dataPanel.add(new JLabel(result[j][k]));
                }
            }
//            Object data[][] = new Object[rowNum][8];
//            for (int j = 0; j < rowNum; j++) {
////                data [j][0] = checkBox[j];
//
//                for (int k = 1; k < 8; k++) {
//                    data[j][k] = result[j][k];
//                }
//            }
//            TableColumnModel tcm = table.getColumnModel();
//            tcm.getColumn(0).setCellEditor(new DefaultCellEditor(new JCheckBox()));

            JScrollPane jsp1 = new JScrollPane(dataPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            jsp1.getVerticalScrollBar().setUnitIncrement(10);
//            JScrollPane jsp1 = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
//                    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            container.add(jsp1);
            dataFrame.setSize(1000,600);
            dataFrame.setLocation(500,330);
//            dataFrame.setVisible(true);
            dataFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        }
        catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

        List<Patient> patientList = new ArrayList<Patient>();
        for (int j = 0; j < result.length; j++) {
            List<Plan> planList = new ArrayList<Plan>();
            File dir = new File(rootpath+"pinnacle_patient_expansion"+sep+"NewPatients"+sep+"Institution_"
                    + result[j][5] + sep+"Mount_0"+sep+"Patient_" + result[j][1]+sep);
            File[] files = dir.listFiles();
            for (int k = 0; k < files.length; k++) {
                if (files[k].isDirectory()) {
                    if (files[k].getName().contains("Plan")) {
                        List<Trial> trialList = new ArrayList<>();
                        try {
                            String s;
                            BufferedReader file1 = new BufferedReader(new FileReader(files[k].getPath()
                                    +sep+"plan.Trial"));
                            while (!(s = DoseParams.readFile(file1,"  Name = ")).equals("-1")) {
                                trialList.add(new Trial(s.substring(1,s.length()-1),result[j][1],result[j][0],
                                        files[k].getName(),result[j][5]));
                                System.out.println(s.substring(1,s.length()-1));
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        planList.add(new Plan(files[k].getName(), trialList, result[j][1],result[j][0],result[j][5]));
                    }
                }
            }

            patientList.add(new Patient(result[j][0], result[j][1], result[j][2], result[j][3],
                    result[j][4], result[j][5], result[j][6], result[j][7], planList));
        }
        PatientTreeTable patientTreeTable = new PatientTreeTable(patientList);
//        TreeTableModel model = new TestTreeTableModel(new JTree().getModel().getRoot());
//        TreeTableModel model = new TestTreeTableModel(result);
        JXTreeTable treeTable = new JXTreeTable(patientTreeTable);
        CheckTreeManager manager = new CheckTreeTableManager(treeTable,true,true);
        manager.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener(){
            public void valueChanged(TreeSelectionEvent treeSelectionEvent){
                for(TreePath path: treeSelectionEvent.getPaths()){
                    boolean flag = false;
                    Iterator<TreePath> sListIterator = treePathList.iterator();
                    while(sListIterator.hasNext()){
                        TreePath e = sListIterator.next();
                        if(e.equals(path)){
                            sListIterator.remove();
                            flag = true;
                        }
                    }
                    if (!flag) {
                        treePathList.add(path);
                    }
//                    System.out.println(path.getLastPathComponent()+": "+treeSelectionEvent.isAddedPath(path));
                }
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(treeTable), BorderLayout.CENTER);
        panel.add(manager.label, BorderLayout.SOUTH);

        JFrame frame = new JFrame();
        frame.getContentPane().add(panel);
        frame.setSize(1000,600);
        frame.setVisible(true);

    }
//    private List<Plan> globalPlanList = new ArrayList<>();
    private List<Trial> globalTrialList = new ArrayList<>();
    private JComboBox[][] Boxes;

    private void selectData(){
        List<Trial> trialList = new ArrayList<>();
//        List<Plan> planList = new ArrayList<>();

//        for (int i = 0; i < treePathList.size(); i++) {
        for (TreePath t : treePathList) {
//            System.out.println(treePathList.get(i));
//            TreePath treePath = treePathList.get(i);
            Object tmp = t.getLastPathComponent();
            if (tmp instanceof Patient) {
                List<Plan> tmpList;
                tmpList = ((Patient) tmp).getPlanList();
//                for (int j = 0; j < tmpList.size(); j++) {
//                    trialList.addAll(tmpList.get(j).getTrialList());
//                }
                for (Plan p : tmpList) {
                    trialList.addAll(p.getTrialList());
                }

            }
            if (tmp instanceof Plan) {
                trialList.addAll(((Plan) tmp).getTrialList());
//                ((Patient) treePath.getPathComponent(1)).getMrn() + ((Plan) tmp).getId();
            }
            if (tmp instanceof Trial) {
                trialList.add((Trial) t.getPathComponent(3));
//                System.out.println(((Patient) treePath.getPathComponent(1)).getMrn() +
//                        ((Plan) treePath.getPathComponent(2)).getId() + ((Trial) tmp).getName());
            }
        }
// planlist添加
//       for (int i = 0; i < treePathList.size(); i++) {
////            System.out.println(treePathList.get(i));
//            TreePath treePath = treePathList.get(i);
//            Object tmp = treePath.getLastPathComponent();
//            if (tmp instanceof Patient) {
//                planList.addAll(((Patient) tmp).getPlanList());
//            }
//            if (tmp instanceof Plan) {
//                planList.add((Plan) tmp);
////                ((Patient) treePath.getPathComponent(1)).getMrn() + ((Plan) tmp).getId();
//            }
//            if (tmp instanceof Trial) {
//                planList.add((Plan) treePath.getPathComponent(2));
////                System.out.println(((Patient) treePath.getPathComponent(1)).getMrn() +
////                        ((Plan) treePath.getPathComponent(2)).getId() + ((Trial) tmp).getName());
//            }
//        }


        globalTrialList.addAll(trialList);
//        globalPlanList.addAll(planList);
        JFrame roiframe = new JFrame();
        JPanel mainPanel = new JPanel();
        roiframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Container container = roiframe.getContentPane();

        Boxes = new JComboBox[trialList.size()][8];
        for (int i = 0; i < trialList.size(); i++) {
            Vector<String> roiList = new Vector<>();
            Trial tmp = trialList.get(i);
            JPanel jPanel = new JPanel(new GridLayout(Boxes[0].length+1,2));
//            roiframe.setLayout(new GridLayout(planList.size(),1));
            mainPanel.setLayout(new GridLayout(trialList.size(),1));
            try {
                String s;
                BufferedReader file1 = new BufferedReader(new FileReader(rootpath+"pinnacle_patient_expansion"
                        + sep+"NewPatients"+sep+"Institution_"+tmp.getInstitution()+sep+"Mount_0"+sep+"Patient_"
                        + tmp.getNumber()+sep+ tmp.getPlanId()+sep+"plan.roi"));
                while (!(s = DoseParams.readFile(file1,"           name: ")).equals("-1")) {
                    roiList.addElement(s);

//                    System.out.println(s);
                }
//                JComboBox jComboBox = new JComboBox(roiList);
                jPanel.add(new JLabel("Patient:"+tmp.getMrn()+sep+tmp.getPlanId()+sep+tmp.getName()));
                jPanel.add(new JLabel());
                jPanel.add(new JLabel("ROI1"));
//                ptvBox = new JComboBox(roiList);
                Boxes[i][0] = new JComboBox(roiList);
                jPanel.add(Boxes[i][0]);
//                jPanel.add(new JLabel("CTV"));
//                jPanel.add(new JComboBox(roiList));
//                jPanel.add(new JLabel("BLADDER"));
                jPanel.add(new JLabel("ROI2"));
                Boxes[i][1] = new JComboBox(roiList);
                jPanel.add(Boxes[i][1]);
//                jPanel.add(new JLabel("BODY"));
//                jPanel.add(new JComboBox(roiList));
//                jPanel.add(new JLabel("RIGHT FEMORAL"));
                jPanel.add(new JLabel("ROI3"));
                Boxes[i][2] = new JComboBox(roiList);
                jPanel.add(Boxes[i][2]);
//                jPanel.add(new JLabel("LEFT FEMORAL"));
                jPanel.add(new JLabel("ROI4"));
                Boxes[i][3] = new JComboBox(roiList);
                jPanel.add(Boxes[i][3]);
                jPanel.add(new JLabel("ROI5"));
                Boxes[i][4] = new JComboBox(roiList);
                jPanel.add(Boxes[i][4]);
                jPanel.add(new JLabel("ROI6"));
                Boxes[i][5] = new JComboBox(roiList);
                jPanel.add(Boxes[i][5]);
                jPanel.add(new JLabel("ROI7"));
                Boxes[i][6] = new JComboBox(roiList);
                jPanel.add(Boxes[i][6]);
                jPanel.add(new JLabel("ROI8"));
                Boxes[i][7] = new JComboBox(roiList);
                jPanel.add(Boxes[i][7]);
                mainPanel.add(jPanel);

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        JScrollPane jsp = new JScrollPane(mainPanel);
        jsp.getVerticalScrollBar().setUnitIncrement(20);
        container.add(jsp);
        roiframe.setSize(480,750);
        roiframe.setVisible(true);
//        int k = 0;
//        for (int i = 0; i < checkBox.length; i++) {
//            if (checkBox[i].isSelected()) {
//                System.out.println(result[i][0] + "\t" + result[i][6]);
//                patientPath[k] = "F:\\pinnacle_patient_expansion\\NewPatients\\Institution_" + result[i][5] +
//                "\\Mount_0\\Patient_" + result[i][1];
//                k++;
//            }
//        }
//        for (int i = 0; i < patientPath.length; i++) {
//            if (patientPath[i]!=null) {
//                System.out.println(patientPath[i]);
//                queryArea.append(patientPath[i]+"\n");
//            }
//        }
//        queryArea.append("\n");
//        System.out.println();
//        drawDVH();

//        System.out.println(doseParams.XDim);
//        System.out.println(doseParams.XStart);
//        System.out.println(doseParams.VoxelSize);
//        System.out.println(doseParams.NumOfFraction);
//        System.out.println(doseParams.PrescriptionPercent);
//        System.out.println(doseParams.UnitsPerFraction);
//        System.out.println(Arrays.toString(doseParams.DoseWeight));


    }

    public void actionPerformed(ActionEvent e){
        String tmp = ((JButton)e.getSource()).getText();
        int flag = 0;
        if (tmp.equals("Connect"))
            flag = 1;
        if (tmp.equals("Disconnect"))
            flag = 2;
        if (tmp.equals("Query"))
            flag = 3;
        if (tmp.equals("Select"))
            flag = 4;
        if (tmp.equals("Process"))
            flag = 5;
        switch (flag){
            case 1: connectServer();break;
            case 2: disConnectServer();break;
            case 3: queryDatabase();break;
            case 4: selectData();break;
            case 5:
                try {
                    Process(globalTrialList, Boxes);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                break;
//            case 5:outputDVH(globalPlanList,Boxes);break;
//            case "Process": drawDVH(globalPlanList.get(0));break;
            default: break;
        }
    }

    public void itemStateChanged(ItemEvent e) {
//        JCheckBox jcb = (JCheckBox) e.getItem();
//        if (jcb.isSelected()) {
//            System.out.println(jcb.getText());
//        } else {
//
//        }
    }

    private static void  readDoseData(String path, float[][][] dosedata, double weight) {
        try {
            RandomAccessFile file1 = new RandomAccessFile(path, "r");
            file1.seek(0);
            for (int i = 0; i < dosedata.length; i++) {
                for (int j = 0; j < dosedata[i].length; j++) {
                    for (int k = 0; k < dosedata[i][j].length; k++) {
                        dosedata[i][dosedata[i].length - j - 1][k] += file1.readFloat() * weight;
                    }
                }
            }
            file1.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    void drawDVH(Plan plan){
        String patientPath = rootpath+"pinnacle_patient_expansion"+sep+"NewPatients"+sep+"Institution_"
                +plan.getInstitution() +sep+"Mount_0"+sep+ "Patient_" + plan.getNumber();
        String planPath = patientPath + sep + plan.getId();
        List<String> files = new ArrayList<>();
        try {
            String s;
            BufferedReader file1 = new BufferedReader(new FileReader(planPath+sep+"plan.Trial"));
            while (!(s = DoseParams.readFile(file1,"      DoseVolume = /XDR:")).equals("-1")) {
                files.add(s.substring(0,s.lastIndexOf("\\")));
//                System.out.println(s.substring(0,s.lastIndexOf("\\")));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        files.remove(files.size()-1);

//        ArrayList<String> plans = new ArrayList<>();
//        File dir = new File(patientPath[0]+"\\");
//        File[] tempList = dir.listFiles();
//        for (int i = 0; i < tempList.length; i++) {
//            if (tempList[i].isDirectory()) {
//                if (tempList[i].getName().contains("Plan"))
//                    plans.add(tempList[i].getName());
//            }
//        }

//        System.out.println(plans.size());
        DoseParams doseParams = new DoseParams(planPath +sep+ "plan.Trial");
        CTParams ctParams = new CTParams(patientPath+sep+"ImageSet_0.header");
//        HashMap<Double, HashSet<Point2D.Double>> KidneySet =
//                readRoiSet("//  ROI: FERUM-L", patientPath[0]+"\\Plan_0\\plan.roi",ctParams);

//        float[][][] dose_data = new float[doseParams.ZDim][doseParams.YDim][doseParams.XDim];
//        for (int i = 0; i < dose_data.length; i++) {
//            for (int j = 0; j < dose_data[i].length; j++) {
//                for (int k = 0; k < dose_data[i][j].length; k++) {
//                    dose_data[i][j][k] = 0f;
//                }
//            }
//        }

        String[] rois = new String[4];
        rois[0] = (String) Boxes[0][0].getSelectedItem();
        rois[1] = (String) Boxes[0][1].getSelectedItem();
        rois[2] = (String) Boxes[0][2].getSelectedItem();
        rois[3] = (String) Boxes[0][3].getSelectedItem();

//        for (int i = 0; i < rois.length; i++) {
//            System.out.println(rois[i]);
//        }
//        long doseFileSize = 0;
//        int fileNum = 0;
//        String[] doseFileList = new String[20];
//        for (int i = 0; i < 100; i++) {
//            String filePath = patientPath[0]+"\\Plan_0\\plan.Trial.binary."+String.format("%0"+3+"d", i);
//            File file = new File(filePath);
//            if (file.exists()){
//                fileNum++;
//                if (doseFileSize < file.length())
//                    doseFileSize = file.length();
//            }
//        }
//        System.out.println(fileNum);
        for (int i = 0; i < files.size(); i++) {
            String filePath = planPath+sep+"plan.Trial.binary."+String.format("%0"+3+"d",
                    Integer.parseInt(files.get(i)));
//            System.out.println(filePath);
//            readDoseData(filePath, dose_data, doseParams.DoseWeight[i]);
        }

        System.out.println("Dose input finish");


//        for (int i = 0; i < rois.length; i++) {
//            HashMap<Double, HashSet<Point2D.Double>>tmpSet =
//                    readRoiSet("//  ROI: " + rois[i], planPath+"\\plan.roi",ctParams);
//            double[] DVHdata_t = DVH(tmpSet,dose_data,ctParams,doseParams);
//            Mygraphic mygraphic_t = new Mygraphic(DVHdata_t, rois[i]);
//            JFrame gFrame_t = new JFrame();
//            gFrame_t.setSize(700, 700);
//            System.out.println(rois[i]+"input finish");
//        }

//        HashMap<Double, HashSet<Point2D.Double>>ptvSet =
//                readRoiSet("//  ROI: " + rois[0], planPath+"\\plan.roi",ctParams);
//        HashMap<Double, HashSet<Point2D.Double>>bldSet =
//                readRoiSet("//  ROI: " + rois[1], planPath+"\\plan.roi",ctParams);

        HashMap<Double, HashSet<Point2D.Double>>[] Sets = new HashMap[4];

        for (int i = 0; i < Sets.length; i++) {
            Sets[i] = readRoiSet("//  ROI: " + rois[i], planPath+sep+"plan.roi",ctParams);
            HashMap<Double,ArrayList<Point2D.Double>> PTV = readRoi("//  ROI: " + rois[0],planPath
                    +sep+"plan.roi");
//            double[] DVHdata = DVH(Sets[i],dose_data,ctParams,doseParams);
//            double[] OVHdata = OVH(Sets[i],PTV);
//            Mygraphic mygraphic = new Mygraphic(DVHdata, rois[i]);
            JFrame gFrame = new JFrame();
            gFrame.setSize(700, 700);
            gFrame.setVisible(true);
            gFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//            gFrame.getContentPane().add(mygraphic);
        }

//        System.out.println("ROI input finish");
//        Set <Map.Entry<Double, HashSet<Point2D.Double>>> entrySet=lfSet.entrySet();
//        for(Map.Entry<Double, HashSet<Point2D.Double>> entry: entrySet){
//            System.out.println(entry.getKey() + ".........." + entry.getValue());
//        }
//        System.out.println(lfSet.size());
//        System.out.println("ROI input finish");
//        double[] DVHdata_p = DVH(ptvSet,dose_data,ctParams,doseParams);
//        double[] DVHdata_b = DVH(bldSet,dose_data,ctParams,doseParams);
//        double[] DVHdata_l = DVH(lfSet,dose_data,ctParams,doseParams);
//        double[] DVHdata_r = DVH(rfSet,dose_data,ctParams,doseParams);

//        Mygraphic mygraphic_p = new Mygraphic(DVHdata_p, "ptv");
//        Mygraphic mygraphic_b = new Mygraphic(DVHdata_b, "bladder");
//        Mygraphic mygraphic_r = new Mygraphic(DVHdata_r, "rf");
//        Mygraphic mygraphic_l = new Mygraphic(DVHdata_l, "lf");
//
//        JFrame gFrame_p = new JFrame();
//        JFrame gFrame_b = new JFrame();
//        JFrame gFrame_r = new JFrame();
//        JFrame gFrame_l = new JFrame();
//        gFrame_p.setSize(700, 700);
//        gFrame_b.setSize(700, 700);
//        gFrame_r.setSize(700, 700);
//        gFrame_l.setSize(700, 700);
//        gFrame_b.setVisible(true);
//        gFrame_b.setVisible(true);
//        gFrame_r.setVisible(true);
//        gFrame_l.setVisible(true);
//        gFrame_p.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        gFrame_b.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        gFrame_l.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        gFrame_r.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        gFrame_p.getContentPane().add(mygraphic_p);
//        gFrame_b.getContentPane().add(mygraphic_b);
//        gFrame_r.getContentPane().add(mygraphic_r);
//        gFrame_l.getContentPane().add(mygraphic_l);
//        for (int i = 0; i < DVHdata.length; i++) {
//
//        }
    }

    private void Process(List<Trial> trialList, JComboBox[][] Boxes) {
//        RList doseList = new RList();
//        RList distanceList = new RList();


        for (int i = 0; i < trialList.size(); i++) {
            List<Double> distanceList = new ArrayList<>();
//            List<Double> doseList = new ArrayList();
            Trial trial = trialList.get(i);
            String patientPath = rootpath+sep+"pinnacle_patient_expansion"+sep+"NewPatients"+sep+"Institution_"
                    +trial.getInstitution()+sep+"Mount_0"+sep + "Patient_" + trial.getNumber();
            String planPath = patientPath + sep + trial.getPlanId();
            List<String> files = new ArrayList<>();
            CTParams ctParams = new CTParams(patientPath+sep+"ImageSet_0.header");
            DoseParams doseParams = new DoseParams(planPath+sep+"plan.Trial");
            TreatParams treatParams = new TreatParams();
            for (int j = 0; j < doseParams.treatParams.size(); j++) {
                if (trial.getName().equals(doseParams.treatParams.get(j).name))
                    treatParams = doseParams.treatParams.get(j);
            }
            double[] c = new double[3];
            int dose_x,dose_y,dose_z;
            double[][] inter = new double[8][3];
            double[] interdose = new double[8];
            double CT_Xstart = ctParams.CTXstart;
            double CT_Ystart = ctParams.CTYstart;
            double CT_Zstart = ctParams.CTZstart;
            double CT_STEP = ctParams.CTstep;
            double CT_STEPZ = ctParams.CTstepZ;
            double dose_Xstart = treatParams.XStart;
            double dose_Ystart = treatParams.YStart;
            double dose_Zstart = treatParams.ZStart;
            String[] rois = new String[Boxes[0].length];
            HashMap<Double, HashSet<Point2D.Double>> Sets[] = new HashMap[rois.length];
            float[][][] dose_data = new float[treatParams.ZDim][treatParams.YDim][treatParams.XDim];
            double totalmean = 0;
            for (int j = 0; j < treatParams.PrescriptionList.size(); j++) {
                Prescription pre = treatParams.PrescriptionList.get(j);
                String pName = pre.name;
                for (int k = 0; k < treatParams.BeamMap.get(pName).size(); k++) {
                    files.add(treatParams.BeamMap.get(pName).get(k).binaryFileName);
                }

                float[][][] dose_data_tmp = new float[treatParams.ZDim][treatParams.YDim][treatParams.XDim];
                for (int l = 0; l < dose_data_tmp.length; l++) {
                    for (int m = 0; m < dose_data_tmp[l].length; m++) {
                        for (int k = 0; k < dose_data_tmp[l][m].length; k++) {
                            dose_data_tmp[l][m][k] = 0f;
                        }
                    }
                }
//                String[] rois = new String[Boxes[0].length];
                for (int k = 0; k < rois.length; k++) {
                    rois[k] = (String) Boxes[i][k].getSelectedItem();
                }
                for (int k = 0; k < treatParams.BeamMap.get(pName).size(); k++) {
                    String filePath = planPath+sep+"plan.Trial.binary."+String.format("%0"+3+"d",
                            Integer.parseInt(treatParams.BeamMap.get(pName).get(k).binaryFileName));
//            System.out.println(filePath);

                    readDoseData(filePath, dose_data_tmp, treatParams.BeamMap.get(pName).get(k).weight);
                    System.out.println(files.get(k)+"weight "+treatParams.BeamMap.get(pName).get(k).weight);
                }
                System.out.println("Dose input finish");

                //计算平均剂量

                HashMap<Double, HashSet<Point2D.Double>> meanDoseRoi;

                double meanDose;
                double ratio;
                {
                    int data_size = 0;
                    int count = 0;
                    double sum = 0;
//                System.out.println(doseParams.PrescriptionRoi+" "+doseParams.PrescriptionMethod+" "+doseParams.NormalizationMethod);
                    if (!(pre.pMethod.equals("Prescribe")&&pre.normMethod.equals("ROI Mean")))
                        System.out.println("Warning: Normalization not correct");
                    meanDoseRoi = readRoiSet("//  ROI: " + pre.pRoi,
                            planPath + sep + "plan.roi", ctParams);
                    Set<Map.Entry<Double, HashSet<Point2D.Double>>> entrySet = meanDoseRoi.entrySet();
                    for (Map.Entry<Double, HashSet<Point2D.Double>> entry : entrySet) {
                        double key = entry.getKey();
//                        System.out.println(key);
                        data_size += meanDoseRoi.get(key).size();
                        Iterator<Point2D.Double> it = meanDoseRoi.get(key).iterator();
                        for (int k = 0; k < meanDoseRoi.get(key).size(); k++) {
                            math.geom2d.Point2D tmp = new math.geom2d.Point2D(it.next());
                            c[0] = tmp.getX();
                            c[1] = tmp.getY();
                            c[2] = key;
                            dose_x = (int) Math.ceil((CT_Xstart - dose_Xstart) / .4 - 1 + CT_STEP / .4 * (c[0] - CT_Xstart)
                                    / CT_STEP);
                            dose_y = (int) Math.ceil((CT_Ystart - dose_Ystart) / .4 - 1 + CT_STEP / .4 * (c[1] - CT_Ystart)
                                    / CT_STEP);
                            dose_z = (int) Math.ceil((CT_Zstart - dose_Zstart) / .4 - 1 + CT_STEPZ / .4 * (c[2] - CT_Zstart)
                                    / CT_STEPZ);
                            try {
                                for (int p = 0; p < 2; p++) {
                                    for (int l = 0; l < 2; l++) {
                                        for (int m = 0; m < 2; m++) {
                                            inter[p * 4 + l * 2 + m][0] = dose_Xstart + 0.4 * (dose_x + p);
                                            inter[p * 4 + l * 2 + m][1] = dose_Ystart + 0.4 * (dose_y + l);
                                            inter[p * 4 + l * 2 + m][2] = dose_Zstart + 0.4 * (dose_z + m);
                                            interdose[p * 4 + l * 2 + m] = dose_data_tmp[dose_z + m][dose_y + l][dose_x + p];
                                        }
                                    }
                                }
                                double x0 = interpolate(inter, interdose, c) * pre.pFractions *
                                        pre.MUpFraction / pre.pPercent * 100;
                                sum += x0;
                            }
                            catch (ArrayIndexOutOfBoundsException e){
                                double x0 = 0;
                                sum += x0;
                            }
                            count++;
                        }
                    }
                    meanDose = sum/count;
//                    System.out.println(sum);
//                    System.out.println(data_size);
//                    System.out.println(sum/data_size);
//                    System.out.println(count);
                    ratio = meanDose/(pre.pDose*100d/pre.pPercent*pre.pFractions);
//                    System.out.println(sum/data_size/ratio);
                    totalmean += meanDose/ratio;
//                    System.out.println("meanDose="+meanDose/ratio);
                    for (int l = 0; l < dose_data_tmp.length; l++) {
                        for (int m = 0; m < dose_data_tmp[0].length; m++) {
                            for (int k = 0; k < dose_data_tmp[0][0].length; k++) {
                                dose_data[l][m][k] += dose_data_tmp[l][m][k]* pre.pFractions *
                                        pre.MUpFraction / pre.pPercent * 100/ratio;
                            }
                        }
                    }
                }
            }
            System.out.println(totalmean);
//            try {
//                String trialName = "  Name = \""+trial.getName()+"\";";
//                String s;
//                String pattern = "      DoseVolume = \\XDR:";
//                BufferedReader file1 = new BufferedReader(new FileReader(planPath+sep+"plan.Trial"));
////                while (!((s = DoseParams.readFile(file1,"      DoseVolume = \\XDR:")).equals("-1"))) {
////                    files.add(s.substring(0,s.lastIndexOf("\\")));
//////                System.out.println(s.substring(0,s.lastIndexOf("\\")));
////                }
////                files.remove(files.size()-1);
//                while ((s = file1.readLine()) != null){
//                    if (s.indexOf(trialName) == 0) {
//                        String line;
//                        while (((line = file1.readLine()).indexOf("}")) != 0) {
////                            String tmp = DoseParams.readFile(file1,"      DoseVolume = \\XDR:");
////                            files.add(tmp.substring(0,s.lastIndexOf("\\")));
//                            if (line.indexOf(pattern)==0){
//                                String tmp = line.substring(pattern.length(),line.length()-2).trim();
//                                files.add(tmp);
//                            }
//                        }
//                    }
//                }
//                file1.close();
//            }
//            catch (Exception e){
//                e.printStackTrace();
//            }


//            DoseParams doseParams = new DoseParams(planPath + sep+"plan.Trial");
//            CTParams ctParams = new CTParams(patientPath+sep+"ImageSet_0.header");
//            float[][][] dose_data = new float[doseParams.ZDim][doseParams.YDim][doseParams.XDim];
//            for (int l = 0; l < dose_data.length; l++) {
//                for (int j = 0; j < dose_data[l].length; j++) {
//                    for (int k = 0; k < dose_data[l][j].length; k++) {
//                        dose_data[l][j][k] = 0f;
//                    }
//                }
//            }
//
//            String[] rois = new String[Boxes[0].length];
//            for (int j = 0; j < rois.length; j++) {
//                rois[j] = (String) Boxes[i][j].getSelectedItem();
//            }
//            Set<Map.Entry<String, List<Double>>> set = doseParams.DoseWeight.entrySet();
//            for (Map.Entry<String, List<Double>> entry:set){
//                String key = entry.getKey();
//                System.out.println(key);
//            }
//            for (int j = 0; j < files.size(); j++) {
//                String filePath = planPath+sep+"plan.Trial.binary."+String.format("%0"+3+"d",
//                        Integer.parseInt(files.get(j)));
////            System.out.println(filePath);
//
//                readDoseData(filePath, dose_data, doseParams.DoseWeight.get(trial.getName()).get(j));
//                System.out.println(files.get(j)+"weight "+doseParams.DoseWeight.get(trial.getName()).get(j));
//            }
//            System.out.println("Dose input finish");

//            try {
//                RandomAccessFile rf = new RandomAccessFile("/home/p3rtp/ljy/plan.Trial.binary.total", "rw");
//                for (int l = 0; l < dose_data.length; l++) {
//                    for (int j = 0; j < dose_data[l].length; j++) {
//                        for (int k = 0; k < dose_data[l][j].length; k++) {
//                            rf.writeFloat(dose_data[l][j][k]);
//                        }
//                    }
//                }
//                rf.close();
//            }
//            catch (Exception e){
//                e.printStackTrace();
//            }



//            int[] distribute = new int[100];
//            CsvWriter cwd = new CsvWriter("/home/p3rtp/ljy/csv/"+plan.getNumber()+"density.csv",',',
//                    Charset.forName("UTF-8"));
//            CsvWriter cwd = new CsvWriter("C://Users/ME/Desktop/"+plan.getNumber()+"density.csv",',',
//                    Charset.forName("UTF-8"));
//            CsvWriter cw = new CsvWriter("/home/p3rtp/ljy/csv/"+trial.getMrn()+"_"+trial.getPlanId()+"_"+
//              trial.getName()+".csv",',',Charset.forName("UTF-8"));
            CsvWriter cw = new CsvWriter("C://Users/ME/Desktop/"+trial.getNumber()+"_"+trial.getPlanId()+"_"+
                    trial.getName()+".csv",',', Charset.forName("UTF-8"));
            String[] doseAxis = new String[2001];
            doseAxis[0]="Dose";
            for (int j = 1; j < doseAxis.length; j++) {
                doseAxis[j]= String.valueOf(j*5-5);
            }
            try {
                cw.writeRecord(doseAxis);
            }
            catch (Exception e){
                e.printStackTrace();
            }

//            FileOutputStream fs = null;
//            PrintStream pr = null;
//            try{
////                fs = new FileOutputStream(new File("/home/p3rtp/ljy/points.txt"));
////                pr = new PrintStream(fs);
//            }
//            catch (Exception e){
//                e.printStackTrace();
//            }



            for (int j = 0; j < Sets.length; j++) {
                double[] distribute = new double[2000];
                List<Double> doseList = new ArrayList<>();
                Sets[j] = readRoiSet("//  ROI: " + rois[j], planPath+sep+"plan.roi",ctParams);
                int data_size = 0;
//                HashMap<Double,ArrayList<Point2D.Double>> PTV = readRoi("//  ROI: " + rois[0],planPath+sep+"plan.roi");
//                double[] DVHdata = DVH(Sets[j],dose_data,ctParams,doseParams);
//                double[] OVHdata = OVH(Sets[j],PTV);
                Set <Map.Entry<Double, HashSet<Point2D.Double>>> entrySet = Sets[j].entrySet();
                for(Map.Entry<Double, HashSet<Point2D.Double>> entry: entrySet){
                    double key = entry.getKey();
                    data_size += Sets[j].get(key).size();
//                    System.out.println(key);
                    Iterator<Point2D.Double> it = Sets[j].get(key).iterator();
//                    try {
//                        System.out.println(PTV.size());
//                        System.out.println(key);
//                    }
//                    catch (Exception e){
//                        e.printStackTrace();
//                    }
//                    MyPolygon2D ptvPolygon = new MyPolygon2D(PTV.get(key));
                    for (int k = 0; k < Sets[j].get(key).size(); k++) {
                        math.geom2d.Point2D tmp = new math.geom2d.Point2D(it.next());
                        c[0] = tmp.getX();
                        c[1] = tmp.getY();
                        c[2] = key;
                        dose_x = (int) Math.ceil((CT_Xstart - dose_Xstart) / .4 - 1 + CT_STEP / .4 * (c[0] - CT_Xstart)
                                / CT_STEP);
                        dose_y = (int) Math.ceil((CT_Ystart - dose_Ystart) / .4 - 1 + CT_STEP / .4 * (c[1] - CT_Ystart)
                                / CT_STEP);
                        dose_z = (int) Math.ceil((CT_Zstart - dose_Zstart) / .4 - 1 + CT_STEPZ / .4 * (c[2] - CT_Zstart)
                                / CT_STEPZ);
                        try {
                            for (int p = 0; p < 2; p++) {
                                for (int l = 0; l < 2; l++) {
                                    for (int m = 0; m < 2; m++) {
                                        inter[p * 4 + l * 2 + m][0] = dose_Xstart + 0.4 * (dose_x + p);
                                        inter[p * 4 + l * 2 + m][1] = dose_Ystart + 0.4 * (dose_y + l);
                                        inter[p * 4 + l * 2 + m][2] = dose_Zstart + 0.4 * (dose_z + m);
                                        interdose[p * 4 + l * 2 + m] = dose_data[dose_z + m][dose_y + l][dose_x + p];
                                    }
                                }
                            }
//                            double x0 = interpolate(inter, interdose, c) * doseParams.NumOfFraction *
//                                    doseParams.UnitsPerFraction / doseParams.PrescriptionPercent * 100;
                            double x0 = interpolate(inter, interdose, c);
                            doseList.add(x0);
                        }
                        catch (ArrayIndexOutOfBoundsException e){
                            double x0 = 0;
                            doseList.add(x0);
                        }

//                        double distance = -ptvPolygon.boundary().signedDistance(tmp);
                        double distance = 0;

//                        if (j==0) {
//                            try {
//                                if (x0 > 6200 && x0 < 6430) {
//                                    pr.println(tmp.getX() + " " + tmp.getY() + " " + key);
//                                }
//                            }
//                            catch (Exception e){
//                                e.printStackTrace();
//                            }
//                        }
                        distanceList.add(distance);
//                        distribute[(int) Math.round(x0 / 5)]++;
                    }
                }

                System.out.println(trial.getMrn()+" "+rois[j]+" done!");
                double[] doseD = new double[doseList.size()];
                double[] distanceD = new double[distanceList.size()];

                for (int i1 = 0; i1 < doseList.size(); i1++) {
                    doseD[i1] = doseList.get(i1);
                }
                for (int i1 = 0; i1 < distanceList.size(); i1++) {
                    distanceD[i1] = distanceList.get(i1);
                }
                REXPDouble doseVector = new REXPDouble(doseD);
                REXPDouble distanceVector = new REXPDouble(distanceD);

//                if (j==0){
//                    double sum = 0;
//                    for (int k = 0; k < doseD.length; k++) {
//                        sum += doseD[k];
//                    }
//                    meanDose = sum/doseD.length;
////                    System.out.println("meandose="+meanDose);
//                }

                try {
//                    double ratio = meanDose/(doseParams.PrescriptionDose*100d/doseParams.PrescriptionPercent
//                            *doseParams.NumOfFraction);
//                    System.out.println("ratio="+ratio);
//                    System.out.println("meandose="+meanDose/ratio);
//                    for (int k = 0; k < doseD.length; k++) {
//                        doseD[k]=doseD[k]/ratio;
//                    }
//                    RConnection rc = new RConnection("170.16.253.120");
//                    RConnection rc = new RConnection("127.0.0.1");
//                    rc.assign("dose", doseVector);
//                    rc.assign("distance", distanceVector);

//            String[] tmp = new String[distribute.length];
//            for (int k = 0; k < tmp.length; k++) {
//                tmp[k] = String.valueOf(distribute[k]);
//            }
//            cwd.writeRecord(tmp);
//            cwd.close();


//                    CsvWriter cw = new CsvWriter("/home/p3rtp/ljy/csv/"+plan.getNumber()+"_"+rois[j]+".csv",',',
//                            Charset.forName("UTF-8"));
                    //DVH微分
                    String[] tmp = new String[distribute.length+1];
                    tmp[0]=rois[j];
                    for (int k = 0; k < doseD.length; k++) {
                        distribute[(int) Math.round(doseD[k] / 5)]++;
                    }
                    for (int k = 0; k < distribute.length; k++) {
                        tmp[k+1] = String.valueOf(distribute[k]);
                    }
                    cw.writeRecord(tmp);

                    //DVH积分
                    for (int k = 0; k < distribute.length; k++) {
                        distribute[k] /=data_size;
                    }
                    for (int k = 1; k < distribute.length; k++) {
                        distribute[k] += distribute[k-1];
                    }
                    for (int k = 0; k < distribute.length; k++) {
                        distribute[k] = 1-distribute[k];
                        tmp[k+1] = String.valueOf(distribute[k]);
                    }
                    cw.writeRecord(tmp);
//            c.eval("plot(dose,distance)");
//            c.eval("save(dose,distance,file= '/home/p3rtp/ljy/save.RData')");
//                rc.eval("save(dose,distance,file= 'C:/Users/ME/Desktop/"+plan.getNumber()+"dd.RData')");
//            rc.eval("save(dose,distance,file= '/home/test/"+plan.getNumber()+rois[j]+"dd.RData')");
//            rc.eval("save(dose,distance,file= 'C:/Users/ME/Desktop/ddbladder"+planList.get(0).getNumber()+".RData')");
////                    rc.eval("save(dose,distance,file= 'C:/Users/ME/Desktop/dd"+plan.getNumber()+".RData')");
////                   System.out.println("data saved!");
//                    rc.eval("write.csv(dose,'C:/Users/ME/Desktop/dose.csv')");
//            c.eval("source('/home/p3rtp/ljy/Rscript/DVH.R')");
//                    rc.eval("write.csv(dose,'"+plan.getNumber()+rois[j]+"dose.csv')");

//            System.out.println(rois[j]+" done!");
//            c.eval("write.csv(distance,\"C:/Users/ME/Desktop/distance.csv\")");
//            c.eval("lm1=lm(dose~distance)");
//            c.eval("abline(lm1)");
//            c.eval("summary(lm1)");
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            cw.close();
//            pr.close();
        }
//        for (int k = 0; k < distribute.length; k++) {
//            distribute[k]=distribute[k]/data_size;
//        }



    }

    private void outputDVH(List<Plan> planList,JComboBox[][] Boxes){
        int ROInum = 4;
        HashMap<Double, HashSet<Point2D.Double>>[] Sets = new HashMap[ROInum];
        for (int i = 0; i < planList.size(); i++) {
            Plan plan = planList.get(i);
            String patientPath = rootpath+"pinnacle_patient_expansion"+sep+"NewPatients"+sep+"Institution_"
                    +plan.getInstitution()+sep+"Mount_0"+sep + "Patient_" + plan.getNumber();
            CsvWriter cw = new CsvWriter("/home/p3rtp/ljy/csv/"+plan.getNumber()+"_"+plan.getId()+".csv",',',
                    Charset.forName("UTF-8"));
            String planPath = patientPath + sep+ plan.getId();
            List<String> files = new ArrayList<>();
            try {
                String s;
                BufferedReader file1 = new BufferedReader(new FileReader(planPath+sep+"plan.Trial"));
                while (!(s = DoseParams.readFile(file1,"      DoseVolume = \\XDR:")).equals("-1")) {
                    files.add(s.substring(0,s.lastIndexOf("\\")));
//                System.out.println(s.substring(0,s.lastIndexOf("\\")));
                }
                files.remove(files.size()-1);
            }
            catch (Exception e){
                e.printStackTrace();
            }


            DoseParams doseParams = new DoseParams(planPath + sep+"plan.Trial");
            CTParams ctParams = new CTParams(patientPath+sep+"ImageSet_0.header");
//            float[][][] dose_data = new float[doseParams.ZDim][doseParams.YDim][doseParams.XDim];
//            for (int l = 0; l < dose_data.length; l++) {
//                for (int j = 0; j < dose_data[l].length; j++) {
//                    for (int k = 0; k < dose_data[l][j].length; k++) {
//                        dose_data[l][j][k] = 0f;
//                    }
//                }
//            }

            String[] rois = new String[ROInum];
            for (int j = 0; j < rois.length; j++) {
                rois[j] = (String) Boxes[i][j].getSelectedItem();
            }

            for (int j = 0; j < files.size(); j++) {
                String filePath = planPath+sep+"plan.Trial.binary."+String.format("%0"+3+"d",
                        Integer.parseInt(files.get(j)));
//                readDoseData(filePath, dose_data, doseParams.DoseWeight[j]);
            }
            System.out.println("Dose input finish");
            double meanDose = 0;
            for (int j = 0; j < Sets.length; j++) {

                Sets[j] = readRoiSet("//  ROI: " + rois[j], planPath+sep+"plan.roi",ctParams);
//                double[] DVHdata = DVH(Sets[j],dose_data,ctParams,doseParams);
//                String[] tmp = new String[DVHdata.length+1];
//                tmp[0] = rois[j];
//                for (int k = 1; k < DVHdata.length+1; k++) {
//                    tmp[k] = String.valueOf(DVHdata[k-1]);
//                }
                try {
//                    cw.write(rois[j]);
//                    cw.writeRecord(tmp);

                    System.out.println("CSV write finish");
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
//            cw.close();


        }



    }

    private static double[] DVH(HashMap<Double, HashSet<Point2D.Double>> ROIset, float[][][] dose_data, CTParams ctParams
            , DoseParams doseParams){
        int[] distribute = new int[100];
        double[] c = new double[3];
        int dose_x,dose_y,dose_z;
        double[][] inter = new double[8][3];
        double[] interdose = new double[8];
        double CT_Xstart = ctParams.CTXstart;
        double CT_Ystart = ctParams.CTYstart;
        double CT_Zstart = ctParams.CTZstart;
        double CT_STEP = ctParams.CTstep;
        double CT_STEPZ = ctParams.CTstepZ;
        double dose_Xstart = doseParams.treatParams.get(0).XStart;
        double dose_Ystart = doseParams.treatParams.get(0).YStart;
        double dose_Zstart = doseParams.treatParams.get(0).ZStart;
        double[] probability = new double[100];
        double[] integrated = new double[100];
        for (int i = 0; i < probability.length; i++) {
            probability[i] = 0.0;
        }
        int data_size=0;
        Set <Map.Entry<Double, HashSet<Point2D.Double>>> entrySet=ROIset.entrySet();
        for(Map.Entry<Double, HashSet<Point2D.Double>> entry: entrySet){
            double key = entry.getKey();
            data_size += ROIset.get(key).size();
            Iterator<Point2D.Double> it = ROIset.get(key).iterator();
            for (int i = 0; i < ROIset.get(key).size(); i++) {
                math.geom2d.Point2D tmp = new math.geom2d.Point2D(it.next());
                c[0] = tmp.getX();
                c[1] = tmp.getY();
                c[2] = key;
                dose_x = (int) Math.ceil((CT_Xstart - dose_Xstart) / .4 - 1 + CT_STEP / .4 * (c[0] - CT_Xstart)
                        / CT_STEP);
                dose_y = (int) Math.ceil((CT_Ystart - dose_Ystart) / .4 - 1 + CT_STEP / .4 * (c[1] - CT_Ystart)
                        / CT_STEP);
                dose_z = (int) Math.ceil((CT_Zstart - dose_Zstart) / .4 - 1 + CT_STEPZ / .4 * (c[2] - CT_Zstart)
                        / CT_STEPZ);
                for (int k = 0; k < 2; k++) {
                    for (int l = 0; l < 2; l++) {
                        for (int m = 0; m < 2; m++) {
                            inter[k * 4 + l * 2 + m][0] = dose_Xstart + 0.4 * (dose_x + k);
                            inter[k * 4 + l * 2 + m][1] = dose_Ystart + 0.4 * (dose_y + l);
                            inter[k * 4 + l * 2 + m][2] = dose_Zstart + 0.4 * (dose_z + m);
                            interdose[k * 4 + l * 2 + m] = dose_data[dose_z + m][dose_y + l][dose_x + k];
                        }
                    }
                }
//                double x0 = interpolate(inter, interdose, c) * doseParams.NumOfFraction *
//                        doseParams.UnitsPerFraction / doseParams.PrescriptionPercent * 100;
//                distribute[(int) Math.floor(x0 / 100)]++;
            }
        }
        probability[0] = distribute[0] / data_size;
        for (int i = 1; i < distribute.length; i++) {
            probability[i] = distribute[i]/(double) data_size+probability[i-1];
        }
        for (int i = 0; i < probability.length; i++) {
//            System.out.println(1-probability[i]);
            integrated[i] = 1-probability[i];
        }
        return integrated;
    }

    private static double[] OVH(HashMap<Double, HashSet<Point2D.Double>> ROIset, HashMap<Double,
            ArrayList<Point2D.Double>> PTV){
        int[] distribute = new int[100];
        double[] probability = new double[100];
        double[] integrated = new double[100];
        double distance = 0;
        int data_size=0;
        Set <Map.Entry<Double, HashSet<Point2D.Double>>> entrySet=ROIset.entrySet();
        for(Map.Entry<Double, HashSet<Point2D.Double>> entry: entrySet){
            double key = entry.getKey();
            data_size += ROIset.get(key).size();
            MyPolygon2D ptvPolygon = new MyPolygon2D(PTV.get(key));
            Iterator<Point2D.Double> it = ROIset.get(key).iterator();
            for (int i = 0; i < ROIset.get(key).size(); i++) {
                math.geom2d.Point2D tmp = new math.geom2d.Point2D(it.next());
                distance = -ptvPolygon.boundary().signedDistance(tmp);
                distribute[5+(int) Math.floor(distance)]++;
            }
        }
        probability[0] = distribute[0] / data_size;
        for (int i = 1; i < distribute.length; i++) {
            probability[i] = distribute[i]/(double) data_size+probability[i-1];
        }
        for (int i = 0; i < probability.length; i++) {
            integrated[i] = 1-probability[i];
        }
        return probability;
    }

    private static double interpolate(double [][] inter, double interdose[], double c[]){
        double z0 = ((inter[1][2] - c[2]) * interdose[0] + (c[2] - inter[0][2]) * interdose[1]) / 0.4;
        double z1 = ((inter[3][2] - c[2]) * interdose[2] + (c[2] - inter[2][2]) * interdose[3]) / 0.4;
        double z2 = ((inter[5][2] - c[2]) * interdose[4] + (c[2] - inter[4][2]) * interdose[5]) / 0.4;
        double z3 = ((inter[7][2] - c[2]) * interdose[6] + (c[2] - inter[6][2]) * interdose[7]) / 0.4;
        double y0 = ((inter[2][1] - c[1]) * z0 + (c[1] - inter[0][1]) * z1) / 0.4;
        double y1 = ((inter[6][1] - c[1]) * z2 + (c[1] - inter[4][1]) * z3) / 0.4;
        double x0 = ((inter[4][0] - c[0]) * y0 + (c[0] - inter[0][0]) * y1) / 0.4;
        return x0;
    }

    private static HashMap<Double, HashSet<Point2D.Double>> readRoiSet(String roi,String FilePath,CTParams ctparam){
        List<Point2D.Double> polygon = new ArrayList<>();
        HashMap<Double, HashSet<Point2D.Double>> RoiSet = new HashMap<>();
        HashSet<Point2D.Double> set = new HashSet<>();

        try {
            FileReader file = new FileReader(FilePath);
            BufferedReader breader = new BufferedReader(file);
            String s;
            double CTXstart, CTYstart, CTZstart, Xpix, Ypix, Zpix;
            int Zdim;
            CTXstart = ctparam.CTXstart;
            CTYstart = ctparam.CTYstart;
            CTZstart = ctparam.CTZstart;
            Xpix = ctparam.CTstep;
            Ypix = ctparam.CTstep;
            Zpix = ctparam.CTstepZ;
            Zdim = ctparam.CTZdim;

            BigDecimal b;
//            List<Double> keyList = new ArrayList<>();
            boolean[][][] flag = new boolean[Zdim][512][512];
            for (int i = 0; i < flag.length; i++) {
                for (int j = 0; j < flag[0].length; j++) {
                    for (int k = 0; k < flag[0][0].length; k++) {
                        flag[i][j][k] = false;
                    }
                }
            }
            while ((s = breader.readLine()) != null){
                if (s.equals(roi)) {
                    for (int i = 0; i < 7; i++) {
                        breader.readLine();
                    }
                    String line;
                    double f1, f2, f3 = 0;
                    while (((line = breader.readLine()).indexOf("}")) == -1) {
                        String str[] = line.split(" ");
                        f1 = Double.parseDouble(str[0].trim());
                        f2 = Double.parseDouble(str[1].trim());
                        f3 = Double.parseDouble(str[2].trim());
//                        keyList.add(f3);
                        Point2D.Double polygon_point = new Point2D.Double(f1, f2);
                        polygon.add(polygon_point);
                    }
                    for (int i = 0; i < 512; i++) {
                        for (int j = 0; j < 512; j++) {
                            Point2D.Double tmp = new Point2D.Double(CTXstart + Xpix * i, CTYstart + Ypix * j);
                            if (checkWithJdkGeneralPath(tmp, polygon)) {
                                flag[(int) Math.round((f3 - CTZstart) / Zpix)][i][j] ^= true;
                            }
                        }
                    }
                    polygon.clear();
                }
            }
            for (int i = 0; i < flag.length; i++) {
                for (int j = 0; j < flag[0].length; j++) {
                    for (int k = 0; k < flag[0][0].length; k++) {
                        if (flag[i][j][k])
                            set.add(new Point2D.Double(CTXstart + Xpix * j, CTYstart + Ypix * k));
                    }
                }
                b = new BigDecimal(CTZstart + Zpix * i);

//                b = new BigDecimal(f3);
                double tmp = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                if (!set.isEmpty())
                    RoiSet.put(tmp, (HashSet<Point2D.Double>) cloneObject(set));
                set.clear();
            }
            file.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return RoiSet;
    }

    private static boolean checkWithJdkGeneralPath(Point2D.Double point, List<Point2D.Double> polygon) {
        java.awt.geom.GeneralPath p = new java.awt.geom.GeneralPath();
        Point2D.Double first = polygon.get(0);
        p.moveTo(first.x, first.y);
        for (Point2D.Double d : polygon) {
            p.lineTo(d.x, d.y);
        }
        p.lineTo(first.x, first.y);
        p.closePath();
        return p.contains(point);
    }

    private static Object cloneObject(Object obj) throws Exception{
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(obj);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in =new ObjectInputStream(byteIn);

        return in.readObject();
    }

    private static HashMap<Double,ArrayList<Point2D.Double>> readRoi(String roi,String filepath){
        //List polygons = new ArrayList<ArrayList<math.geom2d.Point2D>>();
        try {
            List<Point2D.Double> polygon = new ArrayList<>();
            FileReader file = new FileReader(filepath);
            BufferedReader breader = new BufferedReader(file);
            String s;
            //String pattern1 = roi;
            HashMap<Double, ArrayList<Point2D.Double>> map = new HashMap<>();
            while ((s = breader.readLine()) != null) {
                if (s.equals(roi)) {
                    for (int i = 0; i < 7; i++) {
                        breader.readLine();
                    }
                    String line;
                    double f1, f2, f3 = 0;
                    while (((line = breader.readLine()).indexOf("}")) == -1) {
                        String str[] = line.split(" ");
                        f1 = Double.parseDouble(str[0].trim());
                        f2 = Double.parseDouble(str[1].trim());
                        f3 = Double.parseDouble(str[2].trim());
                        Point2D.Double polygon_point = new Point2D.Double(f1, f2);
                        polygon.add(polygon_point);
                    }
                    //polygons.add(cloneObject(polygon));
                    if (map.get(f3) != null) {
                        ArrayList<Point2D.Double> tmp = map.get(f3);
                        tmp.addAll(polygon);
                        map.put(f3, (ArrayList<Point2D.Double>) cloneObject(tmp));
                    } else {
                        BigDecimal b = new BigDecimal(f3);
                        map.put(b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(),
                                (ArrayList<Point2D.Double>) cloneObject(polygon));
                    }
                    //System.out.println(map.get(f3));
                    polygon.clear();
                }
            }

            file.close();
            return map;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
