import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;
import org.jdesktop.swingx.JXTree;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.AbstractTreeTableModel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.plaf.ActionMapUIResource;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.*;
import java.lang.reflect.Field;

import javax.swing.JToggleButton.ToggleButtonModel;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ActionMapUIResource;
import java.awt.event.*;
/**
 * Created by ME on 2016/11/9.
 */
public class CheckTreeTableManager extends CheckTreeManager{
    private JXTreeTable treeTable;
    public CheckTreeTableManager(JXTreeTable treeTable, boolean dig, boolean showRootNodeCheckBox){
        super(getTree(treeTable), dig, showRootNodeCheckBox);
        this.treeTable = treeTable;
        treeTable.addMouseListener(treeTableMouseListener);
    }

    protected void setCellRenderer(){
        JXTree jxTree = (JXTree)tree;
        tree.setCellRenderer(renderer=new CheckTreeCellRenderer(jxTree.getWrappedCellRenderer(), selectionModel, showRootNodeCheckBox));
    }

    @Override
    protected void treeChanged(){
        treeTable.revalidate();
        treeTable.repaint();
    }

    private static JTree getTree(JXTreeTable treeTable){
        try{
            Field field = JXTreeTable.class.getDeclaredField("renderer");
            field.setAccessible(true);
            return (JTree)field.get(treeTable);
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    private MouseListener treeTableMouseListener = new MouseAdapter(){
        public void mouseClicked(MouseEvent me){
            JXTreeTable treeTable = (JXTreeTable)me.getComponent();
            if(me.getModifiers()==0 ||
                    me.getModifiers()==InputEvent.BUTTON1_MASK){
                final int count = treeTable.getColumnCount();
                for(int i = count-1; i>= 0; i--){
                    if(treeTable.isHierarchical(i)){
                        int savedHeight = tree.getRowHeight();
                        tree.setRowHeight(treeTable.getRowHeight());
                        MouseEvent pressed = new MouseEvent
                                (tree,
                                        me.getID(),
                                        me.getWhen(),
                                        me.getModifiers(),
                                        me.getX()-treeTable.getCellRect(0, i, false).x,
                                        me.getY(),
                                        me.getClickCount(),
                                        me.isPopupTrigger());
                        tree.dispatchEvent(pressed);
                        // For Mac OS X, we need to dispatch a MOUSE_RELEASED as well
                        MouseEvent released = new MouseEvent
                                (tree,
                                        java.awt.event.MouseEvent.MOUSE_RELEASED,
                                        pressed.getWhen(),
                                        pressed.getModifiers(),
                                        pressed.getX(),
                                        pressed.getY(),
                                        pressed.getClickCount(),
                                        pressed.isPopupTrigger());
                        tree.dispatchEvent(released);
                        tree.setRowHeight(savedHeight);
                        break;
                    }
                }
            }
        }
    };
}

class CheckTreeManager extends MouseAdapter implements TreeSelectionListener, ActionListener{
    protected CheckTreeSelectionModel selectionModel;
    protected JTree tree = new JTree();
    protected boolean showRootNodeCheckBox;
    int hotspot = new JCheckBox().getPreferredSize().width;

    public CheckTreeManager(JTree tree, boolean dig, boolean showRootNodeCheckBox){
        this.tree = tree;
        this.showRootNodeCheckBox = showRootNodeCheckBox;
        selectionModel = new CheckTreeSelectionModel(tree.getModel(), dig);
        setCellRenderer();
        tree.addMouseListener(this);
        tree.registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), JComponent.WHEN_FOCUSED);
        selectionModel.addTreeSelectionListener(this);
    }

    protected CheckTreeCellRenderer renderer;
    protected void setCellRenderer(){
        tree.setCellRenderer(renderer=new CheckTreeCellRenderer(tree.getCellRenderer(), selectionModel, showRootNodeCheckBox));
    }

    protected void treeChanged(){
        tree.treeDidChange();
    }

