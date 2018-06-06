package ru.reso.wp.report.sql;


/**
 *
 * Какие-то запросы. хз пока что это....
 *
 */
public interface ReportSQL {


    /**
     * Список папок в которых хранятся отчеты (Клиентские отчеты)
     */
    String sqlSelectReportFoldersForClient
            = "select distinct i3.r_folders.folder as id, "
            //+ "                        nvl(i3.r_folders.parent, 0) as parentid, "
            + "                        0 as parentid, "
            + "                        i3.r_folders.name, 1 as \"level\" "
            + "          from i3.r_folders "
            + "          where i3.r_folders.name='Отчеты по клиентам для ЛК' ";

    /**
     * Список папок в которых хранятся отчеты (Полный доступ)
     */
    String sqlSelectReportFoldersForAdmin
            = "select temp.*, level "
            + "  from (select distinct i3.r_folders.folder as id, "
            + "                        nvl(i3.r_folders.parent, 0) as parentid, "
            + "                        i3.r_folders.name "
            + "          from i3.r_folders "
            + "          connect by prior parent = i3.r_folders.folder "
            + "          start with i3.r_folders.folder in ( "
            + "            select distinct i3.reports.folder "
            + "              from i3.reports "
            + "              where i3.reports.actual = 'Д' "
            + "                and i3.reports.bisdevelop = decode(?, 0, 0,i3.reports.bisdevelop))) temp "
            + "  start with parentid = 0 "
            + "  connect by prior id=parentid "
            + "  order by level, name ";

    /**
     * [selnp] - Список папок в которых хранятся отчеты
     */
    String sqlSelectReportFoldersForUser
            = "select temp.*, level "
            + "  from (select distinct i3.r_folders.folder as id, "
            + "                        nvl(i3.r_folders.parent, 0) as parentid, "
            + "                        i3.r_folders.name "
            + "          from i3.r_folders "
            + "          connect by prior parent = i3.r_folders.folder "
            + "          start with i3.r_folders.folder in ( "
            + "            select distinct i3.reports.folder "
            + "              from i3.reports, i3.report_access "
            + "              where i3.reports.actual = 'Д'"
            + "                and i3.reports.bisdevelop = ? "
            + "                and i3.reports.report = i3.report_access.report "
            + "                and i3.report_access.r_id in ("
            + "                  select rg.r_id "
            + "                    from i3.report_groups rg "
            + "                    where rg.r_name in (!TEMP_VAR)) "
            + "                      and i3.reports.folder not in ( "
            + "                        select rf.folder "
            + "                          from i3.r_folders rf "
            + "                          start with rf.parent in ("
            + "                            select r1.folder "
            + "                            from i3.r_folders r1 "
            + "                            where upper(r1.name) like upper('%не использовать%')) "
            + "                          connect by prior rf.folder = rf.parent "
            + "                        union all "
            + "                        select r1.folder "
            + "                          from i3.r_folders r1 "
            + "                          where upper(r1.name) like upper('%не использовать%') "
            + "                        union all "
            + "                        select rf.folder "
            + "                          from i3.r_folders rf "
            + "                          start with rf.parent in ("
            + "                            select r1.folder "
            + "                              from i3.r_folders r1 "
            + "                              where upper(r1.name) like upper('удаленные')) "
            + "                          connect by prior rf.folder = rf.parent "
            + "                        union all "
            + "                        select r1.folder "
            + "                          from i3.r_folders r1 "
            + "                          where upper(r1.name) like upper('удаленные')))"
            + "          order siblings by folder) temp "
            + "  start with parentid = 0 "
            + "  connect by prior id = parentid "
            + "  order by level, name";

    /**
     * Отчеты. Список SQL выражений выполняемых в отчете.
     */
    String sqlSelectReportsSQLClausesByReportID
            = "select * from i3.sqlclause where report = ? order by norder";

    /**
     * Список отчетов доступных разработчику
     */
    String sqlSelectReportsForAdmin
            = "select r.report, r.folder, r.name, r.panels, r.description, "
            + "       r.report_type, r.queuegroup, nvl(r.blank, -1) as blankID, "
            + "       rs.alias, r.source_id, "
            + "       b.blank, b.id, b.file_name, b.display_name "
            + "  from i3.reports r, "
            + "       i3.report_sources rs, "
            + "       i3.blanks b "
            + "  where r.actual = 'Д' and "
            + "        r.bisdevelop = decode(?, 0, 0, r.bisdevelop) and "
            + "        r.blank = b.blank(+) and "
            + "        rs.id (+) = r.source_id "
            + "  order by r.name";

