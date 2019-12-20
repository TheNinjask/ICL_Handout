package parserElements;

import java.util.HashMap;
import java.util.Map;

public class TRef implements IType {
    
    private static final Map<IType, TRef> instances = new HashMap<IType,TRef>();

    private TRef(){}

    public static final IType getInstance(IType type){
        TRef instance = instances.get(type);
        if(instance==null){
            instance = new TRef();
            instances.put(type, instance);
        }
        return instance;
    }
}