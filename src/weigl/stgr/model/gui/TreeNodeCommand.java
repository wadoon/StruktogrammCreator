package weigl.stgr.model.gui;

import java.util.Enumeration;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import weigl.stgr.controller.CommandType;
import weigl.stgr.model.StgrCommand;

public class TreeNodeCommand implements MutableTreeNode 
{
  private CommandType m_type = CommandType.DO;
  private TreeNodeCommandBlock m_parent;  
  private String m_label;
  
  public TreeNodeCommand(TreeNodeCommandBlock parent)
  {
    super();
    m_parent = parent;
  }

  public void insert(MutableTreeNode child, int index) 
  {
    //
  }

  public void remove(int index)
  {
    //
  }

  public void remove(MutableTreeNode node)
  {
    //
  }

  public void removeFromParent()
  {
      m_parent.remove(this);  
  }

  public void setParent(MutableTreeNode newParent)
  {
    m_parent = (TreeNodeCommandBlock) newParent;
  }

  public void setUserObject(Object object)
  {
    m_label = object.toString(); 
  }

  public Enumeration children()
  {
    return null;
  }

  public boolean getAllowsChildren()
  {
    return false;
  }

  public TreeNode getChildAt(int childIndex)
  {
    return null;
  }

  public int getChildCount()
  {
    return 0;
  }

  public int getIndex(TreeNode node)
  {
    return 0;
  }

  public TreeNode getParent()
  {
    return m_parent;
  }

  public boolean isLeaf()
  {
    return false;
  }

  public String getLabel()
  {
    return m_label;
  }

  public void setLabel(String label)
  {
    m_label = label;
  }

  public CommandType getType()
  {
    return m_type;
  }

  public void setType(CommandType type)
  {
    m_type = type;
  }
}
