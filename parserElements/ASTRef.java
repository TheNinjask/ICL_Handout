package parserElements;

import compilerElements.CodeBlock;
import compilerElements.FrameComp;

public class ASTRef implements ASTNode {

    ASTNode ref;

    public ASTRef(ASTNode ref) {
        this.ref = ref;
    }

    public IValue eval(Env<IValue> env) {
        return new VRef(ref.eval(env));
    }

    @Override
    public void compile(Env<FrameComp> env, CodeBlock comp) {
        //TODO
        //comp.emit(String.format("sipush %s", val ? 1 : 0));
    }

    @Override
    public IType typecheker(Env<IType> env) {
        return TRef.getInstance(ref.typecheker(env));
    }
}