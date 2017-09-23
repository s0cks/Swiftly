package com.tazzvose.swiftly.type;

import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

public final class SwiftClass
implements SwiftType{
  public static final SwiftClass OBJECT_CLASS = new SwiftClass(null, null, "Object", null);
  public static final SwiftClass VOID_CLASS = new SwiftClass(null, null, "Void", OBJECT_CLASS);
  public static final SwiftClass STRING_CLASS = new SwiftClass(null, null, "String", OBJECT_CLASS);
  public static final SwiftClass BOOLEAN_CLASS = new SwiftClass(null, null, "Boolean", OBJECT_CLASS);

  private final EnumSet<AccessModifiers> accessModifiers;
  private final EnumSet<MutationModifiers> mutationModifiers;
  private final String name;
  private final SwiftClass parent;
  private final List<SwiftField> fields;
  private final List<SwiftFunction> functions;

  public SwiftClass(EnumSet<AccessModifiers> accessModifiers, EnumSet<MutationModifiers> mutationModifiers, String name, SwiftClass parent){
    this.accessModifiers = accessModifiers;
    this.mutationModifiers = mutationModifiers;
    this.name = name;
    this.parent = parent;
    this.fields = new LinkedList<>();
    this.functions = new LinkedList<>();
    this.functions.add(new SwiftFunction(null, null, this.name, this, VOID_CLASS));
  }

  public SwiftClass(EnumSet<AccessModifiers> accessModifiers, EnumSet<MutationModifiers> mutationModifiers, String name){
    this(accessModifiers, mutationModifiers, name, null);
  }

  public EnumSet<AccessModifiers> getAccessModifiers(){
    return this.accessModifiers;
  }

  public EnumSet<MutationModifiers> getMutationModifiers(){
    return this.mutationModifiers;
  }

  public String getName(){
    return this.name;
  }

  public SwiftClass getParent(){
    return this.parent;
  }

  public List<SwiftField> getFields(){
    return Collections.unmodifiableList(this.fields);
  }

  public SwiftField getField(String name){
    return this.fields.stream()
      .filter((f)->f.getName().equals(name))
      .findFirst().orElse(null);
  }

  public boolean hasField(String name){
    return this.fields.stream()
      .anyMatch((f)->f.getName().equals(name));
  }

  public SwiftField defineMutField(EnumSet<AccessModifiers> accMods, EnumSet<MutationModifiers> mutMods, String name, SwiftClass type){
    if(this.hasField(name)) return null;
    SwiftField field = new SwiftField(accMods, mutMods, name, type, this, true);
    this.fields.add(field);
    return field;
  }

  public SwiftField defineImmutField(EnumSet<AccessModifiers> accMods, EnumSet<MutationModifiers> mutMods, String name, SwiftClass type){
    if(this.hasField(name)) return null;
    SwiftField field = new SwiftField(accMods, mutMods, name, type, this, false);
    this.fields.add(field);
    return field;
  }

  public List<SwiftFunction> getFunctions(){
    return Collections.unmodifiableList(this.functions);
  }

  public SwiftFunction getFunction(String name){
    return this.functions.stream()
      .filter((f)->f.getName().equals(name))
      .findFirst().orElse(null);
  }

  public boolean hasFunction(String name){
    return this.functions.stream()
      .anyMatch((f)->f.getName().equals(name));
  }

  public SwiftFunction getConstructor(){
    return this.getFunction(this.name);
  }

  @Override
  public String toString(){
    return "SwiftClass{" +
           "accessModifiers=" + accessModifiers +
           ", mutationModifiers=" + mutationModifiers +
           ", name='" + name + '\'' +
           ", parent=" + parent +
           '}';
  }
}