package ru.reso.wp.report.models.base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * По ходу дела это какой-то класс для организации структуры хранения папок. То есть для понимания, кто папа, кто мама (шучу), кто child.
 *
 * @rewrite Anton Romanov [ROMAB] 07.05.2018 10:37
 *
 */

public abstract class Folder implements java.io.Serializable {


    /**
     * ИД
     */
    protected int id;

    /**
     * Название папки
     */
    protected String name;

    /**
     * ID родительской папки
     */
    protected int parentID;

    /**
     * Уровень папки в дереве
     */
    protected int level;


    /**
     * Constructor
     *
     * Интересно то, что он бросает SQLException, то есть видимо мы в шаге от связки с БД, то есть, видимо, от структуры отчетов, которую мы получаем из БД.
     *
     * @param rs
     * @throws SQLException
     */
    public Folder(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        name = rs.getString("name");
        parentID = rs.getInt("parentid");
        level = rs.getInt("level");
    }

    /**
     * Constructor
     *
     * А этот конструктор уже без SQLException. То видимо какую-то папку мы можем создать передав ей прямо БДшный результ, она его распарсит и создат сущность,
     * а какую-то папку мы можем сделать типа "вручную". То есть задавая параметры папки через передаваемые аргументы.
     *
     * @param id
     * @param name
     * @param parentID
     * @param level
     */
    public Folder(int id, String name, int parentID, int level) {
        this.id = id;
        this.name = name;
        this.parentID = parentID;
        this.level = level;
    }


    /** =========================================================================================================================================================
     *
     *
     *   Дальще идут Геттеры/Сеттеры - это не особо интересно.
     *
     * ==========================================================================================================================================================*/


    /**
     * ИД папки
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * ИД папки
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Название папки
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Название папки
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * ИД родительской папки
     *
     * @return
     */
    public int getParentID() {
        return parentID;
    }

    /**
     * ИД родтельской папки
     *
     * @param parentID
     */
    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    /**
     * Уровень папки
     *
     * @return
     */
    public int getLevel() {
        return level;
    }

    /**
     * Список вложенных папок
     *
     * @return
     */
    public abstract ArrayList getSubFolders();

    /**
     * Список вложенных папок
     *
     * @param subFolders
     */
    public abstract void setSubFolders(ArrayList subFolders);

    /**
     * Поиск папки по ИД
     *
     * @param a
     * @param id
     * @return
     */
    public static Folder getFolderByID(ArrayList<Folder> a, int id) {
        Folder result = null;
        Iterator i = a.iterator();

        while ((i.hasNext()) && (result == null)) {
            Folder f = (Folder) i.next();
            if (f.getId() == id) {
                result = f;
            } else {
                result = getFolderByID(f.getSubFolders(), id);
            }
        }
        return result;
    }

    /**
     * Считает количество подпапок. Используется, когда папки хранятся линейной
     * структурой, а не иерархической
     *
     * @param a
     * @param id
     * @return
     */
    public static int getFolderCountChildren(ArrayList<Folder> a, int id) {
        int result = 0;
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).getParentID() == id) {
                result++;
            }
        }
        return result;
    }

}
