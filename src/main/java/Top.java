import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.w3c.dom.Node;

import javax.swing.tree.DefaultTreeModel;

public class Top {

    private TreeNode root;
    DefaultTreeTest defaultTreeTest = new DefaultTreeTest();
    DefaultTreeModel defaultTreeSwingModel;
    boolean nullFlag;
    boolean rightFlaf;

    Node left;
    Node right;
    Node parent;
    String value;


    public Top(Node p, String v){
        parent=p;
        value=v;
    }


    public Top() {
        nullFlag = false;
        defaultTreeSwingModel = defaultTreeTest.Do();
        root = new DefaultTreeNode(defaultTreeSwingModel.getRoot().toString(), null);
    }

    public boolean isNull() {
        return nullFlag;
    }
    public void setNull() {
        nullFlag = true;
    }
    public boolean rightIsNull() {
        return true;
    }

    public void moveRight() {



    }

    public void moveLeft() {

    }

    public void treatment() {
        System.out.println("some treat");
    }


}
