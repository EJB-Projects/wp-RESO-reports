package ru.reso.wp.web.utils;

import org.primefaces.context.RequestContext;
import org.primefaces.model.TreeNode;
import ru.reso.wp.report.models.base.Document;

public class Utils {

    /**
     * Сворачиваем/Разворачиваем вершины дерева
     *
     * @param selectedNode
     */
    public static void rollExpandTreeNode(TreeNode selectedNode) {

        RequestContext context = RequestContext.getCurrentInstance();

        if (selectedNode != null) {
            if (selectedNode.getChildCount() > 0) {
                if (selectedNode.isExpanded()) {
                    selectedNode.setExpanded(false);
                    context.update("form:tree");
                } else {
                    selectedNode.setExpanded(true);
                    context.update("form:tree");
                }
            }
        }
    }

   public static void defineSelectedTreeNode(TreeNode allTree, Document selectedNode) {


   }


}
