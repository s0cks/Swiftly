package com.tazzvose.swiftly.type;

import java.util.EnumSet;

public final class SwiftField
implements SwiftType{
  private final EnumSet<AccessModifiers> accessModifiers;
  private final EnumSet<MutationModifiers> mutationModifiers;
  private final String name;
  private final SwiftClass type;
  private final SwiftClass owner;
  private final boolean mutable;

  public SwiftField(EnumSet<AccessModifiers> accessModifiers, EnumSet<MutationModifiers> mutationModifiers, String name, SwiftClass type, SwiftClass owner, boolean mutable){
    this.accessModifiers = accessModifiers;
    this.mutationModifiers = mutationModifiers;
    this.name = name;
    this.type = type;
    this.owner = owner;
    this.mutable = mutable;
  }

  public String getName(){
    return this.name;
  }

  public SwiftClass getOwner(){
    return this.owner;
  }

  public SwiftClass getType(){
    return this.type;
  }

  public EnumSet<AccessModifiers> getAccessModifiers(){
    return this.accessModifiers;
  }

  public EnumSet<MutationModifiers> getMutationModifiers(){
    return this.mutationModifiers;
  }
}