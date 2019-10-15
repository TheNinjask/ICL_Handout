public class ASTBool implements ASTNode{
    
    boolean val;

    public ASTBool(boolean val){
        this.val = val;
    }

    public IValue eval(Env env){
        return new VBool(val);
    }
}