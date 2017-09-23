package com.tazzvose.swiftly.parser;

import com.tazzvose.swiftly.type.AccessModifiers;
import com.tazzvose.swiftly.type.MutationModifiers;

import java.util.HashMap;
import java.util.Map;

public final class Token{
  private static final Map<String, TokenKind> keywords = new HashMap<>();
  private static final Map<Character, TokenKind> symbols = new HashMap<>();

  static void defineKeyword(String txt, TokenKind kind){
    keywords.put(txt, kind);
  }

  static void defineSymbol(char c, TokenKind kind){
    symbols.put(c, kind);
  }

  public static TokenKind getSymbol(char c){
    return symbols.get(c);
  }

  public static TokenKind getKeyword(String txt){
    return keywords.get(txt);
  }

  public static boolean isSymbol(char c){
    return symbols.containsKey(c);
  }

  public static boolean isKeyword(String txt){
    return keywords.containsKey(txt);
  }

  private final TokenKind kind;
  private final String text;
  private final SourceRegion region;

  public Token(TokenKind kind, String text, SourceRegion region){
    this.kind = kind;
    this.text = text;
    this.region = region;
  }

  public TokenKind getKind(){
    return this.kind;
  }

  public String getText(){
    return this.text;
  }

  public SourceRegion getRegion(){
    return this.region;
  }

  public boolean isAccessModifier(){
    return AccessModifiers.isModifier(this.text);
  }

  public boolean isMutationModifier(){
    return MutationModifiers.isModifier(this.text);
  }

  public boolean isBinaryExpr(){
    switch(this.kind){
      case LT:
      case GT:
      case ADD:
      case SUB:
      case DIV:
      case MUL:
      case MOD: return true;
      default: return false;
    }
  }

  @Override
  public String toString(){
    return "Token{" +
           "kind=" + this.kind +
           ", text='" + this.text + '\'' +
           ", region=" + this.region +
           '}';
  }
}