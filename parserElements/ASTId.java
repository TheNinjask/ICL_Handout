package parserElements;

import compilerElements.CodeBlock;

public class ASTId implements ASTNode {

    String val;

    public ASTId(String val) {
        this.val = val;
    }

    public IValue eval(Env<IValue> env) {
        return env.find(val);
    }

    @Override
    public void compile(Env<String> env, CodeBlock comp) {
        comp.emit("aload 4");
        env.find(val, comp.getCurrentFrame(), comp);
    }
}