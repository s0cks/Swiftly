package com.tazzvose.swiftly.type;

import java.util.LinkedList;
import java.util.List;

public final class SwiftProgram{
  private final List<SwiftClass> classes = new LinkedList<>();

  public SwiftProgram(){
    this.defineClass(SwiftClass.OBJECT_CLASS);
    this.defineClass(SwiftClass.STRING_CLASS);
    this.defineClass(SwiftClass.BOOLEAN_CLASS);
  }

  public boolean isDefined(String name){
    return classes.stream()
      .anyMatch((cls)->cls.getName().equals(name));
  }

  public boolean defineClass(SwiftClass cls){
    if(this.isDefined(cls.getName())) return false;
    this.classes.add(cls);
    return true;
  }

  public SwiftClass getDefinedClass(String name){
    return this.classes.stream()
      .filter((cls)->cls.getName().equals(name))
      .findFirst().orElse(null);
  }
}