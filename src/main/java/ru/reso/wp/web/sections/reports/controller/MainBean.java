package ru.reso.wp.web.sections.reports.controller;

/**
 * MAIN TO-DO LIST
 * <p>
 * <p>
 * todo-done:    надо убрать на хуй этот commandLink с его <f:param.
 * todo-done:     надо найти нормальные id папки/отчета и захерачить их в мапу, потому что пока там ваще пиздец: там id генериться рандомно
 * todo-done:     надо по этому id научиться выставлять правильную папку и отчет. Ну и пока выводить это все в мессадж.
 */


import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.faces.event.ActionEvent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;
import javax.servlet.ServletContext;
import ru.reso.wp.report.models.base.ReportFolder;
import ru.reso.wp.report.manager.ReportManager;
import ru.reso.wp.report.models.base.Report;
import ru.reso.wp.web.consts.Consts;
import ru.reso.wp.web.sections.reports.ResoManagedBean;
import ru.reso.wp.web.sections.reports.model.ReportFolderUserObject;
import java.io.File;
import java.util.*;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import ru.reso.wp.web.sections.reports.model.ReportUserObject;
import javax.naming.NamingException;


@ManagedBean(name = MainBean.BEAN_NAME)
@SessionScoped
public class MainBean extends ResoManagedBean implements Serializable {
    public static final String BEAN_NAME = "mainBean";
    private TreeNode root;

    public String getBeanName() {
        return BEAN_NAME;
    }


    /**
     * Отдельный класс с мапой, связывающий id праймфейсовского дерева и id из БД (у отчета или папки)
     */
    public TreeNodeController treeNodeController;

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


    private TreeNode selectedNode;

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

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
    public void init() {


        try {
            folderTreeModel = initSwingTree();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Прошли инициализацию");

        if (folderTreeModel == null) {

            System.out.println("ЩА БУДЕМ ТЕСТИТЬ КОНТРОЛЛЕР НАШ");
            treeNodeController = new TreeNodeController();

            root = treeNodeController.getTreeNode();
            //root = treeParse.Do();
        } else {

            //  treeParse.setDefaultTreeSwingModel(folderTreeModel);
            //   root = treeParse.Do();

            System.out.println("ЩА БУДЕМ ТЕСТИТЬ КОНТРОЛЛЕР НАШ 2");
            treeNodeController = new TreeNodeController(folderTreeModel);
            root = treeNodeController.getTreeNode();
        }
    }


    public void onNodeSelect(NodeSelectEvent event) {

        try {

            this.selectedNode = (TreeNode) event.getTreeNode();

            System.out.println("мы нажали, событие сработало");

            /**
             * todo Вот эта строчка видимо задает какие действия разрешены для текущего юзера. И ее надо будет потом реализовать.
             *
             * this.getUserSessionController().setSimpleAction(Consts.actions.viewReportList);
             *
             **/

            //-- Сворачиваем/Разворачиваем папку
            ru.reso.wp.web.utils.Utils.rollExpandTreeNode(selectedNode);


            // Определяем - это отчет или папка

            String messageText="";

            if (event.getTreeNode().getType().toString() =="child"){
                // отчет

                // ищем нужный отчет по id
                String reportID = treeNodeController.getServerIDByJSFId(selectedNode.getRowKey());
                System.out.println("ID того что выбрали + " + reportID);
                Report report = Report.getReportByID(getReportFolders(), reportID);

                // понимаем parentid
                int folderID = report.getFolderID();

                // ищем папку по номеру id
                ReportFolder f = ReportFolder.getReportFolderByID(getReportFolders(), folderID);
                System.out.println("мы нашли папку #" + f.getName());
                System.out.println("Сучка, мы тя выловили блин - " + report.getName());
                System.out.println("А папочка у этого отчетика у нас = " + folderID);

                String s = treeNodeController.getServerIDByJSFId(selectedNode.getRowKey()) + " : "+f.getName() + " : " + report.getName() + " : " + String.valueOf(folderID);
                messageText = event.getTreeNode().toString() + " - " + event.getTreeNode().getType().toString() + ".  id = " + selectedNode.getRowKey() + " : " + s;


            } else {
                //папка

                System.out.println("А это папка ");
                String nodeID = treeNodeController.getServerIDByJSFId(selectedNode.getRowKey());


                String s = treeNodeController.getServerIDByJSFId(selectedNode.getRowKey()) + " : "+ nodeID;
                messageText = event.getTreeNode().toString() + " - " + event.getTreeNode().getType().toString() + ".  id = " + selectedNode.getRowKey() + " : " + s;
            }



            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", messageText);
            FacesContext.getCurrentInstance().addMessage(null, message);


            /**
             * todo-done: теперь надо надо еще как-то определить отчет/папка отчетов
             *
             * Тут как может быть? Кейса 2
             *
             * 1) мы щелкнули папку отчета? Окей, взяли id папки
             * 2) мы щелкнули отчет/вроженную папку - взяли id напрямую, вытащили id родительской папки
             *
             */


            /**
             * nodeID представляет собой связку из folderID, reportID Если это
             * так то выбрали "Отчет", если нет то папку
             */
            //  if (a.size() == 2) { -- тут мы типа проверем уровень
            //-- Папка в которой хранится отчет
            //
         //   ReportFolder f = ReportFolder.getReportFolderByID(getReportFolders(), Integer.parseInt(a.get(0)));

            //-- Отчет
            //      Report r = f.getReports().get(Integer.parseInt(a.get(1)));
            //-- Загружаем в отчет SQL выражения
            //       r.setSqlClauses(manager.getReportSQLClauses(r.getId()));
            //-- Выставляем в сессионный бин выбранный отчет
            //      manager.setReport(r);

            /**
             * Потратил пол дня на эту шнягу. На самом деле, если выкинуть всякие хери связанные с текущим юзером, авторизацией и прочим - там миллион наследующихся друг от друга объектов
             * а в итоге просто в свойство action класса UserSessionController подставляется некий экшен ввиде строки.
              */


                  this.getUserSessionController().setSimpleAction(Consts.actions.viewReportForm);
            FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "viewReportForm.xhtml");
            //FacesContext.getCurrentInstance (). getExternalContext (). Redirect (url);


        } catch (Exception e) {
//            errorMessage = Notes.noteInnerError;
//            ru.reso.wp.web.utils.Utils.sendError(e);
        }

    }

    public void onTestButtonPress(ActionEvent actionEvent) {
        System.out.println("We are in onTestButtonPress");
       // FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "   ПИЗДЕЦ", null);
       // FacesContext.getCurrentInstance().addMessage(null, message);


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

        /* Если это клиент */
        // todo до хрена логики упростили и закаментили пока
        // if (((UserEmployeeProfile) this.getUserSessionController().getUser().getProfile()).hasModule(UserConsts.WEB_CLAIM)) {
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

    /**
     * Отчеты. Форма отчета.
     *
     * @return
     */
    public String viewReportForm() {


        return "1";
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
            //folderObject.setText(text);
            folderObject.setText(String.format(Consts.TEMPLATE_SQUARE_TEXT_BRACKET, text, f.getId()));

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
            }
            node.add(folderNode);
        }
    }


}