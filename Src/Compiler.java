package Compiler.Src;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;

import Compiler.Src.AST.*;
import Compiler.Src.Grammer.*;
import Compiler.Src.Semantic.*;
import Compiler.Src.Util.*;

public class Compiler {
    public static void main(String[] args) throws Exception {
        try{
    var input = CharStreams.fromStream(new FileInputStream("Src/test/mx/input.mx"));
    MxLexer lexer=new MxLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    MxParser parser = new MxParser(tokens);
    ASTNode astProgram = new ASTBuilder().visit(parser.program());
    new SymbolCollector().visit((ASTRoot)astProgram);
    new SemanticChecker().visit((ASTRoot)astProgram);
        }catch(ASTError | SBCError |ScopeError |SMCError e){
            System.err.println(e.getContent());
            System.exit(1);
        }
  }
}
