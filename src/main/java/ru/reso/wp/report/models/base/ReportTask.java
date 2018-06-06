/*
 * Document   : ReportTask
 * Content    : Задания на исполнение отчета
 * Created on :
 * Author     :
 * Description:
 */

package ru.reso.wp.report.models.base;

import ru.reso.common.utils.ResoDateUtils;
import ru.reso.common.utils.ResoUtils;
import ru.reso.wp.report.sql.ReportSQL;
import ru.reso.wp.srv.ResoRemoteObject;
import ru.reso.wp.srv.consts.ResoSrvConsts;
import ru.reso.wp.srv.db.models.StmtParam;
import ru.reso.wp.srv.db.models.StmtParamList;
import ru.reso.wp.srv.db.utils.ResoDBUtils;

import javax.naming.NamingException;
import javax.sql.rowset.WebRowSet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Задания на исполнение отчета
 *
 * @author Nicole
 * @rewrite Anton Romanov [ROMAB] 07.05.2018 14:22
 */
public class ReportTask extends ResoRemoteObject implements java.io.Serializable {

    /**
     * Коды состояний задания
     */
    public static enum State {

        SUSPEND, READY, WAIT, UNLOADED, EXECUTE
    };
    /**
     * Виды состояний задания
     */
    public static String[] StateText = {"Отложен", "Подготовлен", "В очереди", "Выгружен", "Выполняется"};
    /**
     * Виды состояний выполненного отчета
     */
    public static String[] FileStateText = {"Пустой отчет", "Ок"};
    /**
     * ИД задачи на выполнение отчета
     */
    private int id;
    /**
     * Состояние задания
     */
    private String state = null;
    /**
     * Дата создания задания
     */
    private Calendar createDate = Calendar.getInstance();
    /**
     * Дата начала исполнения задания
     */
    private Calendar startDate = Calendar.getInstance();
    /**
     * Дата к которой отчет должен быть выполнен
     */
    private Calendar executeDate = Calendar.getInstance();
    /**
     * SQL выражение
     */
    private String sqlText;
    /**
     * Ошибка исполнения
     */
    private String error = null;
    /**
     * Параметры ekRTF шаблона
     */
    private String ekRTFParams;
    /**
     * Заголовок задания
     */
    private String header;
    /**
     * Шаблон отчета
     */
    private byte[] outputFileByte = null;

    /**
     * Файл
     */
//    private Blob outputFileBlob = null;
    /**
     * Признак того что задание удалено
     */
    private boolean deleted = false;
    /**
     * Исполняемый отчет
     */
    private Report report = null;

    /**
     * Constructor
     *
     * @param aWorkMode
     * @param aPrimaryDBName
     * @throws NamingException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ReportTask(ResoSrvConsts.TAppWorkMode aWorkMode, String aPrimaryDBName) throws NamingException, SQLException, ClassNotFoundException {
        super(aWorkMode, aPrimaryDBName);
    }

    /**
     * Constructor
     *
     * @param rs
     * @param r
     * @throws SQLException
     */
    public ReportTask(ResultSet rs, Report r) throws NamingException, SQLException, ClassNotFoundException {
        super();

        id = rs.getInt("taskid");
        report = r;
        state = rs.getString("state");
        //-- Кладем в календарь данные времени
        rs.getTime("admdate", createDate);
        //-- Добавляем данные даты
        createDate = ResoDateUtils.getDateCalendarTime2Calendar(rs.getDate("admdate"), createDate);
        rs.getTime("start_time", startDate);
        startDate = ResoDateUtils.getDateCalendarTime2Calendar(rs.getDate("start_time"), startDate);

        if (rs.getDate("execute_date") != null) {
            rs.getTime("execute_date", executeDate);
            executeDate = ResoDateUtils.getDateCalendarTime2Calendar(rs.getDate("execute_date"), executeDate);
        } else {
            executeDate = null;
        }


        sqlText = (rs.getBytes("clsql") != null) ? new String(rs.getBytes("clsql")).toUpperCase() : "";
        error = rs.getString("error_text");
        ekRTFParams = rs.getString("ekrtf_params");
        header = rs.getString("htext");

//-- [kajam 2017-11-23]
        byte[] outputFile = null;

/** [ROMAB] - 2018-05-07: тут типа должна быть проверка видимо: есть файл (создан ли уже) или еще нет.
 *
 * Но по коду какая-то херь. Мы outputFile объявляем нулем. И спрашиваем "а нет ли у нас случайно файла, которого на самом деле нет"...
 *
 * Получается по идее (я правда не знаю какой там должен быть, так как класса у меня нет), что он вернет true. То есть изЭмпти. То есть да, пустой.
 * ... И получается, что строчка  outputFileByte = outputFile не выполняется ни при каких условиях....
 * */
        if (!ResoUtils.isEmpty(outputFile)) {
            outputFileByte = outputFile;
        }

        deleted = ((rs.getString("deleted").equalsIgnoreCase("Д")));
    }

    /**
     * Constructor
     *
     * @param report
     * @param sqlText
     * @param ekRTFParams
     * @param header
     */
    public ReportTask(Report report, String sqlText, String ekRTFParams, String header) throws NamingException, SQLException, ClassNotFoundException {
        super();

        this.report = report;
        this.state = "WAIT";
        this.executeDate = null;
        this.sqlText = sqlText;
        this.ekRTFParams = ekRTFParams;
        this.header = header;
        this.deleted = false;
    }

