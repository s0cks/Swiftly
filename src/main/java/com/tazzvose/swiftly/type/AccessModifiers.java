package com.tazzvose.swiftly.type;

public enum AccessModifiers{
  PRIVATE,
  FILEPRIVATE,
  INTERNAL,
  PUBLIC,
  OPEN;

  public static boolean isModifier(String text){
    try{
      get(text);
      return true;
    } catch(Exception ignored){
      return false;
    }
  }

  public static AccessModifiers get(String text){
    return valueOf(text.toUpperCase());
  }
}