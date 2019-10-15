public class ASTOr implements ASTNode{
    ASTNode t1;
    ASTNode t2;

    public ASTOr(ASTNode t1, ASTNode t2){
        this.t1 = t1;
        this.t2 = t2;
    }

    public IValue eval(Env env){
        IValue v1 = t1.eval(env);
        if(v1 instanceof VBool){
            IValue v2 = t2.eval(env);
            if(v2 instanceof VBool){
                return new VBool(((VBool)v1).getBool() || ((VBool)v2).getBool());
            }
        }
        throw new TypeError("illegal arguments to || operator");
    }
}