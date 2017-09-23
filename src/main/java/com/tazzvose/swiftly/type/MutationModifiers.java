package com.tazzvose.swiftly.type;

public enum MutationModifiers{
  MUTATING,
  NONMUTATING;

  public static boolean isModifier(String text){
    try{
      get(text);
      return true;
    } catch(Exception ignored){
      return false;
    }
  }

  public static MutationModifiers get(String text){
    return valueOf(text.toUpperCase());
  }
}
