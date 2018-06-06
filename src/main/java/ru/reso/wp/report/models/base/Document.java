package ru.reso.wp.report.models.base;

import org.primefaces.model.TreeNode;
import ru.reso.wp.web.sections.reports.controller.MainBean;

import java.io.Serializable;
import javax.faces.event.ActionEvent;


public class Document implements Serializable, Comparable<Document> {

    private String name;
    private String id;
    private String type;
    private MainBean reportController;
    private TreeNode currentNode;

    public TreeNode getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(TreeNode currentNode) {
        this.currentNode = currentNode;
    }

    public Document(String name, String id, String type) {
        this.name = name;
        this.id = id;
        this.type = type;
    }

    public Document(MainBean reportController, String name, String id, String type) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.reportController = reportController;
    }
    public Document(MainBean reportController, String name, String id, String type, TreeNode currentNode) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.reportController = reportController;
        this.currentNode = currentNode;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return id;
    }

    public void setSize(String size) {
        this.id = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Document other = (Document) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

    public int compareTo(Document document) {
        return this.getName().compareTo(document.getName());
    }

    public void nodeClicked(ActionEvent event) {
        reportController.setSelectedNodeObject(this);
    }


}
