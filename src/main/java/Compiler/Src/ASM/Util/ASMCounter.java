package Compiler.Src.ASM.Util;

import java.util.TreeMap;

import Compiler.Src.ASM.Entity.ASMReg;

public class ASMCounter {
    public static int funcCount = -1;
    public int allocaCount;
    public TreeMap<String, ASMReg> name2reg;

    public ASMCounter() {
        funcCount++;
        this.allocaCount = 0;
        this.name2reg = new TreeMap<String, ASMReg>();
    }
}
