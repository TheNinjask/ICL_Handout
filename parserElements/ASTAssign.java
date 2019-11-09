package parserElements;

import compilerElements.CodeBlock;

public class ASTAssign implements ASTNode {

    String id;
    ASTNode value;

    public ASTAssign(String id, ASTNode value) {
        this.id = id;
        this.value = value;
    }

    public IValue eval(Env env) {
        IValue res = value.eval(env);
        env.assoc(id, res);
        return res;
    }

    @Override
    public void compile(Env env, CodeBlock comp) {
        //TODO 
    }
}