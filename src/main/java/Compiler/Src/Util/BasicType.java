package Compiler.Src.Util;

import Compiler.Src.Util.Info.*;

public interface BasicType {

    // BaseType
    TypeInfo voidType = new TypeInfo("void", 0);
    TypeInfo intType = new TypeInfo("int", 0);
    TypeInfo boolType = new TypeInfo("bool", 0);
    TypeInfo stringType = new TypeInfo("string", 0);
    TypeInfo nullType = new TypeInfo("null", 0);
    TypeInfo thisType = new TypeInfo("this", 0);
    // TypeInfo fstringType = new TypeInfo("fstring", 0);
    // TypeInfo constarrayType = new TypeInfo("constarray", 0);

    // BaseFunc
    FuncInfo printFunc = new FuncInfo("print", voidType, stringType);
    FuncInfo printlnFunc = new FuncInfo("println", voidType, stringType);
    FuncInfo printIntFunc = new FuncInfo("printInt", voidType, intType);
    FuncInfo printlnIntFunc = new FuncInfo("printlnInt", voidType, intType);
    FuncInfo getStringFunc = new FuncInfo("getString", stringType);
    FuncInfo getIntFunc = new FuncInfo("getInt", intType);
    FuncInfo toStringFunc = new FuncInfo("toString", stringType, intType);

    FuncInfo arraySize = new FuncInfo("size",intType);
    FuncInfo stringLengthFunc = new FuncInfo("length", intType);
    FuncInfo stringSubstringFunc = new FuncInfo("substring", stringType, intType, intType);
    FuncInfo stringParseintFunc = new FuncInfo("parseInt", intType);
    FuncInfo stringOrdFunc = new FuncInfo("ord", intType, intType);
    

    FuncInfo[] BaseFuncs = { printFunc, printlnFunc, printIntFunc, printlnIntFunc, getStringFunc, getIntFunc,
        toStringFunc };

    
    //BaseClass
    ClassInfo intClass = new ClassInfo("int");
    ClassInfo boolClass = new ClassInfo("bool");
    ClassInfo stringClass = new ClassInfo("string", stringLengthFunc, stringSubstringFunc, stringParseintFunc, stringOrdFunc);
    
    
    ClassInfo[] BaseClasses = { intClass, boolClass, stringClass };
}
