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
    public void compile(Env<FrameComp> env, CodeBlock comp) {
        //TODO
    }

    @Override
    public IType typecheker(Env<IType> env) {
        return ref.typecheker(env);
    }

}