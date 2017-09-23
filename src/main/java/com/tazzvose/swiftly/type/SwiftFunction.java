package com.tazzvose.swiftly.type;

import com.tazzvose.swiftly.ast.BlockNode;

import java.util.EnumSet;

public final class SwiftFunction
implements SwiftType{
  private final EnumSet<AccessModifiers> accessModifiers;
  private final EnumSet<MutationModifiers> mutationModifiers;
  private final String name;
  private final SwiftClass owner;
  private final SwiftClass resultType;
  private final BlockNode block;

  public SwiftFunction(EnumSet<AccessModifiers> accessModifiers, EnumSet<MutationModifiers> mutationModifiers, String name, SwiftClass owner, SwiftClass resultType){
    this.accessModifiers = accessModifiers;
    this.mutationModifiers = mutationModifiers;
    this.name = name;
    this.owner = owner;
    this.resultType = resultType;
    this.block = new BlockNode();
  }

  public SwiftClass getOwner(){
    return this.owner;
  }

  public SwiftClass getResultType(){
    return this.resultType;
  }

  public EnumSet<MutationModifiers> getMutationModifiers(){
    return this.mutationModifiers;
  }

  public EnumSet<AccessModifiers> getAccessModifiers(){
    return this.accessModifiers;
  }

  public String getName(){
    return this.name;
  }

  public BlockNode getBlock(){
    return this.block;
  }
}