package parserElements;

public class TVoid implements IType {
    
    private static final TVoid instance = new TVoid();

    private TVoid(){}

    public static final IType getInstance(){
        return instance;
    }
}