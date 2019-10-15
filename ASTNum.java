public class ASTNum implements ASTNode{
    
    int val;

    public ASTNum(int val){
        this.val = val;
    }

    public IValue eval(Env env){
        return new VInt(val);
    }
}