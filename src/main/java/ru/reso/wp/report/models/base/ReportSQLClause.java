/*
 * Document: ReportSQLClause
 * Content: SQL выражение отчета
 * Created on:
 * Author:
 * Description: i3.sqlclause
 */


package ru.reso.wp.report.models.base;

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;




/**
 * SQL выражение, использемое в отчете. Они его хранят судя по всему отдельно
 *
 * @author Nicole
 */
public class ReportSQLClause  implements java.io.Serializable {


    /**
     * ИД
     */
    private int id;
    /**
     * Код отчета
     */
    private String reportID;
    /**
     * Название sql выражения
     */
    private String name;
    /**
     * Порядковый номер
     */
    private int order;
    /**
     * Текст sql запроса
     */
    private Clob sqlTextClob;
    /**
     * Текст sql запроса
     */
    private String sqlText;
    /**
     * Список параметор sql выражения.
     *
     */
    private ArrayList<String> params = new ArrayList<>();

    /**
     * Constructor
     *
     * Вот тут, судя по коду, мы хватаем из аргумента результат (ResultSet rs) и с помощью простого выражения,
     * вот этого - sqlText = rs.getString("clsql").toUpperCase()
     * прям из "результа" вырезаем текст sql-запроса.
     *
     * То есть получается, ResultSet уже тащит код запроса sql в себе.
     *
     *
     * @param rs
     * @throws SQLException
     */
    private ReportSQLClause(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        reportID = rs.getString("report");
        name = rs.getString("sname");
        order = rs.getInt("norder");
//        sqlTextClob = rs.getClob("clsql");
        sqlText = rs.getString("clsql").toUpperCase();
    }

    /**
     * Constructor
     *
     * А тут мы типа создаем сущность предыдущим простым конструктором, но еще парсим параметры запроса и пихаем их в  ArrayList<String> params
     *
     * @param rs
     * @param panels
     * @throws SQLException
     */
    public ReportSQLClause(ResultSet rs, ArrayList<String> panels) throws SQLException {
        this(rs);

        for (String panelName : panels) {
            int indexFrom = sqlText.indexOf(panelName, 0);
            int indexTo = getMinIndexTo(indexFrom);

            while ((indexFrom != -1) && (indexTo != -1)) {
                String param = sqlText.substring(indexFrom, indexTo).trim();
                if (!params.contains(param)) {
                    params.add(param);
                }
                indexFrom = sqlText.indexOf(panelName, indexFrom + 1);
                indexTo = getMinIndexTo(indexFrom);
            }
        }
    }

    /**
     *
     *
     * @param indexFrom
     * @return
     *
     * С этим я пока не очень разобрался, но наверное и не надо. Какой-то метод, который определяет максимальное число (предел) до которого мы циклим в поиске параметров
     * во втором варианте конструктора ReportSQLClause(ResultSet rs, ArrayList<String> panels)
     */
    private int getMinIndexTo(int indexFrom) {
        int result = -1;
        int indexToVar[] = new int[4];
        indexToVar[0] = (sqlText.indexOf(" ", indexFrom) != -1) ? sqlText.indexOf(" ", indexFrom) : 100000;
        indexToVar[1] = (sqlText.indexOf("\n", indexFrom) != -1) ? sqlText.indexOf("\n", indexFrom) : 100000;
        indexToVar[2] = (sqlText.indexOf(")", indexFrom) != -1) ? sqlText.indexOf(")", indexFrom) : 100000;
        indexToVar[3] = (sqlText.indexOf(",", indexFrom) != -1) ? sqlText.indexOf(",", indexFrom) : 100000;
        int min = 100001;
        for (int z = 0; z < indexToVar.length; z++) {
            if (indexToVar[z] < min) {
                min = indexToVar[z];
            }
        }
        result = (min == 100000) ? -1 : min;
        return result;
    }


    /** =========================================================================================================================================================
     *
     *   Дальще идут Геттеры/Сеттеры - это не особо интересно.
     *
     * ==========================================================================================================================================================*/


    /**
     * ИД SQL выражения
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * ИД SQL выражения
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Название SQL выражения
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Название SQL выражения
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Порядковый номер SQL выражения
     *
     * @return
     */
    public int getOrder() {
        return order;
    }

    /**
     * Порядковый номер SQL выражения
     *
     * @param order
     */
    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * ИД отчета в котором используется SQL выражение
     *
     * @return
     */
    public String getReportID() {
        return reportID;
    }

    /**
     * ИД отчета в котором используется SQL выражение
     *
     * @param reportID
     */
    public void setReportID(String reportID) {
        this.reportID = reportID;
    }


    /**
     * Тело SQL выражения
     *
     * @return
     */
    public String getSqlText() {
        return sqlText;
    }

    /**
     * Тело SQL выражения
     *
     * @param sqlText
     */
    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }

    /**
     * Список параметров SQL выражения
     *
     * @return
     */
    public ArrayList<String> getParams() {
        return params;
    }

    /**
     * Список параметров SQL выражения
     *
     * @param params
     */
    public void setParams(ArrayList<String> params) {
        this.params = params;
    }

}
