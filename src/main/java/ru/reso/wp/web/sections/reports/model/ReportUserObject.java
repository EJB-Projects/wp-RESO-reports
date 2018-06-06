/*
 * Document   : ReportUserObject
 * Content    : IceUserObject for Tree
 * Created on : 22.09.2010
 * Author     : Nicole
 * Description: User object for node
 */

package ru.reso.wp.web.sections.reports.model;

import ru.reso.wp.report.models.base.Report;
import javax.faces.event.ActionEvent;
import ru.reso.wp.web.models.NodeUserObject;
import ru.reso.wp.web.sections.reports.controller.MainBean;
import javax.swing.tree.DefaultMutableTreeNode;

public class ReportUserObject  extends NodeUserObject {

    /**
     * Отчет
     */
    private Report report;
    /**
     * Порядковый номер отчета
     *
     * У отчета нет своего числового id, есть только строковый, что неудобно. Не
     * понятно только почему в таблицу не включили числовое ID.
     */
    private int countID;

    /**
     * Признак "Отчет выполнен"
     */
    private boolean isCompleted = false;
    /**
     * Бин
     */
    private MainBean reportController;

    /**
     * Constructor
     *
     * @param defaultMutableTreeNode
     * @param isCompleted
     * @param reportController
     */
    public ReportUserObject(DefaultMutableTreeNode defaultMutableTreeNode, boolean isCompleted, MainBean reportController) {
        super(defaultMutableTreeNode);
        this.isCompleted = isCompleted;
        this.reportController = reportController;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    @Override
    public String getId() {
        String result = "";

        if (report != null) {
            result = "N" + String.valueOf(report.getFolderID()) + "N" + getCountID();
            System.out.println("result   =   " + result);
        }
        return result;
    }

    public int getCountID() {
        return countID;
    }

    public void setCountID(int countID) {
        this.countID = countID;
    }

    public boolean getIsCompleted() {
        return isCompleted;
    }

    /**
     * Registers a user click with this object and updates the selected node in
     * the TreeBean.
     *
     * @param event that fired this method
     */
    public void nodeClicked(ActionEvent event) {
//        reportController.setSelectedNodeObject(this);
    }

    @Override
    public String toString() {
        return this.getText();
    }

}
