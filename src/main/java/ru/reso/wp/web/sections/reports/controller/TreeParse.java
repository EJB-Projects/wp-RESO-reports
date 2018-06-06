package ru.reso.wp.web.sections.reports.controller;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import ru.reso.wp.report.models.base.Document;

import javax.swing.tree.DefaultTreeModel;
import java.util.HashMap;
import java.util.Map;


//Конвертер Swing Default Tree (который работает с IceFaces) в PrimeFaces TreeNode
//
// Конвертер с парсером и самописным обходчиком дерева. Парсит (по идее) любые деревья. Построен на рекурсивных for'ах.
//Версия с более или менее причесаным кодом
//
// [ROMAB] 03.05.2018

public class TreeParse {

    private TreeNode root;
    private TreeNode idTree;
    private Boolean idFillTurnOn = false;
    private MainBean reportController;



    public Integer level = 0;
    //private TreeNodeController treeNodeController = new TreeNodeController();
    private Map<String, String> keyMap = new HashMap<String, String>();
    // Тестовая болванка со Свинговым деревом. На боевом объекте (видимо в следующей версии) будем пихать это хозяйство уже как параметр
    DefaultTreeTest defaultTreeTest = new DefaultTreeTest();
    // Свинговое дерево-объект, в который зальем выдачу "болванки" ru.reso.wp.web.sections.reports.controller.DefaultTreeTest
    DefaultTreeModel defaultTreeSwingModel;

    TreeParse() {
        // вот как раз заливаем из "Болванки" (тестовой) данные в свинговое дерево-объект с которым будем работать.
        //  В будущем - это универсальный объкт, куда через параметр будет пихать то, что будем парсить.
        defaultTreeSwingModel = defaultTreeTest.Do();
        // Берем рут (корень) от этого свингового дерева, чтобы от него "плясать" (парсить). Причем берем/задаем именно уже Праймфейсовский корень. root - это праймфесофская верхушка
        root = new DefaultTreeNode(defaultTreeSwingModel.getRoot().toString(), null);
        idTree = new DefaultTreeNode("0", null);
    }

    // Второй конструктор, который подпихивает дерево Swing уже как параметр. Его дальше и будем парсить...
    TreeParse(DefaultTreeModel treeModel) {

        this.defaultTreeSwingModel = treeModel;
        root = new DefaultTreeNode(defaultTreeSwingModel.getRoot().toString(), null);
        idTree = new DefaultTreeNode("0", null);
    }


    public Map getMap() {
        return keyMap;
    }


    // Основной метод, который и запускает парсинг.
    public TreeNode Do() {

        // хватаем количество нодов (детей/ веток) первого уровня.
        int i = this.ChildrenForRootCount(defaultTreeSwingModel);
        // запускаем первый цикл. Он не рекурсивный. Все самое интересное - далее.
        ForCycle(defaultTreeSwingModel, i);
        // Возвращаем уже наполненное праймфейсофское дерево
        return root;

    }

    public TreeNode Do(MainBean reportController) {
       this.reportController = reportController;
        int i = this.ChildrenForRootCount(defaultTreeSwingModel);
        ForCycle(defaultTreeSwingModel, i);
        return root;

    }

    // Этот метод добавляет новую PrimeFace ноду
    // @newNodeName - имя ноды. String
    // @selectedNode - в какую ветку собственно добавлять. То есть кто папа. Тип - TreeNode
    public TreeNode addChildNodeRet(String nodeType, String newNodeName, TreeNode selectedNode) {


        /**
         * Вытаскиваем значение id. Если Это папка - то на хер срезаем отображение idNode. Если отчет - то зачем-то он им нужен в дереве. Кстати, а где-нить есть проверка
         * на null? А если кто что-то пойдет не так и отчет или папка будет нуль? Тут вариант 2.
         *
         * 1) назвать его "Без имени" и чих пых бы с ним. Но что тогда делать с id? Тогда надо какой-то обработки id пихать на событие nodeselect
         * 2) выкидывать какой-то эксепшн наверх с каким-то последующим эзерфрендли месседжем и дальше юзера не пускать.
         *
         * По хорошему надо поставить в todo и обработать это потом
         */

        String convertedNodeName;
        String nodeId;
        String parentNodeId;

        if (nodeType == "child") {
            convertedNodeName = newNodeName;
            nodeId = newNodeName.substring(newNodeName.indexOf('[') + 1, newNodeName.indexOf(']'));
            //parentNodeId = selectedNode.g
            //        newNodeName.substring(newNodeName.indexOf('[') + 1, newNodeName.indexOf(']'));

        } else {
            convertedNodeName = newNodeName.substring(0, newNodeName.indexOf('['));
            nodeId = newNodeName.substring(newNodeName.indexOf('[') + 1, newNodeName.indexOf(']'));
        }

        Document document = new Document(reportController, convertedNodeName, nodeId, nodeType);
        //TreeNode newNode = new DefaultTreeNode(nodeType, new Document(reportController, convertedNodeName, nodeId, nodeType), selectedNode);
        TreeNode newNode = new DefaultTreeNode(nodeType, document, selectedNode);
        document.setCurrentNode(newNode);

        String rowK = newNode.getRowKey();
        keyMap.put(rowK, nodeId);
        return newNode;
    }


