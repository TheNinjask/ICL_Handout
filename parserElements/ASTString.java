package parserElements;

import compilerElements.CodeBlock;
import compilerElements.FrameComp;

public class ASTString implements ASTNode {

    String val;

    public ASTString(String val) {
        this.val = val;
    }

    public IValue eval(Env<IValue> env) {
        return new VString(val);
    }

    @Override
    public void compile(Env<FrameComp> env, CodeBlock comp) {
        //TODO
    }
}