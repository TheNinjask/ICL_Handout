package parserElements;

import compilerElements.CodeBlock;

public class ASTNum implements ASTNode {

    int val;

    public ASTNum(int val) {
        this.val = val;
    }

    public IValue eval(Env env) {
        return new VInt(val);
    }

    @Override
    public void compile(Env env, CodeBlock comp) {
        comp.emit(String.format("sipush %s", val));
    }
}