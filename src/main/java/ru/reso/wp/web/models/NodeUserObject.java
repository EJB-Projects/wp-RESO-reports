/*
 * Document   : NodeUserObject
 * Content    : Wrapper for DefaultMutableTreeNode
 * Created on : 22.09.2010
 * Author     : Nicole
 * Description: Обертка для вершины дерева
 */

package ru.reso.wp.web.models;


import com.icesoft.faces.component.tree.IceUserObject;
import javax.swing.tree.DefaultMutableTreeNode;




//public class NodeUserObject extends IceUserObject {
public class NodeUserObject{

    /**
     * Стиль
     */
    private String styleClass = "";



    private String text = "";

    /**
     * ID элемента
     */
    private String id = "";


    /**
     * Constructor
     *
     * @param defaultMutableTreeNode
     */
    public NodeUserObject(DefaultMutableTreeNode defaultMutableTreeNode) {
  //      super(defaultMutableTreeNode);

        //-- Иконка отчета
  //      setLeafIcon("../../../xmlhttp/css/xp/css-images/tree_document.gif");
        //-- Иконка папки отчетов (свернутая)
 //       setBranchContractedIcon("../../../xmlhttp/css/xp/css-images/tree_folder_closed.gif");
        //-- Иконка папки отчетов (развернутая)
   //     setBranchExpandedIcon("../../../xmlhttp/css/xp/css-images/tree_folder_open.gif");
        //-- Развернуть вершину
   //     setExpanded(true);
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getChildCount() {
        return 0;
    }

 //   public boolean getIsLeaf() {
 //      return isLeaf();
 //   }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
