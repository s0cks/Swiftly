package com.tazzvose.swiftly.ast;

public interface AstNodeVisitor{
  public void visitLiteralNode(LiteralNode node);
  public void visitReturnNode(ReturnNode node);
  public void visitBlockNode(BlockNode node);
  public void visitBinaryExprNode(BinaryExprNode node);
  public void visitStoreFieldNode(StoreFieldNode node);
  public void visitLoadFieldNode(LoadFieldNode node);
}