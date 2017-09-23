package com.tazzvose.swiftly.parser;

import com.tazzvose.swiftly.type.SwiftClass;
import com.tazzvose.swiftly.type.SwiftField;
import com.tazzvose.swiftly.type.SwiftProgram;
import org.junit.Test;

public class ParserStreamTest{
  @Test
  public void testParse()
  throws Exception{
    try(ParserStream parser = new ParserStream(System.class.getResourceAsStream("/Test.swift"))){
      SwiftProgram program = parser.parse();
      SwiftClass cls = program.getDefinedClass("Test");
      for(SwiftField field : cls.getFields()){
        System.out.println(field.getName());
      }
    }
  }
}