package com.tazzvose.swiftly.ast;

public final class ReturnNode
implements AstNode{
  private final AstNode value;

  public ReturnNode(AstNode value){
    this.value = value;
  }

  public AstNode getValue(){
    return this.value;
  }

  @Override
  public void visit(AstNodeVisitor vis){
    vis.visitReturnNode(this);
  }

  @Override
  public void visitChildren(AstNodeVisitor vis){
    this.value.visit(vis);
  }
}