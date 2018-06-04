import org.apache.commons.collections.IteratorUtils;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import java.io.Serializable;
import java.util.*;

public class LocationNodeImpl implements MutableTreeNode, Serializable {

        LocationNodeImpl parent;
        List<LocationNodeImpl> children;
        String name;
        String type;
        Integer population;

        public LocationNodeImpl(String name, String type, Integer population, LocationNodeImpl[] children) {
            this.name = name;
            this.type = type;
            this.population = population;

            this.children = new ArrayList<LocationNodeImpl>(Arrays.asList(children));

            for (LocationNodeImpl t : children) {
                t.setupParent(this);
            }
        }

        public LocationNodeImpl(String name, String type, Integer population, Collection<LocationNodeImpl> children) {
            this.name = name;
            this.type = type;
            this.population = population;

            this.children = new ArrayList<LocationNodeImpl>(children);

            for (LocationNodeImpl t : children) {
                t.setupParent(this);
            }
        }

        public LocationNodeImpl(String name, String type, int population) {
            this.name = name;
            this.type = type;
            this.population = population;
        }

        public TreeNode getChildAt(int i) {
            if (children == null) return null;
            return children.get(i);
        }

        private boolean childrenSet() {
            return children != null && children.size() > 0;
        }

        public int getChildCount() {
            if (childrenSet())
                return children.size();
            else
                return 0;
        }

        public TreeNode getParent() {
            return parent;
        }

        public int getIndex(TreeNode treeNode) {
            return children.indexOf(treeNode);
        }

        public boolean getAllowsChildren() {
            return children != null;
        }

        public boolean isLeaf() {
            return getChildCount() == 0;
        }

        // Only to be used at inital construction as this
        // is not a mutable tree.
        public void setupParent(LocationNodeImpl parent) {
            this.parent = parent;
        }

        public Enumeration children() {
            if (children == null)
                return IteratorUtils.asEnumeration(IteratorUtils.emptyIterator());
            return IteratorUtils.asEnumeration(children.iterator());
        }

        public Integer getPopulation() {
            return population;
        }

        public void setPopulation(Integer population) {
            this.population = population;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void insert(MutableTreeNode mutableTreeNode, int i) {
            mutableTreeNode.setParent(this);
            children.add(i, (LocationNodeImpl)mutableTreeNode);
        }

        public void remove(int i) {
            children.remove(i);
        }

        public void remove(MutableTreeNode mutableTreeNode) {
            children.remove(mutableTreeNode);
        }

        public void setUserObject(Object o) {
            // Not required for any ace:tree functionality
            throw new UnsupportedOperationException();
        }

        public void removeFromParent() {
            if (parent != null)
                parent.remove(this);
        }

        public void setParent(MutableTreeNode mutableTreeNode) {
            parent = (LocationNodeImpl) mutableTreeNode;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            LocationNodeImpl that = (LocationNodeImpl) o;

            if (!name.equals(that.name)) return false;
            if (!population.equals(that.population)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = name.hashCode();
            result = 31 * result + population.hashCode();
            return result;
        }
    }
