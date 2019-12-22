package parserElements;

import compilerElements.CodeBlock;
import compilerElements.FrameComp;
import parserExceptions.TypeError;

public class ASTGetField implements ASTNode {

    ASTNode struct;
    String key;

    public ASTGetField(ASTNode struct, String key) {
        this.struct = struct;
        this.key = key;
    }

    public IValue eval(Env<IValue> env) {
        IValue res = struct.eval(env);
        String error = res.getClass().getSimpleName();
        if (res instanceof VStruct) {
            return ((VStruct) res).getValue(key);
        }
        throw new TypeError(String.format("Illegal argument (%s) to . operator", error));
    }

    @Override
    public void compile(Env<FrameComp> env, Env<IType> type, CodeBlock comp) {
    }

    @Override
    public IType typecheker(Env<IType> env) {
        return null;
    }

}