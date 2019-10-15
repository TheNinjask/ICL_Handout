public class ASTId implements ASTNode{
    
    String val;

    public ASTId(String val){
        this.val = val;
    }

    public IValue eval(Env env){
        return env.find(val);
    }
}