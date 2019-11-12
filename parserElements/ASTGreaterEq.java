package parserElements;

import compilerElements.CodeBlock;
import parserExceptions.TypeError;

public class ASTGreaterEq implements ASTNode {
    ASTNode t1;
    ASTNode t2;

    public ASTGreaterEq(ASTNode t1, ASTNode t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    public IValue eval(Env<IValue> env) {
        IValue v1 = t1.eval(env);
        String error = v1.getClass().getSimpleName();
        if (v1 instanceof VInt) {
            IValue v2 = t2.eval(env);
            if (v2 instanceof VInt) {
                return new VBool(((VInt) v1).getval() >= ((VInt) v2).getval());
            }
            error = v2.getClass().getSimpleName();
        }
        throw new TypeError(String.format("Illegal arguments (%s) to >= operator", error));
    }

    @Override
    public void compile(Env<String> env, CodeBlock comp) {
        // TODO Auto-generated method stub
    }
}