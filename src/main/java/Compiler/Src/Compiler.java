package Compiler.Src;

import java.io.FileInputStream;

import org.antlr.v4.runtime.*;

import Compiler.Src.AST.*;
import Compiler.Src.AST.Node.*;
import Compiler.Src.Grammer.*;
import Compiler.Src.Semantic.*;
import Compiler.Src.Util.Error.*;
import Compiler.Src.Util.MxErrorListener;

public class Compiler {
    public static void main(String[] args) throws Exception {
        try {
            CharStream input = CharStreams.fromStream(new FileInputStream("src/test/mx/input.mx"));
            MxLexer lexer = new MxLexer(input);
            lexer.removeErrorListeners();
            lexer.addErrorListener(new MxErrorListener());
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            MxParser parser = new MxParser(tokens);
            parser.removeErrorListeners();
            parser.addErrorListener(new MxErrorListener());
            ASTNode astProgram = new ASTBuilder().visit(parser.program());
            new SymbolCollector().visit((ASTRoot) astProgram);
            new SemanticChecker().visit((ASTRoot) astProgram);
        } catch (BaseError e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        System.out.println("Compile Successfully");
    }
}
