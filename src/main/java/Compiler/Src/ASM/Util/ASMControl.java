package Compiler.Src.ASM.Util;

import java.util.ArrayList;
import java.util.TreeMap;

import Compiler.Src.ASM.Entity.ASMPhysicalReg;
import Compiler.Src.ASM.Entity.ASMReg;
import Compiler.Src.ASM.Entity.ASMVirtualReg;
import Compiler.Src.ASM.Node.ASMNode;
import Compiler.Src.ASM.Node.Inst.Arithmetic.ASMArithR;
import Compiler.Src.ASM.Node.Inst.Memory.ASMStore;
import Compiler.Src.ASM.Node.Inst.Presudo.ASMLi;
import Compiler.Src.ASM.Node.Stmt.ASMBlock;
import Compiler.Src.ASM.Node.Stmt.ASMStmt;

@lombok.Getter
@lombok.Setter
public class ASMControl {
    protected ASMCounter counter;
    protected BuiltInRegs regs;

    // mem2reg
    protected TreeMap<String, ASMBlock> label2block;
    protected ArrayList<ASMBlock> funcBlocks;
    protected int CreateblockCnt;

    protected ASMControl() {
        this.counter = new ASMCounter();
        this.regs = new BuiltInRegs();
        this.CreateblockCnt = 0;
    }

    public ASMPhysicalReg getArgReg(int i) {
        switch (i) {
            case 0:
                return regs.getA0();
            case 1:
                return regs.getA1();
            case 2:
                return regs.getA2();
            case 3:
                return regs.getA3();
            case 4:
                return regs.getA4();
            case 5:
                return regs.getA5();
            case 6:
                return regs.getA6();
            case 7:
                return regs.getA7();
            default:
                return null;
        }
    }

    public ASMStmt StoreAt(ASMReg reg,int offset) {
        var InstList=new ASMStmt();
        if(offset>2047 || offset<-2048){
            InstList.addInst(new ASMLi(regs.getT0(),offset));
            InstList.addInst(new ASMArithR("add",regs.getT0(),regs.getSp(),regs.getT0()));
            InstList.addInst(new ASMStore("sw",reg,0,regs.getT0()));
        }
        else{
            InstList.addInst(new ASMStore("sw",reg,offset,regs.getSp()));
        }
        return InstList;
    }

    public ASMStmt LoadAt(ASMReg reg,int offset) {
        var InstList=new ASMStmt();
        if(offset>2047 || offset<-2048){
            InstList.addInst(new ASMLi(regs.getT0(),offset));
            InstList.addInst(new ASMArithR("add",regs.getT0(),regs.getSp(),regs.getT0()));
            InstList.addInst(new ASMStore("lw",reg,0,regs.getT0()));
        }
        else{
            InstList.addInst(new ASMStore("lw",reg,offset,regs.getSp()));
        }
        return InstList;
    }
}
