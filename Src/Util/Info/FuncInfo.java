package Compile.Src.Util.Info;

import java.util.ArrayList;
@lombok.Getter
@lombok.Setter
public class FuncInfo extends BaseInfo {
    TypeInfo functype;
    ArrayList<TypeInfo> params;
    public FuncInfo(String name,TypeInfo type,TypeInfo... params)
    {
        super(name);
        this.functype=type;
        for(var parm:params)
        {
            this.params.add(parm);
        }
    }

    @Override
    public String str()
    {
        String ret=functype.str()+" "+getName()+"(";
        for(int i=0;i<params.size();++i)
        {
            ret+=params.get(i).str();
            if(i!=params.size()-1)
            {
                ret+=", ";
            }
        }
        ret+=");";
        return ret;
    }
}
