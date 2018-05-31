package ru.tuneit.example.bean.style;

import javax.faces.model.SelectItem;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 * @author nicola
 * @version 1.0 (Jun 20, 2011)
 */
@ManagedBean (name = "styleBean")
@SessionScoped
public class StyleBean implements Serializable {
    
    private static final String RIME = "RIME";
    private static final String XP = "XP";
    private static final String ROYALE = "ROYALE";

    private static final String BASE_CSS_PATH = "/resources/css/";
    private static final String BASE_JS_PATH = "/resources/js/";
    private static final String BASE_IMAGES_PATH = "/resources/images/";

    @ManagedProperty("RIME")
    private String currentStyle;

    // Available style list.
    private ArrayList<SelectItem> styleList;
    
    private HashMap<String, StylePath> styleMap;

    public StyleBean() {
    }

    @PostConstruct
    public void init() {
        // Initialize the style list.
        styleList = new ArrayList<SelectItem>(3);
        styleList.add(new SelectItem(RIME, RIME));
        styleList.add(new SelectItem(XP, XP));
        styleList.add(new SelectItem(ROYALE, ROYALE));

        styleMap = new HashMap<String, StylePath>(3);
        styleMap.put(RIME, new StylePath("/xmlhttp/css/rime/rime.css", "/xmlhttp/css/rime/css-images/"));
        styleMap.put(XP, new StylePath("/xmlhttp/css/xp/xp.css", "/xmlhttp/css/xp/css-images/"));
        styleMap.put(ROYALE, new StylePath("/xmlhttp/css/royale/royale.css", "/xmlhttp/css/royale/css-images/"));
    }

    
    /**
     * Gets the folder for css
     * @return path folder
     */
    public String getBaseCssPath() {
	return BASE_CSS_PATH;
    }

    /**
     * Gets the folder for images
     * @return path folder
     */
    public String getBaseImagePath() {
	return BASE_IMAGES_PATH;
    }

    /**
     *  Gets the folder for JavaScrpt
     * @return path folder
     */
    public String getBaseJsPath() {
	return BASE_JS_PATH;
    }

    /**
     * Gets the current style.
     *
     * @return current style
     */
    public String getCurrentStyle() {
        return currentStyle;
    }

    /**
     * Sets the current style of the application to one of the predetermined
     * themes.
     *
     * @param currentStyle name of new style
     */
    public void setCurrentStyle(String currentStyle) {
        this.currentStyle = currentStyle;
    }

    /**
     * Gets the html needed to insert a valid css link tag.
     *
     * @return the tag information needed for a valid css link tag
     */
    public String getStyle() {
        return "<link rel=\"stylesheet\" type=\"text/css\" href=\"" + ((StylePath) styleMap.get(currentStyle)).getCssPath() + "\"></link>";
    }

    /**
     * Gets the image directory to use for the selectinputdate and tree
     * theming.
     *
     * @return image directory used for theming
     */
    public String getThemeImageDirectory() {
        return ((StylePath) styleMap.get(currentStyle)).getImageDirPath();
    }

    /**
     * Gets the css file path.
     *
     * @return css file used for theming
     */
    public String getThemeCssFilePath() {
        return ((StylePath) styleMap.get(currentStyle)).getCssPath();
    }

    /**
     * Gets a list of available theme names that can be applied.
     *
     * @return available theme list
     */
    public List<SelectItem> getStyleList() {
        return styleList;
    }

    /**
     * Utility class to manage different cssPath and imageDirPath name
     */
    public class StylePath implements Serializable {
        private String cssPath;
        private String imageDirPath;

        public StylePath() {

        }

        public StylePath(String cssPath, String imageDirPath) {
            this.cssPath = cssPath;
            this.imageDirPath = imageDirPath;
        }

        public String getCssPath() {
            return cssPath;
        }

        public void setCssPath(String cssPath) {
            this.cssPath = cssPath;
        }

        public String getImageDirPath() {
            return imageDirPath;
        }

        public void setImageDirPath(String imageDirPath) {
            this.imageDirPath = imageDirPath;
        }
    }
}
