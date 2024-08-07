package Compiler.Src.Util.Error;

@lombok.Getter
@lombok.Setter
public class SMCError {
    private final String content;

    public SMCError() {
        content = "";
    }

    public SMCError(String msg) {
        content = msg;
    }

    public void append(String msg) {
        content += msg;
    }

    public void append(SMCError msg)
    {
        content+=msg.getContent();
    }
}
