package Compiler.Src.Util.Error;

@lombok.Getter
@lombok.Setter
public class ASTError {
    private final String content;

    public ASTError() {
        content = "";
    }

    public ASTError(String msg) {
        content = msg;
    }

    public void append(String msg) {
        content += msg;
    }

    public void append(ASTError msg) {
        content += msg.getContent();
    }
}
