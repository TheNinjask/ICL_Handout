package parserElements;

import java.util.Map;
import java.util.Map.Entry;

import compilerElements.CodeBlock;
import compilerElements.FrameComp;

public class ASTStruct implements ASTNode {

    Map<String, ASTNode> dicAST;

    public ASTStruct(Map<String, ASTNode> dicAST) {
        this.dicAST = dicAST;
    }

    public IValue eval(Env<IValue> env) {
        VStruct struct = new VStruct();
        for (Entry<String, ASTNode> elem : dicAST.entrySet()) {
            String key = elem.getKey();
            IValue value = elem.getValue().eval(env);
            struct.setKeyValue(key, value);
        }
        return struct;
    }

    @Override
    public void compile(Env<FrameComp> env, Env<IType> type, CodeBlock comp) {
    }

    @Override
    public IType typecheker(Env<IType> env) {
        return null;
    }
}