package Compiler.Src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import org.antlr.v4.runtime.*;

import Compiler.Src.ASM.ASMBuilder;
import Compiler.Src.ASM.ASMBuilder_Naive;
import Compiler.Src.ASM.ASMBuilder_Basic;
import Compiler.Src.ASM.ASMBuilder_Formal;
import Compiler.Src.ASM.Node.ASMNode;
import Compiler.Src.AST.*;
import Compiler.Src.AST.Node.*;
import Compiler.Src.Codegen.IRCodegen;
import Compiler.Src.Grammer.*;
import Compiler.Src.IR.IRBuilder;
import Compiler.Src.IR.Node.IRNode;
import Compiler.Src.IR.Node.IRRoot;
import Compiler.Src.OPT.IROptimize;
import Compiler.Src.Semantic.*;
import Compiler.Src.Util.Error.*;
import Compiler.Src.Util.MxErrorListener;

public class Compiler {
    public static void main(String[] args) throws IOException {
        try {
            CharStream input = CharStreams.fromStream(new FileInputStream("src/test/mx/input.mx"));
            // new FileInputStream("src/test/mx/input.mx")
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

            var output1 = new PrintStream(new FileOutputStream("src/test/mx/output_old.ll"));
            // new FileOutputStream("src/test/mx/output_old.ll")
            output1.println(irProgram);
            output1.close();

            ASMNode asmProgram = new ASMBuilder_Naive().visit((IRRoot) irProgram);
            var codegenOutput = new PrintStream(new FileOutputStream("bin/test.s"));
            codegenOutput.println(asmProgram);
            codegenOutput.close();

            ASMNode asmProgram4 = new ASMBuilder_Formal().visit((IRRoot) irProgram);
            var codegenOutput4 = new PrintStream(new FileOutputStream("bin/basic/test.s"));
            codegenOutput4.println(asmProgram4);
            codegenOutput4.close();

            new IROptimize().visit((IRRoot) irProgram);
            // new IRCodegen().visit((IRRoot) irProgram);
            var output2 = new PrintStream(new FileOutputStream("src/test/mx/output_new.ll"));
            // new FileOutputStream("src/test/mx/output_new.ll")
            output2.println(irProgram);
            output2.close();
            // System.out.println(irProgram);
            ASMNode asmProgram2 = new ASMBuilder_Formal().visit((IRRoot) irProgram);
            var codegenOutput2 = new PrintStream(new FileOutputStream("bin/opt/test.s"));
            codegenOutput2.println(asmProgram2);
            codegenOutput2.close();

            ASMNode asmProgram3 = new ASMBuilder().visit((IRRoot) irProgram);
            var codegenOutput3 = new PrintStream(new FileOutputStream("bin/compare/test.s"));
            codegenOutput3.println(asmProgram3);
            codegenOutput3.close();

            String filePath = "builtin.s"; // 文件路径

            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
            System.out.println(asmProgram2);
        } catch (BaseError e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        // System.out.println("Compile Successfully");
    }
}
