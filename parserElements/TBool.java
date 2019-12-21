package parserElements;

public class TBool implements IType {
    
    private static final TBool instance = new TBool();

    private TBool(){}

    public static final IType getInstance(){
        return instance;
    }

    @Override
    public String getType() {
        return "I";
    }

    @Override
    public String getTypeShortName() {
        return "bool";
    }
}