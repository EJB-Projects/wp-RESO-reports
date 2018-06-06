package ru.reso.wp.web.sections.reports.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.primefaces.model.TreeNode;
import ru.reso.wp.admin.consts.UserConsts;
import ru.reso.wp.admin.users.employee.UserEmployeeProfile;

import javax.servlet.ServletContext;

import ru.reso.wp.report.models.base.ReportFolder;
import ru.reso.wp.report.manager.ReportManager;
import ru.reso.wp.report.models.base.Report;
import ru.reso.wp.web.consts.Consts;
import ru.reso.wp.web.sections.reports.ResoManagedBean;
import ru.reso.wp.web.sections.reports.model.ReportFolderUserObject;

import java.io.File;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import ru.reso.wp.web.sections.reports.model.ReportFolderUserObject;
import ru.reso.wp.web.sections.reports.model.ReportUserObject;

import javax.faces.model.SelectItem;
import javax.naming.NamingException;
import javax.sql.rowset.WebRowSet;


@ManagedBean(name = MainBean.BEAN_NAME)
@SessionScoped
public class MainBean extends ResoManagedBean implements Serializable {
    public static final String BEAN_NAME = "mainBean";
    private TreeNode root;

    public String getBeanName() {
        return BEAN_NAME;
    }

    /**
     * Дерево папок отчетов
     */
    private DefaultTreeModel folderTreeModel;

    /**
     * Менеджер отчетов
     */
    private ReportManager manager;

    // надо удалить и использовать из user
    private String errorMessage;


    //TreeParse treeParse = new TreeParse(folderTreeModel);
    TreeParse treeParse = new TreeParse();


    /**
     * Пусть к папке проекта, в которой хранятся палели для шаблонов отчетов
     * (*.xhtml)
     */
    private String reportFoldersPath
            = File.separatorChar + Consts.folderSections
            + File.separatorChar + Consts.folderReports
            + File.separatorChar + Consts.folderReportPanels;


