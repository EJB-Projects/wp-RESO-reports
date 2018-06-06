package ru.reso.wp.report.manager;

import java.io.Serializable;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import org.primefaces.event.NodeSelectEvent;
import javax.faces.context.FacesContext;
import org.primefaces.model.TreeNode;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;


public class EventsView implements Serializable {

    private TreeNode root;

    private TreeNode selectedNode;

  //  @ManagedProperty("#{documentService}")
 //   private DocumentService service;

 //   @PostConstruct
 //   public void init() {
  //      root = service.createDocuments();
 //   }

    public TreeNode getRoot() {
        return root;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

  //  public void setService(DocumentService service) {
  //      this.service = service;
  //  }


    public void onNodeSelect(NodeSelectEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", event.getTreeNode().toString());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

  //  public void onNodeUnselect(NodeUnselectEvent event) {
  //      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Unselected", event.getTreeNode().toString());
 //       FacesContext.getCurrentInstance().addMessage(null, message);
 //   }


}
