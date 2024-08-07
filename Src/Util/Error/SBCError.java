package Compiler.Src.Util.Error;

@lombok.Getter
@lombok.Setter
public class SBCError {
    private final String content;

    public SBCError() {
        content = "";
    }

    public SBCError(String msg) {
        content = msg;
    }

    public void append(String msg) {
        content += msg;
    }

    public void append(SBCError msg)
    {
        content+=msg.getContent();
    }
}
