/**
 * Created by ME on 2016/10/3.
 */
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.jdesktop.swingx.treetable.TreeTableModel;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.TableColumnModel;
import javax.swing.text.AbstractDocument;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Point2D;
import java.io.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.List;

public class mainconnect implements ActionListener,ItemListener {
    JFrame frame = new JFrame("JDB");

    JLabel addrLabel = new JLabel("Server Address");
    JLabel portLabel = new JLabel("Server Port");
    JLabel unLabel = new JLabel("Username");
    JLabel passLabel = new JLabel("Password");
    JLabel dbLabel = new JLabel("Database");
    JLabel cmdLabel =new JLabel("Command");
    JTextField addrText = new JTextField("127.0.0.1");
    JTextField portText = new JTextField("5432");
    JTextField unText = new JTextField("postgres");
    JPasswordField passText = new JPasswordField("59623528");
    JTextField dbText = new JTextField("clinical");
    JTextArea cmdArea = new JTextArea("SELECT * FROM pros.patient WHERE institutionid = 4256\n" +
            "ORDER BY medicalrecordnumber\n" +
            "ASC ",3,60);
    JTextArea queryArea = new JTextArea(10,60);
    JButton connButton = new JButton("Connect");
    JButton queryButton = new JButton("Query");
    JButton discButton = new JButton("Disconnect");
    JButton slcButton = new JButton("Select");
    JButton prcButton = new JButton("Draw");
    Connection c = null;

    JCheckBox checkBox[] = null;
    String result[][] = null;
    String patientPath[] = null;
    DefaultMutableTreeNode treeNode[] = null;
    JTree tree[] = null;

