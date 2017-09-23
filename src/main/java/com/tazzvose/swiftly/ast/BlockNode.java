package com.tazzvose.swiftly.ast;

import java.util.LinkedList;
import java.util.List;

public final class BlockNode
implements AstNode{
  private final List<AstNode> children = new LinkedList<>();

  public void addChild(AstNode node){
    this.children.add(node);
  }

  @Override
  public void visit(AstNodeVisitor vis){
    vis.visitBlockNode(this);
  }

  @Override
  public void visitChildren(AstNodeVisitor vis){
    this.children.forEach((n)->n.visit(vis));
  }
}