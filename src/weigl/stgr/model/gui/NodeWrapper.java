/**
 * 
 */
package weigl.stgr.model.gui;

interface NodeWrapper {
    public int childCount();
    public boolean isLeaf();
    public NodeWrapper getChild(int i);
    public int indexOf(Object child);
}