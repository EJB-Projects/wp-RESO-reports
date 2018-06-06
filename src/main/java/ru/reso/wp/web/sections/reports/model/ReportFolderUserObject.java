/*
 * Document   : ReportFolderUserObject
 * Content    : IceUserObject for Tree
 * Created on : 22.09.2010
 * Author     : Nicole
 * Description:
 */

package ru.reso.wp.web.sections.reports.model;

import ru.reso.wp.report.models.base.ReportFolder;
import ru.reso.wp.web.models.NodeUserObject;
import javax.faces.event.ActionEvent;
import ru.reso.wp.web.sections.reports.controller.MainBean;
import javax.swing.tree.DefaultMutableTreeNode;

public class ReportFolderUserObject extends NodeUserObject {
//public class ReportFolderUserObject  {

    /**
     * Папка в которой хранятся отчеты
     */
    private ReportFolder folder;
    /**
     *
     */
  //  private MainBean reportController;


    /**
     * Constructor
     *
     * @param defaultMutableTreeNode
     * @param //reportController
     */
  /*  public ReportFolderUserObject(DefaultMutableTreeNode defaultMutableTreeNode, MainBean reportController) {
        super(defaultMutableTreeNode);
        this.reportController = reportController;
    }*/

    public ReportFolderUserObject(DefaultMutableTreeNode defaultMutableTreeNode) {
        super(defaultMutableTreeNode);
     //   this.reportController = null;

    }

    public ReportFolder getFolder() {
        return folder;
    }

    public void setFolder(ReportFolder folder) {
        this.folder = folder;
    }

    /**
     * ID папки
     *
     * @return
     */
    @Override
    public String getId() {
        String result = "";

        if (folder != null) {
            result = "N" + folder.getId();
        }
        return result;
    }

    public boolean getIsCompleted() {
        return true;
    }

    /**
     * Registers a user click with this object and updates the selected node in
     * the TreeBean.
     *
     * @param event that fired this method
     */
    public void nodeClicked(ActionEvent event) {
     //   reportController.setSelectedNodeObject(this);
    }

    @Override
    public String toString() {
        return this.getText();
    }
}
