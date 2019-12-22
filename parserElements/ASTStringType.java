package parserElements;

import compilerElements.CodeBlock;
import compilerElements.FrameComp;

public class ASTStringType implements ASTNode {

    public ASTStringType() {}

    public IValue eval(Env<IValue> env) {
        return null;
    }

    @Override
    public void compile(Env<FrameComp> env, Env<IType> type, CodeBlock comp) {
    }

    @Override
    public IType typecheker(Env<IType> env) {
        return TVoid.getInstance();
    }

}