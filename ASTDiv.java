public class ASTDiv implements ASTNode{
    ASTNode t1;
    ASTNode t2;

    public ASTDiv(ASTNode t1, ASTNode t2){
        this.t1 = t1;
        this.t2 = t2;
    }

    public IValue eval(Env env){
        IValue v1 = t1.eval(env);
        if(v1 instanceof VInt){
            IValue v2 = t2.eval(env);
            if(v2 instanceof VInt){
                return new VInt(((VInt)v1).getval() / ((VInt)v2).getval());
            }
        }
        throw new TypeError("illegal arguments to / operator");
    }
}