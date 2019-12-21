package parserElements;

import java.util.HashMap;
import java.util.Map;

public class TRef implements IType {
    
    private static final Map<IType, TRef> instances = new HashMap<IType,TRef>();

    private IType referType;

    private TRef(IType referType){
        this.referType = referType;
    }

    public IType getReferType(){
        return referType;
    }

    public static final IType getInstance(IType type){
        TRef instance = instances.get(type);
        if(instance==null){
            instance = new TRef(type);
            instances.put(type, instance);
        }
        return instance;
    }

    @Override
    public String getType() {
        return String.format("ref_%s", referType.getType());
    }

    @Override
    public String getTypeShortName() {
        return String.format("ref_%s", referType.getType());
    }
}