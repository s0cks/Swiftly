package com.tazzvose.swiftly.parser;

import com.tazzvose.swiftly.ast.AstNode;
import com.tazzvose.swiftly.ast.BinaryExprNode;
import com.tazzvose.swiftly.ast.LiteralNode;
import com.tazzvose.swiftly.ast.StoreFieldNode;
import com.tazzvose.swiftly.type.AccessModifiers;
import com.tazzvose.swiftly.type.MutationModifiers;
import com.tazzvose.swiftly.type.SwiftBoolean;
import com.tazzvose.swiftly.type.SwiftClass;
import com.tazzvose.swiftly.type.SwiftField;
import com.tazzvose.swiftly.type.SwiftProgram;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

public final class ParserStream
implements Closeable{
  private final LexerStream lexer;
  private final List<AccessModifiers> accessModifiers = new LinkedList<>();
  private final List<MutationModifiers> mutationModifiers = new LinkedList<>();
  private SwiftProgram program = null;

  public ParserStream(InputStream input){
    this.lexer = new LexerStream(input);
  }

  private void unexpected(Token token){
    throw new IllegalStateException("Unexpected " + token);
  }

  private Token expect(TokenKind kind, Token token){
    if(token.getKind() != kind) unexpected(token);
    return token;
  }

  private EnumSet<MutationModifiers> getMutationModifiers(){
    if(this.mutationModifiers.isEmpty()) return null;
    EnumSet<MutationModifiers> mutModifiers = EnumSet.copyOf(this.mutationModifiers);
    this.mutationModifiers.clear();
    return mutModifiers;
  }

  private EnumSet<AccessModifiers> getAccessModifiers(){
    if(this.accessModifiers.isEmpty()) return null;
    EnumSet<AccessModifiers> accModifiers = EnumSet.copyOf(this.accessModifiers);
    this.accessModifiers.clear();
    return accModifiers;
  }

  private AstNode parseUnaryExpr(SwiftClass cls)
  throws IOException{
    Token next;
    switch((next = this.lexer.nextToken()).getKind()){
      case LIT_TRUE: return new LiteralNode(SwiftBoolean.TRUE);
      case LIT_FALSE: return new LiteralNode(SwiftBoolean.FALSE);
      default: break;
    }
    this.unexpected(next);
    return null;
  }

  private AstNode parseBinaryExpr(SwiftClass cls)
  throws IOException{
    AstNode left = this.parseUnaryExpr(cls);
    Token next = null;
    while((next = this.lexer.nextToken()).isBinaryExpr()){
      left = new BinaryExprNode(next.getKind(), left, this.parseBinaryExpr(cls));
    }
    this.lexer.pushBack(next);
    return left;
  }

  private void parseFieldList(SwiftClass cls, boolean mutable)
  throws IOException{
    Token next;
    do{
      next = this.expect(TokenKind.IDENTIFIER, this.lexer.nextToken());
      String ident = next.getText();
      next = this.expect(TokenKind.COLON, this.lexer.nextToken());
      next = this.expect(TokenKind.IDENTIFIER, this.lexer.nextToken());
      SwiftClass type = this.program.getDefinedClass(next.getText());

      AstNode value = null;
      if((next = this.lexer.nextToken()).getKind() == TokenKind.EQUAL){
        value = this.parseBinaryExpr(cls);
      }

      if(!mutable && value == null) throw new IllegalStateException("Mutable fields need an initializer");
      SwiftField field = mutable ?
                         cls.defineMutField(this.getAccessModifiers(), this.getMutationModifiers(), ident, type) :
                         cls.defineImmutField(this.getAccessModifiers(), this.getMutationModifiers(), ident, type);

      if(value != null){
        cls.getConstructor()
          .getBlock()
          .addChild(new StoreFieldNode(field, value));
      }

      if((next = this.lexer.nextToken()).getKind() == TokenKind.COMMA){
        continue;
      } else if(next.getKind() == TokenKind.SEMICOLON){
        return;
      }

      this.unexpected(next);
    } while(true);
  }

  private void parseClassDefinition(SwiftClass cls)
  throws IOException{
    Token next = this.expect(TokenKind.LBRACE, this.lexer.nextToken());
    while((next = this.lexer.nextToken()).getKind() != TokenKind.RBRACE){
      if(next.isAccessModifier()){
        this.accessModifiers.add(AccessModifiers.get(next.getText()));
        continue;
      } else if(next.isMutationModifier()){
        this.mutationModifiers.add(MutationModifiers.get(next.getText()));
        continue;
      }

      switch(next.getKind()){
        case LET:{
          this.parseFieldList(cls, false);
          break;
        }
        case VAR:{
          this.parseFieldList(cls, true);
          break;
        }
      }
    }
  }

  public SwiftProgram parse()
  throws IOException{
    SwiftProgram program = this.program = new SwiftProgram();

    Token next;
    while((next = this.lexer.nextToken()).getKind() != TokenKind.EOF){
      if(next.isAccessModifier()){
        this.accessModifiers.add(AccessModifiers.get(next.getText()));
        continue;
      } else if(next.isMutationModifier()){
        this.mutationModifiers.add(MutationModifiers.get(next.getText()));
        continue;
      }

      switch(next.getKind()){
        case CLASS:{
          next = this.expect(TokenKind.IDENTIFIER, this.lexer.nextToken());
          String ident = next.getText();
          SwiftClass cls = new SwiftClass(this.getAccessModifiers(), this.getMutationModifiers(), ident);
          if(!program.defineClass(cls)){
            throw new IllegalStateException("Class " + cls.getName() + " already defined");
          }
          this.parseClassDefinition(cls);
          break;
        }
        case EOF: return program;
        default: this.unexpected(next);
      }
    }

    return program;
  }

  @Override
  public void close()
  throws IOException{
    this.lexer.close();
  }
}