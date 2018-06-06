package ru.reso.wp.web.sections.reports.controller;


import org.primefaces.model.TreeNode;

import javax.swing.tree.DefaultTreeModel;
import java.util.HashMap;
import java.util.Map;


public class TreeNodeController {

    private Map<String, String> hashMap = new HashMap<String, String>();
    private TreeNode treeNode;
    private TreeNode treeNodeID;
    TreeParse treeParse = new TreeParse();
    private MainBean reportController;


    public Map<String, String> getHashMap() {
        return hashMap;
    }
    public void addEntry(String key, String value) {
        this.hashMap.put(key, value);
    }
    public String getServerIDByJSFId(String key){
        return hashMap.get(key).toString();
    }

    public MainBean getReportController() {
        return reportController;
    }

    public void setReportController(MainBean reportController) {
        this.reportController = reportController;
    }

    public TreeNodeController() {
        this.treeNode = treeParse.Do();
        System.out.println("Мы в конструкторе TreeNodeController");
      //  this.treeNodeID = treeParse.getIDTree();
        this.hashMap = treeParse.getMap();

        if (this.hashMap == null){
            System.out.println("MAP IS NULL");
            //----todo какой-то код
        } else {

            //----todo какой-то код

        }
    }

    public TreeNodeController(MainBean reportController) {

        this.reportController = reportController;
        this.treeNode = treeParse.Do(reportController);
        this.hashMap = treeParse.getMap();

        if (this.hashMap == null){
            System.out.println("MAP IS NULL");
        } else {
        }
    }

    public TreeNodeController(DefaultTreeModel defaultTreeSwingModel) {
        treeParse.setDefaultTreeSwingModel(defaultTreeSwingModel);
        this.treeNode = treeParse.Do();
        this.hashMap = treeParse.getMap();

        if (this.hashMap == null){
            System.out.println("MAP IS NULL");
        } else {
            for (Map.Entry<String, String> entry : hashMap.entrySet()) {
                System.out.println(entry.getKey()+" : "+entry.getValue());
            }
        }
    }

    public TreeNodeController(DefaultTreeModel defaultTreeSwingModel, MainBean reportController) {
        this.reportController = reportController;
        treeParse.setDefaultTreeSwingModel(defaultTreeSwingModel);
        this.treeNode = treeParse.Do(reportController);
        this.hashMap = treeParse.getMap();

        if (this.hashMap == null){
            System.out.println("MAP IS NULL");
        } else {
            for (Map.Entry<String, String> entry : hashMap.entrySet()) {
                System.out.println(entry.getKey()+" : "+entry.getValue());
            }
        }
    }


    public TreeNode getTreeNode() {
        return treeNode;
    }

    public TreeNode getTreeNodeID() {
        System.out.println("Дергаем getTreeNodeID");
      //  System.out.println(treeNodeID.getChildCount());
        this.treeNodeID = treeParse.getIDTree();
        return treeNodeID;
    }


}

