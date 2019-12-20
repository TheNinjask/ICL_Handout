package parserElements;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TFunType implements IType {
    
    private static final Map<List<IType>, TFunType> instances = new HashMap<List<IType>,TFunType>();

    private TFunType(){}

    public static final IType getInstance(List<IType> types, IType type){
        types.add(type);
        TFunType instance = instances.get(types);
        if(instance==null){
            instance = new TFunType();
            instances.put(types, instance);
        }
        return instance;
    }
}