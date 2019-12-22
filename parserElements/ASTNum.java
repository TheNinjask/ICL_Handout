package parserElements;

import compilerElements.CodeBlock;
import compilerElements.FrameComp;

public class ASTNum implements ASTNode {

    int val;

    public ASTNum(int val) {
        this.val = val;
    }

    public IValue eval(Env<IValue> env) {
        return new VInt(val);
    }

    @Override
    public void compile(Env<FrameComp> env, Env<IType> type, CodeBlock comp) {
        comp.emit(String.format("sipush %s", val));
    }

    @Override
    public IType typecheker(Env<IType> env) {
        return TInt.getInstance();
    }
}