    /**
     * Список отчетов доступных пользователю
     */
    String sqlSelectReportsForUser
            = "select r.report, r.folder, r.name, r.panels, r.description, "
            + "       r.report_type, r.queuegroup, nvl(r.blank, -1) as blankID, "
            + "       rs.alias, r.source_id, "
            + "       b.blank, b.id, b.file_name, b.display_name "
            + "  from i3.reports r, "
            + "       i3.report_sources rs, "
            + "       i3.blanks b "
            + "  where  r.actual = 'Д' and "
            + "         r.bisdevelop = decode(?, 0, 0, r.bisdevelop) and "
            + "         exists ( "
            + "           select 1 "
            + "             from i3.report_access ra, i3.report_groups rg "
            + "             where report=r.report "
            + "               and ra.r_id = rg.r_id "
            + "               and rg.r_name in ( !TEMP_VAR ) "
            + "         ) "
            + "    and rs.id (+) = r.source_id "
            + "    and r.report_type <> 'QR' "
            + "    and r.blank = b.blank(+) "
            + "    and r.folder not in (  "
            + "      select rf.folder "
            + "        from i3.r_folders rf "
            + "        start with rf.parent in (select r1.folder "
            + "                                   from i3.r_folders r1 "
            + "                                   where upper(r1.name) like "
            + "                                         upper('%не использовать%')) "
            + "        connect by prior rf.folder = rf.parent "
            + "      union all "
            + "      select r1.folder "
            + "        from i3.r_folders r1 "
            + "        where upper(r1.name) like upper('%не использовать%') "
            + "      union all "
            + "      select rf.folder "
            + "        from i3.r_folders rf "
            + "        start with rf.parent in (select r1.folder "
            + "                                   from i3.r_folders r1 "
            + "                                   where upper(r1.name) like upper('удаленные')) "
            + "        connect by prior rf.folder = rf.parent "
            + "      union all "
            + "      select r1.folder "
            + "        from i3.r_folders r1 where upper(r1.name) like upper('удаленные') "
            + ") order by name";

    /**
     *
     */
    String sqlSelectNextValue
            = "select I3.SEQ_GEN_REPORTS.NEXTVAL as id from dual";

    /**
     * Добавление отчета в очередб на выполнение
     */
    String sqlInsertReportTask
            = "insert into i3.report_queue_schedule ("
            + "    taskid, userid, computername, report, rqgid, state, admdate, "
            + "    start_time, ekrtf_params, htext, report_type, deleted, clsql) "
            + "values ("
            + "    ?, ?, 'AgentResoRu', ?, ?, ?, to_date( ?, 'DD.MM.YYYY HH24:MI:SS'), "
            + "    to_date( ?, 'DD.MM.YYYY HH24:MI:SS'), ?, ?, ?, 'Н', ? )";

    /**
     * Delete report task older 10 days
     */
    String sqlUpdateReportTaskOutputFileByPeriod
            = "update i3.report_queue_schedule set output_file=empty_blob(), deleted='Д' where userid=? and (sysdate-nvl(execute_date,sysdate)) >= 10";

    /**
     * Report task list
     */
    String sqlSelectUserReportTasks =
//              "select rq.* "
            "select rq.taskid, rq.state, rq.admdate, rq.start_time, rq.execute_date, rq.clsql, rq.error_text, rq.ekrtf_params, rq.htext, rq.deleted, rq.report,"
                    + "    r.name as report_name"
                    + "  from i3.report_queue_schedule rq, i3.reports r "
                    + "  where r.report = rq.report                     "
                    + "    and rq.userid = ?                            "
                    + "    and rq.deleted = 'Н'                         "
                    + "  order by rq.admdate desc";

    /**
     * Report task output file
     */
    String sqlUserReportTaskFileByID = "select output_file from i3.report_queue_schedule where taskid = ?";

    /**
     * Часто используемые отчеты
     */
    String sqlSelectUsableReportByUserName
            = "select report, count(1) as report_count from i3.report_audit where admuser=? and start_time > sysdate-30 group by report order by count(1) desc";

    /**
     *
     */
    String sqlUpdateReportTaskStateByTaskID
            = "update i3.report_queue_schedule set state=? where userid=? and taskid=?";

    /**
     * Dynamic panel settings
     */
    String sqlSelectDynamicPanelByID
            = "select "
            + "    panels.*, "
            + "    parametrs.sql_parametrs, "
            + "    parametrs.ekrtf_parametrs "
            + "from i3.report_panels panels "
            + "    left join (select "
            + "        UPPER(panel_eng_name) as panel, "
            + "        wm_concat(sql_parametr) as sql_parametrs, "
            + "        wm_concat(ekrtf_parametr) as ekrtf_parametrs "
            + "    from i3.report_boss_panels "
            + "    group by UPPER(panel_eng_name) ) parametrs "
            + "    on ? = parametrs.panel "
            + "where UPPER(panels.panel)=?";

    /**
     *
     */
    String sqlSelectUserReportTaskBlob
            = "select output_file              "
            + "  from i3.report_queue_schedule "
            + "  where userid = ?              "
            + "    and deleted = 'Н'           "
            + "    and taskid = ?";

    /**
     *
     */
    String sqlUpdateReportTaskDeletedByTaskID
            = "update i3.report_queue_schedule set deleted='Д', output_file=empty_blob() where userid=? and taskid=?";

    /**
     *
     */
    String sqlUpdateReportTaskDeletedByTaskIDs
            = "update i3.report_queue_schedule set deleted='Д', output_file=empty_blob() where userid=? and taskid in (%s)";

    /**
     * Dinamic panels
     */
    String sqlSelectAllDynamicPanel
            = "select PANEL from i3.report_panels where ptype in ('PTCOMBOBOX', 'PTEDIT', 'PTMTREELIST')";




}
