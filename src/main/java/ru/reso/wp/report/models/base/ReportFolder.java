package ru.reso.wp.report.models.base;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Папка в которой хранятся отчеты
 *
 * @author Nicole
 * @rewrite Anton Romanov [ROMAB] 07.05.2018 10:35
 *
 */
 public class ReportFolder extends Folder implements Serializable {

    /**
     * Список вложенных папок
     */
    private ArrayList<ReportFolder> subFolders;
    /**
     * Список вложенных отчетов
     */
    private ArrayList<Report> reports;

    /**
     * Constructor
     *
     * @param rs
     * @throws SQLException
     */
    public ReportFolder(ResultSet rs) throws SQLException {
        super(rs);
        reports = new ArrayList<Report>();
        subFolders = new ArrayList<ReportFolder>();
    }

    public ReportFolder(int id, String name, int parentID, int level){
        super(id, name, parentID, level);
        reports = new ArrayList<Report>();
        subFolders = new ArrayList<ReportFolder>();
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    /**
     * Список отчетов в папке
     *
     * @return
     */
    public ArrayList<Report> getReports() {
        return reports;
    }

    /**
     * Список отчетов в папке
     *
     * @param reports
     */
    public void setReports(ArrayList<Report> reports) {
        this.reports = reports;
    }

    /**
     * Список вложенных папок
     *
     * @return
     */
    @Override
    public ArrayList<ReportFolder> getSubFolders() {
        return subFolders;
    }

    /**
     * Список вложенных папок
     *
     * @param subFolders
     */
    @Override
    public void setSubFolders(ArrayList subFolders) {
        this.subFolders = subFolders;
    }

    /**
     * Поиск папки по ИД
     *
     * @param a
     * @param id
     * @return
     */
    public static ReportFolder getReportFolderByID(ArrayList<ReportFolder> a, int id) {
        ReportFolder result = null;
        Iterator i = a.iterator();

        while ((i.hasNext()) && (result == null)) {
            ReportFolder f = (ReportFolder) i.next();

            if (f.getId() == id) {
                result = f;
            } else {
                result = getReportFolderByID(f.getSubFolders(), id);
            }
        }
        return result;
    }

    /**
     * Моя собственная заглушка. Заливаем отчеты без подключения к БД тестовыми холостыми значениями.
     *
     * @return ReportFolder
     */

    public static ReportFolder fillReportFoldersByAnton() {

        ReportFolder result = null;



        return result;

    }



}
