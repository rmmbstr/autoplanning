package one;

import org.jdesktop.swingx.treetable.AbstractTreeTableModel;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ME on 2016/11/9.
 */
public class TestTreeTableModel extends AbstractTreeTableModel {
    private final static String[] COLUMN_NAMES = {"Id", "Name", "Doj", "Photo"};
    TestTreeTableModel(Object root){
        super(root);
    }
    MyTreeNode treeNode[] = null;
    TestTreeTableModel(String[][] result){
        treeNode = new MyTreeNode[result.length];
        for (int i = 0; i < treeNode.length; i++) {
            for (int j = 0; j < 8; j++) {
            try {
                treeNode[i] = new MyTreeNode(result[i][0],result[i][1]);
                treeNode[i].data = result[i];
            }
            catch (Exception e){
                e.printStackTrace();
            }
            }
        }
        System.out.println(treeNode[0].data[0]);
    }

    public int getColumnCount(){
        return 8;
    }

    public Object getValueAt(Object node, int column){
        System.out.println(node.getClass());
        MyTreeNode treenode = (MyTreeNode) node;
        return treenode.data[column];
    }

    public Object getChild(Object o, int i){
        return ((TreeNode)o).getChildAt(i);
    }

    public int getChildCount(Object o){
        return ((TreeNode)o).getChildCount();
    }

    public int getIndexOfChild(Object o, Object o2){
        return ((TreeNode)o).getIndex((TreeNode)o2);
    }

    public String getColumnName( int column )
    {
        switch( column )
        {
            case 0: return "medicalrecordnumber";
            case 1: return "patientid";
            case 2: return "firstname";
            case 3: return "lastname";
            case 4: return "middlename";
            case 5: return "institutionid";
            case 6: return "gender";
            case 7: return "patientpath";
            default: return "Unknown";
        }
    }
}

class MyTreeNode
{
    String[] data = null;
    private String name;
    private String description;
    private List<MyTreeNode> children = new ArrayList<MyTreeNode>();

    public MyTreeNode()
    {
    }

    public MyTreeNode( String name, String description )
    {
        this.name = name;
        this.description = description;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public List<MyTreeNode> getChildren()
    {
        return children;
    }

    public String toString()
    {
        return "MyTreeNode: " + name + ", " + description;
    }
}
