import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.TreeNode;


@ManagedBean(name = MainBean.BEAN_NAME)
@ViewScoped
public class MainBean implements Serializable {
    public static final String BEAN_NAME = "mainBean";
    public String getBeanName() {
        return BEAN_NAME;
    }
    private TreeNode root;

    TreeParse treeParse = new TreeParse();

    @PostConstruct
    public void init() {
        root = treeParse.Do();
    }

    public TreeNode getRoot() {
        return root;
    }




}