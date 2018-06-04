import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.icefaces.ace.model.tree.NodeState;
import org.icefaces.ace.model.tree.NodeStateCreationCallback;
import org.icefaces.ace.model.tree.NodeStateMap;
import org.icefaces.util.JavaScriptRunner;

@ManagedBean(name= MainBean.BEAN_NAME)
@SessionScoped
public class MainBean implements Serializable {
    public static final String BEAN_NAME = "mainBean";
    public String getBeanName() { return BEAN_NAME; }
    private List<LocationNodeImpl> treeRoots = TreeDataFactory.getTreeRoots();
    private NodeStateMap stateMap;

    private transient NodeStateCreationCallback contractProvinceInit = new TreeNodeStateCreationCallback();

    public List<LocationNodeImpl> getTreeRoots() {
        return treeRoots;
    }

    public void print(String text) {
        JavaScriptRunner.runScript(FacesContext.getCurrentInstance(),
                "alert('"+text+"');");
    }

    public NodeStateMap getStateMap() {
        return stateMap;
    }

    public void setStateMap(NodeStateMap stateMap) {
        this.stateMap = stateMap;
    }

    public NodeStateCreationCallback getContractProvinceInit() {
        return contractProvinceInit;
    }

    public void setContractProvinceInit(NodeStateCreationCallback contractProvinceInit) {
        this.contractProvinceInit = contractProvinceInit;
    }

    /* Proxy method to avoid JBossEL accessing stateMap like map for method invocations */
    public List getSelected() {
        if (stateMap == null) return Collections.emptyList();
        return stateMap.getSelected();
    }

    private static class TreeNodeStateCreationCallback implements NodeStateCreationCallback, Serializable {
        public NodeState initializeState(NodeState newState, Object node) {
            LocationNodeImpl loc = (LocationNodeImpl) node;
            if (loc.getType().equals("country"))
                newState.setExpanded(true);
            return newState;
        }
    }
}