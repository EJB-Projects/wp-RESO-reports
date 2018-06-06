package ru.reso.wp.report.models.base;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Шаблон отчета. То есть, видимо, есть какие-то шаблоны... то есть все отчеты по шаблонам.... бла бла бла
 *
 * @author Nicole
 * @rewrite Anton Romanov [ROMAB] 07.05.2018 11:03
 */
public class ReportBlank implements java.io.Serializable {


    /**
     * ID шаблона
     */
    private int id;

    /**
     * Код шаблона
     */
    private String code;

    /**
     * Название файла шаблона
     */
    private String fileName;

    /**
     * Отображаемое имя шаблона
     */
    private String description;


    /**
     * Constructor
     *
     * Видимо, подразумевается, что
     *
     * @param rs
     * @throws SQLException
     */
    public ReportBlank(ResultSet rs) throws SQLException {
        id = rs.getInt("blank");
        code = rs.getString("id");
        fileName = rs.getString("file_name");
        description = rs.getString("display_name");
    }

    /** =========================================================================================================================================================
     *
     *                                                           Дальще идут Геттеры/Сеттеры - это не особо интересно.
     *
     * ==========================================================================================================================================================*/


    /**
     * Код шаблона отчета
     *
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     * Код шаблона отчета
     *
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Описание шаблона отчета
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Описание шаблона отчета
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Название файла шаблона отчета
     *
     * @return
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Название файла шаблона отчета
     *
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * ИД шаблона отчета
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * ИД шаблона отчета
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }


}