    private VetoableChangeListener changeListener;
    public void setVetoableChangeListener(VetoableChangeListener changeListener){
        this.changeListener = changeListener;
    }

    public static interface CheckBoxCustomizer{
        public boolean showCheckBox(TreePath path);
    }

    private CheckBoxCustomizer checkBoxCustomer = null;
    public void setCheckBoxCustomer(CheckBoxCustomizer checkBoxCustomer){
        this.checkBoxCustomer = checkBoxCustomer;
        renderer.checkBoxCustomer = checkBoxCustomer;
    }

    public void toggleSelection(TreePath path){
        if(path==null)
            return;

        boolean selected = selectionModel.isPathSelected(path, selectionModel.isDigged());
        if(selectionModel.isDigged() && !selected && selectionModel.isPartiallySelected(path))
            selected = !selected;
        selectionModel.removeTreeSelectionListener(this);

        try{
            if(selected){
                if(changeListener!=null)
                    changeListener.vetoableChange(new PropertyChangeEvent(this, "uncheckSelection", path, null));
                selectionModel.removeSelectionPath(path);
            }else{
                if(changeListener!=null)
                    changeListener.vetoableChange(new PropertyChangeEvent(this, "checkSelection", null, path));
                selectionModel.addSelectionPath(path);
            }
        }catch(PropertyVetoException ex){
            // ignore
        }finally{
            selectionModel.addTreeSelectionListener(this);
            treeChanged();
            TreePath[] selectionPaths = selectionModel.getSelectionPaths();
            if(selectionPaths==null)
                label.setText("Selection:");
            else{
                StringBuffer buf = new StringBuffer();
                for(int i = 0; i<selectionPaths.length; i++)
                    buf.append(selectionPaths[i].getLastPathComponent().toString()+", ");
                String text = buf.toString();
                label.setText("Selection: "+ (text.isEmpty() ? text : text.substring(0, text.length()-2)));
            }
        }
    }

    JLabel label = new JLabel("Selection:");
    public void mouseClicked(MouseEvent me){
        TreePath path = tree.getPathForLocation(me.getX(), me.getY());
        if(path==null)
            return;
        if(checkBoxCustomer!=null && !checkBoxCustomer.showCheckBox(path))
            return;
        if(me.getX()>tree.getPathBounds(path).x+hotspot)
            return;

        toggleSelection(path);
    }

    public CheckTreeSelectionModel getSelectionModel(){
        return selectionModel;
    }

    public void valueChanged(TreeSelectionEvent e){
        tree.treeDidChange();
    }

    public Enumeration getAllCheckedNodes(){
        ArrayList<Object> selected = new ArrayList<Object>();
        for(TreePath treePath : selectionModel.getSelectionPaths()){
            selected.add(treePath.getLastPathComponent());
        }
        if(selectionModel.isDigged())
            return new PreorderEnumeration(tree.getModel(), selected.toArray());
        else
            return Collections.enumeration(selected);
    }

    /*-----------------------------[ ActionListener ]------------------------------*/

    public void actionPerformed(ActionEvent e){
        toggleSelection(tree.getSelectionPath());
    }
}

class CheckTreeSelectionModel extends DefaultTreeSelectionModel{
    private TreeModel model;
    private boolean dig = true;

    public CheckTreeSelectionModel(TreeModel model, boolean dig){
        this.model = model;
        this.dig = dig;
        setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
    }

    public boolean isDigged(){
        return dig;
    }

    // tests whether there is any unselected node in the subtree of given path
    public boolean isPartiallySelected(TreePath path){
        if(isPathSelected(path, true))
            return false;
        TreePath[] selectionPaths = getSelectionPaths();
        if(selectionPaths==null)
            return false;
        for(int j = 0; j<selectionPaths.length; j++){
            if(isDescendant(selectionPaths[j], path))
                return true;
        }
        return false;
    }

    // tells whether given path is selected.
    // if dig is true, then a path is assumed to be selected, if
    // one of its ancestor is selected.
    public boolean isPathSelected(TreePath path, boolean dig){
        if(!dig)
            return super.isPathSelected(path);
        while(path!=null && !super.isPathSelected(path))
            path = path.getParentPath();
        return path!=null;
    }

