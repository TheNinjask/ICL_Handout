package parserElements;

public class TInt implements IType {
    
    private static final TInt instance = new TInt();

    private TInt(){}

    public static final IType getInstance(){
        return instance;
    }

    @Override
    public String getType() {
        return "I";
    }

    @Override
    public String getTypeShortName() {
        return "int";
    }
}