    /**
     * Дата создания задания
     *
     * @return
     */
    public Calendar getCreateDate() {
        return createDate;
    }

    /**
     * Дата создания задания
     *
     * @param createDate
     */
    public void setCreateDate(Calendar createDate) {
        this.createDate = createDate;
    }

    /**
     * Задание удалено
     *
     * @return
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * Задание удалено
     *
     * @param deleted
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * Параметры ekRTF шаблона
     *
     * @return
     */
    public String getEkRTFParams() {
        return ekRTFParams;
    }

    /**
     * Параметры ekRTF шаблона
     *
     * @param ekRTFParams
     */
    public void setEkRTFParams(String ekRTFParams) {
        this.ekRTFParams = ekRTFParams;
    }

    /**
     * Ошибка исполнения
     *
     * @return
     */
    public String getError() {
        return error;
    }

    /**
     * Ошибка исполнения
     *
     * @param error
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * Дата к которой отчет должен быть выполнен
     *
     * @return
     */
    public Calendar getExecuteDate() {
        return executeDate;
    }

    /**
     * Дата к которой отчет должен быть выполнен
     *
     * @param executeDate
     */
    public void setExecuteDate(Calendar executeDate) {
        this.executeDate = executeDate;
    }

    /**
     * Заголовок задания
     *
     * @return
     */
    public String getHeader() {
        return header;
    }

    /**
     * Заголовок задания
     *
     * @param header
     */
    public void setHeader(String header) {
        this.header = header;
    }

    /**
     * ИД задания
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * ИД задания
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Report output file
     *
     * @return
     */
    public byte[] getOutputFileByte() {
        //-- get file
        if (ResoUtils.isEmpty(outputFileByte)) {
            StmtParamList _paramList = new StmtParamList();

            try {
                _paramList.add(new StmtParam(Types.INTEGER, this.getId()));

                String _rsStr = this.getResobj_EjbDatabaseInteraction().prepareStatementExecuteQuery(ReportSQL.sqlUserReportTaskFileByID, _paramList);

               // Короче мы типа должны отконвертить вот это вот _rsStr в типа WebRowSet. Пока забьем хер на это
                WebRowSet _rs = ResoDBUtils.decodeWebRowSet(_rsStr);

                while (_rs.next()) {
                    byte[] outputFile = _rs.getBytes("output_file");

                    if (!ResoUtils.isEmpty(outputFile)) {
                        outputFileByte = outputFile;
                    }
                }

                _rs.close();
            } catch (SQLException ex) {
                //--
            }
        }

        return outputFileByte;
    }

    /**
     * Шаблон отчета
     *
     * @param outputFileByte
     */
    public void setOutputFileByte(byte[] outputFileByte) {
        this.outputFileByte = outputFileByte;
    }

    /**
     * SQL выражение
     *
     * @return
     */
    public String getSqlText() {
        return sqlText;
    }

    /**
     * SQL выражение
     *
     * @param sqlText
     */
    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }

    /**
     * Дата начала исполнения задания
     *
     * @return
     */
    public Calendar getStartDate() {
        return startDate;
    }

    /**
     * Дата начала исполнения задания
     *
     * @param startDate
     */
    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    /**
     * Состояние задания
     *
     * @return
     */
    public String getState() {
        return state;
    }

    /**
     * Состояние задания
     *
     * @param state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Исполняемый отчет
     *
     * @return
     */
    public Report getReport() {
        return report;
    }

    /**
     * Исполняемый отчет
     *
     * @param report
     */
    public void setReport(Report report) {
        this.report = report;
    }

    /**
     * EKRtf тип отчета
     *
     * @return
     */
    public boolean isRTFType() {
        return report.isRTFType();
    }

    /**
     * XLS тип отчета
     *
     * @return
     */
    public boolean isXLSType() {
        return report.isXLSType();
    }

    /**
     * HTML тип содержания отчета
     *
     * @return
     */
    public String getHtmlContentType() {
        if (isXLSType()) {
            return "application/vnd.ms-excel";
        } else {
            return "application/msword";
        }
    }

    /**
     * Корректный отчет
     *
     * @return
     */
    public boolean isCorrect() {
        return ((error == null) || (error.isEmpty()));
    }

    /**
     * Состояни отчета (текст)
     *
     * @return
     */
    public String getStateText() {
        String result = state;
        if (State.valueOf(state).ordinal() <= StateText.length) {
            result = StateText[State.valueOf(state).ordinal()];
        }
        return result;
    }

    /**
     * Состояние файла (текст)
     *
     * @return
     */
    public String getFileStateText() {
        String result = FileStateText[1];
        if (((outputFileByte == null) || (outputFileByte.length == 0)) && ((State.valueOf(state).ordinal() == 1) || (State.valueOf(state).ordinal() == 3))) {
            result = FileStateText[0];
        }
        return result;
    }

    /**
     * Поиск задания на исполение отчета по ИД
     *
     * @param a
     * @param id
     * @return
     */
    public static ReportTask getReportTaskByID(ArrayList<ReportTask> a, int id) {
        ReportTask result = null;
        for (ReportTask a1 : a) {
            if (a1.getId() == id) {
                result = a1;
                break;
            }
        }
        return result;
    }






}