    // is path1 descendant of path2
    private boolean isDescendant(TreePath path1, TreePath path2){
        return path1.isDescendant(path2);
    }

    public void setSelectionPaths(TreePath[] paths){
        clearSelection();
        if(dig){
            for(int i=0; i<paths.length; i++)
                addSelectionPath(paths[i]);
        }else
            super.setSelectionPaths(paths);
    }

    public void addSelectionPaths(TreePath[] paths){
        if(!dig){
            super.addSelectionPaths(paths);
            return;
        }

        // unselect all descendants of paths[]
        for(int i = 0; i<paths.length; i++){
            TreePath path = paths[i];
            TreePath[] selectionPaths = getSelectionPaths();
            if(selectionPaths==null)
                break;
            ArrayList toBeRemoved = new ArrayList();
            for(int j = 0; j<selectionPaths.length; j++){
                if(isDescendant(selectionPaths[j], path))
                    toBeRemoved.add(selectionPaths[j]);
            }
            super.removeSelectionPaths((TreePath[])toBeRemoved.toArray(new TreePath[0]));
        }

        // if all siblings are selected then unselect them and select parent recursively
        // otherwize just select that path.
        for(int i = 0; i<paths.length; i++){
            TreePath path = paths[i];
            TreePath temp = null;
            while(areSiblingsSelected(path)){
                temp = path;
                if(path.getParentPath()==null)
                    break;
                path = path.getParentPath();
            }
            if(temp!=null){
                if(temp.getParentPath()!=null)
                    addSelectionPath(temp.getParentPath());
                else{
                    if(!isSelectionEmpty())
                        removeSelectionPaths(getSelectionPaths());
                    super.addSelectionPaths(new TreePath[]{temp});
                }
            }else
                super.addSelectionPaths(new TreePath[]{ path});
        }
    }

    // tells whether all siblings of given path are selected.
    private boolean areSiblingsSelected(TreePath path){
        TreePath parent = path.getParentPath();
        if(parent==null)
            return true;
        Object node = path.getLastPathComponent();
        Object parentNode = parent.getLastPathComponent();

        int childCount = model.getChildCount(parentNode);
        for(int i = 0; i<childCount; i++){
            Object childNode = model.getChild(parentNode, i);
            if(childNode==node)
                continue;
            if(!isPathSelected(parent.pathByAddingChild(childNode)))
                return false;
        }
        return true;
    }

    public void removeSelectionPaths(TreePath[] paths){
        if(!dig){
            super.removeSelectionPaths(paths);
            return;
        }

        for(int i = 0; i<paths.length; i++){
            TreePath path = paths[i];
            if(path.getPathCount()==1)
                _removeSelectionPath(path);
            else
                toggleRemoveSelection(path);
        }
    }

    private void _removeSelectionPath(TreePath path){
        ArrayList list = new ArrayList();
        list.add(path);
        for(TreePath selectedPath: super.getSelectionPaths()){
            if(path.isDescendant(selectedPath)){
                list.add(selectedPath);
            }
        }
        super.removeSelectionPaths((TreePath[])list.toArray(new TreePath[list.size()]));
    }

    // if any ancestor node of given path is selected then unselect it
    //  and selection all its descendants except given path and descendants.
    // otherwise just unselect the given path
    private void toggleRemoveSelection(TreePath path){
        Stack stack = new Stack();
        TreePath parent = path.getParentPath();
        while(parent!=null && !isPathSelected(parent)){
            stack.push(parent);
            parent = parent.getParentPath();
        }
        if(parent!=null)
            stack.push(parent);
        else{
            _removeSelectionPath(path);
            return;
        }

        while(!stack.isEmpty()){
            TreePath temp = (TreePath)stack.pop();
            TreePath peekPath = stack.isEmpty() ? path : (TreePath)stack.peek();
            Object node = temp.getLastPathComponent();
            Object peekNode = peekPath.getLastPathComponent();
            int childCount = model.getChildCount(node);
            for(int i = 0; i<childCount; i++){
                Object childNode = model.getChild(node, i);
                if(childNode!=peekNode)
                    super.addSelectionPaths(new TreePath[]{temp.pathByAddingChild(childNode)});
            }
        }
        super.removeSelectionPaths(new TreePath[]{parent});
    }
}

