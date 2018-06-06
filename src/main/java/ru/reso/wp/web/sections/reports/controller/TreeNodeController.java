package ru.reso.wp.web.sections.reports.controller;


import org.primefaces.model.TreeNode;

import javax.swing.tree.DefaultTreeModel;
import java.util.HashMap;
import java.util.Map;


public class TreeNodeController {

    private Map<String, Integer> hashMap = new HashMap<String, Integer>();
    private TreeNode treeNode;
    TreeParse treeParse = new TreeParse();


    public Map<String, Integer> getHashMap() {
        return hashMap;
    }

    public void addEntry(String string, int i) {
        this.hashMap.put(string, i);
    }

    public String getServerIDByJSFId(String key){

        return hashMap.get(key).toString();

    }

    public TreeNodeController() {
        this.treeNode = treeParse.Do();
        this.hashMap = treeParse.getMap();

        if (this.hashMap == null){
            System.out.println("MAP IS NUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUllll");
        } else {

            System.out.println("                --------------------------- MAP IS not null");

            for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
                System.out.println(entry.getKey()+" : "+entry.getValue());
            }

        }
    }

    public TreeNodeController(DefaultTreeModel defaultTreeSwingModel) {
        treeParse.setDefaultTreeSwingModel(defaultTreeSwingModel);
        this.treeNode = treeParse.Do();
        this.hashMap = treeParse.getMap();

        if (this.hashMap == null){
            System.out.println("MAP IS NUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUllll");
        } else {

            System.out.println("                --------------------------- MAP IS not null");

            for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
                System.out.println(entry.getKey()+" : "+entry.getValue());
            }

        }
    }





    public TreeNode getTreeNode() {

       // this.treeNode = treeParse.Do();
        return treeNode;
    }

    /*public void setTreeNode(TreeNode treeNode) {
        this.treeNode = treeNode;
    }

    */

}

