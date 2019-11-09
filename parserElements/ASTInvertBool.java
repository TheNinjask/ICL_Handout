package parserElements;

import compilerElements.CodeBlock;
import parserExceptions.TypeError;

public class ASTInvertBool implements ASTNode {

    ASTNode value;

    public ASTInvertBool(ASTNode value) {
        this.value = value;
    }

    public IValue eval(Env env) {
        IValue res = value.eval(env);
        String error = res.getClass().getSimpleName();
        if (res instanceof VBool) {
            return new VBool(!((VBool) res).getBool());
        }
        throw new TypeError(String.format("Illegal argument (%s) to ~ operator", error));
    }

    @Override
    public void compile(Env env, CodeBlock comp) {
        // TODO Auto-generated method stub
    }
}