class CheckTreeCellRenderer extends JPanel implements TreeCellRenderer{
    private CheckTreeSelectionModel selectionModel;
    private TreeCellRenderer delegate;
    private boolean showRootNodeCheckBox;
    private TristateCheckBox checkBox = new TristateCheckBox("");
    protected CheckTreeManager.CheckBoxCustomizer checkBoxCustomer;
    public CheckTreeCellRenderer(TreeCellRenderer delegate, CheckTreeSelectionModel selectionModel, boolean showRootNodeCheckBox){
        this.delegate = delegate;
        this.selectionModel = selectionModel;
        this.showRootNodeCheckBox = showRootNodeCheckBox;
        setLayout(new BorderLayout());
        setOpaque(false);
        checkBox.setOpaque(false);
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus){
        Component renderer = delegate.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
        if(!showRootNodeCheckBox && tree.getModel().getRoot()==value)
            return renderer;

        TreePath path = tree.getPathForRow(row);
        if(path!=null){
            if(checkBoxCustomer!=null && !checkBoxCustomer.showCheckBox(path))
                return renderer;
            if(selectionModel.isPathSelected(path, selectionModel.isDigged()))
                checkBox.getTristateModel().setState(TristateState.SELECTED);
            else
                checkBox.getTristateModel().setState(selectionModel.isDigged() && selectionModel.isPartiallySelected(path) ? TristateState.INDETERMINATE : TristateState.DESELECTED);
        }
        removeAll();
        add(checkBox, BorderLayout.WEST);
        add(renderer, BorderLayout.CENTER);
        return this;
    }
}

final class TristateCheckBox extends JCheckBox {
    // Listener on model changes to maintain correct focusability
    private final ChangeListener enableListener =
            new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    TristateCheckBox.this.setFocusable(
                            getModel().isEnabled());
                }
            };

    public TristateCheckBox(String text) {
        this(text, null, TristateState.DESELECTED);
    }

    public TristateCheckBox(String text, Icon icon,
                            TristateState initial) {
        super(text, icon);

        //Set default single model
        setModel(new TristateButtonModel(initial));

        // override action behaviour
        super.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                TristateCheckBox.this.iterateState();
            }
        });
        ActionMap actions = new ActionMapUIResource();
        actions.put("pressed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                TristateCheckBox.this.iterateState();
            }
        });
        actions.put("released", null);
        SwingUtilities.replaceUIActionMap(this, actions);
    }

    // Next two methods implement new API by delegation to model
    public void setIndeterminate() {
        getTristateModel().setIndeterminate();
    }

    public boolean isIndeterminate() {
        return getTristateModel().isIndeterminate();
    }

    public TristateState getState() {
        return getTristateModel().getState();
    }

    //Overrides superclass method
    public void setModel(ButtonModel newModel) {
        super.setModel(newModel);

        //Listen for enable changes
        if (model instanceof TristateButtonModel)
            model.addChangeListener(enableListener);
    }

    //Empty override of superclass method
    public void addMouseListener(MouseListener l) {
    }

    // Mostly delegates to model
    private void iterateState() {
        //Maybe do nothing at all?
        if (!getModel().isEnabled()) return;

        grabFocus();

        // Iterate state
        getTristateModel().iterateState();

        // Fire ActionEvent
        int modifiers = 0;
        AWTEvent currentEvent = EventQueue.getCurrentEvent();
        if (currentEvent instanceof InputEvent) {
            modifiers = ((InputEvent) currentEvent).getModifiers();
        } else if (currentEvent instanceof ActionEvent) {
            modifiers = ((ActionEvent) currentEvent).getModifiers();
        }
        fireActionPerformed(new ActionEvent(this,
                ActionEvent.ACTION_PERFORMED, getText(),
                System.currentTimeMillis(), modifiers));
    }

    //Convenience cast
    public TristateButtonModel getTristateModel() {
        return (TristateButtonModel) super.getModel();
    }
}

