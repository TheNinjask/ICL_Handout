package parserElements;

import compilerElements.CodeBlock;
import compilerElements.FrameComp;

public class ASTIntType implements ASTNode {

    public ASTIntType() {}

    public IValue eval(Env<IValue> env) {
        return null;
    }

    @Override
    public void compile(Env<FrameComp> env, CodeBlock comp) {
    }

    @Override
    public IType typecheker(Env<IType> env) {
        return TInt.getInstance();
    }

}