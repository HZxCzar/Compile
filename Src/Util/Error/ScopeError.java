package Compiler.Src.Util.Error;

@lombok.Getter
@lombok.Setter
public class ScopeError {
    private final String content;

    public ScopeError() {
        content = "";
    }

    public ScopeError(String msg) {
        content = msg;
    }

    public void append(String msg) {
        content += msg;
    }

    public void append(ScopeError msg)
    {
        content+=msg.getContent();
    }
}
