package parserElements;

import compilerElements.CodeBlock;

public class ASTBool implements ASTNode {

    boolean val;

    public ASTBool(boolean val) {
        this.val = val;
    }

    public IValue eval(Env env) {
        return new VBool(val);
    }

    @Override
    public void compile(Env env, CodeBlock comp) {
        comp.emit(String.format("sipush %s", val ? 1 : 0));
    }
}