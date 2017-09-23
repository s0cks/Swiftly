package com.tazzvose.swiftly.ast;

import com.tazzvose.swiftly.parser.TokenKind;

public final class BinaryExprNode
implements AstNode{
  private final TokenKind kind;
  private final AstNode left;
  private final AstNode right;

  public BinaryExprNode(TokenKind kind, AstNode left, AstNode right){
    this.kind = kind;
    this.left = left;
    this.right = right;
  }

  public TokenKind getKind(){
    return this.kind;
  }

  public AstNode getLeft(){
    return this.left;
  }

  public AstNode getRight(){
    return this.right;
  }

  @Override
  public void visit(AstNodeVisitor vis){
    vis.visitBinaryExprNode(this);
  }

  @Override
  public void visitChildren(AstNodeVisitor vis){
    this.getLeft().visit(vis);
    this.getRight().visit(vis);
  }
}