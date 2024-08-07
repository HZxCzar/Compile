package Compile.Src.Util.Error;
@lombok.Getter
@lombok.Setter
public class SBCError {
    private final String content;
    public SBCError(){
        content = "";
    }
    public void append(String msg){
        content += msg;
    }
}
