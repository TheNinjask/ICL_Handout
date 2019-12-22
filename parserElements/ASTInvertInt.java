package parserElements;

import compilerElements.CodeBlock;
import compilerElements.FrameComp;
import parserExceptions.TypeError;

public class ASTInvertInt implements ASTNode {

    ASTNode value;

    public ASTInvertInt(ASTNode value) {
        this.value = value;
    }

    public IValue eval(Env<IValue> env) {
        IValue res = value.eval(env);
        String error = res.getClass().getSimpleName();
        if (res instanceof VInt) {
            return new VInt(-((VInt) res).getval());
        }
        throw new TypeError(String.format("Illegal argument (%s) to - operator", error));
    }

    @Override
    public void compile(Env<FrameComp> env, Env<IType> type, CodeBlock comp) {
        typecheker(type);
        value.compile(env, type, comp);
        comp.emit("ineg");
    }

    @Override
    public IType typecheker(Env<IType> env) {
        IType valType = value.typecheker(env);
        if(valType.equals(TInt.getInstance()))
            return TInt.getInstance();
        throw new TypeError("Illegal type for - operator");
    }
    
}