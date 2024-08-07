package Compile.Src.Util.ScopeUtil;

import Compile.Src.Util.Info.*;

import java.util.TreeMap;

// @lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class LoopScope extends BaseScope {
    private int loopCnt;

    public LoopScope(BaseScope parent, BaseInfo info) {
        super(parent, info);
        this.loopCnt = 0;
    }
}
