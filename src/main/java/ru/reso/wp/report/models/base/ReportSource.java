/*
 * Document   : ReportSource
 * Content    : Источник отчета
 * Created on : 07.05.2018 11:21
 * Author     : ROMAB
 * Description: Для какой БД создан отчет.
 */
package ru.reso.wp.report.models.base;


import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Источник отчета (БД)
 *
 * @author Nicole
 * @rewrite Anton Romanov [ROMAB] 07.05.2018 11:21
 *
 */
public class ReportSource  implements java.io.Serializable {

    /**
     * ID БД
     */
    private int id;
    /**
     * Alias БД
     */
    private String name;

    /**
     * Constructor
     *
     * @param rs
     * @throws SQLException
     */
    public ReportSource(ResultSet rs) throws SQLException {
        id = rs.getInt("source_id");
        name = rs.getString("alias");
    }

    public ReportSource(int dbId, String dbName) {
        id = dbId;
        name = dbName;
    }

    /**
     * ИД БД источника отчета
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * ИД БД источника отчета
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Название БД источника отчета
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Название БД источника отчета
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }


}
