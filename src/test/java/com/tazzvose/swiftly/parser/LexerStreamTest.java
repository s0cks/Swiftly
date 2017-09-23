package com.tazzvose.swiftly.parser;

import org.junit.Test;

public class LexerStreamTest{
  @Test
  public void nextToken()
  throws Exception{
    try(LexerStream lexer = new LexerStream(System.class.getResourceAsStream("/Test.swift"))){
      Token next;
      while((next = lexer.nextToken()).getKind() != TokenKind.EOF){
        System.out.println(next);
      }
    }
  }
}