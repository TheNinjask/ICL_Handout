package parserElements;

import compilerElements.CodeBlock;
import compilerElements.FrameComp;
import parserExceptions.TypeError;

public class ASTDeRef implements ASTNode {

    ASTNode ref;

    public ASTDeRef(ASTNode ref) {
        this.ref = ref;
    }

    public IValue eval(Env<IValue> env) {
        IValue res = ref.eval(env);
        String error = res.getClass().getSimpleName();
        if (res instanceof VRef) {
            return ((VRef) res).content;
        }
        throw new TypeError(String.format("Illegal argument (%s) to ! operator", error));
    }

    @Override
    public void compile(Env<FrameComp> env, Env<IType> type, CodeBlock comp) {
        IType typing = typecheker(type);
        ref.compile(env, type, comp);
        comp.emitRefGet(typing.getType(), typing.getTypeShortName());
    }

    @Override
    public IType typecheker(Env<IType> env) {
        IType refer = ref.typecheker(env);
        if(refer instanceof TRef) 
            return ((TRef)refer).getReferType();
        throw new TypeError("Illegal type to ! operator");
    }

}