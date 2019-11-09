package parserElements;

import compilerElements.CodeBlock;
import parserExceptions.TypeError;

public class ASTAnd implements ASTNode {
    ASTNode t1;
    ASTNode t2;

    public ASTAnd(ASTNode t1, ASTNode t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    public IValue eval(Env env) {
        IValue v1 = t1.eval(env);
        String error = v1.getClass().getSimpleName();
        if (v1 instanceof VBool) {
            IValue v2 = t2.eval(env);
            if (v2 instanceof VBool) {
                return new VBool(((VBool) v1).getBool() && ((VBool) v2).getBool());
            }
            error = v2.getClass().getSimpleName();
        }
        throw new TypeError(String.format("Illegal arguments (%s) to && operator", error));
    }

    @Override
    public void compile(Env env, CodeBlock comp) {
        //TODO
        t1.compile(env, comp);
        t2.compile(env, comp);
        comp.emit("iand");
    }
}