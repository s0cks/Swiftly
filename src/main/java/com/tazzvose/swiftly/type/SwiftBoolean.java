package com.tazzvose.swiftly.type;

public final class SwiftBoolean
implements SwiftType{
  public static final SwiftBoolean TRUE = new SwiftBoolean(true);
  public static final SwiftBoolean FALSE = new SwiftBoolean(false);

  private final boolean value;

  private SwiftBoolean(boolean value){
    this.value = value;
  }

  public boolean getJavaValue(){
    return this.value;
  }
}