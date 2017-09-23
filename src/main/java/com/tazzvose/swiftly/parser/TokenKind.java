package com.tazzvose.swiftly.parser;

public enum TokenKind{
  DOT('.'),
  LBRACE('{'),
  RBRACE('}'),
  LPAREN('('),
  RPAREN(')'),
  LBRACKET('['),
  RBRACKET(']'),
  COLON(':'),
  SEMICOLON(';'),
  LT('<'),
  GT('>'),
  UNDERSCORE('_'),
  BANG('!'),
  QUESTION('?'),
  AT('@'),
  AND('&'),
  SUB('-'),
  EQUAL('='),
  OR('|'),
  DIV('/'),
  ADD('+'),
  MUL('*'),
  MOD('%'),
  CARET('^'),
  TILDE('~'),
  COMMA(','),
  // Keywords
  CLASS("class"),
  WHITE("while"),
  FOR("for"),
  LET("let"),
  VAR("var"),
  REPEAT("repeat"),
  IF("if"),
  ELSE("else"),
  SWITCH("switch"),
  CASE("case"),
  DEFAULT("default"),
  BREAK("break"),
  CONTINUE("continue"),
  RETURN("return"),
  THROW("throw"),
  DEFER("defer"),
  DO("do"),
  CATCH("catch"),
  STRUCT("struct"),
  CONVENIENCE("convenience"),
  DYNAMIC("dynamic"),
  FINAL("final"),
  INFIX("infix"),
  LAZY("lazy"),
  OPTIONAL("optional"),
  OVERRIDE("override"),
  POSTFIX("postfix"),
  PREFIX("prefix"),
  REQUIRED("required"),
  STATIC("static"),
  UNOWNED("unowned"),
  SAFE("safe"),
  UNSAFE("unsafe"),
  WEAK("weak"),
  PRIVATE("private"),
  PUBLIC("public"),
  FILEPRIVATE("fileprivate"),
  INTERNAL("internal"),
  OPEN("open"),
  MUTATING("mutating"),
  NONMUTATING("nonmutating"),
  // Literals
  LIT_NUMBER,
  LIT_STRING,
  LIT_TRUE("true"),
  LIT_FALSE("false"),
  LIT_CHARACTER,
  // Misc
  IDENTIFIER,
  EOF('\0');

  private TokenKind(char c){
    Token.defineSymbol(c, this);
  }

  private TokenKind(String kw){
    Token.defineKeyword(kw, this);
  }

  private TokenKind(){}
}