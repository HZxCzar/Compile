package Compiler.Src.Util.Info;
@lombok.Getter
@lombok.Setter
public class TypeInfo extends BaseInfo {
    private int depth;
    private boolean defined;

    public TypeInfo(String name, int depth) {
        super(name);
        this.depth = depth;
        if (name.equals("int") || name.equals("void") || name.equals("bool") || name.equals("string")) {
            this.defined = true;
        } else {
            this.defined = false;
        }
    }

    @Override
    public String str() {
        return getName() + "[]".repeat(depth);
    }

    @Override
    public boolean equals(BaseInfo rhs)
    {
        if(!(rhs instanceof TypeInfo))
        {
            return false;
        }
        if(this.getName()==rhs.getName() && this.getDepth()==((TypeInfo)rhs).getDepth())
        {
            return true;
        }
        return false;
    }
}
