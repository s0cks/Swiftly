package com.tazzvose.swiftly.ast;

public interface AstNode{
  public void visit(AstNodeVisitor vis);
  public default void visitChildren(AstNodeVisitor vis){}
}