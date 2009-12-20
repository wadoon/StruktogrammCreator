package weigl.stgr.model.gui;


import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

public class TreeNodeCommandBlock extends TreeNodeCommand implements MutableTreeNode 
{
  private List<MutableTreeNode> m_children = new LinkedList<MutableTreeNode>();

  public TreeNodeCommandBlock(TreeNodeCommandBlock parent)
  {
    super(parent);   
  }

  public void insert(MutableTreeNode child, int index)
  {
    m_children.add(index, child);
  }


  public void remove(int index)
  {
    m_children.remove(index);
  }


  public void remove(MutableTreeNode node)
  {
    m_children.remove(node);
  }

  public Enumeration children()
  {
    return Collections.enumeration(m_children);
  }

  public boolean getAllowsChildren()
  {
    return true;
  }

  public TreeNode getChildAt(int childIndex)
  {
    return m_children.get(childIndex);
  }


  public int getChildCount()
  {
    return m_children.size();
  }

  public int getIndex(TreeNode node)
  {
    return m_children.indexOf(node);
  }

  public boolean isLeaf()
  {
    return true;
  }
}