    List<TreePath> treePathList = new ArrayList<>();
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
    public  void go(){
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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(160,180);
//        frame.setSize(600,500);
        frame.pack();
        frame.setVisible(true);
    }
    void connectServer(){

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
    void disConnectServer(){
        try{
            c.close();
            System.out.println("Database Disconnected");
        }
        catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }
    void queryDatabase(){
        JFrame dataFrame = new JFrame("data");
        Container container = dataFrame.getContentPane();
        JPanel dataPanel = new JPanel();
        int rowNum = 0;
        int i = 0;
        Statement stmt = null;
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
            patientPath = new String[rowNum];
            checkBox = new JCheckBox[rowNum];
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

                checkBox[i] = new JCheckBox(result[i][0]);
                treeNode[i] = new DefaultMutableTreeNode(result[i][0]);
                tree[i] = new JTree(treeNode[i]);
                treeNode[i].add(new DefaultMutableTreeNode("Plan_0"));
                treeNode[i].add(new DefaultMutableTreeNode("Plan_1"));
                tree[i].addTreeSelectionListener(new TreeSelectionListener() {
                    public void valueChanged(TreeSelectionEvent e) {
                        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) e.getNewLeadSelectionPath()
                                .getLastPathComponent();
                        System.out.println("Current selected: " + selectedNode + ", leaf: " + selectedNode.isLeaf()
                                + ", originObj: " + selectedNode.getUserObject().getClass());
                    }
                });
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

            for (int j = 0; j < column.length; j++) {
                dataPanel.add(new JLabel(column[j]));
            }
            for (int j = 0; j < rowNum; j++) {
//                dataPanel.add(checkBox[j]);
                dataPanel.add(tree[j]);
                for (int k = 1; k < 8 ; k++) {
                    dataPanel.add(new JLabel(result[j][k]));
                }
            }

            Object data[][] = new Object[rowNum][8];
            for (int j = 0; j < rowNum; j++) {
//                data [j][0] = checkBox[j];

                for (int k = 1; k < 8; k++) {
                    data[j][k] = result[j][k];
                }
            }
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
            File dir = new File("F:\\pinnacle_patient_expansion\\NewPatients\\Institution_" + result[j][5] +
                    "\\Mount_0\\Patient_" + result[j][1]+"\\");
            File[] files = dir.listFiles();
            for (int k = 0; k < files.length; k++) {
                if (files[k].isDirectory()) {
                    if (files[k].getName().contains("Plan")) {
                        List<Trial> trialList = new ArrayList<>();
                        try {
                            String s;
                            BufferedReader file1 = new BufferedReader(new FileReader(files[k].getPath()+"\\plan.Trial"));
                            while (!(s = DoseParams.readFile(file1,"  Name = ")).equals("-1")) {
                                trialList.add(new Trial(s.substring(1,s.length()-1)));
                                System.out.println(s);
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        planList.add(new Plan(files[k].getName(), trialList, result[j][1]));
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
                    treePathList.add(path);
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

    void selectData(){
        List<Trial> trialList = new ArrayList<>();
        List<Plan> planList = new ArrayList<>();

        for (int i = 0; i < treePathList.size(); i++) {
//            System.out.println(treePathList.get(i));
            TreePath treePath = treePathList.get(i);
            Object tmp = treePath.getLastPathComponent();
            if (tmp instanceof Patient) {
                planList.addAll(((Patient) tmp).getPlanList());
            }
            if (tmp instanceof Plan) {
                planList.add((Plan) tmp);
//                ((Patient) treePath.getPathComponent(1)).getMrn() + ((Plan) tmp).getId();
            }
            if (tmp instanceof Trial) {
                planList.add((Plan) treePath.getPathComponent(2));
//                System.out.println(((Patient) treePath.getPathComponent(1)).getMrn() +
//                        ((Plan) treePath.getPathComponent(2)).getId() + ((Trial) tmp).getName());
            }
        }

        JFrame roiframe = new JFrame();
        JPanel mainPanel = new JPanel();
        roiframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Container container = roiframe.getContentPane();
        for (int i = 0; i < planList.size(); i++) {
            Vector<String> roiList = new Vector<>();
            Plan tmp = planList.get(i);
            JPanel jPanel = new JPanel(new GridLayout(5,2));
//            roiframe.setLayout(new GridLayout(planList.size(),1));
            mainPanel.setLayout(new GridLayout(planList.size(),1));
            try {
                String s;
                BufferedReader file1 = new BufferedReader(new FileReader("F:\\pinnacle_patient_expansion" +
                        "\\NewPatients\\Institution_4256\\Mount_0\\Patient_" + tmp.getNumber()+"\\"+tmp.getId()+"\\plan.roi"));
                while (!(s = DoseParams.readFile(file1,"           name: ")).equals("-1")) {
                    roiList.addElement(s);

                    System.out.println(s);
                }
                JComboBox jComboBox = new JComboBox(roiList);
                jPanel.add(new JLabel("Patient:"+tmp.getNumber()+"/"+tmp.getId()));
                jPanel.add(new JLabel());
                jPanel.add(new JLabel("PTV"));
                jPanel.add(new JComboBox(roiList));
//                jPanel.add(new JLabel("CTV"));
//                jPanel.add(new JComboBox(roiList));
                jPanel.add(new JLabel("BLADDER"));
                jPanel.add(new JComboBox(roiList));
//                jPanel.add(new JLabel("BODY"));
//                jPanel.add(new JComboBox(roiList));
                jPanel.add(new JLabel("RIGHT FEMORAL"));
                jPanel.add(new JComboBox(roiList));
                jPanel.add(new JLabel("LEFT FEMORAL"));
                jPanel.add(new JComboBox(roiList));
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
        switch (((JButton)e.getSource()).getText()){
            case "Connect": connectServer();break;
            case "Disconnect": disConnectServer();break;
            case "Query": queryDatabase();break;
            case "Select": selectData();break;
            case "Draw": Draw();break;
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

    void readDoseData(String path, float[][][] dosedata, float weight) {
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
        List<String> files = new ArrayList<>();
        try {
            String s;
            BufferedReader file1 = new BufferedReader(new FileReader("F:\\pinnacle_patient_expansion" +
                    "\\NewPatients\\Institution_4256\\Mount_0\\Patient_" + plan.getNumber()+"\\"+plan.getId()+"\\plan.roi"));
            while (!(s = DoseParams.readFile(file1,"      DoseVolume = \\XDR:")).equals("-1")) {
                files.add(s);
                System.out.println(s);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        ArrayList<String> plans = new ArrayList<>();
        File dir = new File(patientPath[0]+"\\");
        File[] tempList = dir.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isDirectory()) {
                if (tempList[i].getName().contains("Plan"))
                    plans.add(tempList[i].getName());
            }
        }

        System.out.println(plans.size());
        DoseParams doseParams = new DoseParams(patientPath[0] + "\\Plan_0\\plan.Trial");
        CTParams ctParams = new CTParams(patientPath[0]+"\\ImageSet_0.header");
//        HashMap<Double, HashSet<Point2D.Double>> KidneySet =
//                readRoiSet("//  ROI: FERUM-L", patientPath[0]+"\\Plan_0\\plan.roi",ctParams);

        float[][][] dose_data = new float[doseParams.ZDim][doseParams.YDim][doseParams.XDim];
        for (int i = 0; i < dose_data.length; i++) {
            for (int j = 0; j < dose_data[i].length; j++) {
                for (int k = 0; k < dose_data[i][j].length; k++) {
                    dose_data[i][j][k] = 0f;
                }
            }
        }

        long doseFileSize = 0;
        int fileNum = 0;
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
        int j = 0;
        for (int i = 0; i < fileNum; i++) {

            String filePath = patientPath[0]+"\\Plan_0\\plan.Trial.binary."+String.format("%0"+3+"d", i);
            File file = new File(filePath);
//            if (file.length() == doseFileSize) {
//                System.out.println(file.getName());
//                i++;
//                readDoseData(filePath, dose_data, doseParams.DoseWeight[j]);
//                System.out.println(doseParams.DoseWeight[j]);
//                j++;
//            }
        }

        System.out.println("Dose input finish");

//        HashMap<Double, HashSet<Point2D.Double>> gtvSet =
//                readRoiSet("//  ROI: SPINAL CORD", patientPath[0]+ "\\Plan_0\\plan.roi",ctParams);

//        Set <Map.Entry<Double, HashSet<Point2D.Double>>> entrySet=gtvSet.entrySet();
//        for(Map.Entry<Double, HashSet<Point2D.Double>> entry: entrySet){
//            System.out.println(entry.getKey() + ".........." + entry.getValue());
//        }
//        System.out.println(gtvSet.size());
        System.out.println("ROI input finish");
//        double[] DVHdata = DVH(gtvSet,dose_data,ctParams,doseParams,-19.7);

//        Mygraphic mygraphic = new Mygraphic(DVHdata);

        JFrame gFrame = new JFrame();
        gFrame.setSize(700, 700);
        gFrame.setVisible(true);
        gFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        gFrame.getContentPane().add(mygraphic);
//        for (int i = 0; i < DVHdata.length; i++) {
//
//        }
    }

    public static void Draw(){

    }
    public static double[] DVH(HashMap<Double, HashSet<Point2D.Double>> ROIset, float[][][] dose_data, CTParams ctParams
            , DoseParams doseParams, double chosen){
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
        double dose_Xstart = doseParams.XStart;
        double dose_Ystart = doseParams.YStart;
        double dose_Zstart = doseParams.ZStart;
        double[] probability = new double[100];
        double[] integrated = new double[100];
        for (int i = 0; i < probability.length; i++) {
            probability[i] = 0.0;
        }
        int data_size = ROIset.get(chosen).size();
        Iterator<Point2D.Double> it = ROIset.get(chosen).iterator();
        for (int i = 0; i < data_size; i++) {
            math.geom2d.Point2D tmp = new math.geom2d.Point2D(it.next());
            c[0] = tmp.getX();
            c[1] = tmp.getY();
            c[2] = chosen;
            dose_x = (int) Math.ceil((CT_Xstart - dose_Xstart) / .4 - 1 + CT_STEP / .4 * (c[0] - CT_Xstart) / CT_STEP);
            dose_y = (int) Math.ceil((CT_Ystart - dose_Ystart) / .4 - 1 + CT_STEP / .4 * (c[1] - CT_Ystart) / CT_STEP);
            dose_z = (int) Math.ceil((CT_Zstart - dose_Zstart) / .4 - 1 + CT_STEPZ / .4 * (c[2] - CT_Zstart) / CT_STEPZ);
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
            double x0 = interpolate(inter, interdose, c)*doseParams.NumOfFraction*doseParams.UnitsPerFraction/doseParams.PrescriptionPercent*100;
            distribute[(int) Math.floor(x0/100)]++;
        }
        probability[0] = distribute[0]/data_size;
        for (int i = 1; i < distribute.length; i++) {
            probability[i] = distribute[i]/(double) data_size+probability[i-1];
        }
        for (int i = 0; i < probability.length; i++) {
            System.out.println(1-probability[i]);
            integrated[i] = 1-probability[i];
        }
        return integrated;
    }

    public static double interpolate(double [][] inter, double interdose[], double c[]){
        double z0 = ((inter[1][2] - c[2]) * interdose[0] + (c[2] - inter[0][2]) * interdose[1]) / 0.4;
        double z1 = ((inter[3][2] - c[2]) * interdose[2] + (c[2] - inter[2][2]) * interdose[3]) / 0.4;
        double z2 = ((inter[5][2] - c[2]) * interdose[4] + (c[2] - inter[4][2]) * interdose[5]) / 0.4;
        double z3 = ((inter[7][2] - c[2]) * interdose[6] + (c[2] - inter[6][2]) * interdose[7]) / 0.4;
        double y0 = ((inter[2][1] - c[1]) * z0 + (c[1] - inter[0][1]) * z1) / 0.4;
        double y1 = ((inter[6][1] - c[1]) * z2 + (c[1] - inter[4][1]) * z3) / 0.4;
        double x0 = ((inter[4][0] - c[0]) * y0 + (c[0] - inter[0][0]) * y1) / 0.4;
        return x0;
    }

    public static HashMap<Double, HashSet<Point2D.Double>> readRoiSet(String roi,String FilePath,CTParams ctparam){
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
            boolean[][][] flag = new boolean[Zdim][512][512];
            for (int i = 0; i < flag.length; i++) {
                for (int j = 0; j < flag[0].length; j++) {
                    for (int k = 0; k < flag[0][0].length; k++) {
                        flag[i][j][k] = false;
                    }
                }
            }
            while ((s = breader.readLine()) != null){
                if (s.indexOf(roi) == 0) {
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
                    for (int i = 0; i < 512; i++) {
                        for (int j = 0; j < 512; j++) {
                            Point2D.Double tmp = new Point2D.Double(CTXstart + Xpix * i, CTYstart + Ypix * j);
                            if (checkWithJdkGeneralPath(tmp, polygon)) {
                                flag[(int) ((f3 - CTZstart) / Zpix)][i][j] ^= true;
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
                BigDecimal b = new BigDecimal(CTZstart + Zpix * i);
                double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                RoiSet.put(f1, (HashSet<Point2D.Double>) cloneObject(set));
                set.clear();
            }
            file.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return RoiSet;
    }

    public static boolean checkWithJdkGeneralPath(Point2D.Double point, List<Point2D.Double> polygon) {
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

    public static Object cloneObject(Object obj) throws Exception{
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(obj);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in =new ObjectInputStream(byteIn);

        return in.readObject();
    }
}
