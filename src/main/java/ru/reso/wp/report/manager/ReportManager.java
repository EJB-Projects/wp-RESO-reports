package ru.reso.wp.report.manager;

import ru.reso.wp.admin.users.User;
import ru.reso.wp.report.models.base.Report;
import ru.reso.wp.report.models.base.ReportFolder;
import ru.reso.wp.report.models.base.ReportTask;
import ru.reso.wp.report.sql.ReportSQL;
import ru.reso.wp.srv.ResoRemoteObject;
import ru.reso.wp.srv.consts.ResoSrvConsts;
import ru.reso.wp.srv.db.models.StmtParamList;

import javax.naming.NamingException;
import javax.sql.rowset.WebRowSet;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Класс менеджер отчетов
 *
 * @author kajam
 * @rewrite Anton Romanov [ROMAB] 07.05.2018 12:17
 */

public class ReportManager extends ResoRemoteObject implements Serializable {

    /**
     * Пользователь ЛК
     */
    private User user;

    /**
     * Список всех динамических панелей
     */
    private static ArrayList<String> allDynamicPanels = new ArrayList<>();
    /**
     * Время последнего обновления списка динамических панелей
     */
    private static long allDynamicPanelLastUpdateTime = Long.MIN_VALUE;
    /**
     * Список ненужных панелей для web-интерфейса
     */
    private ArrayList<String> unnecessary = new ArrayList<>();


    /**
     * Список корневых папок отчетов
     */
    private ArrayList<ReportFolder> reportFolders = new ArrayList<>();
    /**
     * Выбранный отчет
     */
    private Report report;
    /**
     * Задача на выполнение отчета
     */
    private ReportTask reportTask;


    /**
     * Constructor
     *
     * @param user
     * @throws NamingException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ReportManager(User user) throws NamingException, SQLException, ClassNotFoundException {
        super();

        this.user = user;
        unnecessary.add("PAACTUALRESOSB");
    }

    public ReportManager(String user) throws NamingException, SQLException, ClassNotFoundException {
        super();

        this.user = null;
        unnecessary.add("PAACTUALRESOSB");
    }

    /**
     * Constructor
     *
     * @param user
     * @param aWorkMode
     * @param aPrimaryDBName
     * @throws NamingException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ReportManager(User user, ResoSrvConsts.TAppWorkMode aWorkMode, String aPrimaryDBName) throws NamingException, SQLException, ClassNotFoundException {
        super(aWorkMode, aPrimaryDBName);

        this.user = user;
        unnecessary.add("PAACTUALRESOSB");
    }

    /**
     * Отчеты. Загружаем дерево отчетов из БД - Модуль в системе "Рабочее место"
     * <p>
     * ID = 34
     *
     * @param
     * @return
     * @throws SQLException
     * @throws Exception
     */
    public ArrayList<ReportFolder> getReportFoldersClient() throws SQLException, Exception {

        System.out.println("Мы в getReportFoldersClient...");
        if (reportFolders.isEmpty()) {

            /*         //-- Список ролей пользователя в системе отчеты

// todo до хрена логики упростили и закаментили пока

           String sql = "";
            StmtParamList paramList = new StmtParamList();

            sql = ReportSQL.sqlSelectReportFoldersForClient;

            //paramList.add(new StmtParam(Types.INTEGER, (isDeveloper ? 1 : 0)));

            String rsStr = this.getResobj_EjbDatabaseInteraction().prepareStatementExecuteQuery(sql, paramList);
            WebRowSet rs = ResoDBUtils.decodeWebRowSet(rsStr);

            while (rs.next()) {
                ReportFolder f = new ReportFolder(rs);

                //-- Корневые вершины
                if ((f.getLevel() == 1) && (f.getParentID() == 0)) {
                    reportFolders.add(f);
                } //-- Дочерние вершины
                else {
                    ReportFolder parent = ReportFolder.getReportFolderByID(reportFolders, f.getParentID());

                    if (parent != null) {
                        parent.getSubFolders().add(f);
                    }
                }
            }

            rs.close();
*/
            ReportFolder f = new ReportFolder(268853, "Отчеты по клиентам", 0, 1);
            reportFolders.add(f);
            f = new ReportFolder(8103111, "Персональные данные", 0, 1);
            reportFolders.add(f);




            f = new ReportFolder(15137434, "Уведомление о расторжении полиса", 268853, 2);
            ReportFolder parent = ReportFolder.getReportFolderByID(reportFolders, f.getParentID());

            if (parent != null) {
                parent.getSubFolders().add(f);
            }

            f = new ReportFolder(28498885, "Отчеты по клиентам для ЛК", 268853, 2);
            parent = ReportFolder.getReportFolderByID(reportFolders, f.getParentID());

            // вот эту проверку мы для других нод конечно уберем, но потом надо не забыть ее восстановить.
            if (parent != null) {
                parent.getSubFolders().add(f);
            }

            //-- Заполняем папки отчетами
            setReports("", true, true);
        }
     //   System.out.println(Arrays.toString(reportFolders.toArray()));
        return reportFolders;
    }

