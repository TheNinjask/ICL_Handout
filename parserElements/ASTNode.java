package parserElements;

import compilerElements.CodeBlock;
import compilerElements.FrameComp;

public interface ASTNode {
    public IValue eval(Env<IValue> env);
    public void compile(Env<FrameComp> env, CodeBlock comp);
    //public IType typecheker(Env<IType> env);
}