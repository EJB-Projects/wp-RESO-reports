import javax.swing.*;
import javax.swing.tree.*;

public class DefaultTreeTest {

    final String ROOT = "Корневая запись";
    // Массив листьев деревьев
    final String[] nodes = new String[]{"Напитки", "Сладости"};
    final String[][] leafs = new String[][]{{"Чай", "Кофе", "Коктейль",
            "Сок", "Морс", "Минералка"},
            {"Пирожное", "Мороженое", "Зефир", "Халва"}};

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
        treeRoot.add(new DefaultMutableTreeNode("Фрукты", true));

        for ( int i = 0; i < leafs[0].length; i++)
            drink.add(new DefaultMutableTreeNode(leafs[0][i], false));

        DefaultTreeModel treeModel1 = new DefaultTreeModel(treeRoot, true);

        return treeModel1;

    }


    public void createNodes(DefaultMutableTreeNode top) {

        DefaultMutableTreeNode category = null;
        DefaultMutableTreeNode book = null;

        category = new DefaultMutableTreeNode("Books for Java Programmers");
        top.add(category);

        //original Tutorial
        book = new DefaultMutableTreeNode("The Java Tutorial: A Short Course on the Basics");
        category.add(book);
        // return null;
    }


}