    private void setReports(String s, boolean b, boolean b1) {


        Report r = new Report(15137434, "1111", "panel1, panel2", "This is Description", "reportType");
        ReportFolder ff = new ReportFolder(151374341, "Уведомление о расторжении полиса", 15137434, 2);
        ReportFolder parentt = ReportFolder.getReportFolderByID(reportFolders, ff.getParentID());


        if (parentt != null) {
            parentt.getReports().add(r);
        }

        r = new Report(268853, "Детализация заполняемости телефонов", "panel1, panel2", "This is Description", "reportType");
        ff = new ReportFolder(2688531, "Отчеты по клиентам", 268853, 1);
        parentt = ReportFolder.getReportFolderByID(reportFolders, ff.getParentID());
        Report r1 = new Report(268853, "Заполняемость телефонов", "panel1, panel2", "This is Description", "reportType");
        Report r2 = new Report(268853, "Контактная информация о страхователе", "panel1, panel2", "This is Description", "reportType");
        Report r3 = new Report(268853, "Поиск банковских счетов партнера", "panel1, panel2", "This is Description", "reportType");
        Report r4 = new Report(268853, "Проверка наличия карт ТКБ по филиалу", "panel1, panel2", "This is Description", "reportType");
        Report r5 = new Report(268853, "Санкции", "panel1, panel2", "This is Description", "reportType");
        Report r6 = new Report(268853, "Список карт ТКБ", "panel1, panel2", "This is Description", "reportType");
        Report r7 = new Report(268853, "Поиск банковских счетов партнера", "panel1, panel2", "This is Description", "reportType");

        if (parentt != null) {
            parentt.getReports().add(r);
            parentt.getReports().add(r1);
            parentt.getReports().add(r2);
            parentt.getReports().add(r3);
            parentt.getReports().add(r4);
            parentt.getReports().add(r5);
            parentt.getReports().add(r6);
            parentt.getReports().add(r7);
        }


        r = new Report(15137434, "Реестр полисов для отправки уведомлений о расторжении", "panel1, panel2", "This is Description", "reportType");
        ff = new ReportFolder(151374341, "Уведомление о расторжении полиса", 15137434, 2);
        parentt = ReportFolder.getReportFolderByID(reportFolders, ff.getParentID());

        if (parentt != null) {
            parentt.getReports().add(r);
        }


        r = new Report(28498885, "Реестр по страхователю", "panel1, panel2", "This is Description", "reportType");
        ff = new ReportFolder(284988851, "Отчеты по клиентам для ЛК", 28498885, 2);
        parentt = ReportFolder.getReportFolderByID(reportFolders, ff.getParentID());

        if (parentt != null) {
            parentt.getReports().add(r);
        }

        r = new Report(28498885, "Реестр страховых событий", "panel1, panel2", "This is Description", "reportType");
        ff = new ReportFolder(284988852, "Отчеты по клиентам для ЛК", 28498885, 2);
        parentt = ReportFolder.getReportFolderByID(reportFolders, ff.getParentID());
        parentt.getReports().add(r);


        Report r8 = new Report(8103111, "Анкета клиента - индивидуального предпринимателя", "panel1, panel2", "This is Description", "reportType");
        ff = new ReportFolder(81031111, "Персональные данные", 8103111, 1);
        parentt = ReportFolder.getReportFolderByID(reportFolders, ff.getParentID());
        Report r9 = new Report(8103111, "Анкета клиента - иностранного юр.лица", "panel1, panel2", "This is Description", "reportType");
        Report r10 = new Report(8103111, "Анкета клиента - физ.лица", "panel1, panel2", "This is Description", "reportType");
        Report r11 = new Report(8103111, "Анкета клиента - физ.лица -  иностранного публичного должностного лица", "panel1, panel2", "This is Description", "reportType");
        Report r12 = new Report(8103111, "Анкета клиента - юр.лица", "panel1, panel2", "This is Description", "reportType");

        if (parentt != null) {
            parentt.getReports().add(r8);
            parentt.getReports().add(r9);
            parentt.getReports().add(r10);
            parentt.getReports().add(r11);
            parentt.getReports().add(r12);
        }
    }

}
