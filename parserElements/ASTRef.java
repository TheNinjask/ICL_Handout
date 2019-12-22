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
    public void compile(Env<FrameComp> env, Env<IType> type, CodeBlock comp) {
        TRef typing = (TRef)typecheker(type);
        comp.emitRef(typing.getReferType().getType(), typing.getReferType().getTypeShortName());
        ref.compile(env, type, comp);
        comp.emitRefPut(typing.getReferType().getType(), typing.getReferType().getTypeShortName());
    }

    @Override
    public IType typecheker(Env<IType> env) {
        return TRef.getInstance(ref.typecheker(env));
    }
}