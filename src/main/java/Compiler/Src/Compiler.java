package Compiler.Src;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.management.RuntimeErrorException;

import org.antlr.v4.runtime.*;

import Compiler.Src.AST.*;
import Compiler.Src.AST.Node.*;
import Compiler.Src.Codegen.IRCodegen;
import Compiler.Src.Grammer.*;
import Compiler.Src.IR.IRBuilder;
import Compiler.Src.IR.Node.IRNode;
import Compiler.Src.IR.Node.IRRoot;
import Compiler.Src.Semantic.*;
import Compiler.Src.Util.Error.*;
import Compiler.Src.Util.MxErrorListener;

public class Compiler {
    public static void main(String[] args) throws IOException {
        try {
            CharStream input = CharStreams.fromStream(new FileInputStream("src/test/mx/input.mx"));// new FileInputStream("src/test/mx/input.mx")
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
            IRNode irProgram = new IRBuilder().visit((ASTRoot) astProgram);
            // new IRCodegen().visit((IRRoot) irProgram);
            var output = new PrintStream(new FileOutputStream("src/test/mx/output.ll"));
            output.println(irProgram);
            output.close();
        } catch (BaseError e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        // System.out.println("Compile Successfully");
    }
}
