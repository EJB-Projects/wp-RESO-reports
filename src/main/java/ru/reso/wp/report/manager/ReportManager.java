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



    final String[][] leafs = new String[][]{{"Детализация заполняемости телефонов", "Заполняемость телефонов", "Контактная инфа о страхователе",
            "Поиск банковских счетов партнера", "Проверка наличия карт ТКБ по филиалу", "Санкции", "Список карт ТКБ"},
            {"Анкета клиента - индивидуального предпринимателя", "Анкета клиента - иностранного юр.лица", "Анкета клиента - физ.лица", "Анкета клиента - физ.лица - иностранного публичного должностного лица", "Анкета клиента - юр.лица"}};
    final String[][] leafs2 = new String[][]{{"1111", "Реестр полисов для отправки уведомлений о расторжении"},
            {"Реестр по страхователю", "Реестр страховых событий"}};
*/
            ReportFolder f = new ReportFolder(268853, "Отчеты по клиентам", 0, 1);
            reportFolders.add(f);
            f = new ReportFolder(8103111, "Персональные данные", 0, 1);
            reportFolders.add(f);

            f = new ReportFolder(8231844, "Детализация заполняемости телефонов", 268853, 2);
            ReportFolder parent = ReportFolder.getReportFolderByID(reportFolders, f.getParentID());
            parent.getSubFolders().add(f);

            f = new ReportFolder(9408629, "Заполняемость телефонов", 268853, 2);
            parent = ReportFolder.getReportFolderByID(reportFolders, f.getParentID());
            parent.getSubFolders().add(f);
            f = new ReportFolder(8503680, "Контактная информация о страхователе", 268853, 2);
            parent = ReportFolder.getReportFolderByID(reportFolders, f.getParentID());
            parent.getSubFolders().add(f);

            f = new ReportFolder(8231839, "Поиск банковских счетов партнера", 268853, 2);
            parent = ReportFolder.getReportFolderByID(reportFolders, f.getParentID());
            parent.getSubFolders().add(f);
            f = new ReportFolder(12968957, "Проверка наличия карт ТКБ по филиалу", 268853, 2);
            parent = ReportFolder.getReportFolderByID(reportFolders, f.getParentID());
            parent.getSubFolders().add(f);
            f = new ReportFolder(28498881, "Санкции", 268853, 2);
            parent = ReportFolder.getReportFolderByID(reportFolders, f.getParentID());
            parent.getSubFolders().add(f);
            f = new ReportFolder(28498881, "Список карт ТКБ", 268853, 2);
            parent = ReportFolder.getReportFolderByID(reportFolders, f.getParentID());
            parent.getSubFolders().add(f);

            f = new ReportFolder(28498881, "Поиск банковских счетов партнера", 268853, 2);
            parent = ReportFolder.getReportFolderByID(reportFolders, f.getParentID());
            parent.getSubFolders().add(f);

            f = new ReportFolder(15137434, "Уведомление о расторжении полиса", 268853, 2);
            parent = ReportFolder.getReportFolderByID(reportFolders, f.getParentID());

            if (parent != null) {
                parent.getSubFolders().add(f);
            }

            f = new ReportFolder(28498885, "Отчеты по клиентам для ЛК", 268853, 2);
            parent = ReportFolder.getReportFolderByID(reportFolders, f.getParentID());

            // вот эту проверку мы для других нод конечно уберем, но потом надо не забыть ее восстановить.
            if (parent != null) {
                parent.getSubFolders().add(f);
            }

            f = new ReportFolder(7774711, "Анкета клиента - индивидуального предпринимателя", 8103111, 2);
            parent = ReportFolder.getReportFolderByID(reportFolders, f.getParentID());
            parent.getSubFolders().add(f);

            f = new ReportFolder(10518454, "Анкета клиента - иностранного юр.лица", 8103111, 2);
            parent = ReportFolder.getReportFolderByID(reportFolders, f.getParentID());
            parent.getSubFolders().add(f);

            f = new ReportFolder(11221820, "Анкета клиента - физ.лица", 8103111, 2);
            parent = ReportFolder.getReportFolderByID(reportFolders, f.getParentID());
            parent.getSubFolders().add(f);

            f = new ReportFolder(11556843, "Анкета клиента - физ.лица -  иностранного публичного должностного лица", 8103111, 2);
            parent = ReportFolder.getReportFolderByID(reportFolders, f.getParentID());
            parent.getSubFolders().add(f);

            f = new ReportFolder(14179895, "Анкета клиента - юр.лица", 8103111, 2);
            parent = ReportFolder.getReportFolderByID(reportFolders, f.getParentID());
            parent.getSubFolders().add(f);


            //-- Заполняем папки отчетами
            setReports("", true, true);
        }
        System.out.println(Arrays.toString(reportFolders.toArray()));
        return reportFolders;
    }

    private void setReports(String s, boolean b, boolean b1) {

        //    while (rs.next()) {
        Report r = new Report("1111", "panel1, panel2", "This is Description", "reportType");
        ReportFolder ff = new ReportFolder(15137435, "Уведомление о расторжении полиса", 15137434, 2);
        ReportFolder parentt = ReportFolder.getReportFolderByID(reportFolders, ff.getParentID());
        //      ReportFolder f = ReportFolder.getReportFolderByID(reportFolders, r.getFolderID());

        //-- Некоторые отчеты из неиспользуемых папок
        if (parentt != null) {
            parentt.getReports().add(r);
        }


        r = new Report("Реестр полисов для отправки уведомлений о расторжении", "panel1, panel2", "This is Description", "reportType");
        ff = new ReportFolder(15137436, "Уведомление о расторжении полиса", 15137434, 2);
        parentt = ReportFolder.getReportFolderByID(reportFolders, ff.getParentID());

        if (parentt != null) {
            parentt.getReports().add(r);
        }


        r = new Report("Реестр по страхователю", "panel1, panel2", "This is Description", "reportType");
        ff = new ReportFolder(15137437, "Отчеты по клиентам для ЛК", 28498885, 2);
        parentt = ReportFolder.getReportFolderByID(reportFolders, ff.getParentID());

        if (parentt != null) {
            parentt.getReports().add(r);
        }

        r = new Report("Реестр страховых событий", "panel1, panel2", "This is Description", "reportType");
        ff = new ReportFolder(15137438, "Отчеты по клиентам для ЛК", 28498885, 2);
        parentt = ReportFolder.getReportFolderByID(reportFolders, ff.getParentID());
        System.out.println("ДОБАВЛЯЕМ ОТЧЕТ   =   " + r.getName());
        parentt.getReports().add(r);
        //   }
        //     rs.close();

    }


}
