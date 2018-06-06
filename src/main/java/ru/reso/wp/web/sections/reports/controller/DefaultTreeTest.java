package ru.reso.wp.web.sections.reports.controller;

import javax.swing.*;
import javax.swing.tree.*;

public class DefaultTreeTest {

    final String ROOT = "Корневая запись";
    TreeNode currentNode;
    // Массив листьев деревьев
    final String[] nodes = new String[]{"Отчеты по клиентам", "Персональные данные"};
    final String[] nodes2 = new String[]{"Отчеты по клиентам для ЛК", "Уведомление о расторжении полиса"};
    final String[][] leafs = new String[][]{{"Детализация заполняемости телефонов", "Заполняемость телефонов", "Контактная инфа о страхователе",
            "Поиск банковских счетов партнера", "Проверка наличия карт ТКБ по филиалу", "Санкции", "Список карт ТКБ"},
            {"Анкета клиента - индивидуального предпринимателя", "Анкета клиента - иностранного юр.лица", "Анкета клиента - физ.лица", "Анкета клиента - физ.лица - иностранного публичного должностного лица", "Анкета клиента - юр.лица"}};
    final String[][] leafs2 = new String[][]{{"1111", "Реестр полисов для отправки уведомлений о расторжении"},
            {"Реестр по страхователю", "Реестр страховых событий"}};

    // создание дерева на основе массива
    Object[] data = new Object[]{nodes[0], nodes[1],
            new String[]{leafs[0][0], leafs[0][1], leafs[0][2]}};

    public DefaultTreeModel Do() {

        // Создание древовидной структуры
        DefaultMutableTreeNode treeRoot = new DefaultMutableTreeNode(ROOT);
      //  createNodes(treeRoot);


        // Ветви первого уровня
        DefaultMutableTreeNode drink = new DefaultMutableTreeNode(nodes[0]);
        DefaultMutableTreeNode sweet = new DefaultMutableTreeNode(nodes[1]);


        treeRoot.add(drink);
        treeRoot.add(sweet);


        for ( int i = 0; i < leafs[0].length; i++)
            drink.add(new DefaultMutableTreeNode(leafs[0][i], false));


     //------------------------------
        DefaultMutableTreeNode clientRep12 = new DefaultMutableTreeNode(nodes2[1]);
        DefaultMutableTreeNode clientRep11 = new DefaultMutableTreeNode(nodes2[0]);
        drink.add(clientRep12);
        drink.add(clientRep11);


        for ( int i = 0; i < leafs2[0].length; i++)
            clientRep12.add(new DefaultMutableTreeNode(leafs2[0][i], false));

        for ( int i = 0; i < leafs2[1].length; i++)
            clientRep11.add(new DefaultMutableTreeNode(leafs2[1][i], false));



        for ( int i = 0; i < leafs[1].length; i++)
            sweet.add(new DefaultMutableTreeNode(leafs[1][i], false));



        DefaultTreeModel treeModel1 = new DefaultTreeModel(treeRoot, true);

        return treeModel1;

    }





}
