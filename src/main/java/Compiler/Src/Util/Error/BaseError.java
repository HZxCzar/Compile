package Compiler.Src.Util.Error;

@lombok.Getter
@lombok.Setter
abstract public class BaseError extends RuntimeException {
    protected String message;
  
    public BaseError(String msg) {
      this.message = msg;
    }
}
