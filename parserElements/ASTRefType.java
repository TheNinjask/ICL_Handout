package parserElements;

import compilerElements.CodeBlock;
import compilerElements.FrameComp;

public class ASTRefType implements ASTNode {

    ASTNode type;

    public ASTRefType(ASTNode type) {
        this.type = type;
    }

    public IValue eval(Env<IValue> env) {
        return null;
    }

    @Override
    public void compile(Env<FrameComp> env, CodeBlock comp) {
    }

    @Override
    public IType typecheker(Env<IType> env) {
        return TRef.getInstance(type.typecheker(env));
    }

}