package parserElements;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TFunType implements IType {
    
    private static final Map<List<IType>, TFunType> instances = new HashMap<List<IType>,TFunType>();

    private List<IType> paramsType;

    private IType returnType;

    private TFunType(List<IType> paramsType, IType returnType){
        this.paramsType = paramsType;
        this.returnType = returnType;
    }

    public IType getReturnType(){
        return returnType;
    }

    public List<IType> getParamsType(){
        return paramsType;
    }

    public static final IType getInstance(List<IType> types, IType type){
        types.add(type);
        TFunType instance = instances.get(types);
        if(instance==null){
            instance = new TFunType(types, type);
            instances.put(types, instance);
        }
        return instance;
    }

	@Override
	public String getType() {
        String concat = new String();
		for (IType iType : paramsType) {
            concat = concat.length()>0 ? concat + "x" + iType.getType() : iType.getType(); 
        }
        return concat + "2" + returnType;
	}

	@Override
	public String getTypeShortName() {
		String concat = new String();
		for (IType iType : paramsType) {
            concat = concat.length()>0 ? concat + "x" + iType.getType() : iType.getType(); 
        }
        return concat + "2" + returnType;
	}
}