    /**
     * Это первый цикл, который запускает обход первого уровня, а из него уже работает основной рекурсивный цикл-парсер
     *
     * @param swingTree - сюда мы подаем собственно наше Свинговое дерево
     * @param count     - Какое количество нодов обходить.
     */
    public void ForCycle(DefaultTreeModel swingTree, int count) {


        for (int i = 0; i < count; i++) {

            /** newNode - это переменная уже типа PrimeFaces, то есть Праймфейсофская нода, куда мы будем пихать все, что найдем далее
             * Мы ее передаем в наш главный рекурсивный метод NodeCycle, чтобы он знал куда добавлять все, что он спарсит.
             * Получается логика такая:
             *
             * - добавили (addChildNodeRet) в PrimeFace дерево ноду из Свинга, метод вернул нам ссылку на эту ноду типа PrimeFace, который мы
             *      пихнем в NodeCycle позже, чтобы он знал куда пихать то, что он спарсит.
             * - взяли у текущей свинговой ноды объект в формате свингового TreeNode.  По нему будет бегать наш рекурсивный NodeCycle.
             */
           // if (idFillTurnOn){
            TreeNode newNode = this.addChildNodeRet("parent", (defaultTreeSwingModel.getChild((defaultTreeSwingModel.getRoot()), i).toString()), root);
          //  TreeNode newNodeID = this.addChildNodeRet("parent", "01", root);


            /**
             * Вытащим ка значение id отчета
             */

        //    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        //   System.out.println(defaultTreeSwingModel.getChild((defaultTreeSwingModel.getRoot()), i));
        //    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

            // выбираем текущую ноду по итератору цикла чтобы к ней обращаться
            javax.swing.tree.TreeNode o = (javax.swing.tree.TreeNode) defaultTreeSwingModel.getChild(swingTree.getRoot(), i);
            level = 1;
            NodeCycle(o, newNode);
        }
    }

    /**
     * Вишенка на торте. Рекурсивный метод-цикл, который собственно все и парсит.
     *
     * @param node        - свинговая TreeNode, по которой бегаем
     * @param currentNode - праймфесовская ветка
     */
    public void NodeCycle(javax.swing.tree.TreeNode node, TreeNode currentNode) {

        /** Надо понять есть ли дочерние ноды у переданной внутрь метода ноды
         *
         *  (тут надо не путать важный момент. !!!! То, что в блоке if фигурирует @defaultTreeSwingModel
         *  - это не значит, что мы вновь и вновь везду и всюду пихаем все дерево, обходим его, хотя вроде бы
         *  работаем с конкретной нодой (в данном случае javax.swing.tree.TreeNode node). Нет!
         *  Просто @defaultTreeSwingModel выступает как объект, у которого мы вызываем метод getChildCount. Но метод
         *  getChildCount работает с детьми не @defaultTreeSwingModel, а конкретной ноды (javax.swing.tree.TreeNode node),
         *  которую мы ему и передаем. То есть он обходит только ее.
         *
         */


        if (defaultTreeSwingModel.getChildCount(node) > 0) {

            // бегаем в цикле до "количества нодов" у основного дерева
            for (int i = 0; i < defaultTreeSwingModel.getChildCount(node); i++) {

                // Снова хватаем свинговую NodeTree (через каст).
                javax.swing.tree.TreeNode leaf = (javax.swing.tree.TreeNode) defaultTreeSwingModel.getChild(node, i);
                // добавляем найденное -> в PrimeFaces ноду

                TreeNode newInternalNode;
               // TreeNode newInternalNodeID;

                //Проверяем уровень вложенности
                //     if (level == 1) {

                //   } else {

                //       newInternalNode = this.addChildNodeRet("child", (leaf.toString() + " - " + level.toString()), currentNode);
                //  }
                /** Еще одна проверка для вызова рекурсии. То есть, если дети есть, вызываем этот же метод
                 *      рекурсивно и все по новой.
                 *
                 *      По хорошему - надо бы еще сделать проверку isLeaf. Но поглядим в следующей версии. Если там нужны листья
                 *      - добавим. Но пока что-то я сомневаюсь, что у Праймфейсофского дерева вообще есть листья.
                 */
                if (defaultTreeSwingModel.getChildCount(leaf) > 0) {
                    Integer l = level + 1;
                    //newInternalNodeID = this.addChildNodeRet("childwchild", "0101", currentNode);
                    newInternalNode = this.addChildNodeRet("childwchild", (leaf.toString() + " - " + l.toString()), currentNode);
                    level = level + 1;
                    NodeCycle(leaf, newInternalNode);
                    level = level - 1;
                } else {
                    Integer l = level + 1;
                    newInternalNode = this.addChildNodeRet("child", (leaf.toString() + " - " + l.toString()), currentNode);
                }
            }
        }

    }

    /**
     * Это метод для подсчета (начального) количества детей верхнего уровня у ноды. На самом деле добавил, просто
     * чтобы сократить громозкость кода в первом цикле
     *
     * @param swingTree - свинговое дерево, которое будем обрабатывать
     * @return - количество детей.
     */
    private int ChildrenForRootCount(DefaultTreeModel swingTree) {
        return defaultTreeSwingModel.getChildCount(swingTree.getRoot());
    }

    public DefaultTreeModel getDefaultTreeSwingModel() {
        return defaultTreeSwingModel;
    }

    public void setDefaultTreeSwingModel(DefaultTreeModel defaultTreeSwingModel) {
        this.defaultTreeSwingModel = defaultTreeSwingModel;
    }

    public TreeNode getIDTree() {
        idFillTurnOn = true;
        int i = this.ChildrenForRootCount(defaultTreeSwingModel);
        ForCycle(defaultTreeSwingModel, i);
        return idTree;

    }

    public MainBean getReportController() {
        return reportController;
    }

    public void setReportController(MainBean reportController) {
        this.reportController = reportController;
    }
}
