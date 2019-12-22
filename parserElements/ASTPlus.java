package parserElements;

import compilerElements.CodeBlock;
import compilerElements.FrameComp;
import parserExceptions.TypeError;

public class ASTPlus implements ASTNode {
    ASTNode t1;
    ASTNode t2;

    public ASTPlus(ASTNode t1, ASTNode t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    public IValue eval(Env<IValue> env) {
        IValue v1 = t1.eval(env);
        String error = v1.getClass().getSimpleName();
        if (v1 instanceof VInt) {
            IValue v2 = t2.eval(env);
            if (v2 instanceof VInt) {
                return new VInt(((VInt) v1).getval() + ((VInt) v2).getval());
            }
            error = v2.getClass().getSimpleName();
        }
        throw new TypeError(String.format("Illegal arguments (%s) to + operator", error));
    }

    @Override
    public void compile(Env<FrameComp> env, Env<IType> type, CodeBlock comp) {
        typecheker(type);
        t1.compile(env, type, comp);
        t2.compile(env, type, comp);
        comp.emit("iadd");
    }

    @Override
    public IType typecheker(Env<IType> env) {
        IType left = t1.typecheker(env);
        IType right = t2.typecheker(env);
        if(left.equals(TInt.getInstance()) && right.equals(TInt.getInstance()))
            return TInt.getInstance();
        throw new TypeError("Illegal type to + operator!");
    }

}