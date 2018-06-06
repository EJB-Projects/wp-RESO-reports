package ru.reso.wp.report.models.base;


import ru.reso.common.utils.ResoUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


/**
 * Видимо класс, представляющий сам отчет
 *
 * @author Nicole
 * @rewrite Anton Romanov [ROMAB] 07.05.2018 11:00
 */
public class Report implements java.io.Serializable {


    // Основные поля отчета. Тут все понятно

    /**
     * Кодовое название отчета
     */
    private String id;

    /**
     * ID папки в которой хранится отчет
     */
    private int folderID;

    /**
     * Название отчета
     */
    private String name;

    /**
     * Список панелей
     */
    private String panelString;
    /**
     * Описание
     */
    private String description;

    /**
     * Шаблон
     */
    private ReportBlank reportBlank = null;

    /**
     * Список sql выражений
     */
    private ArrayList<ReportSQLClause> sqlClauses = new ArrayList<ReportSQLClause>();

    /**
     * Тип отчета
     */
    private String reportType;

    /**
     * Источник отчета (из какой БД)
     */
    private ReportSource source = null;

    /**
     * Группа отчета для очередей выполнения
     */
    private int group;


    /**
     * Constructor
     *
     * @param rs
     * @throws SQLException
     */
    public Report(ResultSet rs) throws SQLException {
        id = rs.getString("report");
        folderID = rs.getInt("folder");
        name = rs.getString("name");
        panelString = rs.getString("panels");
        panelString = (panelString == null) ? panelString : panelString.toUpperCase();
        description = rs.getString("description");
        reportType = rs.getString("report_type");
        source = new ReportSource(rs);
        group = rs.getInt("queuegroup");
        if (rs.getInt("blankID") != -1) {
            reportBlank = new ReportBlank(rs);
        }

    }

    /**
     * [ROMAB] 08.05.2018 14:25
     * Метод, чтобы генерить случайные id для тестового конструктора
     *
     * @param min, max
     * @author ROMAB
     */
    private static int getRandomNumberInRange(int min, int max) {

        Random r = new Random();
        //int tempInt = r.nextInt((max - min) + 1) + min;
        return r.nextInt((max - min) + 1) + min;
    }

    /**
     * Constructor
     * <p>
     * [ROMAB] 08.05.2018 14:23.
     * Мой "болваночный" конструктор для тестирования....
     *
     * @param rs
     * @throws SQLException
     */
    public Report(String reportName, String panelsList, String desc, String repType) {
        id = String.valueOf(getRandomNumberInRange(2000, 100000));
        folderID = getRandomNumberInRange(2000, 100000);
        this.name = reportName;
        panelString = panelsList;
        panelString = (panelString == null) ? panelString : panelString.toUpperCase();
        description = desc;
        reportType = repType;
        source = new ReportSource(1, "test");
        group = getRandomNumberInRange(2000, 100000);
        reportBlank = null;
    }

    /**
     * Описание отчета
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Описание отчета
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * ИД папки в которой хранится отчет
     *
     * @return
     */
    public int getFolderID() {
        return folderID;
    }

    /**
     * ИД папки в которой хранится отчет
     *
     * @param folderID
     */
    public void setFolderID(int folderID) {
        this.folderID = folderID;
    }

    /**
     * ИД отчета
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * ИД отчета
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Название отчета
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Название отчета
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Список панелей используемых в отчете
     *
     * @return
     */
    public String getPanelString() {
        return panelString;
    }

    /**
     * Список панелей используемых в отчете
     *
     * @param panelString
     */
    public void setPanelString(String panelString) {
        this.panelString = panelString;
    }

    /**
     * Список панелей используемых в отчете
     *
     * @return
     */
    public ArrayList<String> getPanels() {
        /*
         * Добавляем динамическую панель на уровне отчёта, бред конечно,
         * но с существующим кодом это пока единственная идея.
         */
        ArrayList<String> result;
        result = ResoUtils.getArrayListFromString(panelString, ",");
//        result.add("DynamicPanel");
        return result;
    }

    /**
     * Список SQL выражений используемых в отчете
     *
     * @return
     */
    public ArrayList<ReportSQLClause> getSqlClauses() {
        return sqlClauses;
    }

    /**
     * Список SQL выражений используемых в отчете
     *
     * @param sqlClauses
     */
    public void setSqlClauses(ArrayList<ReportSQLClause> sqlClauses) {
        this.sqlClauses = sqlClauses;
    }

    /**
     * Шаблон отчета
     *
     * @return
     */
    public ReportBlank getReportBlank() {
        return reportBlank;
    }

    /**
     * Шаблон отчета
     *
     * @param reportBlank
     */
    public void setReportBlank(ReportBlank reportBlank) {
        this.reportBlank = reportBlank;
    }

    /**
     * Тип отчета
     *
     * @return
     */
    public String getReportType() {
        return reportType;
    }

    /**
     * Тип отчета
     *
     * @param reportType
     */
    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    /**
     * Источник отчета (БД)
     *
     * @return
     */
    public ReportSource getSource() {
        return source;
    }

    /**
     * Источник отчета (БД)
     *
     * @param source
     */
    public void setSource(ReportSource source) {
        this.source = source;
    }

    /**
     * Тип отчета (EKRtf)
     *
     * @return
     */
    public boolean isRTFType() {
        return reportType.equalsIgnoreCase("EK");
    }

    /**
     * Тип отчета (XLS - GR)
     *
     * @return
     */
    public boolean isXLSType() {
        return reportType.equalsIgnoreCase("GR");
    }

    /**
     * Группа в которую входит отчет
     *
     * @return
     */
    public int getGroup() {
        return group;
    }

    /**
     * Группа в которую входит отчет
     *
     * @param group
     */
    public void setGroup(int group) {
        this.group = group;
    }

    /**
     * Поиск отчето по его коду
     *
     * @param a
     * @param id
     * @return
     */
    public static Report getReportByID(ArrayList<ReportFolder> a, String id) {
        Report result = null;
        Iterator i = a.iterator();

        while ((i.hasNext()) && (result == null)) {
            ReportFolder f = (ReportFolder) i.next();
            for (int j = 0; j < f.getReports().size(); j++) {
                Report r = f.getReports().get(j);
                if (r.getId().equalsIgnoreCase(id)) {
                    result = r;
                }
            }
            if (result == null) {
                result = getReportByID(f.getSubFolders(), id);
            }
        }
        return result;
    }

    /**
     * Возвращает порядковый номер расположения отчета в папке
     *
     * @param f
     * @param reportID
     * @return
     */
    public static int getReportNumberByID(ReportFolder f, String reportID) {
        int i = -1;
        for (int j = 0; j < f.getReports().size(); j++) {
            if (f.getReports().get(j).getId().equalsIgnoreCase(reportID)) {
                i = j;
                break;
            }
        }
        return i;
    }

}