    public MainBean() {
        try {
            manager = new ReportManager("User");
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void init(){


        try {
            folderTreeModel =  initSwingTree();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Прошли инициализацию");

           if (folderTreeModel == null) {
               System.out.println("Пиписька is empty");
        //}
            root = treeParse.Do();


        } else {

            treeParse.setDefaultTreeSwingModel(folderTreeModel);
            root = treeParse.Do();


        }


    }

    public TreeNode getRoot() {
        return root;
    }

    public DefaultTreeModel initSwingTree() throws Exception {
        //-- Дерево отчетов
        if (folderTreeModel == null) {

            System.out.println("Да, сцуко, ноль....");
            //-- Корневая вершина
            DefaultMutableTreeNode rootTreeNode = new DefaultMutableTreeNode();

            // Задали наше дерево и ей корневую вершину. Это свинг пока
            folderTreeModel = new DefaultTreeModel(rootTreeNode);

            //-- Строим дерево отчетов
            buildReportFolderTree(rootTreeNode, getReportFolders());
            //-- Добавляем ветку "Часто используемые отчеты"

        }

        return folderTreeModel;
    }

    /**
     * Дерево отчетов
     *
     * @return
     */
    public DefaultTreeModel getFolderTreeModel() throws Exception {
        //-- Дерево отчетов
        if (folderTreeModel == null) {

            //-- Корневая вершина
            DefaultMutableTreeNode rootTreeNode = new DefaultMutableTreeNode();

            //  ReportFolderUserObject rootObject = new ReportFolderUserObject(null, this);


            /**
             * В PrimeFaces userObeject'а нету. Поэтому, к примеру, иконки папок (открыта/закрыта) пришлось подпихивать, оверрайдя родной Праймфесовский css.
             * То есть через свой, юзерский самописный css. Ипался долго, но в принципе работает.
             * Из плюсов то, что пока получается, что это уменьшает количество Java кода. Но пока это как-то криво работает в браузерах Opera и ГуглоХром.
             *
             * Следовательно, вот эти строчки из старого кода уже нахрен не нужны:
             *
             * rootObject.setBranchContractedIcon("../../../xmlhttp/css/xp/css-images/tree_folder_closed.gif");
             * rootObject.setBranchExpandedIcon("../../../xmlhttp/css/xp/css-images/tree_folder_open.gif");
             * rootObject.setText("Root Node");
             * rootObject.setExpanded(true);  // - вот эта х...ня кажется только для IceFaces, чтобы рут раскрылся. Для PF по ходу это нах не надо.
             * rootTreeNode.setUserObject(rootObject);
             */


            // Задали наше дерево и ей корневую вершину. Это свинг пока
            folderTreeModel = new DefaultTreeModel(rootTreeNode);

            //-- Строим дерево отчетов
            buildReportFolderTree(rootTreeNode, getReportFolders());
            //-- Добавляем ветку "Часто используемые отчеты"
            //addUsableReport(rootTreeNode);
        }

        return folderTreeModel;
    }


    /**
     * Отчеты. Загружаем дерево отчетов из БД - Модуль в системе "Рабочее место"
     * <p>
     * ID = 34
     *
     * @return
     * @throws SQLException
     * @throws Exception
     */
    public ArrayList<ReportFolder> getReportFolders() throws SQLException, Exception {

        System.out.println("Мы в getReportFolders...");
        ArrayList<ReportFolder> result = null;
        System.out.println("Прошли ArrayList...");
        /* Если это клиент */
        //    if (((UserEmployeeProfile) this.getUserSessionController().getUser().getProfile()).hasModule(UserConsts.WEB_CLAIM)) {
        if (manager == null) System.out.println("Менеджер - нуль");

        result = manager.getReportFoldersClient();
        System.out.println("Прошли result = manager.getReportFoldersClient();");
        //     } else {
        //         Module module = ((UserEmployeeProfile) this.getUserSessionController().getUser().getProfile()).getModule(34);
        //         result = manager.getReportFolders(module);
        //      }
        return result;
    }

    /**
     * @return
     */
    public String getReportPath(String aPanelFileName) {
        String result = "";

        ServletContext sc = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

        String path = reportFoldersPath + File.separatorChar + aPanelFileName + ".xhtml";

        //  if (!ResoUtils.isEmpty(sc.getRealPath(path))) {
        result = sc.getRealPath(path);
        //  }

        return result;
    }

    /**
     * Проверяем существование панели необходимой для формирования отчета
     *
     * @param aReport
     * @param aSendError
     * @return
     */
    public boolean existsRequiredPanels(Report aReport, boolean aSendError) {
        boolean result = true;

        ArrayList<String> _PanelNameList = aReport.getPanels();

        for (int i = 0; i < _PanelNameList.size(); i++) {
            System.out.println(Arrays.deepToString(new String[]{_PanelNameList.get(i)}));
        }


        for (int i = 0; i < _PanelNameList.size(); i++) {
            String _ReportPath = getReportPath(_PanelNameList.get(i));

            //-- kajam 2017-10-31
       //     File _ReportFile = new File("");

            //-- Static panel
            //     if (!ResoUtils.isEmpty(_ReportPath)) {
      //      _ReportFile = new File(_ReportPath);
            //    }
//            else {
//                result = false;
//                break;
//            }

//----------------------------> Добавляем ещё одно условие при котором будут доступны отчёты с динамическими панелями (!allDynamicPanels.contains(_reportPanels.get(i)))
            //        if (!_ReportFile.exists() && !manager.getUnnecessary().contains(_PanelNameList.get(i)) && !manager.getAllDynamicPanels().contains(_PanelNameList.get(i))) {
            //              if (aSendError) {
            //                 ResoMailUtils.sendEMailInfo(Consts.applicationName,
            //                          String.format(ResoConsts.TEMPLATE_SEND_ERROR,
            //                                 Consts.applicationName,
            //                                  Notes.noteErrorWrongReportPanel, aReport.getId() + " " + _PanelNameList.get(i)));
        }
//---------------------------->
        //            result = false;
        //              break;
        //          }

        //      }
        return result;
    }


    private void buildReportFolderTree(DefaultMutableTreeNode node, ArrayList<ReportFolder> array) {
        //-- Список папок в которых хранятся отчеты
        for (int i = 0; i < array.size(); i++) {
            ReportFolder f = array.get(i);
            DefaultMutableTreeNode folderNode = new DefaultMutableTreeNode();
            //ReportFolderUserObject folderObject = new ReportFolderUserObject(folderNode, this);
            ReportFolderUserObject folderObject = new ReportFolderUserObject(folderNode);
            folderObject.setFolder(f);
            String text = f.getName();
            folderObject.setText(text);
         //   folderObject.setExpanded(false);
            folderNode.setUserObject(folderObject);

            //-- Вложенные папки
            if (f.getSubFolders().size() > 0) {
                buildReportFolderTree(folderNode, f.getSubFolders());
            }

            //-- Список отчетов в папке
            for (int j = 0; j < f.getReports().size(); j++) {
                Report r = f.getReports().get(j);
                DefaultMutableTreeNode reportNode = new DefaultMutableTreeNode();
                ReportUserObject reportObject = new ReportUserObject(reportNode, existsRequiredPanels(r, false), this);
                reportObject.setReport(r);
                reportObject.setCountID(j);
                String reportText = (r.getName().length() > 70) ? r.getName().substring(0, 70) + "..." : r.getName();
                reportObject.setText(String.format(Consts.TEMPLATE_SQUARE_TEXT_BRACKET, reportText, r.getId()));
             //   reportObject.setLeaf(true);
                reportNode.setUserObject(reportObject);
                folderNode.add(reportNode);
                System.out.println("reportObject   =   " + j + " - " + reportObject.toString());
                System.out.println("reportText   =   " + reportText);
            }

      //      node.add( new DefaultMutableTreeNode("Socrates"));
            System.out.println("i   =   " + i + text);
      node.add(folderNode);
        }
    }


}