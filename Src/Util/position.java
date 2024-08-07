package Compiler.Src.Util;

public class position {
    private int row, column;
    public position(int row, int col) {
        this.row = row;
        this.column = col;
    }
    public String str() {
        return "row: " + row + " | column: " + column;
    }
}
