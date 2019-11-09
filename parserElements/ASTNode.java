package parserElements;

import compilerElements.CodeBlock;

public interface ASTNode {
    public IValue eval(Env<IValue> env);
    public void compile(Env<String> env, CodeBlock comp);
}