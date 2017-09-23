package com.tazzvose.swiftly.ast;

import com.tazzvose.swiftly.type.SwiftField;

public final class LoadFieldNode
implements AstNode{
  private final SwiftField field;

  public LoadFieldNode(SwiftField field){
    this.field = field;
  }

  public SwiftField getField(){
    return this.field;
  }

  @Override
  public void visit(AstNodeVisitor vis){
    vis.visitLoadFieldNode(this);
  }
}