package com.tazzvose.swiftly.parser;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public final class LexerStream
implements Closeable{
  static{
    //TODO: Figure out how to remove this hack
    // This forces the JVM to load the TokenKind class before this class, otherwise this parser wouldn't work properly

    try{
      Class.forName("com.tazzvose.swiftly.parser.TokenKind");
    } catch(ClassNotFoundException ignored){
      // Fallthrough
    }
  }

  private final InputStream input;
  private final StringBuilder buffer = new StringBuilder();
  private char peekChar = '\0';
  private Token peekToken = null;
  private int row = 1;
  private int column = 1;

  public LexerStream(InputStream input){
    if(input == null) throw new NullPointerException("input == null");
    this.input = input;
  }

  private char peekChar()
  throws IOException{
    if(this.peekChar != '\0') return this.peekChar;
    int read = this.input.read();
    if(read == -1) return '\0';
    return this.peekChar = (char) read;
  }

  private char nextChar()
  throws IOException{
    if(this.peekChar != '\0'){
      char res = this.peekChar;
      this.peekChar = '\0';
      return res;
    }

    int read = this.input.read();
    if(read == -1) return '\0';
    char res = (char) read;
    this.column++;
    switch(res){
      case '\n':{
        this.column = 1;
        this.row++;
      }
      default:{
        return res;
      }
    }
  }

  private char nextRealChar()
  throws IOException{
    char next;
    while(Character.isWhitespace(next = this.nextChar()));
    return next;
  }

  private SourceRegion getSourceRegion(){
    return new SourceRegion(this.row, this.column);
  }

  private Token getToken(TokenKind kind, String txt){
    return new Token(kind, txt, this.getSourceRegion());
  }

  private Token getToken(TokenKind kind, char c){
    return new Token(kind, c + "", this.getSourceRegion());
  }

  private boolean isHexDigit(char c){
    return (c == 'x' || c == 'X') ||
           (c >= 'A' && c <= 'F') ||
           (c >= 'a' && c <= 'f');
  }

  private String consume(){
    String result = this.buffer.toString();
    this.buffer.delete(0, this.buffer.length());
    return result;
  }

  public void pushBack(Token token){
    this.peekToken = token;
  }

  public Token nextToken()
  throws IOException{
    if(this.peekToken != null){
      Token res = this.peekToken;
      this.peekToken = null;
      return res;
    }

    char next = this.nextRealChar();
    if(Token.isSymbol(next)){
      return this.getToken(Token.getSymbol(next), next);
    } else if(Character.isDigit(next)){
      this.buffer.append(next);
      while(Character.isDigit(next = this.peekChar()) || this.isHexDigit(next)){
        this.buffer.append(this.nextChar());
      }
      return this.getToken(TokenKind.LIT_NUMBER, this.consume());
    } else{
      this.buffer.append(next);
      while(!Token.isSymbol(next = this.peekChar()) && !Character.isWhitespace(next)){
        this.buffer.append(this.nextChar());
        if(Token.isKeyword(this.buffer.toString())){
          return this.getToken(Token.getKeyword(this.buffer.toString()), this.consume());
        }
      }
      return this.getToken(TokenKind.IDENTIFIER, this.consume());
    }
  }

  @Override
  public void close()
  throws IOException{
    this.input.close();
  }
}