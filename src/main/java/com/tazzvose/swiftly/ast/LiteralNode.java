package com.tazzvose.swiftly.ast;

import com.tazzvose.swiftly.type.SwiftType;

public final class LiteralNode
implements AstNode{
  private final SwiftType value;

  public LiteralNode(SwiftType value){
    this.value = value;
  }

  @Override
  public void visit(AstNodeVisitor vis){
    vis.visitLiteralNode(this);
  }
}
