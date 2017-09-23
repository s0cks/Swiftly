package com.tazzvose.swiftly.ast;

import com.tazzvose.swiftly.type.SwiftField;

public final class StoreFieldNode
implements AstNode{
  private final SwiftField field;
  private final AstNode value;

  public StoreFieldNode(SwiftField field, AstNode value){
    this.field = field;
    this.value = value;
  }

  public SwiftField getField(){
     return this.field;
  }

  public AstNode getValue(){
    return this.value;
  }

  @Override
  public void visit(AstNodeVisitor vis){
    vis.visitStoreFieldNode(this);
  }

  @Override
  public void visitChildren(AstNodeVisitor vis){
    this.getValue().visit(vis);
  }
}