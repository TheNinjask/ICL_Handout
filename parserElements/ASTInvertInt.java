package parserElements;

import compilerElements.CodeBlock;
import parserExceptions.TypeError;

public class ASTInvertInt implements ASTNode {

    ASTNode value;

    public ASTInvertInt(ASTNode value) {
        this.value = value;
    }

    public IValue eval(Env env) {
        IValue res = value.eval(env);
        String error = res.getClass().getSimpleName();
        if (res instanceof VInt) {
            return new VInt(-((VInt) res).getval());
        }
        throw new TypeError(String.format("Illegal argument (%s) to - operator", error));
    }

    @Override
    public void compile(Env env, CodeBlock comp) {
        // TODO Auto-generated method stub
    }
}