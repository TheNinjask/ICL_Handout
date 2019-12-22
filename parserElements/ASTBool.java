package parserElements;

import compilerElements.CodeBlock;
import compilerElements.FrameComp;

public class ASTBool implements ASTNode {

    boolean val;

    public ASTBool(boolean val) {
        this.val = val;
    }

    public IValue eval(Env<IValue> env) {
        return new VBool(val);
    }

    @Override
    public void compile(Env<FrameComp> env, Env<IType> type, CodeBlock comp) {
        comp.emit(String.format("sipush %s", val ? 1 : 0));
    }

    @Override
    public IType typecheker(Env<IType> env) {
        return TBool.getInstance();
    }

}