package com.tazzvose.swiftly.parser;

public final class SourceRegion{
  private final int column;
  private final int row;

  public SourceRegion(int row, int column){
    this.row = row;
    this.column = column;
  }

  public int getRow(){
    return this.row;
  }

  public int getColumn(){
    return this.column;
  }

  @Override
  public String toString(){
    return "SourceRegion{" +
           "column=" + this.column +
           ", row=" + this.row +
           '}';
  }
}