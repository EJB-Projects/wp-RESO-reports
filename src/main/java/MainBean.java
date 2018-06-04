import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;


@ManagedBean(name = MainBean.BEAN_NAME)
@ViewScoped
public class MainBean implements Serializable {
    public static final String BEAN_NAME = "mainBean";

    public String getBeanName() {
        return BEAN_NAME;
    }

    private TreeNode root;
    private TreeNode root2;
    private TreeNode root3;
    private String rootName;

    TreeConverter treeConverter = new TreeConverter();
    TestRecursive testRecursive = new TestRecursive();

    @PostConstruct
    public void init() {


        root3 = testRecursive.Do();

        root2 = treeConverter.doConvert();
        root = root3;

    }

    public TreeNode getRoot() {
        return root;
    }
}