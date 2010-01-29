package weigl.stgr.model.gui;

import java.util.Iterator;

import weigl.stgr.controller.CommandType;
import weigl.stgr.model.ICommand;
import weigl.stgr.model.StgrCall;
import weigl.stgr.model.StgrCommand;
import weigl.stgr.model.StgrCommandBlock;
import weigl.stgr.model.StgrIfElse;
import weigl.stgr.model.StgrModel;
import weigl.stgr.model.StgrRepeat;
import weigl.stgr.model.StgrSwitch;
import weigl.stgr.model.StgrWhile;

public class StgrModelTransformer
{
  
  public TreeNodeCommandBlock buildTree(StgrModel model)
  {
    TreeNodeCommandBlock block = new TreeNodeCommandBlock(null);
    
    StgrCommandBlock rootBlock = model.getCode();
    build(block, rootBlock);    
    return block;
  }
  
  private void build(TreeNodeCommandBlock root, StgrCommandBlock block)
  {
    Iterator<ICommand> i = block.iterator();
    while (i.hasNext())
    {
      ICommand c = i.next();
      build(root, c);
    }
  }
  
  private void build(TreeNodeCommandBlock root, StgrIfElse block)
  {
    TreeNodeCommandBlock ifBlock, elseBlock;
    ifBlock = new TreeNodeCommandBlock(root);
    elseBlock = new TreeNodeCommandBlock(root);
    
    ifBlock.setType(CommandType.IF);
    elseBlock.setType(CommandType.ELSE);
    
    build(ifBlock, block.getIf());
    build(ifBlock, block.getElse());

  }
  
  private void build(TreeNodeCommandBlock root, StgrSwitch block)
  {
    
  }
  
  private void build(TreeNodeCommandBlock root, StgrCommand block)
  {
    
  }
  
  private void build(TreeNodeCommandBlock root, StgrCall block)
  {
    
  }
  
  private void build(TreeNodeCommandBlock root, StgrRepeat block)
  {
    
  }
  
  private void build(TreeNodeCommandBlock root, StgrWhile block)
  {
    
  }
  
  private void build(TreeNodeCommandBlock root, ICommand block)
  {
    System.err.println("Not supported ICommand.");
  }
}
