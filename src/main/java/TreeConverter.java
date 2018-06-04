// IceFaces (Swing) Tree to PrimeFaces Tree converter
//
// Anton Romanov [ROMAB]
//
// 27.04.2018


import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import javax.swing.tree.*;

public class TreeConverter {
    private TreeNode root;


    DefaultTreeTest defaultTreeTest = new DefaultTreeTest();
    DefaultTreeModel defaultTreeSwingModel;


    public void addChildNode(String newNodeName, TreeNode selectedNode) {
        TreeNode newNode = new DefaultTreeNode(newNodeName, selectedNode);
        newNode.getChildren().add(new DefaultTreeNode("Node 1.1"));
    }

    public TreeNode addChildNodeRet(String newNodeName, TreeNode selectedNode) {
        TreeNode newNode = new DefaultTreeNode(newNodeName, selectedNode);
        return newNode;
    }

    public void addLeafs(String newNodeName, TreeNode node) {
        node.getChildren().add(new DefaultTreeNode(newNodeName));
    }


    public TreeNode doConvert() {

        defaultTreeSwingModel = defaultTreeTest.Do();

        // Взяли рут (корень) со свинга
        root = new DefaultTreeNode(defaultTreeSwingModel.getRoot().toString(), null);

        for (int i = 0; i < defaultTreeSwingModel.getChildCount(defaultTreeSwingModel.getRoot()); i++) {

            TreeNode newNode = this.addChildNodeRet((defaultTreeSwingModel.getChild((defaultTreeSwingModel.getRoot()), i).toString()), root);

            // выбираем текущую ноду по итератору цикла чтобы к ней обращаться
            javax.swing.tree.TreeNode o = (javax.swing.tree.TreeNode) defaultTreeSwingModel.getChild(defaultTreeSwingModel.getRoot(), i);

            // Теперь надо понять есть ли дочерние ноды у текущей
            if (defaultTreeSwingModel.getChildCount(o) > 0) {
                for (int j = 0; j < defaultTreeSwingModel.getChildCount(o); j++) {

                    javax.swing.tree.TreeNode leaf = (javax.swing.tree.TreeNode) defaultTreeSwingModel.getChild(o, j);
                    String leafName = "";

                    if (leaf.isLeaf()){
                        leafName = "fucker";
                    } else {

                        leafName = "aaaaaaaaaaaaaaa";
                    }

                  //  this.addLeafs(leaf.toString(), newNode);
                    this.addLeafs(leafName, newNode);

                }
            }
        }

        return root;
    }

}