class TristateButtonModel extends ToggleButtonModel {
    private TristateState state = TristateState.DESELECTED;

    public TristateButtonModel(TristateState state) {
        setState(state);
    }

    public TristateButtonModel() {
        this(TristateState.DESELECTED);
    }

    public void setIndeterminate() {
        setState(TristateState.INDETERMINATE);
    }

    public boolean isIndeterminate() {
        return state == TristateState.INDETERMINATE;
    }

    // Overrides of superclass methods
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        // Restore state display
        displayState();
    }

    public void setSelected(boolean selected) {
        setState(selected ?
                TristateState.SELECTED : TristateState.DESELECTED);
    }

    // Empty overrides of superclass methods
    public void setArmed(boolean b) {
    }

    public void setPressed(boolean b) {
    }

    void iterateState() {
        setState(state.next());
    }

    public void setState(TristateState state) {
        //Set internal state
        this.state = state;
        displayState();
        if (state == TristateState.INDETERMINATE && isEnabled()) {
            // force the events to fire

            // Send ChangeEvent
            fireStateChanged();

            // Send ItemEvent
            int indeterminate = 3;
            fireItemStateChanged(new ItemEvent(
                    this, ItemEvent.ITEM_STATE_CHANGED, this,
                    indeterminate));
        }
    }

    private void displayState() {
        super.setSelected(state != TristateState.DESELECTED);
        super.setArmed(state == TristateState.INDETERMINATE);
        super.setPressed(state == TristateState.INDETERMINATE);

    }

    public TristateState getState() {
        return state;
    }
}

enum TristateState {
    SELECTED {
        public TristateState next() {
            return INDETERMINATE;
        }
    },
    INDETERMINATE {
        public TristateState next() {
            return DESELECTED;
        }
    },
    DESELECTED {
        public TristateState next() {
            return SELECTED;
        }
    };

    public abstract TristateState next();
}

class PreorderEnumeration implements Enumeration{
    private TreeModel treeModel;
    protected Stack stack;
    private int depth = 0;

    public PreorderEnumeration(TreeModel treeModel){
        this(treeModel, new Object[]{ treeModel.getRoot() });
    }

    public PreorderEnumeration(TreeModel treeModel, Object elements[]){
        this.treeModel = treeModel;
        Vector v = new Vector(elements.length);
        for(Object element: elements)
            v.addElement(element);
        stack = new Stack();
        stack.push(v.elements());
    }

    public boolean hasMoreElements(){
        return (!stack.empty() &&
                ((Enumeration)stack.peek()).hasMoreElements());
    }

    public Object nextElement(){
        Enumeration enumer = (Enumeration)stack.peek();
        Object node = enumer.nextElement();
        depth = enumer instanceof ChildrenEnumeration
                ? ((ChildrenEnumeration)enumer).depth
                : 0;
        if(!enumer.hasMoreElements())
            stack.pop();
        ChildrenEnumeration children = new ChildrenEnumeration(treeModel, node);
        children.depth = depth+1;
        if(children.hasMoreElements()){
            stack.push(children);
        }
        return node;
    }

    public int getDepth(){
        return depth;
    }
}

class ChildrenEnumeration implements Enumeration{
    TreeModel treeModel;
    Object node;
    int index = -1;
    int depth;

    public ChildrenEnumeration(TreeModel treeModel, Object node){
        this.treeModel = treeModel;
        this.node = node;
    }

    public boolean hasMoreElements(){
        return index<treeModel.getChildCount(node)-1;
    }

    public Object nextElement(){
        return treeModel.getChild(node, ++index);
    }
}