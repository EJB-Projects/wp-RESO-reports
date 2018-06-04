import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import javax.swing.tree.DefaultTreeModel;
import java.util.Stack;

public class TestRecursive {

    private TreeNode root;
    // private Top top;
    DefaultTreeTest defaultTreeTest = new DefaultTreeTest();
    DefaultTreeModel defaultTreeSwingModel;

    TestRecursive() {
        defaultTreeSwingModel = defaultTreeTest.Do();
        // Взяли рут (корень) со свинга
        root = new DefaultTreeNode(defaultTreeSwingModel.getRoot().toString(), null);

    }

    public void FirstStep() {

        Top top = new Top();
        Stack stack = new Stack<Top>();
        System.out.println("IS NUUUUUL = " + top.isNull());

        while (!top.isNull() || !stack.empty()) {
            if (!stack.empty()) {
                top = (Top) stack.pop();
                System.out.println("pop = " + stack.toString());
                top.treatment();

                if (!top.rightIsNull()) top.moveRight();
                else top.setNull();
            }
            while (!top.isNull()) {
                stack.push(top);
                System.out.println(stack.toString());
                top.moveLeft();
            }
        }


    }

    public TreeNode Do() {

        int i = this.ChildrenforRootCount(defaultTreeSwingModel);
        System.out.println("->" + defaultTreeSwingModel.getRoot().toString());
        ForCycle(defaultTreeSwingModel, i);

        return root;

    }



    public TreeNode addChildNodeRet(String newNodeName, TreeNode selectedNode) {
        TreeNode newNode = new DefaultTreeNode(newNodeName, selectedNode);
        return newNode;
    }

    public void addLeafs(String newNodeName, TreeNode node) {
        //node.getChildren().add(new DefaultTreeNode(newNodeName));
        node.getChildren().add(new DefaultTreeNode(newNodeName));
    }

    public void ForCycle(DefaultTreeModel swingTree, int count) {

        for (int i = 0; i < count; i++) {


            TreeNode newNode = this.addChildNodeRet((defaultTreeSwingModel.getChild((defaultTreeSwingModel.getRoot()), i).toString()), root);

            // выбираем текущую ноду по итератору цикла чтобы к ней обращаться
            javax.swing.tree.TreeNode o = (javax.swing.tree.TreeNode) defaultTreeSwingModel.getChild(swingTree.getRoot(), i);

            //        DefaultTreeModel currentNode = (DefaultTreeModel) swingTree.getChild(swingTree.getRoot(), i);

            //DefaultTreeModel currentNode = (DefaultTreeModel) swingTree.
                      System.out.println("| " + o.toString());
            NodeCycle(o, newNode);
            // если находим потомков, вызываем этот же цикл рекурсивно
            //   if (defaultTreeSwingModel.getChildCount(o) > 0) {

            //         ForCycle(currentNode, defaultTreeSwingModel.getChildCount(o));

            //     }


        }
    }

    public void NodeCycle(javax.swing.tree.TreeNode node, TreeNode currentNode) {

        System.out.println("|______");

        // Теперь надо понять есть ли дочерние ноды у текущей
        if (defaultTreeSwingModel.getChildCount(node) > 0) {

            for (int i = 0; i < defaultTreeSwingModel.getChildCount(node); i++) {


                javax.swing.tree.TreeNode leaf = (javax.swing.tree.TreeNode) defaultTreeSwingModel.getChild(node, i);

                System.out.println("       - " + leaf.toString());
                //this.addLeafs(leaf.toString(), currentNode);
                //TreeNode newInternalNode = this.addChildNodeRet((defaultTreeSwingModel.getChild((defaultTreeSwingModel.getRoot()), i).toString()), root);
                TreeNode newInternalNode = this.addChildNodeRet((leaf.toString()), currentNode);
                // Еще одна проверка для вызова рекурсии
                if (defaultTreeSwingModel.getChildCount(leaf) > 0) {

                    NodeCycle(leaf, newInternalNode);
                }
            }
        }
    }


    private int ChildrenforRootCount(DefaultTreeModel swingTree) {
        return defaultTreeSwingModel.getChildCount(swingTree.getRoot());
    }

    private int ChildrenCount(DefaultTreeModel swingTree, javax.swing.tree.TreeNode node) {
        return defaultTreeSwingModel.getChildCount(node);
    }


}
