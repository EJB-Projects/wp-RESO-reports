package ru.reso.wp.web.sections.reports.controller;


import org.primefaces.model.TreeNode;

import javax.swing.tree.DefaultTreeModel;
import java.util.HashMap;
import java.util.Map;


public class TreeNodeController {

    private Map<String, String> hashMap = new HashMap<String, String>();
    private TreeNode treeNode;
    TreeParse treeParse = new TreeParse();


    public Map<String, String> getHashMap() {
        return hashMap;
    }

    public void addEntry(String key, String value) {
        this.hashMap.put(key, value);
    }

    public String getServerIDByJSFId(String key){

        return hashMap.get(key).toString();

    }

    public TreeNodeController() {
        this.treeNode = treeParse.Do();
        this.hashMap = treeParse.getMap();

        if (this.hashMap == null){
            System.out.println("MAP IS NULL");
        } else {

            //System.out.println("                --------------------------- MAP IS not null");

            for (Map.Entry<String, String> entry : hashMap.entrySet()) {
                System.out.println(entry.getKey()+" : "+entry.getValue());
            }

        }
    }

    public TreeNodeController(DefaultTreeModel defaultTreeSwingModel) {
        treeParse.setDefaultTreeSwingModel(defaultTreeSwingModel);
        this.treeNode = treeParse.Do();
        this.hashMap = treeParse.getMap();

        if (this.hashMap == null){
            System.out.println("MAP IS NULL");
        } else {

         //   System.out.println("                --------------------------- MAP IS not null");

            for (Map.Entry<String, String> entry : hashMap.entrySet()) {
                System.out.println(entry.getKey()+" : "+entry.getValue());
            }
        }
    }


    public TreeNode getTreeNode() {
        return treeNode;
    }



}

